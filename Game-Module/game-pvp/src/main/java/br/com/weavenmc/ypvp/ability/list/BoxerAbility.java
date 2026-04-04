package br.com.weavenmc.ypvp.ability.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import br.com.weavenmc.commons.core.permission.Group;
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

public class BoxerAbility extends Ability {

	public BoxerAbility() {
		setName("Boxer");
		setHasItem(false);
		setGroupToUse(Group.LIGHT);
		setIcon(Material.IRON_SWORD);
		setDescription(new String[] { "§7Dê 0.50 á mais de dano e receba", "§70.50 á menos de dano." });
		setPrice(45000);
		setTempPrice(3000);
	}

	@Override
	public void eject(Player p) {

	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player e = (Player) event.getEntity();
			Player d = (Player) event.getDamager();
			if (hasKit(d)) 
				event.setDamage(event.getDamage() + 0.50D);			
			if (hasKit(e)) 
				event.setDamage(event.getDamage() - 0.50D);
			e = null;
			d = null;
		}
	}
}
