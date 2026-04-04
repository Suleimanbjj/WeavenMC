package br.com.weavenmc.hg.player.inventories;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.item.ItemBuilder;
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

public abstract class CustomInventory implements Listener {

	private Manager manager;

	private String title;
	private ItemBuilder itemBuilder;

	public CustomInventory(Manager manager) {
		this.manager = manager;
		this.itemBuilder = new ItemBuilder(Material.AIR);
	}

	@EventHandler
	public abstract void onClick(InventoryClickEvent event);

	public String getTitle() {
		return title;
	}

	public ItemBuilder getItemBuilder() {
		return itemBuilder;
	}

	public Manager getManager() {
		return manager;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
