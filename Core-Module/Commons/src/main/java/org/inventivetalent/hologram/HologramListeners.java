package org.inventivetalent.hologram;

import java.util.Collections;
import java.util.LinkedList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;

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

public class HologramListeners implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onRespawn(PlayerRespawnEvent event) {
		event.getRespawnLocation().getChunk().load();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		for (Hologram h : HologramAPI.getHolograms()) {
			if ((h.isSpawned()) && (h.getLocation().getWorld().getName().equals(e.getTo().getWorld().getName()))) {
				try {
					HologramAPI.spawn(h, new LinkedList<>(Collections.singletonList(p)));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		for (Hologram h : HologramAPI.getHolograms()) {
			if ((h.isSpawned()) && (h.getLocation().getWorld().getName().equals(p.getWorld().getName()))) {
				try {
					HologramAPI.spawn(h, new LinkedList<>(Collections.singletonList(p)));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChunkLoad(ChunkLoadEvent e) {
		for (Hologram h : HologramAPI.getHolograms()) {
			if ((h.isSpawned()) && (h.getLocation().getChunk().equals(e.getChunk()))) {
				try {
					HologramAPI.spawn(h, h.getLocation().getWorld().getPlayers());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
