package br.com.weavenmc.ypvp.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import br.com.weavenmc.ypvp.Management;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.gamer.Gamer;

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

public class GamerManager extends Management {

	private HashMap<UUID, Gamer> gamers;
	
	public GamerManager(yPvP plugin) {
		super(plugin);
	}

	public void loadGamer(UUID uuid, Gamer gamer) {
		gamers.put(uuid, gamer);
	}
	
	public Gamer getGamer(UUID uuid) {
		return gamers.get(uuid);
	}
	
	public Collection<Gamer> getGamers() {
		return gamers.values();
	}
	
	public void unloadGamer(UUID uuid) {
		gamers.remove(uuid);
	}
	
	@Override
	public void enable() {
		gamers = new HashMap<>();
	}

	@Override
	public void disable() {
		gamers.clear();
		gamers = null;
	}
}
