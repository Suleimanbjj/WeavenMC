package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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

public class Hulk extends Kit {

	public Hulk(Manager manager) {
		super(manager);
		
		setPrice(46000);
		setCooldownTime(10D);
		setIcon(new ItemStack(Material.SADDLE));
		setFree(false);
		setDescription("Use sua incrivel força e carregue os seus inimigos na sua cabeça.");
		setRecent(false);
	}

	@EventHandler
	public void hulk(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof Player) {
			Player clicked = (Player) event.getRightClicked();
			if (!player.isInsideVehicle() && !clicked.isInsideVehicle() && player.getItemInHand().getType() == Material.AIR && hasKit(player)) {

				if (inCooldown(player)) {
					sendCooldown(player);
					return;
				}

				addCooldown(player);
				player.setPassenger(clicked);
			}
		}
	}

	@EventHandler
	public void noHulkMor(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity();
			Player hulk = (Player) event.getDamager();
			if (hulk.getPassenger() != null && hulk.getPassenger() == player && hasKit(hulk) && hulk.getPassenger() == player) {
				event.setCancelled(true);
				player.setSneaking(true);

				Vector v = player.getEyeLocation().getDirection().multiply(1.5F);
				v.setY(0.6D);
				player.setVelocity(v);

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getManager().getPlugin(), new Runnable() {
					public void run() {
						player.setSneaking(false);
					}
				}, 10L);
			}
		}
	}
}
