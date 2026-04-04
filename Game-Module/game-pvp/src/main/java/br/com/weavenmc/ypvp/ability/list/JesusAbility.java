package br.com.weavenmc.ypvp.ability.list;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
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

public class JesusAbility extends Ability {

	public JesusAbility() {
		setName("Jesus");
		setHasItem(false);
		setGroupToUse(Group.LIGHT);
		setIcon(Material.SNOW_BLOCK);
		setDescription(new String[] { "§7Receba o poder divino de andar", "§7sobre a água." });
		setPrice(65000);
		setTempPrice(6500);
	}

	@Override
	public void eject(Player p) {

	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (!hasKit(p))
			return;
		if (p.getGameMode() != GameMode.ADVENTURE && p.getGameMode() != GameMode.SURVIVAL)
			return;
		if (p.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.STATIONARY_WATER)
				|| p.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.WATER)
				|| p.getLocation().add(0, 0, 0).getBlock().getType().equals(Material.STATIONARY_WATER)
				|| p.getLocation().add(0, 0, 0).getBlock().getType().equals(Material.WATER)
				|| p.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)
				|| p.getLocation().getBlock().getType().equals(Material.WATER)) {
			if (!p.getAllowFlight())
				p.setAllowFlight(true);
			p.setFlying(true);
			return;
		}
		if (!p.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.STATIONARY_WATER)
				|| !p.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.WATER)
				|| !p.getLocation().add(0, 0, 0).getBlock().getType().equals(Material.STATIONARY_WATER)
				|| !p.getLocation().add(0, 0, 0).getBlock().getType().equals(Material.WATER)
				|| !p.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)
				|| !p.getLocation().getBlock().getType().equals(Material.WATER)) {
			if (p.getAllowFlight())
				p.setAllowFlight(false);
			p.setFlying(false);
		}
	}
}
