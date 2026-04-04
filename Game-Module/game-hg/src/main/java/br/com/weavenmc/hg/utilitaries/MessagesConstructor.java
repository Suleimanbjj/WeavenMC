package br.com.weavenmc.hg.utilitaries;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.api.actionbar.BarAPI;
import br.com.weavenmc.commons.bukkit.api.title.TitleAPI;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
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

public enum MessagesConstructor {

	COLOR(1, "§f"),
	PREFIX(2, "§6§lLITTLE§f§lHG §6"),
	WEBSITE(3, "www.littlemc.com.br"),
	WEBSTORE(4, "loja.littlemc.com.br"),
	MAIN_COLOR(5, "§6");

	private int id;
	private String parameters;

	private MessagesConstructor(int id, String parameters) {
		this.id = id;
		this.parameters = parameters;
	}

	public int getId() {
		return id;
	}

	public String getParameters() {
		return parameters;
	}

	public MessagesConstructor getPrefix() {
		return PREFIX;
	}

	public static void sendActionBarMessage(Player player, String message) {
		BarAPI.send(player, message);
	}

	public static void sendClickMessage(Player player, String message, String messageClick, String message2, String cmd) {
		if (player != null) {
			IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"" + message + "\",\"extra\":[{\"text\":\"" + messageClick + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + message2
					+ "\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + cmd + "\"}}]}");
			PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		} else {
			for (Player players : Bukkit.getOnlinePlayers())
				sendClickMessage(players, message, messageClick, message2, cmd);
		}
	}

	public static void sendTitleMessage(Player player, String message, String message2) {
		TitleAPI.setTitle(player, message, message2);
	}
}
