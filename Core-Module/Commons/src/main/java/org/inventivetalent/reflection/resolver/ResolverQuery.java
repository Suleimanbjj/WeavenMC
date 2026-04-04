package org.inventivetalent.reflection.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * Container class for resolver-queries Used by {@link MethodResolver}
 *
 * @see org.inventivetalent.reflection.resolver.ResolverQuery.Builder
 */
public class ResolverQuery {

	private String     name;
	private Class<?>[] types;

	public ResolverQuery(String name, Class<?>... types) {
		this.name = name;
		this.types = types;
	}

	public ResolverQuery(String name) {
		this.name = name;
		this.types = new Class[0];
	}

	public ResolverQuery(Class<?>... types) {
		this.types = types;
	}

	public String getName() {
		return name;
	}

	public Class<?>[] getTypes() {
		return types;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		ResolverQuery that = (ResolverQuery) o;

		if (name != null ? !name.equals(that.name) : that.name != null) { return false; }
		// Probably incorrect - comparing Object[] arrays with Arrays.equals
		return Arrays.equals(types, that.types);

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (types != null ? Arrays.hashCode(types) : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ResolverQuery{" +
				"name='" + name + '\'' +
				", types=" + Arrays.toString(types) +
				'}';
	}

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder class for {@link ResolverQuery} Access using {@link ResolverQuery#builder()}
	 */
	public static class Builder {

		private List<ResolverQuery> queryList = new ArrayList<ResolverQuery>();

		private Builder() {
		}

		public Builder with(String name, Class<?>[] types) {
			queryList.add(new ResolverQuery(name, types));
			return this;
		}

		public Builder with(String name) {
			queryList.add(new ResolverQuery(name));
			return this;
		}

		public Builder with(Class<?>[] types) {
			queryList.add(new ResolverQuery(types));
			return this;
		}

		public ResolverQuery[] build() {
			return queryList.toArray(new ResolverQuery[queryList.size()]);
		}

	}
}
