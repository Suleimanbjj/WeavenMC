package br.com.weavenmc.hg.schematic;

import java.nio.charset.Charset;
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

public final class NBTConstants {

	public static final Charset CHARSET = Charset.forName("UTF-8");

	public static final int TYPE_END = 0, TYPE_BYTE = 1, TYPE_SHORT = 2, TYPE_INT = 3, TYPE_LONG = 4, TYPE_FLOAT = 5,
			TYPE_DOUBLE = 6, TYPE_BYTE_ARRAY = 7, TYPE_STRING = 8, TYPE_LIST = 9, TYPE_COMPOUND = 10;

	private NBTConstants() {

	}
}
