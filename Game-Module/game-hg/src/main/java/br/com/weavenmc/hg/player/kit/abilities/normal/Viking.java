package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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

public class Viking extends Kit {

	public Viking(Manager manager) {
		super(manager);
		
		setPrice(47500);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.STONE_AXE));
		setFree(false);
		setDescription("Use seu machado viking para atingir mais facilmente seus inimigos.");
		setRecent(false);
		setItems(new ItemStack(Material.STONE_AXE));
	}

	@EventHandler
	public void onViking(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
			return;

		Player player = (Player) event.getDamager();

		if (!hasKit(player) || player.getItemInHand() == null || !player.getItemInHand().getType().name().contains("AXE"))
			return;

		event.setDamage(event.getDamage() + 1.0D);
		if (event.getDamager().getFallDistance() > 0.0D) {
			event.setDamage(event.getDamage() + 1.0D);
		}
	}

}
