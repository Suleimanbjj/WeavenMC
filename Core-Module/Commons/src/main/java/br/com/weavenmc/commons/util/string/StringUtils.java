package br.com.weavenmc.commons.util.string;

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
public class StringUtils {

	public static String createArgs(int index, String[] args, String defaultArgs, boolean color) {
		StringBuilder sb = new StringBuilder();
		for (int i = index; i < args.length; i++) 
			sb.append(args[i]).append((i + 1 >= args.length ? "" : " "));
		if (sb.length() == 0)
			sb.append(defaultArgs);
		return color ? sb.toString().replace("&", "§") : sb.toString();
	}
	
	public static String replace(String message, String[] old, String[] now) {
		String replaced = message;
		for (int i = 0; i < old.length; i++) 
			replaced = replaced.replace(old[i], now[i]);		
		return replaced;
	}
}
