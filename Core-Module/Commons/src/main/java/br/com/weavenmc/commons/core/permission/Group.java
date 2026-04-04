package br.com.weavenmc.commons.core.permission;

import br.com.weavenmc.commons.core.account.Tag;
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
public enum Group {
	
	DONO(26, Tag.DONO),
	GANSO(25, Tag.GANSO),
	ESTRELA(24, Tag.ESTRELA, true),
	DEV(23, Tag.DEV, true),
	DIRETOR(22, Tag.DIRETOR),
	ADMIN(21, Tag.ADMIN),
	GERENTE(20, Tag.GERENTE),
	MODPLUS(19, Tag.MODPLUS),
	MODGC(18, Tag.MODGC),
	MOD(17, Tag.MOD),
	YOUTUBERPLUS(16, Tag.YOUTUBERPLUS, true),
	TRIAL(15, Tag.TRIAL),
	INVESTIDOR(14, Tag.INVESTIDOR),
	BUILDER(13, Tag.BUILDER, true),
	STAFF(12, Tag.STAFF),
	DESIGNER(11, Tag.DESIGNER, true),
	YOUTUBER(10, Tag.YOUTUBER, true),
	ULTIMATE(9, Tag.ULTIMATE),
	BETA(8, Tag.BETA),
	COPA(7, Tag.COPA, true),
	BLADE(9, Tag.ULTIMATE),
	PREMIUM(6, Tag.PREMIUM),
	LIGHT(5, Tag.LIGHT),
	// Legacy aliases kept for older modules.
	ULTRA(9, Tag.ULTIMATE),
	PRO(6, Tag.PREMIUM),
	VIP(5, Tag.LIGHT),
	TORNEIO(4, Tag.TORNEIO, true),
	WINNER(3, Tag.WINNER, true),
	STREAMER(2, Tag.STREAMER),
	MEMBRO(1, Tag.MEMBRO);
	
	private int id;
	private boolean exclusive;
	
	private Group(int id, Tag tagToUse) {
		this(id, tagToUse, false);
	}
	
	private Group(int id, Tag tagToUse, boolean exclusive) {
		this.id = id;
		this.exclusive = exclusive;
	}
	
	public Tag getTagToUse() {
		if (this == ULTRA)
			return Tag.ULTIMATE;
		if (this == PRO)
			return Tag.PREMIUM;
		if (this == VIP)
			return Tag.LIGHT;
		if (this == BLADE)
			return Tag.ULTIMATE;
		return Tag.valueOf(name());
	}
	
	public static Group fromId(int id) {
		for (Group group : values()) {
			if (group.getId() != id)
				continue;
			return group;
		}
		return null;
	}
	
	public static Group fromString(String name) {
		Tag tag = Tag.fromString(name);
		if (tag != null)
			return tag.getGroupToUse();
		return null;
	}
}
