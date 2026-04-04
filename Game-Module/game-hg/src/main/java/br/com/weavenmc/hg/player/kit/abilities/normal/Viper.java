package br.com.weavenmc.hg.player.kit.abilities.normal;

import java.util.Random;
import org.bukkit.Material;
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

public class Viper extends Kit {

	public Viper(Manager manager) {
		super(manager);
		
		setPrice(45000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.SPIDER_EYE));
		setFree(false);
		setDescription("Use suas habilidades para envenenar a vida de seus inimigos.");
		setRecent(false);
	}

	@EventHandler
	public void onViper(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			Player player = (Player) event.getDamager();

			if (hasKit(player) && new Random().nextInt(100) <= 33)
				((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 0));
		}
	}

}
