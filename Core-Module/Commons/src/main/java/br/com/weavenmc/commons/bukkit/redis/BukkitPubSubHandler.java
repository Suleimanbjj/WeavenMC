package br.com.weavenmc.commons.bukkit.redis;

import org.bukkit.Bukkit;
import br.com.weavenmc.commons.bukkit.event.redis.BukkitRedisMessageEvent;
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

public class BukkitPubSubHandler extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        Bukkit.getPluginManager().callEvent(new BukkitRedisMessageEvent(channel, message));
    }
}
