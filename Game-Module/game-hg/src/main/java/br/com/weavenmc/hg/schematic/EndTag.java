package br.com.weavenmc.hg.schematic;

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
public final class EndTag extends Tag {

	public EndTag() {
		super("");
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public String toString() {
		return "TAG_End";
	}
}
