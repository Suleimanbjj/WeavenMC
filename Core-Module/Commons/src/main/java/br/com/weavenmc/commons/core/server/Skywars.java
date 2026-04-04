package br.com.weavenmc.commons.core.server;

import com.google.gson.JsonObject;
import br.com.weavenmc.commons.WeavenMC;
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
public class Skywars extends NetworkServer {
	
	private int time;
	private int playersLeft;
	private SkywarsState state;

	public Skywars(String serverId, int port, ServerType serverType, int maxPlayers, int players, boolean joinEnabled,
			boolean online, long lastData, int time, int playersLeft, SkywarsState state) {
		super(serverId, port, serverType, maxPlayers, players, joinEnabled, online, lastData);
		this.time = time;
		this.playersLeft = playersLeft;
		this.state = state;
	}
	
	@Override
	public boolean canBeSelected() {
		return super.canBeSelected() && !isInProgress() && playersLeft > 0;
		
	}
	
	public boolean isInProgress() {
		return getState() == SkywarsState.GAME || getState() == SkywarsState.JAIL || getState() == SkywarsState.END;
	}
	
	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.add("id", WeavenMC.getGson().toJsonTree(getServerId()));
		json.add("port", WeavenMC.getGson().toJsonTree(getPort()));
		json.add("type", WeavenMC.getGson().toJsonTree(getServerType().toString()));
		json.add("maxPlayers", WeavenMC.getGson().toJsonTree(getMaxPlayers()));
		json.add("players", WeavenMC.getGson().toJsonTree(getPlayers()));
		json.add("joinEnabled", WeavenMC.getGson().toJsonTree(String.valueOf(isJoinEnabled())));
		json.add("online", WeavenMC.getGson().toJsonTree(String.valueOf(isOnline())));
		json.add("lastData", WeavenMC.getGson().toJsonTree(String.valueOf(getLastData())));
		json.add("state", WeavenMC.getGson().toJsonTree(state.name()));
		json.add("time", WeavenMC.getGson().toJsonTree(time));
		json.add("playersLeft", WeavenMC.getGson().toJsonTree(playersLeft));
		return json;
	}

	public enum SkywarsState {
		
		LOBBY, JAIL, GAME, END;
	}
}
