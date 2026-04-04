package org.inventivetalent.reflection.resolver.minecraft;

import org.inventivetalent.reflection.minecraft.Minecraft;
import org.inventivetalent.reflection.resolver.ClassResolver;
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

/**
 * {@link ClassResolver} for <code>org.bukkit.craftbukkit.*</code> classes
 */
public class OBCClassResolver extends ClassResolver {

	@SuppressWarnings("rawtypes")
	@Override
	public Class resolve(String... names) throws ClassNotFoundException {
		for (int i = 0; i < names.length; i++) {
			if (!names[i].startsWith("org.bukkit.craftbukkit")) {
				names[i] = "org.bukkit.craftbukkit." + Minecraft.getVersion() + names[i];
			}
		}
		return super.resolve(names);
	}
}
