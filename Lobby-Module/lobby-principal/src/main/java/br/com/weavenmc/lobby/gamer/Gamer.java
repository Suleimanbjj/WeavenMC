package br.com.weavenmc.lobby.gamer;

import java.util.UUID;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.scoreboard.Sidebar;
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
public class Gamer {

	private String name;
	private UUID uniqueId;
	
	@Setter
	private Sidebar sidebar;
	@Setter
	private boolean flying = false;
	
	public Gamer(BukkitPlayer bP) {
		this.name = bP.getName();
		this.uniqueId = bP.getUniqueId();
	}
}
