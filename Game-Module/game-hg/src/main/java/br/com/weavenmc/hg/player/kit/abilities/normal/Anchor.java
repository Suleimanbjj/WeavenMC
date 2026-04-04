package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
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

public class Anchor extends Kit {

	public Anchor(Manager manager) {
		super(manager);

		setPrice(41000);
		setCooldownTime(30D);
		setIcon(new ItemStack(Material.ANVIL));
		setFree(false);
		setDescription(
				"Não dê nem receba knockbck.");
		setRecent(false);
	}

	@EventHandler
	public void onAnchor(EntityDamageByEntityEvent event) {
		if ((!(event.getEntity() instanceof Player)) || (!(event.getDamager() instanceof Player))) {
			return;
		}
		Player player = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		if ((hasKit(player)) || (hasKitSecondary(player)) || (hasKit(damager)) || (hasKitSecondary(damager))) {
			anchor(player);
			anchor(damager);
		} 
	}

	private void anchor(final Player player) {
		player.setVelocity(new Vector(0, 0, 0));

		new BukkitRunnable() {
			public void run() {
				player.setVelocity(new Vector(0, 0, 0));
			}
		}.runTaskLater(getManager().getPlugin(), 1L);
	}

}
