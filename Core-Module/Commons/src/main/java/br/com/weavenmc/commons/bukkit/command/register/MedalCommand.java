package br.com.weavenmc.commons.bukkit.command.register;

import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.account.Medal;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;

/**
 * Copyright (C) WeavenMC. All Rights Reserved.
 *
 * Unauthorized copying, modification, distribution, or use of this file,
 * via any medium, is strictly prohibited without explicit permission
 * from the copyright holder.
 *
 * Distribution and use for educational purposes are permitted.
 * Commercial use, resale, or any form of monetized distribution
 * is strictly prohibited under GNU License terms and applicable
 * copyright laws.
 *
 * Violators may be subject to legal action.
 */

public class MedalCommand implements CommandClass {

	@Command(name = "medal", aliases = { "medalha" }, runAsync = true)
	public void medal(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			Player p = sender.getPlayer();
			BukkitPlayer bP = BukkitPlayer.getPlayer(p.getUniqueId());
			if (args.length == 0) {
				p.sendMessage("§6§lMEDALHAS§f Utilize: /medalha <nome, remover>");
				p.sendMessage("§7-");

				p.sendMessage(Medal.PEACE.getColor() + "§l" + Medal.PEACE.getSymbol()
						+ " PEACE §7- §fMedalha exclusiva para o grupo "
						+ Medal.PEACE.getGroupToUse().getTagToUse().getName());

				p.sendMessage(Medal.SMILE.getColor() + "§l" + Medal.SMILE.getSymbol()
						+ " SMILE §7- §fMedalha exclusiva para o grupo "
						+ Medal.SMILE.getGroupToUse().getTagToUse().getName());

				p.sendMessage(Medal.YIN_YANG.getColor() + "§l" + Medal.YIN_YANG.getSymbol()
						+ " YING&YANG §7- §fMedalha exclusiva para o grupo "
						+ Medal.YIN_YANG.getGroupToUse().getTagToUse().getName());

				p.sendMessage(Medal.KING.getColor() + "§l" + Medal.KING.getSymbol()
						+ " KING §7- §fMedalha exclusiva para o grupo "
						+ Medal.KING.getGroupToUse().getTagToUse().getName());

				p.sendMessage(Medal.DANGER.getColor() + "§l" + Medal.DANGER.getSymbol()
						+ " DANGER §7- §fMedalha exclusiva para o grupo "
						+ Medal.DANGER.getGroupToUse().getTagToUse().getName());

				p.sendMessage(Medal.TOXIC.getColor() + "§l" + Medal.TOXIC.getSymbol()
						+ " TOXIC §7- §fMedalha exclusiva para o grupo "
						+ Medal.TOXIC.getGroupToUse().getTagToUse().getName());
			} else if (args[0].equalsIgnoreCase("remover") || args[0].equalsIgnoreCase("tirar")) {
				if (BukkitMain.getInstance().getTagManager().isUsingMedal(p)) {
					BukkitMain.getInstance().getTagManager().removeMedal(p);
					p.sendMessage("§6§lMEDALHAS§f Você §c§lREMOVEU§f a sua medalha.");
				} else {
					p.sendMessage("§6§lMEDALHAS§f Você não está §3§lUTILIZANDO§f nenhuma medalha.");
				}
			} else {
				Medal medal = Medal.fromString(args[0]);
				if (medal != null) {
					if (bP.hasGroupPermission(medal.getGroupToUse())) {
						BukkitMain.getInstance().getTagManager().setMedal(p, medal);
						p.sendMessage("§6§lMEDALHAS§f Você agora está §3§lUTILIZANDO§f a medalha " + medal.getColor()
								+ "§l" + medal.getSymbol() + " " + medal.name().replace("YIN_YANG", "YING&YANG"));
					} else {
						p.sendMessage("§6§lMEDALHAS§f Você precisa possuir o grupo "
								+ medal.getGroupToUse().getTagToUse().getName() + "§f para usar esta medalha!");
					}
				} else {
					p.sendMessage("§6§lMEDALHAS§f A medalha " + args[0] + " não foi encontrada.");
				}
			}
		} else {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
		}
	}
}
