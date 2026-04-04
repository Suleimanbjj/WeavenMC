package br.com.weavenmc.hg.player.kit.abilities;

import org.bukkit.Material;
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

public class Nenhum extends Kit {

	public Nenhum(Manager manager) {
		super(manager);
		
		setPrice(0);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.GLASS));
		setItems(new ItemStack(Material.AIR));
		setFree(false);
		setDescription("Kit sem habilidade");
	}
}
