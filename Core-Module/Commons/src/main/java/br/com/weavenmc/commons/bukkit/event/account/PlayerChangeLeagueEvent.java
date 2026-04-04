package br.com.weavenmc.commons.bukkit.event.account;

import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.event.PlayerCancellableEvent;
import br.com.weavenmc.commons.core.account.League;
import lombok.Getter;

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

@Getter
public class PlayerChangeLeagueEvent extends PlayerCancellableEvent {

	private League currentLeague;
	private League futureLeague;
	
	public PlayerChangeLeagueEvent(Player player, League currentLeague, League futureLeague) {
		super(player);
		this.currentLeague = currentLeague;
		this.futureLeague = futureLeague;
	}
}
