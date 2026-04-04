package br.com.weavenmc.hg.player.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
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

public class PlayerInventoryOpenEvent extends CustomEvent implements Cancellable {

	private final Player player;
	private final Inventory inventory;

	private boolean cancelled = false;

	public PlayerInventoryOpenEvent(Player player, Inventory inventory) {
		this.player = player;
		this.inventory = inventory;
	}

	public Player getPlayer() {
		return player;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
