package br.com.weavenmc.commons.bukkit.api.player;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;

import com.comphenix.protocol.utility.MinecraftReflection;

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

public class PingAPI {

	public static int getPing(Player player) {
		Object entityPlayer;
		try {
			entityPlayer = MinecraftReflection.getCraftPlayerClass().getMethod("getHandle").invoke(player);
			return (int) MinecraftReflection.getEntityPlayerClass().getField("ping").get(entityPlayer);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
