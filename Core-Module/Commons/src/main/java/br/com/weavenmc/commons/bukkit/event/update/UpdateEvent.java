package br.com.weavenmc.commons.bukkit.event.update;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
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

public class UpdateEvent extends Event {

	public static final HandlerList handlers = new HandlerList();
	private UpdateType type;
	private long currentTick;
	
	@Deprecated
	protected UpdateEvent() {}

	@Deprecated
	protected UpdateEvent(UpdateType type) {
		this(type, -1);
	}
	
	public UpdateEvent(UpdateType type, long currentTick) {
		this.type = type;
		this.currentTick = currentTick;
	}

	public UpdateType getType() {
		return type;
	}

	public long getCurrentTick() {
		return currentTick;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public static enum UpdateType {
		TICK, SECOND, MINUTE, HOUR;
	}
}
