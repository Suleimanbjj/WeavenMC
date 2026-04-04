package br.com.weavenmc.login.account;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
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

public class SimpleManager {

	private static final HashMap<UUID, Authenticator> authenticators = new HashMap<>();
	
	public static Authenticator getAuthenticator(UUID id) {
		return authenticators.get(id);
	}
	
	public static void loadAuthenticator(Authenticator authenticator) {
		authenticators.put(authenticator.getId(), authenticator);
	}
	
	public static void unloadAuthenticator(UUID id) {
		authenticators.remove(id);
	}
	
	public static Collection<Authenticator> getAuthenticators() {
		return authenticators.values();
	}
}
