package br.com.weavenmc.skywars.player;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

import br.com.weavenmc.skywars.game.GameController;

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

public class PlayerController {
	
	public static boolean live;
	public static HashMap<UUID, Integer> kills = new HashMap<>();
	
	public static PlayerManager getPlayer(Player player) {
		return (PlayerManager) GameController.check.get(player.getUniqueId());
	}

}
