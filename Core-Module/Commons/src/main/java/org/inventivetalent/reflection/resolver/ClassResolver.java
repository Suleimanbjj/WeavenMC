package org.inventivetalent.reflection.resolver;

import org.inventivetalent.reflection.resolver.wrapper.ClassWrapper;
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
 * Default {@link ClassResolver}
 */
@SuppressWarnings("rawtypes")
public class ClassResolver extends ResolverAbstract<Class> {

	@SuppressWarnings({ "unchecked" })
	public ClassWrapper resolveWrapper(String... names) {
		return new ClassWrapper<>(resolveSilent(names));
	}

	public Class resolveSilent(String... names) {
		try {
			return resolve(names);
		} catch (Exception e) {
		}
		return null;
	}

	public Class resolve(String... names) throws ClassNotFoundException {
		ResolverQuery.Builder builder = ResolverQuery.builder();
		for (String name : names)
			builder.with(name);
		try {
			return super.resolve(builder.build());
		} catch (ReflectiveOperationException e) {
			throw (ClassNotFoundException) e;
		}
	}

	@Override
	protected Class resolveObject(ResolverQuery query) throws ReflectiveOperationException {
		return Class.forName(query.getName());
	}

	@Override
	protected ClassNotFoundException notFoundException(String joinedNames) {
		return new ClassNotFoundException("Could not resolve class for " + joinedNames);
	}
}
