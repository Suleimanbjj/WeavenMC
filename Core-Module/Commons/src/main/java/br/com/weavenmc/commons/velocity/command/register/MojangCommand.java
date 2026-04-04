package br.com.weavenmc.commons.velocity.command.register;

import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class MojangCommand implements CommandClass {

	@Command(name = "mojang", groupToUse = Group.DONO)
	public void onMojang(VelocityCommandSender sender, String label, String[] args) {
		sender.sendMessage("§9Estatisticas de jogadores §6originais§9 e §epiratas§9:");

		int crackedCount = 0, premiumCount = 0, onlineCount = 0;
		for (RegisteredServer server : VelocityMain.getInstance().getProxy().getAllServers()) {
			int localCracked = 0, localPremium = 0;
			for (Player o : server.getPlayersConnected()) {
				if (o.isOnlineMode()) {
					localPremium++;
					premiumCount++;
				} else {
					localCracked++;
					crackedCount++;
				}
				onlineCount++;
			}
			sender.sendMessage("§9" + server.getServerInfo().getName().toUpperCase() + ": §6" + localPremium + "§9/§e"
					+ localCracked);
		}

		sender.sendMessage("§9No momento há em toda a Rede:");
		sender.sendMessage("§6" + premiumCount + " jogadores originais");
		sender.sendMessage("§e" + crackedCount + " jogadores piratas");
		sender.sendMessage("§a" + onlineCount + " jogadores totais online");
	}
}
