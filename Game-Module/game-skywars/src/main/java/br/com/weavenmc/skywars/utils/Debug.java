package br.com.weavenmc.skywars.utils;

import org.bukkit.Bukkit;
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

public class Debug {
	
	public enum TypeAlert {
		AVISO, SUCESSO, ALERTA, ERROR
	}
	
	public void sendMessage(String message, TypeAlert typeAlert) {
		if (typeAlert == TypeAlert.ALERTA) {
			Bukkit.getConsoleSender().sendMessage("§6[WeavenSkyWars] §e"+message);
		}
		else if (typeAlert == TypeAlert.AVISO) {
			Bukkit.getConsoleSender().sendMessage("§6[WeavenSkyWars] §c"+message);
		}
		else if (typeAlert == TypeAlert.SUCESSO) {
			Bukkit.getConsoleSender().sendMessage("§6[WeavenSkyWars] §a"+message);
		}
		else if (typeAlert == TypeAlert.ERROR) {
			Bukkit.getConsoleSender().sendMessage("§6[WeavenSkyWars] §c"+message);
		}
	}

}
