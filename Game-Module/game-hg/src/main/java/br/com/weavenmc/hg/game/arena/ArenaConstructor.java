package br.com.weavenmc.hg.game.arena;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.weavenmc.hg.HungerGames;
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

public class ArenaConstructor {

	private Location l1, l2, l3, l4, l5;
	private int count;
	private boolean stopProcess;

	public ArenaConstructor() {
		this.stopProcess = false;

		this.l1 = new Location(Bukkit.getWorld("arena"), 0, 55, 0);
		this.l2 = new Location(Bukkit.getWorld("arena"), 0, 55, 0);
		this.l3 = new Location(Bukkit.getWorld("arena"), 0, 55, 0);
		this.l4 = new Location(Bukkit.getWorld("arena"), 0, 55, 0);
		this.l5 = new Location(Bukkit.getWorld("arena"), 0, 55, 0);
		
		this.count = 0;
	}

	public void teleportAll() {

		Manager manager = HungerGames.getManager();

		for (Player players : Bukkit.getOnlinePlayers()) {
			Gamer gamer = HungerGames.getManager().getGamerManager().getGamer(players);

			if (gamer.isAlive()) {
				HungerGames.getManager().getArenaManager().getGamersAlready().add(players);
			}
		}
		
		while (this.stopProcess == false && this.count >= 5){
			
			if (count >= 4 && this.stopProcess == false){
				count = 0;
			}
			
			count++;
		}

		while (this.stopProcess == false) {

			Player randomPlayer = manager.getArenaManager().getGamersAlready()
					.get(new Random().nextInt(manager.getArenaManager().getGamersAlready().size()));

			Gamer gmr = manager.getGamerManager().getGamer(randomPlayer);

			if (gmr.isAlive()) {
				manager.getArenaManager().getGamersAlready().remove(randomPlayer);
				randomPlayer.teleport(getLocation());
			}

		}

	}

	public Location getLocation() {
		Location loc = null;
		Manager manager = HungerGames.getManager();
		ArenaManager arenaManager = manager.getArenaManager();

		if (arenaManager.getLastLocation() == null) {
			loc = l1;
		} else {
			if (arenaManager.getLastLocation() == l1) {
				loc = l2;
			} else if (arenaManager.getLastLocation() == l2) {
				loc = l3;
			} else if (arenaManager.getLastLocation() == l3) {
				loc = l4;
			} else if (arenaManager.getLastLocation() == l4) {
				loc = l5;
			}
		}
		
		return loc;
	}

}
