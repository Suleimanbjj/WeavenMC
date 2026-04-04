package br.com.weavenmc.ypvp.ability.list;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

public class SnailAbility extends Ability {

	public SnailAbility() {
		setName("Snail");
		setHasItem(false);
		setGroupToUse(Group.MEMBRO);
		setIcon(Material.WEB);
		setDescription(
				new String[] { "§7A cada hit tenha 30% de chance de", "§7seu inimigo pegar efeito de lentidão." });
		setPrice(35000);
		setTempPrice(4000);
	}

	@Override
	public void eject(Player p) {
		p.getActivePotionEffects().clear();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player p = (Player) event.getDamager();
			if (hasKit(p)) {
				if (!event.isCancelled()) {
					Player target = (Player) event.getEntity();
					if (!target.hasPotionEffect(PotionEffectType.SLOW)) {
						int random = new Random().nextInt(100);
						if (random > 0 && random <= 30) {
							target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 3));
						}
					}
				}
			}
		}
	}
}
