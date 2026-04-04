package br.com.weavenmc.lobby.commands;

import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.lobby.Lobby;
import br.com.weavenmc.lobby.gamer.Gamer;

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

public class BeneficiesCommand implements CommandClass {

	@Command(name = "fly", aliases = { "voar" }, groupToUse = Group.VIP)
	public void fly(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			Player p = sender.getPlayer();
			Gamer gamer = Lobby.getPlugin().getGamerManager().getGamer(p.getUniqueId());
			if (!gamer.isFlying()) {
				gamer.setFlying(true);
				while (!p.getAllowFlight()) 
					p.setAllowFlight(true);
				p.setFlying(true);
				p.sendMessage("§6§lFLY§f Você §a§lHABILITOU§f o modo §e§lFLY.");
			} else {
				gamer.setFlying(false);
				while (p.getAllowFlight())
					p.setAllowFlight(false);
				p.setFlying(false);
				p.sendMessage("§6§lFLY§f Você §c§lDESABILITOU§f o modo §e§lFLY.");
			}
			gamer = null;
			p = null;
		} else {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
		}
	}
}
