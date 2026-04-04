package br.com.weavenmc.commons.bukkit.listeners;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.player.PlayerAPI;
import br.com.weavenmc.commons.core.clan.Clan;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
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

public class AccountListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public synchronized void onAsyncLoginListener(AsyncPlayerPreLoginEvent event)
	{
		if (!WeavenMC.getClanCommon().isClansLoaded() || !WeavenMC.getProfileCommon().isProfilesLoaded()) 
		{
			event.disallow(Result.KICK_OTHER, "§c§lSTARTUP§f Aguarde o término da inicialização do sistema...");
			return;
		}
		
		long start = System.currentTimeMillis();
		
		String name = event.getName();
		UUID uuid = event.getUniqueId();

		BukkitPlayer bP = new BukkitPlayer(uuid, name); // account start
		WeavenMC.getAccountCommon().loadWeavenPlayer(bP.getUniqueId(), bP);
		
		WeavenMC.debug("[" + (System.currentTimeMillis() - start) + "ms] BukkitPlayer loading -> " + name + "(" + uuid + ")");
		if (bP.load(DataCategory.ACCOUNT, DataCategory.BALANCE, DataCategory.CONNECTION, DataCategory.PERSONAL_DATA)) 
		{
			
			WeavenMC.debug("[" + (System.currentTimeMillis() - start) + "ms] BukkitPlayer loaded -> " + name + "(" + uuid + ")");
			
			bP.organizeGroups();
			bP.loadPermissions();
			
			if (!bP.getClanName().equals("Nenhum")) {
				Clan clan = WeavenMC.getClanCommon().tryInCacheFromName(bP.getClanName());
				if (clan == null) {
					clan = WeavenMC.getClanCommon().tryInDatabaseFromName(bP.getClanName());
					if (clan == null) {
						bP.setClanName("Nenhum");
						bP.save(DataCategory.ACCOUNT);
					} 
				} 
			}
			
		} else {
			WeavenMC.debug("[" + (System.currentTimeMillis() - start) + "ms] BukkitPlayer load error -> " + name + "(" + uuid + ")");
			event.disallow(Result.KICK_OTHER, "§4§lERRO§f Ocorreu um erro ao tentar carregar sua conta!");
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onValidate(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (!PlayerAPI.validateName(p.getName()) || p.getName().length() < 3 || p.getName().length() > 16) {
			p.kickPlayer("§4§lERRO§f O nick que voce está usando é §c§lINVALIDO!");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDisallowed(AsyncPlayerPreLoginEvent event) {
		if (event.getLoginResult() != Result.ALLOWED) {
			handleQuit(event.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onLogin(PlayerLoginEvent event) {
		Player p = event.getPlayer();
		BukkitPlayer player = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		if (player != null) {
			if (player.getGroup() == Group.DONO) {
				if (!p.isOp()) {
					p.setOp(true);
				}
			} else {
				if (p.isOp()) {
					p.setOp(false);
				}
			}
			if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
				if (!player.hasGroupPermission(Group.LIGHT)) {
					event.setKickMessage(
							"§4§lERRO §fO servidor está lotado!\n Compre §6§lVIP§f em §b§lLOJA.MC-WEAVEN.COM.BR§f para entrar ou tente novamente em §c§lBREVE!");
				} else {
					event.allow();
				}
			} else if (event.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
				if (!player.isStaffer()) {
					event.setKickMessage("§c§lWHITELIST§f O servidor está com o acesso §4§lRESTRITO!");
				} else {
					event.allow();
				}
			}
			player = null;
		} else {
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
					"§4§lERRO§f Ocorreu um erro ao tentar carregar sua conta.");
		}
		p = null;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onResult(PlayerLoginEvent event) {
		Player p = event.getPlayer();
		if (event.getResult() == PlayerLoginEvent.Result.ALLOWED) {
			BukkitPlayer player = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
			player.setServerConnected(WeavenMC.getServerId());
			player.setServerConnectedType(WeavenMC.getServerType());
			player = null;
		} else {
			handleQuit(p.getUniqueId());
		}
		p = null;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeave(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (p.isOp())
			p.setOp(false);
		handleQuit(p.getUniqueId());
	}

	public void handleQuit(UUID uuid) {
		WeavenMC.getAccountCommon().unloadWeavenPlayer(uuid);
	}
}
