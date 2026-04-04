package br.com.weavenmc.lobby.commands;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.lobby.Lobby;

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

public class AdministratrorCommand implements CommandClass {
	
	@Command(name = "hologram", aliases = { "hl" }, groupToUse = Group.DONO)
	public void hologram(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			if (args.length == 0) {
				sender.sendMessage("§3§lHOLOGRAM§f Utilize: /hologram <ranking, hgwins, pvpkills>");
			} else {
				if (args[0].equalsIgnoreCase("ranking")) {
					Player p = sender.getPlayer();
					Location playerLocation = p.getLocation();
					FileConfiguration config = Lobby.getPlugin().getConfig();
					config.set("hologram.toprank.location.x", playerLocation.getX());
					config.set("hologram.toprank.location.y", playerLocation.getY());
					config.set("hologram.toprank.location.z", playerLocation.getZ());
					config.set("hologram.toprank.location.yaw", playerLocation.getYaw());
					config.set("hologram.toprank.location.pitch", playerLocation.getPitch());
					Lobby.getPlugin().saveConfig();
					
					if (Lobby.getPlugin().getTopHologram() != null) 
						Lobby.getPlugin().getTopHologram().setLocation(playerLocation);
					 else 
						Lobby.getPlugin().checkTopHologram();		
					
					sender.sendMessage("§3§lHOLOGRAM§f O local do §b§lTOP RANKING§f foi §b§lATUALIZADO§f!");
				} else if (args[0].equalsIgnoreCase("hgwins")) {
					Player p = sender.getPlayer();
					Location playerLocation = p.getLocation();
					FileConfiguration config = Lobby.getPlugin().getConfig();
					config.set("hologram.hgwins.location.x", playerLocation.getX());
					config.set("hologram.hgwins.location.y", playerLocation.getY());
					config.set("hologram.hgwins.location.z", playerLocation.getZ());
					config.set("hologram.hgwins.location.yaw", playerLocation.getYaw());
					config.set("hologram.hgwins.location.pitch", playerLocation.getPitch());
					Lobby.getPlugin().saveConfig();
					
					if (Lobby.getPlugin().getTopHGWins() != null) 
						Lobby.getPlugin().getTopHGWins().setLocation(playerLocation);
					 else 
						Lobby.getPlugin().checkTopHolograms();		
					
					sender.sendMessage("§3§lHOLOGRAM§f O local do §b§lTOP HG WINS§f foi §b§lATUALIZADO§f!");
				} else if (args[0].equalsIgnoreCase("pvpkills")) {
					Player p = sender.getPlayer();
					Location playerLocation = p.getLocation();
					FileConfiguration config = Lobby.getPlugin().getConfig();
					config.set("hologram.pvpkills.location.x", playerLocation.getX());
					config.set("hologram.pvpkills.location.y", playerLocation.getY());
					config.set("hologram.pvpkills.location.z", playerLocation.getZ());
					config.set("hologram.pvpkills.location.yaw", playerLocation.getYaw());
					config.set("hologram.pvpkills.location.pitch", playerLocation.getPitch());
					Lobby.getPlugin().saveConfig();
					
					if (Lobby.getPlugin().getTopPVPKills() != null) 
						Lobby.getPlugin().getTopPVPKills().setLocation(playerLocation);
					 else 
						Lobby.getPlugin().checkTopHolograms();		
					
					sender.sendMessage("§3§lHOLOGRAM§f O local do §b§lTOP PVP KILLS§f foi §b§lATUALIZADO§f!");
				} else {
					sender.sendMessage("§3§lHOLOGRAM§f Utilize: /hologram <ranking, hgwins, hgkills, pvpkills>");
				}
			}
		}
	}
}
