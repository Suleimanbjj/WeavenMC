package br.com.weavenmc.commons.bukkit.account;

import br.com.weavenmc.commons.core.permission.Group;
import lombok.Getter;
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

@Getter
public enum Medal {

	KING("§4", "♛", Group.PREMIUM, "rei"), DANGER("§c", "☠ ", Group.BETA, "perigoso", "perigo"),
	TOXIC("§e", "☣", Group.ULTIMATE, "tóxico", "toxico"), SMILE("§a", "ツ", Group.LIGHT, "sorriso"),
	PEACE("§b", "☮", Group.LIGHT, "paz"), NITRO("§b", "☮", Group.LIGHT, "paz"),
	YIN_YANG("§3", "☯", Group.PREMIUM, "ying&yang", "yingyang", "yingeyang", "yingandyang");

	private String color;
	private String symbol;
	private Group groupToUse;
	private String[] names;

	private Medal(String color, String symbol, Group groupToUse, String... names) {
		this.color = color;
		if (symbol.contains("☠") && symbol.length() > 1) {
			this.symbol = symbol.substring(0, 1);
		} else {
			this.symbol = symbol;
		}
		this.groupToUse = groupToUse;
		this.names = names;
	}

	public static Medal fromString(String name) {
		for (Medal medal : values()) {
			if (!name.equalsIgnoreCase(medal.name())) {
				for (String n : medal.getNames()) {
					if (!name.equalsIgnoreCase(n))
						continue;
					return medal;
				}
			} else {
				return medal;
			}
		}
		return null;
	}
}
