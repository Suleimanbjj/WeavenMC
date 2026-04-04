package br.com.weavenmc.commons.velocity.login;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import br.com.weavenmc.commons.util.string.StringTimeUtils;

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

public class LoginCache {

	private Map<String, Long> loggedNicks;
	
	public LoginCache() {
		loggedNicks = new ConcurrentHashMap<>();
	}
	
	public void clearSessions() {
		loggedNicks.clear();
	}
	
	public void removeSession(String username) {
		if (hasSession(username)) {
			loggedNicks.remove(username.toLowerCase());
		}
	}
	
	public boolean hasSession(String username) {
		if (!loggedNicks.containsKey(username.toLowerCase()))
			return false;
		return loggedNicks.get(username.toLowerCase()) >= System.currentTimeMillis();
	}
	
	public void addSession(String username) {
		long sessionTime;		
		try {
			sessionTime = StringTimeUtils.parseDateDiff("24h", true);
		} catch (Exception ex) {
			sessionTime = ((3600 * 24) * 1000L) + System.currentTimeMillis();
		}
		loggedNicks.put(username.toLowerCase(), sessionTime);
	}
}
