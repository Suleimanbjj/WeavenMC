package br.com.weavenmc.ypvp.managers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

import br.com.weavenmc.ypvp.Management;
import br.com.weavenmc.ypvp.yPvP;

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

public class CooldownManager extends Management {

	private Map<UUID, Long> C;

	public CooldownManager(yPvP plugin) {
		super(plugin);
	}

	@Override
	public void enable() {
		C = new ConcurrentHashMap<>();
	}

	public void addCooldown(Player player, int time) {
		C.put(player.getUniqueId(), (System.currentTimeMillis() + (time * 1000L)));
	}

	public double getCooldown(Player p) {
		return C.containsKey(p.getUniqueId())
				? (double) (C.get(p.getUniqueId()) - System.currentTimeMillis()) / 10 / 100
				: 0;
	}

	public boolean hasCooldown(Player p) {
		return C.containsKey(p.getUniqueId()) ? C.get(p.getUniqueId()) >= System.currentTimeMillis() ? true : false
				: false;
	}

	public void removeCooldown(Player p) {
		C.remove(p.getUniqueId());
	}
	@Override
	public void disable() {
		C.clear();
		C = null;
	}
}
