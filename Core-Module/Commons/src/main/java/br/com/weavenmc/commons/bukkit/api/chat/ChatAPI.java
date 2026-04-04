package br.com.weavenmc.commons.bukkit.api.chat;

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

public class ChatAPI {

	@Getter
	@Setter
	private ChatState chatState = ChatState.ENABLED;
	@Getter
	private static final ChatAPI instance = new ChatAPI();

	public enum ChatState {
		ENABLED, DISABLED;
	}
}
