package br.com.weavenmc.commons.bukkit.chat;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

public class ChatHandlerAPI {

	private static final Queue<ChatHandler> chathandlers = new ConcurrentLinkedQueue<>();
	
	public static boolean hasHandler() {
		return chathandlers.size() > 0;
	}
	
	public static ChatEvent callChatEvent(final AsyncPlayerChatEvent event) {
		ChatEvent chatevent = new ChatEvent(event);
		Iterator<ChatHandler> handlers = chathandlers.iterator();
		while (handlers.hasNext()) 
			handlers.next().onChat(chatevent);		
		return chatevent;
	}
	
	public static void addHandler(ChatHandler handler) {
		chathandlers.add(handler);
	}
}
