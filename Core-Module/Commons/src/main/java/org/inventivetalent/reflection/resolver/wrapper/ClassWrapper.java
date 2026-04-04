package org.inventivetalent.reflection.resolver.wrapper;

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
public class ClassWrapper<R> extends WrapperAbstract {

	private final Class<R> clazz;

	public ClassWrapper(Class<R> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean exists() {
		return this.clazz != null;
	}

	public Class<R> getClazz() {
		return clazz;
	}

	public String getName() {
		return this.clazz.getName();
	}

	public R newInstance() {
		try {
			return this.clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public R newInstanceSilent() {
		try {
			return this.clazz.newInstance();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) { return true; }
		if (object == null || getClass() != object.getClass()) { return false; }

		ClassWrapper<?> that = (ClassWrapper<?>) object;

		return clazz != null ? clazz.equals(that.clazz) : that.clazz == null;

	}

	@Override
	public int hashCode() {
		return clazz != null ? clazz.hashCode() : 0;
	}
}
