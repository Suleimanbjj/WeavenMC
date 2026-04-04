package org.inventivetalent.hologram.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

public abstract class AccessUtil {

	/**
	 * Set a specified Field accessible
	 *
	 * @param f Field set accessible
	 */
	public static Field setAccessible(Field f) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
		return f;
	}

	/**
	 * Set a specified Method accessible
	 *
	 * @param m Method set accessible
	 */
	public static Method setAccessible(Method m) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.setAccessible(true);
		return m;
	}

}
