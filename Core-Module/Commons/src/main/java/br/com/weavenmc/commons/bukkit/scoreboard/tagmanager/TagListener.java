package br.com.weavenmc.commons.bukkit.scoreboard.tagmanager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;

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

public class TagListener implements Listener {

	private TagManager manager;
	
	public TagListener(TagManager manager) {
		this.manager = manager;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		manager.removePlayerTag(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinFirst(PlayerJoinEvent event) {
		event.getPlayer().setScoreboard(manager.getServer().getScoreboardManager().getNewScoreboard());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinListener(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		BukkitPlayer player = BukkitPlayer.getPlayer(p.getUniqueId());
		if (player == null)
			return;
		player.updateTags();
		manager.setTag(p, player.getTags().get(0));
		player = null;
		p = null;
	}
}
