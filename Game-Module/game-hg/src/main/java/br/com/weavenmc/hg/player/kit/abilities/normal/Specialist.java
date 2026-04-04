package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.kit.Kit;
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

public class Specialist extends Kit {

	public Specialist(Manager manager) {
		super(manager);
		
		this.setActive(false);
		setPrice(49000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.BOOK));
		setFree(false);
		setDescription("Tenha a habilidade de encantar seus itens.");
		setRecent(false);
		setItems(createItemStack("§aSpecialist", Material.BOOK));
	}

	@EventHandler
	private void playerInteractEvent(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = player.getItemInHand();
		if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) && hasKit(player) && item != null && item.getType() == Material.BOOK) {
			event.setCancelled(true);
			player.openInventory(Bukkit.createInventory(null, InventoryType.ENCHANTING));
		}
	}

	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity().getKiller();
		if (player == null) {
			return;
		}
		if (hasKit(player)) {
			player.setLevel(player.getLevel() + 1);
		}
	}

	@EventHandler
	public void expBottleEvent(ExpBottleEvent event) {
		event.setExperience(event.getExperience() * 2 + 5);
	}

}
