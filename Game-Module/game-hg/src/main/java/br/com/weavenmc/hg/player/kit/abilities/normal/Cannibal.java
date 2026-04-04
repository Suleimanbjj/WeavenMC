package br.com.weavenmc.hg.player.kit.abilities.normal;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

public class Cannibal extends Kit {

	public Cannibal(Manager manager) {
		super(manager);
		
		setPrice(45000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.RAW_FISH));
		setFree(false);
		setDescription("Cause fome em seus inimigos");
		setRecent(false);
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof Player && hasKit((Player) event.getDamager()) && new Random().nextInt(100) <= 20) {

			((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 80, 1), true);
			Player player = (Player) event.getDamager();
			int hungry = player.getFoodLevel();
			hungry++;
			if (hungry <= 20) {
				player.setFoodLevel(hungry);
			}
		}
	}

}
