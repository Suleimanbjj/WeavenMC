package br.com.weavenmc.skywars.player;

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

public class PlayerManager {
	
	private static UUID uuid;
	private static int kills;

	
	public PlayerManager(Player player) {
		uuid = player.getUniqueId();
		if (!GameController.player.contains(player.getUniqueId())) {
			PlayerController.live = false;
		} else {
			PlayerController.live = true;
		}
		PlayerManager.kills = PlayerController.kills.get(player.getUniqueId());
	}
	
	public void setHash() {
		PlayerController.kills.put(uuid, getKills());
	}
	
	public void setArray() {
		if (PlayerController.live == true) {
			GameController.player.add(uuid);
			GameController.spectador.remove(uuid);
		} else {
			GameController.player.remove(uuid);
			GameController.spectador.add(uuid);
		}
	}
	
	public int getKills() {
		return kills;
	}
	
	public UUID getUuid() {
		return uuid;
	}

}
