package br.com.weavenmc.hg.player.events;

import org.bukkit.entity.Player;
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

public class PlayerAdminEvent extends CustomEvent {

	private final Player player;
	private final boolean join;

	public PlayerAdminEvent(Player player, boolean join) {
		this.player = player;
		this.join = join;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isJoin() {
		return join;
	}
}
