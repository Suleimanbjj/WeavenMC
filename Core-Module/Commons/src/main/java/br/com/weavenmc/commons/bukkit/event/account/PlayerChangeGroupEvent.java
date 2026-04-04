package br.com.weavenmc.commons.bukkit.event.account;

import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.event.PlayerCancellableEvent;
import br.com.weavenmc.commons.core.permission.Group;
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
public class PlayerChangeGroupEvent extends PlayerCancellableEvent {

	private Group group;
	private long groupTime;
	
	public PlayerChangeGroupEvent(Player player, Group group, long groupTime) {
		super(player);
		this.group = group;
		this.groupTime = groupTime;
	}
}
