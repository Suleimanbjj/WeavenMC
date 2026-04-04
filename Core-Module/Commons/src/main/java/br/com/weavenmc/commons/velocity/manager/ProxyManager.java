package br.com.weavenmc.commons.velocity.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.server.NetworkServer;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.event.redis.ProxyRedisMessageEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import lombok.Getter;

@Getter
public class ProxyManager {

	private final VelocityMain plugin;
	private final Map<String, NetworkServer> servers = new ConcurrentHashMap<>();
	private final Map<String, ServerStatus> serversStatus = new ConcurrentHashMap<>();

	public ProxyManager(VelocityMain plugin) {
		this.plugin = plugin;
		plugin.getProxy().getEventManager().register(plugin, this);
		plugin.getProxy().getScheduler().buildTask(plugin, this::tick).repeat(2, TimeUnit.SECONDS).schedule();
	}

	@Subscribe
	public void onRedisPubSub(ProxyRedisMessageEvent event) {
		if (event.getChannel().equals(WeavenMC.BROADCAST_SERVER_STARTING)) {
			return;
		}
		NetworkServer server = null;
		if (event.getChannel().equals(WeavenMC.SERVER_INFO_CHANNEL)) {
			server = NetworkServer.fromJsonString(event.getMessage());
		} else if (event.getChannel().equals(WeavenMC.HG_SERVER_INFO_CHANNEL)) {
			server = NetworkServer.byJsonString(event.getMessage());
		} else if (event.getChannel().equals(WeavenMC.SW_SERVER_INFO_CHANNEL)) {
			server = NetworkServer.fromSkywarsJson(event.getMessage());
		}
		if (server != null) {
			servers.put(server.getServerId(), server);
		}
	}

	public RegisteredServer getAvailableServer(ServerType serverType) {
		NetworkServer server = getAvailable(serverType);
		return server == null ? null : plugin.getProxy().getServer(server.getServerId()).orElse(null);
	}

	public NetworkServer getWeavenServer(String serverId) {
		return servers.get(serverId);
	}

	public NetworkServer getWeavenServer(RegisteredServer server) {
		return server == null ? null : servers.get(server.getServerInfo().getName());
	}

	public NetworkServer getAvailable(ServerType serverType) {
		NetworkServer result = null;
		int max = -1;
		for (NetworkServer server : servers.values()) {
			if (!server.getServerType().equals(serverType)) {
				continue;
			}
			if (!server.canBeSelected()) {
				continue;
			}
			if (getServerStatus(server.getServerId()) == ServerStatus.OFFLINE) {
				continue;
			}
			if (server.getPlayers() > max) {
				max = server.getPlayers();
				result = server;
			}
		}
		return result;
	}

	public ServerStatus getServerStatus(String serverId) {
		return serversStatus.getOrDefault(serverId, ServerStatus.OFFLINE);
	}

	private void tick() {
		pingServers();
		NetworkServer server = new NetworkServer(
				"Velocity",
				-1,
				ServerType.NETWORK,
				1000,
				plugin.getProxy().getPlayerCount(),
				true,
				true,
				(10 * 1000L) + System.currentTimeMillis());
		WeavenMC.getCommonRedis().getJedis().publish(WeavenMC.SERVER_INFO_CHANNEL, server.toJson().toString());
	}

	private void pingServers() {
		for (RegisteredServer server : plugin.getProxy().getAllServers()) {
			server.ping().whenComplete((ping, throwable) -> {
				serversStatus.put(server.getServerInfo().getName(), throwable == null ? ServerStatus.ONLINE : ServerStatus.OFFLINE);
			});
		}
	}

	public enum ServerStatus {
		ONLINE,
		OFFLINE
	}
}
