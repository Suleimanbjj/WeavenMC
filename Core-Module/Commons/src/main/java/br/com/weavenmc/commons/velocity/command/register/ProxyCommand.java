package br.com.weavenmc.commons.velocity.command.register;

import java.net.InetSocketAddress;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.checker.CheckingConnection;
import br.com.weavenmc.commons.velocity.checker.CheckingConnectionAPI;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;

public class ProxyCommand implements CommandClass {

	@Command(name = "join", groupToUse = Group.DONO)
	public void join(VelocityCommandSender sender, String label, String[] args) {
		sender.sendMessage("§6Pirate authentication: §e" + br.com.weavenmc.commons.velocity.listeners.ProfileLookupListener.joinEnabled);
	}

	@Command(name = "proxy", aliases = { "proxie" }, groupToUse = Group.DONO)
	public void proxy(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§c§lPROXY§f Utilize: §c§l/proxy§f <list, allow, disallow> [ip]");
			return;
		}
		if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			int allowed = 0, disallowed = 0, checking = 0;
			for (CheckingConnection proxie : CheckingConnectionAPI.getConnections()) {
				if (proxie.isChecking()) {
					checking++;
					continue;
				}
				if (proxie.isAllowed() && proxie.isChecked() && !proxie.hasError()) {
					allowed++;
				} else if (!proxie.isAllowed() && proxie.isChecked() && !proxie.hasError()) {
					disallowed++;
				}
			}
			sender.sendMessage("§eConexoes sendo processadas: §6" + checking);
			sender.sendMessage("§cConexoes nao permitidas: §4" + disallowed);
			sender.sendMessage("§aConexoes permitidas: §2" + allowed);
			return;
		}
		if (args.length < 2) {
			sender.sendMessage("§c§lPROXY§f Utilize: §c§l/proxy§f <allow, disallow> <ip>");
			return;
		}

		CheckingConnection proxy = CheckingConnectionAPI.getConnection(args[1]);
		if (proxy == null) {
			sender.sendMessage("§cA conexao com o ip '" + args[1] + "' nunca acessou o servidor.");
			return;
		}

		if (args[0].equalsIgnoreCase("allow")) {
			if (proxy.isAllowed()) {
				sender.sendMessage("§cA conexao com o ip '" + args[1] + "' já esta permitida a entrar no servidor.");
				return;
			}
			proxy.allow();
			proxy.error(null);
			proxy.setChecked(true);
			proxy.fullyChecked();
			CheckingConnectionAPI.saveProxy(proxy, proxy.saved);
			proxy.saved = true;
			sender.sendMessage("§aVocê permitiu a conexao com o ip '" + args[1] + "' a entrar no servidor.");
			return;
		}

		if (args[0].equalsIgnoreCase("disallow")) {
			if (!proxy.isAllowed()) {
				sender.sendMessage("§cA conexao com o ip '" + args[1] + "' nao esta permitida a entrar no servidor.");
				return;
			}
			proxy.disallow();
			proxy.error(null);
			proxy.setChecked(true);
			proxy.fullyChecked();
			CheckingConnectionAPI.saveProxy(proxy, proxy.saved);
			proxy.saved = true;

			VelocityMain.getInstance().getProxy().getAllPlayers().forEach(player -> {
				if (player.getRemoteAddress() instanceof InetSocketAddress) {
					String ip = ((InetSocketAddress) player.getRemoteAddress()).getHostString();
					if (ip.equals(proxy.getIpAddress())) {
						player.disconnect(br.com.weavenmc.commons.velocity.VelocityText.of(
								"§cSua conexao foi bloqueada no servidor!"));
					}
				}
			});
			sender.sendMessage("§aVocê bloqueou a conexao com o ip '" + args[1] + "' a entrar no servidor.");
			return;
		}

		sender.sendMessage("§c§lPROXY§f Utilize: §c§l/proxy§f <allow, disallow> <ip>");
	}
}
