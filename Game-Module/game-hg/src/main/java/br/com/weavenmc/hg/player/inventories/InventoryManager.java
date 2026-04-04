package br.com.weavenmc.hg.player.inventories;

import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
import br.com.weavenmc.hg.player.inventories.inventory.KitSelectorHandler;
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

public class InventoryManager extends Management {

	private KitSelectorHandler kitSelectorHandler;

	public InventoryManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		this.kitSelectorHandler = new KitSelectorHandler(getManager());

		return kitSelectorHandler != null;
	}

	public KitSelectorHandler getKitSelectorHandler() {
		return kitSelectorHandler;
	}

}
