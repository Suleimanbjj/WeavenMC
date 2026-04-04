package br.com.weavenmc.commons.core.data.punish;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
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
public class Mute {

	private UUID accountId;
	
	private String mutedBy;
	
	private long muteTime;
	private String reason;
	
	private long appliedTime = 0L;
	
	public boolean isPermanent() {
		return muteTime == -1;
	}
	
	public boolean hasExpired() {
		return muteTime != -1 && muteTime < System.currentTimeMillis();
	}
	
	public String toDateApplied() {
		Date date = new Date(appliedTime);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        return df2.format(date);
	}
}
