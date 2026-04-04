package org.inventivetalent.reflection.util;

import java.lang.reflect.Constructor;
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

/**
 * Helper class to set fields, methods &amp; constructors accessible
 */
public abstract class AccessUtil {

	/**
	 * Sets the field accessible and removes final modifiers
	 *
	 * @param field Field to set accessible
	 * @return the Field
	 * @throws ReflectiveOperationException  (usually never)
	 */
	public static Field setAccessible(Field field) throws ReflectiveOperationException {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
		return field;
	}

	/**
	 * Sets the method accessible
	 *
	 * @param method Method to set accessible
	 * @return the Method
	 * @throws ReflectiveOperationException  (usually never)
	 */
	public static Method setAccessible(Method method) throws ReflectiveOperationException {
		method.setAccessible(true);
		return method;
	}

	/**
	 * Sets the constructor accessible
	 *
	 * @param constructor Constructor to set accessible
	 * @return the Constructor
	 * @throws ReflectiveOperationException  (usually never)
	 */
	@SuppressWarnings("rawtypes")
	public static Constructor setAccessible(Constructor constructor) throws ReflectiveOperationException {
		constructor.setAccessible(true);
		return constructor;
	}
}
