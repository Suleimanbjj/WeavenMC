package br.com.weavenmc.commons.bukkit.event.account;

import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import lombok.Setter;

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
@Setter
public class PlayerChangeTagEvent extends PlayerCancellableEvent {
	
	private String teamID;
	private String tagPrefix;
	private String tagSuffix;

	public PlayerChangeTagEvent(Player player, String teamID, String tagPrefix, String tagSuffix) {
		super(player);
		this.teamID = teamID;
		this.tagPrefix = tagPrefix;
		this.tagSuffix = tagSuffix;
	}
}
