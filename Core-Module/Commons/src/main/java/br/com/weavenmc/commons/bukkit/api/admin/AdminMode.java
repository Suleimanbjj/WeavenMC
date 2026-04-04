package br.com.weavenmc.commons.bukkit.api.admin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import br.com.weavenmc.commons.bukkit.event.admin.PlayerAdminModeEnterEvent;
import br.com.weavenmc.commons.bukkit.event.admin.PlayerAdminModeQuitEvent;
import br.com.weavenmc.commons.core.permission.Group;
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

import br.com.weavenmc.commons.bukkit.api.vanish.VanishAPI;;

public class AdminMode {
	private Set<UUID> admin;
	private static final AdminMode instance = new AdminMode();

	public AdminMode() {
		admin = new HashSet<UUID>();
	}

	public static AdminMode getInstance() {
		return instance;
	}

	public void setAdmin(Player p) {
		PlayerAdminModeEnterEvent event = new PlayerAdminModeEnterEvent(p);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		if (!admin.contains(p.getUniqueId()))
			admin.add(p.getUniqueId());
		p.setGameMode(GameMode.CREATIVE);
		Group group = VanishAPI.getInstance().hidePlayer(p);
		p.sendMessage("§4§lADMIN§f Modo Admin §a§lATIVADO");
		p.sendMessage("§e§lVANISH§f Você está INVISIVEL para " + group.name() + " e abaixo!");
	}

	public void setPlayer(Player p) {
		PlayerAdminModeQuitEvent event = new PlayerAdminModeQuitEvent(p);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;
		if (admin.contains(p.getUniqueId())) {
			p.sendMessage("§4§lADMIN§f Modo Admin §c§lDESATIVADO");
			admin.remove(p.getUniqueId());
		}
		p.sendMessage("§e§lVANISH§f Você está VISIVEL para TODOS OS PLAYERS");
		p.setGameMode(GameMode.SURVIVAL);
		VanishAPI.getInstance().showPlayer(p);
	}

	public boolean isAdmin(Player p) {
		return p != null && admin.contains(p.getUniqueId());
	}

	public int playersInAdmin() {
		return admin.size();
	}

	public void removeAdmin(Player p) {
		admin.remove(p.getUniqueId());
	}
}
