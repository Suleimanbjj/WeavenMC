package br.com.weavenmc.hg.game.event;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.weavenmc.hg.player.events.CustomEvent;
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

public class GamerDeathEvent extends CustomEvent {

	private Player killer, entity;
	private Location drops;
	private List<ItemStack> items;

	public GamerDeathEvent(Player killer, Player entity, Location dropsLocation, List<ItemStack> drops) {
		this.killer = killer;
		this.entity = entity;
		this.drops = dropsLocation;
		this.items = drops;
	}

	public Player getPlayer() {
		return entity;
	}

	public Player getKiller() {
		return killer;
	}

	public List<ItemStack> getDrops() {
		return items;
	}

	public Location getDropsLocation() {
		return drops;
	}

	public void setDropsLocation(Location location) {
		drops = location;
	}

	public Player getEntity() {
		return entity;
	}

}
