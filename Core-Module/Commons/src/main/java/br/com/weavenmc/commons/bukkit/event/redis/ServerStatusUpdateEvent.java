package br.com.weavenmc.commons.bukkit.event.redis;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import br.com.weavenmc.commons.core.server.NetworkServer;
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

public class ServerStatusUpdateEvent extends Event implements Cancellable {

	private final static HandlerList handlers = new HandlerList();
	
	@Setter
	@Getter
	private NetworkServer weavenServer;
	@Setter
	@Getter
	private String messageChannel;
	private boolean cancel = false;
	
	public ServerStatusUpdateEvent(NetworkServer server, String messageChannel) {
		this.weavenServer = server;
		this.messageChannel = messageChannel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
