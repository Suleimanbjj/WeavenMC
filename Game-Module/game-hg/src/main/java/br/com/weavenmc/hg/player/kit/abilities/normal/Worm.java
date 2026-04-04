package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
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

public class Worm extends Kit {

	public Worm(Manager manager) {
		super(manager);

		setPrice(41000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.DIRT));
		setFree(false);
		setDescription("Transforme-se em uma minhoca e regenere sua fome comendo terra.");
		setRecent(false);
	}

	@EventHandler
	public void onDamage(BlockDamageEvent event) {
		if (hasKit(event.getPlayer()) && event.getBlock().getType() == Material.DIRT) {
			event.setInstaBreak(true);
			if (((Damageable) event.getPlayer()).getHealth() < ((Damageable) event.getPlayer()).getMaxHealth()) {
				double life = ((Damageable) event.getPlayer()).getHealth() + 2.0D;
				if (life > ((Damageable) event.getPlayer()).getMaxHealth()) {
					life = ((Damageable) event.getPlayer()).getMaxHealth();
				}
				event.getPlayer().setHealth(life);
			} else if (event.getPlayer().getFoodLevel() < 20) {
				event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 1);
			}
		}
	}
}
