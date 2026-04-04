package br.com.weavenmc.commons.bukkit.api.player;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;

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

public class ProfileAPI {
	
	public static final LoadingCache<WrappedGameProfile, WrappedSignedProperty> Textures = CacheBuilder.newBuilder()
			.expireAfterWrite(30L, TimeUnit.MINUTES)
			.build(new CacheLoader<WrappedGameProfile, WrappedSignedProperty>() {
				@Override
				public WrappedSignedProperty load(WrappedGameProfile key) throws Exception {
					return loadTextures(key);
				}
			});

	private static final WrappedSignedProperty loadTextures(WrappedGameProfile profile) {
		try {
			Object minecraftServer = MinecraftReflection.getMinecraftServerClass().getMethod("getServer").invoke(null);
			((MinecraftSessionService) minecraftServer.getClass().getMethod("aD").invoke(minecraftServer))
					.fillProfileProperties((GameProfile) profile.getHandle(), true);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		return Iterables.getFirst(profile.getProperties().get("textures"), null);
	}
}
