package org.inventivetalent.hologram.reflection;

import java.lang.reflect.Field;
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

public abstract class NMUClass {

	private static boolean initialized = false;

	public static Class<?> gnu_trove_map_TIntObjectMap;
	public static Class<?> gnu_trove_map_hash_TIntObjectHashMap;
	public static Class<?> gnu_trove_impl_hash_THash;
	public static Class<?> io_netty_channel_Channel;

	static {
		if (!initialized) {
			for (Field f : NMUClass.class.getDeclaredFields()) {
				if (f.getType().equals(Class.class)) {
					try {
						String name = f.getName().replace("_", ".");
						f.set(null, Class.forName(name));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			initialized = true;
		}
	}

}
