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
public abstract class WrapperAbstract {

	/**
	 * Check whether the wrapped object exists (i.e. is not null)
	 *
	 * @return <code>true</code> if the wrapped object exists
	 */
	public abstract boolean exists();

}
