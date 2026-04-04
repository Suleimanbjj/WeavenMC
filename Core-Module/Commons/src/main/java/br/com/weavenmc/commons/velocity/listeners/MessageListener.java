package br.com.weavenmc.commons.velocity.listeners;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.permission.GroupMessage;
import br.com.weavenmc.commons.core.server.NetworkServer;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import br.com.weavenmc.commons.velocity.event.redis.ProxyRedisMessageEvent;
import br.com.weavenmc.commons.velocity.login.LoginCache;
import br.com.weavenmc.commons.velocity.login.LoginCacheAPI;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class MessageListener {

	private static final LegacyChannelIdentifier CHANNEL_VELOCITY = new LegacyChannelIdentifier("Velocity");
	private static final LegacyChannelIdentifier CHANNEL_COMMONS = new LegacyChannelIdentifier("Commons");

	private final VelocityMain plugin;

	public MessageListener(VelocityMain plugin) {
		this.plugin = plugin;
	}

	@Subscribe
	public void onGroupMessage(ProxyRedisMessageEvent event) {
		if (!event.getChannel().equals(WeavenMC.GROUP_MANAGEMENT_CHANNEL)) {
			return;
		}
		GroupMessage msg = WeavenMC.getGson().fromJson(event.getMessage(), GroupMessage.class);
		Group group = msg.readGroup();
		if (msg.isAdd()) {
			for (Player o : plugin.getProxy().getAllPlayers()) {
				ProxyPlayer pP = ProxyPlayer.getPlayer(o.getUniqueId());
				if (pP == null || !pP.isStaffer() || pP.getServerConnectedType() == ServerType.LOGIN) {
					continue;
				}
				o.sendMessage(VelocityText.of("§6§lGROUPSET§f " + msg.getUserName() + " recebeu o grupo "
						+ group.getTagToUse().getName() + "§f com duração "
						+ (msg.getGroupTime() == -1 ? "§e§lETERNA" : "§e§l" + StringTimeUtils.formatDifference(msg.getGroupTime()))
						+ "§f via requisição de " + msg.getMessageResponse()));
			}
			Player target = plugin.getProxy().getPlayer(msg.getUserName()).orElse(null);
			if (target != null) {
				ProxyPlayer pP = ProxyPlayer.getPlayer(target.getUniqueId());
				if (pP != null) {
					pP.addGroup(msg.readGroup(), msg.getGroupTime());
					pP.organizeGroups();
				}
			}
		} else {
			for (Player o : plugin.getProxy().getAllPlayers()) {
				ProxyPlayer pP = ProxyPlayer.getPlayer(o.getUniqueId());
				if (pP == null || !pP.isStaffer() || pP.getServerConnectedType() == ServerType.LOGIN) {
					continue;
				}
				o.sendMessage(VelocityText.of("§6§lGROUPSET§f " + msg.getUserName() + " teve o grupo "
						+ group.getTagToUse().getName() + "§f removido da conta via requisição de " + msg.getMessageResponse()));
			}
			Player target = plugin.getProxy().getPlayer(msg.getUserName()).orElse(null);
			if (target != null) {
				ProxyPlayer pP = ProxyPlayer.getPlayer(target.getUniqueId());
				if (pP != null) {
					HashMap<Group, Long> map = pP.getGroupById(msg.getGroupId());
					if (map != null && map.keySet().toArray(new Group[] {})[0] == msg.readGroup()) {
						pP.removeGroup(msg.getGroupId());
						pP.organizeGroups();
					} else {
						target.disconnect(VelocityText.of("§4§lERRO§f Ocorreu um erro ao tentar atualizar dados em sua conta!"));
					}
				}
			}
		}
	}

	@Subscribe
	public void onPluginMessageListener(PluginMessageEvent event) {
		if (!event.getIdentifier().equals(CHANNEL_VELOCITY) && !event.getIdentifier().equals(CHANNEL_COMMONS)) {
			return;
		}
		if (!(event.getSource() instanceof ServerConnection) || !(event.getTarget() instanceof Player)) {
			return;
		}
		event.setResult(PluginMessageEvent.ForwardResult.handled());

		Player player = (Player) event.getTarget();
		ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
		String subChannel = in.readUTF();

		if (subChannel.equals("AuthenticationRequest")) {
			String ip = player.getRemoteAddress() instanceof InetSocketAddress
					? ((InetSocketAddress) player.getRemoteAddress()).getHostString()
					: "unknown";
			LoginCache cache = LoginCacheAPI.getCache(ip);
			if (cache != null) {
				cache.addSession(player.getUsername());
			} else {
				LoginCacheAPI.addCache(ip, player.getUsername());
			}

			player.sendMessage(VelocityText.of(
					"§4§lLOGIN§f Você agora sera §c§lAUTOMATICAMENTE AUTENTICADO§f neste nick por §c§l24 HORAS§f caso seu endereço de ip permaneça o mesmo."));

			NetworkServer lobby = plugin.getServerManager().getAvailable(ServerType.LOBBY);
			if (lobby == null) {
				player.sendMessage(VelocityText.of("§4§lERRO§f Nenhum servidor disponivel de §c§lLOBBY§f foi encontrado!"));
				return;
			}
			RegisteredServer server = plugin.getProxy().getServer(lobby.getServerId()).orElse(null);
			if (server == null) {
				player.sendMessage(VelocityText.of("§4§lERRO§f Nenhum servidor disponivel de §c§lLOBBY§f foi encontrado!"));
				return;
			}
			plugin.getProxy().getScheduler().buildTask(plugin, () -> player.createConnectionRequest(server).connect())
					.delay(4, TimeUnit.SECONDS).schedule();
			return;
		}

		if (subChannel.equals("AntiCheatBan")) {
			String reason = in.readUTF();
			plugin.getProxy().getCommandManager().executeAsync(plugin.getProxy().getConsoleCommandSource(),
					"tempban " + player.getUsername() + " 30m " + reason);
			return;
		}

		if (subChannel.equals("FindServer")) {
			ServerType serverType = ServerType.valueOf(in.readUTF());
			RegisteredServer serverInfo = plugin.getServerManager().getAvailableServer(serverType);
			if (serverInfo != null) {
				player.createConnectionRequest(serverInfo).connect();
			} else {
				player.sendMessage(VelocityText
						.of("§4§lERRO §fNenhum servidor disponível de §c§l" + serverType.name() + "§f foi encontrado!"));
			}
			return;
		}

		if (subChannel.equals("FastHG")) {
			RegisteredServer fastHg = plugin.getServerManager().getAvailableServer(ServerType.DOUBLEKITHG);
			if (fastHg != null) {
				player.createConnectionRequest(fastHg).connect();
				return;
			}
			RegisteredServer lobby = plugin.getProxy().getServer("lobby").orElse(null);
			if (lobby == null) {
				lobby = plugin.getServerManager().getAvailableServer(ServerType.LOBBY);
			}
			if (lobby != null) {
				player.createConnectionRequest(lobby).connect();
			} else {
				player.disconnect(VelocityText.of(
						"§4§lDISCONNECTED\n\n§fNenhum servidor disponivel de §c§lDOUBLEKITHG§f ou §c§lLOBBY§f foi encontrado!\n\n§c§lwww.mc-weaven.com.br"));
			}
		}
	}
}
