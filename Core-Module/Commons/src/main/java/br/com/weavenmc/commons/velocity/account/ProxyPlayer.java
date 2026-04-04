package br.com.weavenmc.commons.velocity.account;

import java.util.UUID;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.account.WeavenPlayer;
import br.com.weavenmc.commons.core.data.punish.Mute;
import lombok.Getter;
import lombok.Setter;

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
@Setter
public class ProxyPlayer extends WeavenPlayer {
	
	private boolean staffChatMessages = true;
	private boolean staffChatEnabled = false;
	private boolean clanChatEnabled = false;
	private long reportCooldown = 0L;
	
	private Mute recentMute;

	public ProxyPlayer(UUID uniqueId, String name) {
		super(uniqueId, name);
	}
	
	public void addMuteReport(int time) {
		reportCooldown = (time * 1000L + System.currentTimeMillis());
	}
	
	public boolean hasReportMute() {
		return reportCooldown >= System.currentTimeMillis();
	}
	
	public static ProxyPlayer getPlayer(UUID uuid) {
		return (ProxyPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(uuid);
	}
}
