package br.com.weavenmc.commons.core.backend.mysql;

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

@Getter
@AllArgsConstructor
public class MySQL {

	private String hostname;
	private String database;
	private String username;
	private String password;
	private int port;
}
