package br.com.weavenmc.hg.game.structures;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

import br.com.weavenmc.hg.bo2.BO2Constructor.FutureBlock;
import br.com.weavenmc.hg.manager.Manager;
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

public abstract class Structure {

	private final List<FutureBlock> blocks;

	private final Manager manager;

	protected Location location;

	public Structure(Manager manager) {
		this.manager = manager;

		this.blocks = new ArrayList<>();
	}

	public Structure(Manager manager, Location location) {
		this.manager = manager;
		this.location = location;

		this.blocks = new ArrayList<>();
	}

	public Manager getManager() {
		return manager;
	}

	public Location getLocation() {
		return location;
	}

	public List<FutureBlock> getBlocks() {
		return blocks;
	}
}
