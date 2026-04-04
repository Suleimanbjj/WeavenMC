package br.com.weavenmc.commons.velocity.command.register;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.command.CommandFramework.Completer;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

public class StaffCommand implements CommandClass {

	public static final HashMap<UUID, Long> reportMute = new HashMap<>();

	@Command(name = "pull", groupToUse = Group.DONO)
	public void pull(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			return;
		}
		if (args.length == 0) {
			sender.sendMessage("Utilize: /pull <server>");
			return;
		}

		RegisteredServer server = VelocityMain.getInstance().getProxy().getServer(args[0]).orElse(null);
		if (server == null) {
			sender.sendMessage("Este servidor não existe");
			return;
		}

		for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			if (online.getCurrentServer().map(sc -> sc.getServerInfo().getName().equalsIgnoreCase(args[0])).orElse(false)) {
				continue;
			}
			online.createConnectionRequest(server).connect();
		}

		for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer proxyPlayer = ProxyPlayer.getPlayer(online.getUniqueId());
			if (proxyPlayer == null || !proxyPlayer.isStaffer()) {
				continue;
			}
			online.sendMessage(br.com.weavenmc.commons.velocity.VelocityText
					.of(sender.getName() + " puxou todos os jogadores para o servidor " + args[0]));
		}
	}

	@Command(name = "finder", aliases = { "find", "buscar", "go" }, groupToUse = Group.TRIAL)
	public void finder(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§3§lFINDER§f Utilize: §b§l/finder§f <player>");
			return;
		}
		Player t = VelocityMain.getInstance().getProxy().getPlayer(args[0]).orElse(null);
		if (t == null) {
			sender.sendMessage("§3§lFINDER§f Jogador não encontrado.");
			return;
		}
		ProxyPlayer target = ProxyPlayer.getPlayer(t.getUniqueId());
		String prefix = target == null ? "" : target.getGroup().getTagToUse().getPrefix();
		String server = t.getCurrentServer().map(sc -> sc.getServerInfo().getName()).orElse("desconhecido");
		Component component = VelocityText.of(prefix + t.getUsername() + " §f- §9" + server.toUpperCase())
				.clickEvent(ClickEvent.runCommand("/connect " + server))
				.hoverEvent(HoverEvent.showText(VelocityText.of("§9Clique para se conectar ate o servidor")));
		sender.getPlayer().sendMessage(component);
	}

	@Command(name = "staffsee", aliases = { "stafflist" })
	public void staffsee(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		ProxyPlayer player = ProxyPlayer.getPlayer(sender.getUniqueId());
		if (player == null || !player.isStaffer()) {
			sender.sendMessage("§c§lPERMISSAO§f Você não tem §c§lPERMISSAO§f para executar este comando!");
			return;
		}
		for (Player o : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer online = ProxyPlayer.getPlayer(o.getUniqueId());
			if (online == null || !online.isStaffer()) {
				continue;
			}
			String server = o.getCurrentServer().map(sc -> sc.getServerInfo().getName()).orElse("desconhecido");
			Component component = VelocityText.of(online.getGroup().getTagToUse().getPrefix() + online.getName() + " §f- §9"
					+ server.toUpperCase()).clickEvent(ClickEvent.runCommand("/connect " + server))
					.hoverEvent(HoverEvent.showText(VelocityText.of("§9Clique para se conectar ate o servidor")));
			sender.getPlayer().sendMessage(component);
		}
	}

	@Command(name = "sc", aliases = { "staffchat", "s" })
	public void staffchat(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		ProxyPlayer player = ProxyPlayer.getPlayer(sender.getUniqueId());
		if (player == null || !player.isStaffer()) {
			sender.sendMessage("§c§lPERMISSAO§f Você não tem §c§lPERMISSAO§f para executar este comando!");
			return;
		}
		player.setStaffChatEnabled(!player.isStaffChatEnabled());
		sender.sendMessage(player.isStaffChatEnabled() ? "§e§lSTAFFCHAT§f Você §a§lHABILITOU§f o modo staff-chat."
				: "§e§lSTAFFCHAT§f Você §c§lDESABILITOU§f o modo staff-chat.");
	}

	@Command(name = "silent", aliases = { "togglesc" }, groupToUse = Group.TRIAL)
	public void silent(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		ProxyPlayer player = ProxyPlayer.getPlayer(sender.getUniqueId());
		if (player == null || !player.isStaffer()) {
			sender.sendMessage("§c§lPERMISSAO§f Você não tem §c§lPERMISSAO§f para executar este comando!");
			return;
		}
		player.setStaffChatMessages(!player.isStaffChatMessages());
		sender.sendMessage(player.isStaffChatMessages() ? "§e§lSTAFFCHAT§f Você §a§lHABILITOU§f as mensagens!"
				: "§e§lSTAFFCHAT§f Você §c§lDESABILITOU§f as mensagens!");
	}

	@Command(name = "mutereport", groupToUse = Group.TRIAL)
	public void mutereport(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§c§lREPORT§f Utilize: /mutereport <player>");
			return;
		}
		Player target = VelocityMain.getInstance().getProxy().getPlayer(args[0]).orElse(null);
		if (target == null) {
			sender.sendMessage("§c§lREPORT§f O player §c§l" + args[0] + "§f não foi encontrado.");
			return;
		}
		reportMute.put(target.getUniqueId(), (1200 * 60) * 1000L + System.currentTimeMillis());
		sender.sendMessage("§c§lREPORT§f Você impossibilitou o player §c§l" + target.getUsername()
				+ "§f de mutar por §c§l1 HORA!");
		target.sendMessage(br.com.weavenmc.commons.velocity.VelocityText
				.of("§c§lMUTE§f Você foi §c§lIMPOSSIBILITADO§f de reportar por §c§l1 HORA!"));
	}

	@Command(name = "ss", aliases = { "screenshare" }, groupToUse = Group.MODGC)
	public void screenshare(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		if (args.length == 0) {
			sender.sendMessage("§a§lSCREENSHARE§f Utilize: §2§l/ss§f <player>");
			return;
		}
		Player actor = sender.getPlayer();
		Player target = VelocityMain.getInstance().getProxy().getPlayer(args[0]).orElse(null);
		if (target == null) {
			sender.sendMessage("§a§lSCREENSHARE§f O jogador " + args[0] + " não foi encontrado!");
			return;
		}
		ProxyPlayer tP = ProxyPlayer.getPlayer(target.getUniqueId());
		if (tP == null) {
			target.disconnect(br.com.weavenmc.commons.velocity.VelocityText.of("§4§lERRO§f Sua conta está com problemas!"));
			sender.sendMessage("§a§lSCREENSHARE§f A conta do jogador estava com §c§lPROBLEMAS!");
			return;
		}
		if (tP.getServerConnectedType() != ServerType.SCREENSHARE) {
			RegisteredServer ssServer = VelocityMain.getInstance().getServerManager().getAvailableServer(ServerType.SCREENSHARE);
			if (ssServer == null) {
				sender.sendMessage("§a§lSCREENSHARE§f Nenhum servidor disponivel de §2§lSCREENSHARE§f foi encontrado!");
				return;
			}
			target.createConnectionRequest(ssServer).connect();
			if (!actor.getCurrentServer().map(sc -> sc.getServerInfo().getName().equalsIgnoreCase(ssServer.getServerInfo().getName()))
					.orElse(false)) {
				actor.createConnectionRequest(ssServer).connect();
			}
			for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
				ProxyPlayer proxyPlayer = ProxyPlayer.getPlayer(online.getUniqueId());
				if (proxyPlayer == null || !proxyPlayer.isStaffer()) {
					continue;
				}
				online.sendMessage(VelocityText.of("§a§lSCREENSHARE§f " + target.getUsername() + "("
						+ target.getUniqueId().toString().replace("-", "")
						+ ") agora está em screenshare via requisiçao de §a§l" + actor.getUsername()));
			}
		} else {
			RegisteredServer lobby = VelocityMain.getInstance().getServerManager().getAvailableServer(ServerType.LOBBY);
			if (lobby == null) {
				sender.sendMessage("§a§lSCREENSHARE§f Nenhum servidor disponivel de §2§lLOBBY§f foi encontrado");
				return;
			}
			target.createConnectionRequest(lobby).connect();
			if (!actor.getCurrentServer().map(sc -> sc.getServerInfo().getName().equalsIgnoreCase(lobby.getServerInfo().getName()))
					.orElse(false)) {
				actor.createConnectionRequest(lobby).connect();
			}
			sender.sendMessage("§a§lSCREENSHARE§f Você §2§lCONCLUIU§f a screenshare");
			target.sendMessage(VelocityText.of("§a§lSCREENSHARE§f Você foi §2§lLIBERADO"));
		}
	}

	@Completer(name = "finder", aliases = { "find", "buscar", "go" })
	public List<String> finderCompleter(Player player, String label, String[] args) {
		return onlineNameSuggestions(args);
	}

	@Completer(name = "ss", aliases = { "screenshare" })
	public List<String> ssCompleter(Player player, String label, String[] args) {
		return onlineNameSuggestions(args);
	}

	public static boolean hasMute(Player p) {
		return reportMute.containsKey(p.getUniqueId()) && reportMute.get(p.getUniqueId()) >= System.currentTimeMillis();
	}

	private List<String> onlineNameSuggestions(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 1) {
			for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
				if (online.getUsername().toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(online.getUsername());
				}
			}
		}
		return list;
	}
}
