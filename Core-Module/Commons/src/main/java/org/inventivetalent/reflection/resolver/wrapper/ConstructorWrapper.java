package org.inventivetalent.reflection.resolver.wrapper;

import java.lang.reflect.Constructor;
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

public class ConstructorWrapper<R> extends WrapperAbstract {

	private final Constructor<R> constructor;

	public ConstructorWrapper(Constructor<R> constructor) {
		this.constructor = constructor;
	}

	@Override
	public boolean exists() {
		return this.constructor != null;
	}

	public R newInstance(Object... args) {
		try {
			return this.constructor.newInstance(args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public R newInstanceSilent(Object... args) {
		try {
			return this.constructor.newInstance(args);
		} catch (Exception e) {
		}
		return null;
	}

	public Class<?>[] getParameterTypes() {
		return this.constructor.getParameterTypes();
	}

	public Constructor<R> getConstructor() {
		return constructor;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) { return true; }
		if (object == null || getClass() != object.getClass()) { return false; }

		ConstructorWrapper<?> that = (ConstructorWrapper<?>) object;

		return constructor != null ? constructor.equals(that.constructor) : that.constructor == null;

	}

	@Override
	public int hashCode() {
		return constructor != null ? constructor.hashCode() : 0;
	}
}
