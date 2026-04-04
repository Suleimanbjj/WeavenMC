package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.events.ServerTimeEvent;
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

public class Poseidon extends Kit {

	public Poseidon(Manager manager) {
		super(manager);
		
		setPrice(47000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.WATER_BUCKET));
		setFree(false);
		setDescription("Ganhe habilidades de um deus grego e adquira diversos poderes como força.");
		setRecent(false);
	}

	@EventHandler
	public void onPoseidon(ServerTimeEvent event) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (hasKit(player)) {
				Block block = player.getLocation().getBlock();
				if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1));
					player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 80, 0));
				}
			}
		}
	}
}
