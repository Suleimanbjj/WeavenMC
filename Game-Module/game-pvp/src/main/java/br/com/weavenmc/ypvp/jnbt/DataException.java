package br.com.weavenmc.ypvp.jnbt;

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
public class DataException extends Exception {
	private static final long serialVersionUID = 5806521052111023788L;

	public DataException(String msg) {
		super(msg);
	}

	public DataException() {
	}
}
