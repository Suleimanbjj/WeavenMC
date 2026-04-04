package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
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

public class Cultivator extends Kit {

	public Cultivator(Manager manager) {
		super(manager);
		
		setPrice(40000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.SAPLING));
		setFree(false);
		setDescription("Ganhe as habilidades de um fazendeiro e cultive rapidamente suas sementes.");
		setRecent(false);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCultivator(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!hasKit(player)) {
			return;
		}
		if (event.getBlock().getType() == Material.SAPLING) {
			event.getBlock().setType(Material.AIR);

			boolean arvore = event.getBlock().getWorld().generateTree(event.getBlock().getLocation(), TreeType.TREE);
			if (!arvore) {
				event.getBlock().setTypeIdAndData(Material.SAPLING.getId(), event.getBlock().getData(), false);
			}
		} else if (event.getBlock().getType() == Material.CROPS) {
			event.getBlock().setData((byte) 7);
		}
	}

}
