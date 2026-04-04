package br.com.weavenmc.commons.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.backend.mysql.MySQL;
import br.com.weavenmc.commons.core.backend.mysql.MySQLBackend;
import br.com.weavenmc.commons.core.backend.redis.PubSubListener;
import br.com.weavenmc.commons.core.backend.redis.RedisBackend;
import br.com.weavenmc.commons.core.command.CommandLoader;
import br.com.weavenmc.commons.core.data.punish.PunishHistory;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.velocity.checker.CheckingConnectionAPI;
import br.com.weavenmc.commons.velocity.command.VelocityCommandFramework;
import br.com.weavenmc.commons.velocity.config.YamlConfig;
import br.com.weavenmc.commons.velocity.listeners.JoinListener;
import br.com.weavenmc.commons.velocity.listeners.MessageListener;
import br.com.weavenmc.commons.velocity.listeners.ChatListener;
import br.com.weavenmc.commons.velocity.listeners.ProfileLookupListener;
import br.com.weavenmc.commons.velocity.listeners.ProxyAccountListener;
import br.com.weavenmc.commons.velocity.manager.ProxyManager;
import br.com.weavenmc.commons.velocity.redis.ProxyPubSubHandler;
import br.com.weavenmc.commons.velocity.vpnblocker.Proxy.VPNDetection;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import lombok.Getter;

@Plugin(id = "weavencommons", name = "WeavenCommons", version = "3.1-SNAPSHOT")
@Getter
public class VelocityMain {

	@Getter
	private static VelocityMain instance;
	@Getter
	private static VPNDetection detection;

	private final ProxyServer proxy;
	private final Logger logger;
	private final Path dataDirectory;

	private MySQL mySQL;
	private YamlConfig config;
	private ProxyManager serverManager;
	private ProxyPubSubHandler redisPubSub;
	private PubSubListener pubSubListener;

	private int messagesIndex = 0;
	private String messagesPrefix;
	private List<String> messages;

	@Inject
	public VelocityMain(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
		this.proxy = proxy;
		this.logger = logger;
		this.dataDirectory = dataDirectory;
	}

	@Subscribe
	public void onProxyInitialize(ProxyInitializeEvent event) {
		instance = this;
		loadConfigurations();
		detection = new VPNDetection(getConfig().getString("velocity.vpnblockerkey"));

		WeavenMC.setLogger(new Slf4jToJulLoggerAdapter(logger));
		WeavenMC.setAsynchronousExecutor(new VelocityAsynchronousExecutor(this));
		WeavenMC.setCommonMysql(new MySQLBackend(mySQL));
		WeavenMC.setCommonRedis(new RedisBackend(getConfig().getString("redis.hostname"),
				getConfig().getString("redis.password"), getConfig().getInt("redis.port")));

		MySQLBackend commonMysql = WeavenMC.getCommonMysql();
		try {
			commonMysql.openConnection();
		} catch (SQLException ex) {
			logger.error("Erro ao abrir MySQL", ex);
			proxy.shutdown();
			return;
		}

		WeavenMC.getCommonRedis().openConnection();
		if (!WeavenMC.getCommonRedis().isConnected()) {
			logger.error("Redis connection closed!");
			proxy.shutdown();
			return;
		}

		try {
			WeavenMC.setPunishHistory(new PunishHistory());
			commonMysql.createTablesIfNotExists();
			WeavenMC.getClanCommon().createTableIfNotExists();
		} catch (SQLException ex) {
			logger.error("Erro ao iniciar banco", ex);
			proxy.shutdown();
			return;
		}

		WeavenMC.getAsynchronousExecutor().runAsync(() -> {
			try {
				CheckingConnectionAPI.load();
			} catch (SQLException ex) {
				logger.error("Erro ao carregar proxy checks", ex);
			}
		});

		WeavenMC.getAsynchronousExecutor().runAsync(() -> WeavenMC.getProfileCommon().loadCrackedProfiles());
		WeavenMC.getClanCommon().loadClans();

		WeavenMC.setServerId("network");
		WeavenMC.setServerType(ServerType.NETWORK);

		proxy.getChannelRegistrar().register(new LegacyChannelIdentifier("Velocity"));
		proxy.getChannelRegistrar().register(new LegacyChannelIdentifier("Commons"));

		pubSubListener = new PubSubListener(redisPubSub = new ProxyPubSubHandler(this), WeavenMC.SERVER_INFO_CHANNEL,
				WeavenMC.HG_SERVER_INFO_CHANNEL, WeavenMC.SW_SERVER_INFO_CHANNEL, WeavenMC.BROADCAST_SERVER_STARTING,
				WeavenMC.CLAN_FIELD_UPDATE, WeavenMC.GROUP_MANAGEMENT_CHANNEL);
		WeavenMC.getAsynchronousExecutor().runAsync(pubSubListener);

		serverManager = new ProxyManager(this);
		proxy.getEventManager().register(this, new JoinListener(this));
		proxy.getEventManager().register(this, new MessageListener(this));
		proxy.getEventManager().register(this, new ChatListener());
		proxy.getEventManager().register(this, new ProfileLookupListener(this));
		proxy.getEventManager().register(this, new ProxyAccountListener());
		new CommandLoader(new VelocityCommandFramework(this))
				.loadCommandsFromPackage("br.com.weavenmc.commons.velocity.command.register");

		messagesPrefix = config.getString("velocity.messages.prefix");
		messages = config.getStringList("velocity.messages.broadcast");
		proxy.getScheduler().buildTask(this, this::broadcastMessage).repeat(2, TimeUnit.MINUTES).delay(2, TimeUnit.MINUTES)
				.schedule();
	}

	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event) {
		try {
			if (WeavenMC.getCommonMysql() != null) {
				WeavenMC.getCommonMysql().closeConnection();
			}
		} catch (SQLException ex) {
			logger.error("Erro ao fechar mysql", ex);
		}
		if (WeavenMC.getCommonRedis() != null) {
			WeavenMC.getCommonRedis().closeConnection();
		}
	}

	private void broadcastMessage() {
		if (messages == null || messages.isEmpty()) {
			return;
		}
		if (messagesIndex >= messages.size()) {
			messagesIndex = 0;
		}
		String message = messagesPrefix.replace("&", "§") + messages.get(messagesIndex++).replace("&", "§");
		proxy.sendMessage(VelocityText.of(message));
	}

	private void loadConfigurations() {
		Path configPath = dataDirectory.resolve("config.yml");
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.yml")) {
			if (input == null) {
				throw new IllegalStateException("config.yml não encontrado nos recursos");
			}
			YamlConfig.copyDefault(configPath, input);
			config = YamlConfig.fromFile(configPath);
		} catch (IOException ex) {
			throw new IllegalStateException("Falha ao carregar config.yml", ex);
		}

		mySQL = new MySQL(config.getString("mysql.address"), config.getString("mysql.database"),
				config.getString("mysql.username"), config.getString("mysql.password"), config.getInt("mysql.port"));
	}
}
