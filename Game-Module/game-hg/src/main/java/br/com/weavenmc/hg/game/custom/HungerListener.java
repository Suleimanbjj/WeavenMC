package br.com.weavenmc.hg.game.custom;

import org.bukkit.event.Listener;
import br.com.weavenmc.hg.HungerGames;
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

public class HungerListener implements Listener {

	public Manager getManager() {
		return HungerGames.getManager();
	}

}
