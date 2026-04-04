package br.com.weavenmc.commons.core.account;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
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

public class AccountManager {

	private Map<UUID, WeavenPlayer> players;
	
	public AccountManager(){
		players = new ConcurrentHashMap<>();
	}
	
	public void loadWeavenPlayer(UUID uuid, WeavenPlayer player) {
		players.put(uuid, player);
	}
	
	public WeavenPlayer getWeavenPlayer(UUID uuid) {
		return players.get(uuid);
	}
	
	public void unloadWeavenPlayer(UUID uuid) {
		players.remove(uuid);
	}
	
	public Collection<WeavenPlayer> getPlayers() {
		return players.values();
	}
}
