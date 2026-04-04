package br.com.weavenmc.commons.bukkit;

import org.bukkit.Server;
import org.bukkit.event.Listener;
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

public abstract class BukkitCommon {
	
	private BukkitMain plugin;
	
	public BukkitCommon(BukkitMain plugin) {
		this.plugin = plugin;
	}
	
	public abstract void onEnable();
	
	public abstract void onDisable();

	public BukkitMain getPlugin() {
		return plugin;
	}
	
	public Server getServer() {
		return plugin.getServer();
	}
	
	public void registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, plugin);
	}
}
