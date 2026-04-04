package br.com.weavenmc.hg.game.arena;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
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

public class ArenaManager extends Management {

	public final List<Player> gamersAlready = new ArrayList<>();
	private Location lastLocation = null;

	public ArenaManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		return true;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public List<Player> getGamersAlready() {
		return gamersAlready;
	}

}
