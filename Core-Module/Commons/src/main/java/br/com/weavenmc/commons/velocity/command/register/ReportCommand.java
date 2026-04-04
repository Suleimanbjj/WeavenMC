package br.com.weavenmc.commons.velocity.command.register;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.command.CommandFramework.Completer;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.commons.util.string.StringUtils;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class ReportCommand implements CommandClass {

	private final HashMap<UUID, Long> reportCooldown = new HashMap<>();

	@Command(name = "report", aliases = { "reportar", "denunciar", "denuncia" })
	public void report(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
			return;
		}
		Player reporter = sender.getPlayer();
		if (args.length <= 1) {
			sender.sendMessage("§9§lREPORT§f Utilize: §3§l/report§f <jogador> <motivo>");
			return;
		}
		Player reported = VelocityMain.getInstance().getProxy().getPlayer(args[0]).orElse(null);
		if (reported == null) {
			sender.sendMessage("§9§lREPORT§f O jogador §3§l" + args[0] + "§f não foi encontrado.");
			return;
		}
		if (reported.getUniqueId().equals(reporter.getUniqueId())) {
			sender.sendMessage("§9§lREPORT§f Indique outro jogador para §3§lDENUNCIAR!");
			return;
		}
		if (StaffCommand.hasMute(reporter)) {
			sender.sendMessage("§c§lMUTE§f Você está §c§lTEMPORARIAMENTE§f impossibilitado de §c§l§oREPORTAR!");
			return;
		}
		if (hasCooldown(reporter.getUniqueId())) {
			sendCooldown(sender, reporter.getUniqueId());
			return;
		}
		addCooldown(reporter.getUniqueId());

		String reason = StringUtils.createArgs(1, args, "", false);
		RegisteredServer server = reported.getCurrentServer().map(sc -> sc.getServer()).orElse(null);
		String serverName = server == null ? "desconhecido" : server.getServerInfo().getName();
		ProxyPlayer reporterAcc = ProxyPlayer.getPlayer(reporter.getUniqueId());
		ProxyPlayer reportedAcc = ProxyPlayer.getPlayer(reported.getUniqueId());
		for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer p = ProxyPlayer.getPlayer(online.getUniqueId());
			if (p == null || !p.isStaffer() || p.getServerConnectedType().equals(ServerType.LOGIN)) {
				continue;
			}
			Component component = VelocityText.of("§9§lREPORT§f O player " + reported.getUsername() + " foi REPORTADO!");
			String hoverText = "§9§lREPORT\n\n§fJogador reportado:\n§fNick: §3" + reported.getUsername()
					+ "\n§fGrupo: " + (reportedAcc == null ? "§7DESCONHECIDO" : reportedAcc.getGroup().getTagToUse().getName())
					+ "\n§fMotivo: §3" + reason + "\n§fServidor: §3" + serverName.toUpperCase()
					+ "\n\n§fQuem reportou:\n§fNick: §3" + reporter.getUsername() + "\n§fGrupo: "
					+ (reporterAcc == null ? "§7DESCONHECIDO" : reporterAcc.getGroup().getTagToUse().getName());
			if (server != null && online.getCurrentServer().map(sc -> sc.getServerInfo().getName().equalsIgnoreCase(serverName)).orElse(false)) {
				component = component.clickEvent(ClickEvent.runCommand("/tp " + reported.getUsername()))
						.hoverEvent(HoverEvent.showText(VelocityText.of(hoverText + "\n\n§3[Clique para teleportar]")));
			} else if (server != null) {
				component = component.clickEvent(ClickEvent.runCommand("/connect " + serverName))
						.hoverEvent(HoverEvent.showText(VelocityText.of(hoverText + "\n\n§3[Clique para conectar]")));
			} else {
				component = component.hoverEvent(HoverEvent.showText(VelocityText.of(hoverText)));
			}
			online.sendMessage(component);
		}
		sender.sendMessage("§9§lREPORT§f Sua §3§lDENUNCIA§f foi enviada com §a§lSUCESSO!");
	}

	private boolean hasCooldown(UUID uuid) {
		return reportCooldown.containsKey(uuid) && reportCooldown.get(uuid) >= System.currentTimeMillis();
	}

	private void addCooldown(UUID uuid) {
		reportCooldown.put(uuid, (30 * 1000L + System.currentTimeMillis()));
	}

	private void sendCooldown(VelocityCommandSender sender, UUID uuid) {
		String cooldown = StringTimeUtils
				.toMillis((double) (reportCooldown.get(uuid) - System.currentTimeMillis()) / 10 / 100);
		sender.sendMessage("§9§lREPORT§f Aguarde §3§l" + cooldown + "§f para reportar novamente!");
	}

	@Completer(name = "report", aliases = { "reportar", "denunciar", "denuncia" })
	public List<String> reportCompleter(Player player, String label, String[] args) {
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
