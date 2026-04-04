package br.com.weavenmc.hg.player.kitaward.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import br.com.weavenmc.hg.game.custom.HungerListener;
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

public class AwardListener extends HungerListener {

	@EventHandler()
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory() != null) {
			if (event.getInventory().getName().contains("surpresa")) {
				event.setCancelled(true);
			}
		}
	}
}
