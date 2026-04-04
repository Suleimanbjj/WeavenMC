package br.com.weavenmc.skywars.scoreboard;

import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;

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

public class FastOfflinePlayer implements OfflinePlayer {
	
	private final String playerName;

	public FastOfflinePlayer(String playerName) {
		this.playerName = playerName;
	}

	public boolean isOnline() {
		return false;
	}

	public String getName() {
		return this.playerName;
	}

	public UUID getUniqueId() {
		return UUID.nameUUIDFromBytes(this.playerName.getBytes(Charsets.UTF_8));
	}

	public boolean isBanned() {
		return false;
	}

	public void setBanned(boolean banned) {
		throw new UnsupportedOperationException();
	}

	public boolean isWhitelisted() {
		return false;
	}

	public void setWhitelisted(boolean value) {
		throw new UnsupportedOperationException();
	}

	public Player getPlayer() {
		throw new UnsupportedOperationException();
	}

	public long getFirstPlayed() {
		return System.currentTimeMillis();
	}

	public long getLastPlayed() {
		return System.currentTimeMillis();
	}

	public boolean hasPlayedBefore() {
		return false;
	}

	public Location getBedSpawnLocation() {
		throw new UnsupportedOperationException();
	}

	public boolean isOp() {
		return false;
	}

	public void setOp(boolean value) {
		throw new UnsupportedOperationException();
	}

	public Map<String, Object> serialize() {
		throw new UnsupportedOperationException();
	}
}
