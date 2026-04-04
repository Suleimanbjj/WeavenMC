package br.com.weavenmc.lobby.enums;

import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Getter
public enum Heads {

	SLIME("Slime", "kobyjo"), BEACON("Beacon", "MFH_Beacon"), PUMPKIN("Abóbora", "Rovertdoow1st"),
	CHICKEN("Galinha", "Gaygus"), BOY("Garoto", "Hentalon");

	private String headName, skinHeadName;

}
