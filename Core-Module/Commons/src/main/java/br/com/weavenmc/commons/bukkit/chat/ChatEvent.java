package br.com.weavenmc.commons.bukkit.chat;

import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
public class ChatEvent {

	private final Player player;
	private final Set<Player> recipients;
	private String message;
	private String format;
	private boolean cancelled;
	
	public ChatEvent(final AsyncPlayerChatEvent event) {
		player = event.getPlayer();
		recipients = event.getRecipients();
		message = event.getMessage();
		format = event.getFormat();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
