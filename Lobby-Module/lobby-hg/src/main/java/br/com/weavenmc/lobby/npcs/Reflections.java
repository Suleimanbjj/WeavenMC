package br.com.weavenmc.lobby.npcs;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.Packet;

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

public class Reflections {
	public static void setValue(String name, Object obj, Object value) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception exception) {
		}
	}

	public static Object getValue(String name, Object obj) {
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception exception) {
			return null;
		}
	}

	public static void sendPacket(Packet<?> packet, Player player) {
		(((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packet);
	}

	public static void sendPacket(Packet<?> packet) {
		for (Player player : Bukkit.getOnlinePlayers())
			sendPacket(packet, player);
	}
}