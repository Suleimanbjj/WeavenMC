package br.com.weavenmc.ypvp.ability.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.ability.Ability;

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

public class SpiderAbility extends Ability {

	public SpiderAbility() {
		setName("Spider");
		setHasItem(true);
		setGroupToUse(Group.LIGHT);
		setIcon(Material.SNOW_BALL);
		setDescription(new String[] { "§7Lançe sua teia e prenda seus", "§7inimigos nela." });
		setPrice(55000);
		setTempPrice(5000);
	}

	@Override
	public void eject(Player p) {

	}

	@EventHandler
	public void onSpiderCatch(ProjectileHitEvent event) {
		if (event.getEntity().hasMetadata("Spiderball")) {
			List<Block> webs = new ArrayList<>();
			Location loc = event.getEntity().getLocation();
			int x = new Random().nextInt(2) - 1;
			int z = new Random().nextInt(2) - 1;
			for (int y = 0; y < 2; y++) {
				for (int xx = 0; xx < 2; xx++) {
					for (int zz = 0; zz < 2; zz++) {
						Block b = loc.clone().add(x + xx, y, z + zz).getBlock();
						if (b.getType() == Material.AIR) {
							b.setType(Material.WEB);
							webs.add(b);
						}
					}
				}
			}
			event.getEntity().remove();
			new BukkitRunnable() {
				@Override
				public void run() {
					for (Block web : webs) {
						web.setType(Material.AIR);
					}
				}
			}.runTaskLater(yPvP.getPlugin(), 10 * 20);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (hasKit(p)) {
			ItemStack itemInHand = p.getItemInHand();
			if (itemInHand.getType() == getIcon()) {
				if (itemInHand.hasItemMeta()) {
					if (itemInHand.getItemMeta().getDisplayName().equals("§e§l" + getName())) {
						event.setCancelled(true);
						if (!inCooldown(p)) {
							addCooldown(p, 25);
							p.throwSnowball().setMetadata("Spiderball",
									new FixedMetadataValue(yPvP.getPlugin(), "Spiderball"));
						} else {
							sendCooldown(p);
						}
						p.updateInventory();
					}
				}
			}
		}
	}
}
