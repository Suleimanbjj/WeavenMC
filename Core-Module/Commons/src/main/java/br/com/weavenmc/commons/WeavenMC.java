package br.com.weavenmc.commons;

import java.util.UUID;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import br.com.weavenmc.commons.core.AsynchronousExecutor;
import br.com.weavenmc.commons.core.account.AccountManager;
import br.com.weavenmc.commons.core.backend.mysql.MySQLBackend;
import br.com.weavenmc.commons.core.backend.redis.RedisBackend;
import br.com.weavenmc.commons.core.clan.ClanCommon;
import br.com.weavenmc.commons.core.data.punish.PunishHistory;
import br.com.weavenmc.commons.core.profile.Profile;
import br.com.weavenmc.commons.core.profile.ProfileCommon;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.core.util.mojang.NameFetcher;
import br.com.weavenmc.commons.core.util.mojang.UUIDFetcher;
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

public class WeavenMC {
	
	public final static long MULTIPLIER_DURATION = 60000 * 60;
	
	public final static String CLAN_FIELD_UPDATE = "clan-field";
	public final static String BROADCAST_SERVER_STARTING = "server-starting";
	public final static String GROUP_MANAGEMENT_CHANNEL = "group-manager";
	public final static String SERVER_INFO_CHANNEL = "server-info";
	public final static String HG_SERVER_INFO_CHANNEL = "server-info-hg";
	public final static String SW_SERVER_INFO_CHANNEL = "server-info-sw";
	
	@Getter
	@Setter
	private static String serverAddress;
	@Getter
	@Setter
	private static String serverId;
	@Getter
	@Setter
	private static ServerType serverType;

	@Getter
	private static ProfileCommon profileCommon = new ProfileCommon();
	@Getter
	private static AccountManager accountCommon = new AccountManager();
	@Getter
	private static ClanCommon clanCommon = new ClanCommon();
	
	@Getter
	private final static JsonParser parser = new JsonParser();
	@Getter
	private final static Gson gson = new Gson();
	
	@Getter
	@Setter
	private static AsynchronousExecutor asynchronousExecutor;
	
	@Getter
	@Setter
	private static PunishHistory punishHistory;
	
	@Getter
	@Setter
	private static RedisBackend commonRedis;	
	@Getter
	@Setter
	private static MySQLBackend commonMysql;
	
	@Getter
	@Setter
	private static Logger logger;
	
	private static UUIDFetcher uuidFetcher = new UUIDFetcher();
	private static NameFetcher nameFetcher = new NameFetcher();
	
	public static String getNameOf(UUID uuid) {
		return nameFetcher.getName(uuid);
	}
	
	public static UUID getUUIDOf(String name) {
		return uuidFetcher.getUUID(name);
	}
	
	public static Profile getProfile(String name) {
		return profileCommon.getProfile(name);
	}
	
	public static Profile getProfile(UUID uuid) {
		return profileCommon.getProfile(uuid);
	}
	
	public static void debug(String s) {
		getLogger().info(s);
	}
}
