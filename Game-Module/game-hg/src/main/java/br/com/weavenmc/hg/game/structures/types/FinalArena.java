package br.com.weavenmc.hg.game.structures.types;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import br.com.weavenmc.hg.game.structures.Structure;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.gamer.Gamer;
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

public class FinalArena extends Structure {

	public FinalArena(Manager manager, Location location) {
		super(manager, new Location(Bukkit.getWorld("arena"), 0, 55, 0));
	}

	public void cleanPlayer() {
		int time = 0;

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {

			new BukkitRunnable() {
				public void run() {
					player.teleport(new Location(Bukkit.getWorld("arena"), 0, 55, 0));
				}
			}.runTaskLater(getManager().getPlugin(), time);

			time++;
		}

		for (Gamer gamer : getManager().getGamerManager().getGamers().values()) {
			Player player = gamer.getPlayer();
			if (gamer.isAlive() && player.isOnline()) {
				player.closeInventory();
				for (Item i : player.getWorld().getEntitiesByClass(Item.class)) {
					i.remove();
				}
			}
		}
	}

}
