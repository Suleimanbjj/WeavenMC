package br.com.weavenmc.ypvp.tournament;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import br.com.weavenmc.commons.bukkit.event.update.UpdateEvent;
import br.com.weavenmc.commons.bukkit.event.update.UpdateEvent.UpdateType;

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

public class TournamentListener implements Listener {

	@EventHandler
	public void onTournamentUpdate(UpdateEvent event) {
		if (event.getType() == UpdateType.MINUTE) {
			
		}
	}
}
