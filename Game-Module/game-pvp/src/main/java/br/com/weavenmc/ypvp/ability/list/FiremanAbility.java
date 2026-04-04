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

public class FiremanAbility extends Ability {

	public FiremanAbility() {
		setName("Fireman");
		setHasItem(false);
		setGroupToUse(Group.MEMBRO);
		setIcon(Material.LAVA_BUCKET);
		setDescription(new String[] { "§7Não receba dano para nenhum elemento", "§7relacionado á fogo." });
		setPrice(60000);
		setTempPrice(4000);
	}

	@Override
	public void eject(Player p) {
		
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (hasKit(p)) {
				DamageCause cause = event.getCause();
				if (cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK || cause == DamageCause.LAVA) {
					event.setCancelled(true);
					p.setFireTicks(0);
				}
				cause = null;
			}
			p = null;
		}
	}
}
