package br.com.weavenmc.ypvp.ability.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.ability.Ability;
import br.com.weavenmc.ypvp.yPvP.PvPType;

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

public class UrgalAbility extends Ability {

	public UrgalAbility() {
		setName("Urgal");
		setHasItem(true);
		setGroupToUse(Group.BETA);
		setIcon(Material.POTION);
		setDescription(
				new String[] { "§7Receba força para matar seus", "§7inimigos mais rápido." });
		setPrice(100000);
		setTempPrice(12000);
	}

	@Override
	public void eject(Player p) {
		p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
	}

	@EventHandler
	public void onUrgal(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (hasKit(p)) {
			if (isItem(p.getItemInHand())) {
				if (event.getAction().name().contains("RIGHT")) {
					event.setCancelled(true);
					if (!p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						if (!inCooldown(p)) {
							addCooldown(p, 60);
							if (yPvP.getPlugin().getPvpType() == PvPType.FULLIRON) {
								p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
							} else {
								p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0));
							}
							p.sendMessage("§5§lURGAL§f Você recebeu §9§lFORÇA!");
						} else {
							sendCooldown(p);
						}
					} else {
						p.sendMessage("§5§lURGAL§f Você já está com §9§lFORÇA!");
					}
				}
			}
		}
	}
}
