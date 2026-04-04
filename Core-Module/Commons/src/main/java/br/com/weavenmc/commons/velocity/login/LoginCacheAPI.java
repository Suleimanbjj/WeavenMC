package br.com.weavenmc.commons.velocity.login;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

public class LoginCacheAPI {

	private final static Map<String, LoginCache> CACHES = new ConcurrentHashMap<>();
	
	public static LoginCache getCache(String ipAddress) {
		return CACHES.get(ipAddress);
	}
	
	public static LoginCache addCache(String ipAddress, String username) {
		LoginCache cache = new LoginCache();
		cache.addSession(username);
		return CACHES.computeIfAbsent(ipAddress, c -> cache);
	}
}
