package br.com.weavenmc.commons.core.data.player.type;

import java.util.ArrayList;
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
public enum DataType {

	ACCOUNT_UUID(null, "String", "uniqueId", "VARCHAR(50)"), 
	USERNAME(null, "String", "username", "VARCHAR(17)"),
	GROUPS(new ArrayList<>(), "String", "groups", "VARCHAR(8000)"),
	CLAN_NAME("Nenhum", "String", "clan", "VARCHAR(16)"),
	PASSWORD("*", "String", "password", "VARCHAR(30)"),
	CITY("", "String", "city", "VARCHAR(30)"),
	COUNTRY("", "String", "country", "VARCHAR(30)"),
	PLAYER_PERMISSIONS(new ArrayList<>(), "String", "permissions", "VARCHAR(8000)"),

	TICKETS(0, "Int", "tickets", "INT(100)"),
	MONEY(0, "Int", "money", "INT(100)"),
	TOTAL_XP(0, "Int", "totalXp", "INT(100)"),
	XP(0, "Int", "xp", "INT(100)"),
	CURRENT_LEAGUE("UNRANKED", "String", "league", "VARCHAR(20)"),
	CASH(0, "Int", "cash", "INT(100)"),
	DOUBLEXPMULTIPLIER(0, "Int", "doubleXpMultiplier", "INT(100)"),
	LASTACTIVATEDMULTIPLIER(-1L, "Long", "lastActivatedMultiplier", "VARCHAR(100)"),
	
	PVP_KILLS(0, "Int", "kills", "INT(100)"),
	PVP_DEATHS(0, "Int", "deaths", "INT(100)"),
	PVP_KILLSTREAK(0, "Int", "streak", "INT(100)"),
	PVP_GREATER_KILLSTREAK(0, "Int", "greater_streak", "INT(100)"),
	
	LAVA_CHALLENGE_EASY_COMPLETIONS(0, "Int", "easy", "INT(100)"),
	LAVA_CHALLENGE_MEDIUM_COMPLETIONS(0, "Int", "medium", "INT(100)"),
	LAVA_CHALLENGE_HARD_COMPLETIONS(0, "Int", "hard", "INT(100)"),
	LAVA_CHALLENGE_EXTREME_COMPLETIONS(0, "Int", "extreme", "INT(100)"),
	
	PVP_1V1_KILLS(0, "Int", "kills", "INT(100)"),
	PVP_1V1_DEATHS(0, "Int", "deaths", "INT(100)"),
	PVP_1V1_KILLSTREAK(0, "Int", "streak", "INT(100)"),
	PVP_1V1_GREATER_KILLSTREAK(0, "Int", "greater_streak", "INT(100)"),
	
	GLADIATOR_WINS(0, "Int", "wins", "INT(100)"),
	GLADIATOR_LOSES(0, "Int", "loses", "INT(100)"),
	GLADIATOR_WINSTREAK(0, "Int", "streak", "INT(100)"),
	GLADIATOR_GREATER_WINSTREAK(0, "Int", "greater_streak", "INT(100)"),
	
	SKYWARS_SOLO_WINS(0, "Int", "solo_wins", "INT(100)"),
	SKYWARS_SOLO_KILLS(0, "Int", "solo_kills", "INT(100)"),
	SKYWARS_DUO_WINS(0, "Int", "duo_wins", "INT(100)"),
	SKYWARS_DUO_KILLS(0, "Int", "duo_kills", "INT(100)"),
	SKYWARS_TEAM_WINS(0, "Int", "team_wins", "INT(100)"),
	SKYWARE_TEAM_KILLS(0, "Int", "team_kills", "INT(100)"),
	
	HG_WINS(0, "Int", "wins", "INT(100)"),
	HG_DEATHS(0, "Int", "deaths", "INT(100)"),
	HG_KILLS(0, "Int", "kills", "INT(100)"),
	HG_HAS_PENDENT_WINNER(false, "Boolean", "winner", "VARCHAR(20)"),
	PARTICIPANDO(false, "Boolean", "participando", "VARCHAR(10)"),
	
	TEAMHG_WINS(0, "Int", "wins", "INT(100)"),
	TEAMHG_DEATHS(0, "Int", "deaths", "INT(100)"),
	TEAMHG_KILLS(0, "Int", "kills", "INT(100)"),
	
	SILVER_CRATES(0, "Int", "silver", "INT(100)"),
	GOLD_CRATES(0, "Int", "gold", "INT(100)"),
	DIAMOND_CRATES(0, "Int", "diamond", "INT(100)"),
	
	PVP_100MORE_KILLS(false, "Boolean", "pvpmore100kills", "VARCHAR(10)"),
	PVP_50_KILLSTREAK(false, "Boolean", "pvp50streak", "VARCHAR(10)"),
	HG_50MORE_WINS(false, "Boolean", "hgmore50wins", "VARCHAR(10)"),
	GLADIATOR_40_WINSTREAK(false, "Boolean", "gladmore40streak", "VARCHAR(10)"),
	PVP_1V1_25_KILLSTREAK(false, "Boolean", "pvp1v125streak", "VARCHAR(10)"),
	SERVER_1HOURS_PLAYING_PER_DAY(false, "Boolean", "server1hourplayed", "VARCHAR(10)"),
	
	COMPUTER_ADDRESS("", "String", "macAddress", "VARCHAR(50)"),
	LAST_COMPUTER_ADDRES("", "String", "lastMacAddress", "VARCHAR(50)"),
	IP_ADDRESS("", "String", "ipAddress", "VARCHAR(50)"),
	LAST_IP_ADDRESS("", "String", "lastIpAddress", "VARCHAR(50)"),
	ONLINE_TIME(0L, "Long", "onlineTime", "VARCHAR(100)"),
	JOIN_TIME(0L, "Long", "joinTime", "VARCHAR(100)"),
	FIRST_LOGGED_IN(0L, "Long", "firstLoggedIn", "VARCHAR(100)"),
	LAST_LOGGED_IN(0L, "Long", "lastLoggedIn", "VARCHAR(100)"),
	
	SKYPE("Nenhum", "String", "skype", "VARCHAR(8000)"),
	TWITTER("Nenhum", "String", "twitter", "VARCHAR(8000)"),
	DISCORD("Nenhum", "String", "discord", "VARCHAR(8000)"),
	YOUTUBE_CHANNEL("Nenhum", "String", "ytChannel", "VARCHAR(8000)"),
	STEAM("Nenhum", "String", "steam", "VARCHAR(8000)"),
	TWITCH("Nenhum", "String", "twitch", "VARCHAR(8000)"),
	
	IS_ONLINE(false, "Boolean", "online", "VARCHAR(10)"),
	IN_SCREENSHARE(false, "Boolean", "screensharing", "VARCHAR(10)"),
	SERVER_CONNECTED("none", "String", "server", "VARCHAR(100)"),
	SERVER_CONNECTED_TYPE("NONE", "String", "serverType", "VARCHAR(100)"),
	LAST_SERVER_CONNECTED("none", "String", "lastServer", "VARCHAR(100)"),
	LAST_SERVR_CONNECTED_TYPE("NONE", "String", "lastServerType", "VARCHAR(100)");
	
	private Object defaultValue;
	private String classExpected;
	private String field;
	private String tableType;
	
	private DataType(Object defaultValue, String classExpected, String field, String tableType) {
		this.defaultValue = defaultValue;
		this.field = field;
		this.classExpected = classExpected;
		this.tableType = tableType;
	}
	
	public boolean isUUID() {
		return this == ACCOUNT_UUID;
	}
	
	public boolean isUsername() {
		return this == USERNAME;
	}
}
