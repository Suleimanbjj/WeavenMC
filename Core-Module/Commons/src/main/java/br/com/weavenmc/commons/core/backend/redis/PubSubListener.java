package br.com.weavenmc.commons.core.backend.redis;

import java.util.logging.Level;
import br.com.weavenmc.commons.WeavenMC;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

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

public class PubSubListener implements Runnable {
	private JedisPubSub jpsh;

	private final String[] channels;

	public PubSubListener(JedisPubSub s, String... channels) {
		this.jpsh = s;
		this.channels = channels;
	}

	@Override
	public void run() {
		boolean broken = false;
		try (Jedis rsc = WeavenMC.getCommonRedis().getPool().getResource()) {
			try {
				rsc.subscribe(jpsh, channels);
			} catch (Exception e) {
				WeavenMC.getLogger().log(Level.INFO, "PubSub error, attempting to recover.", e);
				try {
					jpsh.unsubscribe();
				} catch (Exception e1) {

				}
				broken = true;
			}
		}

		if (broken) {
			run();
		}
	}

	public void addChannel(String... channel) {
		jpsh.subscribe(channel);
	}

	public void removeChannel(String... channel) {
		jpsh.unsubscribe(channel);
	}

	public void poison() {
		jpsh.unsubscribe();
	}
}
