package org.inventivetalent.hologram.touch;

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
public enum TouchAction {
	
	RIGHT_CLICK, LEFT_CLICK, UNKNOWN;

	private TouchAction() {
	}

	public static TouchAction fromUseAction(Object useAction) {
		if (useAction == null) {
			return UNKNOWN;
		}
		int i = ((Enum<?>) useAction).ordinal();
		switch (i) {
		case 0:
			return RIGHT_CLICK;
		case 1:
			return LEFT_CLICK;
		}
		return UNKNOWN;
	}
	
}
