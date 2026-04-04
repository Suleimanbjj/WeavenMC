package br.com.weavenmc.skywars.nametag;

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
public class Tag {
	
	private String prefix,suffix,teamString;
	
	public Tag(String prefix, String suffix,String teamString) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.teamString = teamString;
	}
	
	public String getTeamString() {
		return teamString;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
