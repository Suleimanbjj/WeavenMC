package br.com.weavenmc.commons.bukkit.event.redis;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
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
public class BukkitRedisMessageEvent extends Event {

	private final static HandlerList handlers = new HandlerList();
	
	private String channel, message;
	
	public BukkitRedisMessageEvent(String channel, String message) {
		this.channel = channel;
		this.message = message;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
