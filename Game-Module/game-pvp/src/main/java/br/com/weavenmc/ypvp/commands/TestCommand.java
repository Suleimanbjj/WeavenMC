package br.com.weavenmc.ypvp.commands;

import java.io.File;
import java.io.IOException;
import org.bukkit.entity.Player;

import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.jnbt.DataException;
import br.com.weavenmc.ypvp.jnbt.Schematic;

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

public class TestCommand implements CommandClass {

	@Command(name = "schematic")
	public void test(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			Player p = sender.getPlayer();
			if (args.length == 0) {
				p.sendMessage("§3§lSCHEMATIC§f Utilize: §b§l/schematic§f [file name ...]");
			} else {
				Schematic file = null;
				
				try {
					file = Schematic.getInstance().loadSchematic(new File(yPvP.getPlugin().getDataFolder(), args[0]));
				} catch (IOException | DataException ex) {
					p.sendMessage("§3§lSCHEMATIC§f Erro: §b" + ex.getMessage());
				}
				
				if (file != null) 
				{
					p.sendMessage("§3§lSCHEMATIC§f Spawnando o schematic...");					
					Schematic.spawn(p.getWorld(), p.getLocation(), file);
				}
			}
		} else {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}
}
