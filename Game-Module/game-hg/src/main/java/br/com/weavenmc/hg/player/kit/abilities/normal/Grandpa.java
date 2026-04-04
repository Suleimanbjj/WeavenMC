package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
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

public class Grandpa extends Kit {

	public Grandpa(Manager manager) {
		super(manager);
		
		setPrice(40000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.STICK));
		setFree(false);
		setRecent(false);
		setDescription("Use seu graveto para arremessar seus oponentes para trás.");
		setItems(createItemStack("§aGrandpa", Material.STICK));
	}

	@EventHandler(ignoreCancelled = true)
	public void grandpaKit(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();

			if (hasKit(player) && isKitItem(player.getItemInHand(), "§aGrandpa")) {
				Vector vector = ((LivingEntity) event.getEntity()).getLocation().toVector().subtract(player.getLocation().toVector()).normalize();

				double knockBack = 2.0D;
				
				((LivingEntity) event.getEntity()).setVelocity(vector.multiply(knockBack));
			}
		}
	}
}
