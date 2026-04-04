package br.com.weavenmc.hg.utilitaries;

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
public enum ServerOptions {

	CHAT(true),
	BUILD(true),
	DROPS(true),
	PVP(true),
	DAMAGE(true),
	GLOBAL_PVP(true);

	private Boolean active;

	private ServerOptions(Boolean active) {
		this.active = active;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
