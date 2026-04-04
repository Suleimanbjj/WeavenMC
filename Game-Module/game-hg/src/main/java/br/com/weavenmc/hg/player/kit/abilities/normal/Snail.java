package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Effect;
import org.bukkit.Location;
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

public class Snail extends Kit {

	public Snail(Manager manager) {
		super(manager);
		
		setPrice(45000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.SOUL_SAND));
		setFree(false);
		setDescription("Deixe seu inimigo com lentidão.");
		setRecent(false);
	}

	@EventHandler
	public void onSnail(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		Player snail = (Player) event.getDamager();
		Location location = player.getLocation();
		if (!hasKit(snail)) {
			return;
		}
		if (player instanceof Player && getManager().getRandom().nextInt(3) == 0) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
			location.getWorld().playEffect(location.add(0.0D, 0.4D, 0.0D), Effect.STEP_SOUND, 159, 13);
		}
	}

}
