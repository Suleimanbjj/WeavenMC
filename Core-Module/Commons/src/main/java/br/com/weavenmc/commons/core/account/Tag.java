package br.com.weavenmc.commons.core.account;

import java.util.Arrays;
import java.util.List;
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
public enum Tag {

	DONO(25, "§4§lDONO §4", "§4§lDONO", "A", Group.DONO, "owner", "master", "ceo"),
	GANSO(24, "§6§lGANSO §6", "§6§lGANSO", "B", Group.GANSO,"ganso"),
	ESTRELA(23, "§4§lSTAR §4", "§4§lSTAR", "B", Group.ESTRELA, "star"),
	DEV(22, "§3§lDEV §3", "§3§lDEV", "C", Group.DEV, "developer", "coder"),
	DIRETOR(21, "§4§lDIRETOR §4", "§4§lDIRETOR", "D", Group.DIRETOR, "diretor"),
	ADMIN(21,"§c§lADMIN §c", "§c§lADMIN", "E", Group.ADMIN, "administrador"),
	GERENTE(20, "§c§lGERENTE §c", "§c§lGERENTE", "F", Group.GERENTE, "ger"),
	MODPLUS(19, "§5§lMOD+ §5", "§5§lMOD+", "G", Group.MODPLUS, "mod+"),
	MODGC(18, "§5§lMODGC §5", "§5§lMODGC", "H", Group.MODGC, "gc", "gcdetector"),
	MOD(17, "§5§lMOD §5", "§5§lMOD", "I", Group.MOD, "moderador"),
	YOUTUBERPLUS(16, "§3§lYT+ §3", "§3§lYT+", "J", Group.YOUTUBERPLUS, "yt+", "ytplus", "youtuber+"),
	TRIAL(15, "§5§lTRIAL §5", "§5§lTRIAL", "K", Group.TRIAL),
	INVESTIDOR(14, "§2§lINVST §2", "§2§lINVESTIDOR", "L", Group.INVESTIDOR),
	BUILDER(13, "§e§lBUILDER §e", "§e§lBUILDER", "M", Group.BUILDER, "construtor"),
	STAFF(12, "§e§lSTAFF §e", "§e§lSTAFF", "N", Group.STAFF, "staffer"),
	DESIGNER(11, "§2§lDESIGNER §2", "§2§lDESIGNER", "O", Group.DESIGNER, "dzn"),
	YOUTUBER(10, "§b§lYT §b", "§b§lYT", "P", Group.YOUTUBER, "yt"),
	ULTIMATE(9, "§d§lULTIMATE §d", "§d§lULTIMATE", "Q", Group.ULTIMATE),
	BETA(8, "§1§lBETA §1", "§1§lBETA", "R", Group.BETA),
	COPA(7, "§e§lCOPA §e", "§e§lCOPA", "S", Group.COPA, "copa", "evento"),
	PREMIUM(6, "§6§lPREMIUM §6", "§6§lPREMIUM", "T", Group.PREMIUM),
	LIGHT(5, "§a§lLIGHT §a", "§a§lLIGHT", "U", Group.LIGHT),
	TORNEIO(4, "§1§lTORNEIO §1", "§1§lTORNEIO", "V", Group.TORNEIO, "torneio", "tournament"),
	WINNER(3, "§2§lWINNER §2", "§2§lWINNER", "W", Group.WINNER, "vencedor"),
	STREAMER(2, "§3§lSTREAMER §3", "§3§lSTREAMER", "X", Group.STREAMER),
	MEMBRO(1, "§7", "§7§lMEMBRO", "Y", Group.MEMBRO, "normal");
	
	private int id;
	private String prefix;
	private String name, team;
	private List<String> aliases;
	
	private Tag(int id, String prefix, String name, String team, Group groupToUse, String... aliases) {
		this.id = id;
		this.prefix = prefix;
		this.name = name;
		this.team = team;
		this.aliases = Arrays.asList(aliases);
	}
	
	public Group getGroupToUse() {
		return Group.valueOf(name());
	}
	
	public static Tag fromString(String name) {
		for (Tag tag : values()) {
			if (!name.equalsIgnoreCase(tag.name())) {
				for (String alias : tag.getAliases()) {
					if (!alias.equalsIgnoreCase(name))
						continue;
					return tag;
				}
			} else {
				return tag;
			}
		}
		return null;
	}
	
	public static Tag fromId(int id) {
		for (Tag tag : values()) {
			if (tag.getId() != id)
				continue;
			return tag;
		}
		return null;
	}
}
