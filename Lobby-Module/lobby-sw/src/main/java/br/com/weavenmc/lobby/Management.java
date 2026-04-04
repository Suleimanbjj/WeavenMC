package br.com.weavenmc.lobby;

import org.bukkit.Server;
import org.bukkit.event.Listener;
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
public abstract class Management {

	private Lobby plugin;
	
	public Management(Lobby plugin) {
		this.plugin = plugin;
	}
	
	public abstract void enable();
	
	public abstract void disable();
	
	public void registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, getPlugin());
	}
	
	public Server getServer() {
		return plugin.getServer();
	}
}
