package br.com.weavenmc.ypvp.ability.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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

public class NoFallAbility extends Ability {

	public NoFallAbility() {
		setName("NoFall");
		setHasItem(false);
		setGroupToUse(Group.LIGHT);
		setIcon(Material.IRON_BOOTS);
		setDescription(new String[] { "§7Não receba dano de queda." });
		setPrice(45000);
		setTempPrice(3500);
	}

	@Override
	public void eject(Player p) {
		
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (hasKit((Player) event.getEntity())) {
				if (event.getCause() == DamageCause.FALL) {
					event.setCancelled(true);
				}
			}
		}
	}
}
