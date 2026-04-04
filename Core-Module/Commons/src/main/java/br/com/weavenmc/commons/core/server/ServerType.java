package br.com.weavenmc.commons.core.server;

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
public enum ServerType {

	TOURNAMENT(18), 
	LOGIN(17), 
	PRACTICE(16), 
	DOUBLEKITHG(15), 
	INSANEHG(14), 
	TEAMHG(13), 
	SINGLEKITHG(12), 
	GLADIATOR(11), 
	PVP_FULLIRON(10), 
	PVP_SIMULATOR(9), 
	SKYWARS(8),
	SKYWARS_LOBBY(7), 
	HG_LOBBY(6), 
	LOBBY(5), 
	TESTSERVER(4), 
	SCREENSHARE(3), 
	NETWORK(2), 
	NONE(1);
	
	private int id;
	
	private ServerType(int id) {
		this.id = id;
	}

    public boolean isLobby() {
        return this == LOBBY || this == SKYWARS_LOBBY;
    }

    public ServerType getServerLobby() {
        switch (this) {
            case SKYWARS:
                return SKYWARS_LOBBY;
            case DOUBLEKITHG:
            	return HG_LOBBY;
            case TEAMHG:
            	return HG_LOBBY;
            default:
                return LOBBY;
        }
}

}
