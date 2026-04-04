package br.com.weavenmc.login.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import br.com.weavenmc.commons.bukkit.event.update.UpdateEvent;
import br.com.weavenmc.commons.bukkit.scoreboard.Sidebar;
import br.com.weavenmc.login.account.Authenticator;
import br.com.weavenmc.login.account.SimpleManager;

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

public class AuthenticatorListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLoginListener(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		if (player != null) {
			BukkitPlayer bP = BukkitPlayer.getPlayer(player.getUniqueId());
			if (bP != null) {
				Authenticator authenticator = new Authenticator(bP);
				SimpleManager.loadAuthenticator(authenticator);
			} else {
				event.disallow(Result.KICK_OTHER, "§4§lERRO§f Não foi possível carregar sua conta.");
			}
		} else {
			event.disallow(Result.KICK_OTHER, "§4§lERRO§f Não foi possível carregar sua conta.");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuitListener(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		SimpleManager.unloadAuthenticator(event.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String message = event.getMessage();
		if (!message.toLowerCase().startsWith("/login") && !message.toLowerCase().startsWith("/logar")
				&& !message.toLowerCase().startsWith("/register") && !message.toLowerCase().startsWith("/registrar")) {
			event.setCancelled(true);
			event.getPlayer()
					.sendMessage("§4§lAUTHENTICATOR§f Você não pode usar este §c§lCOMANDO§f neste §c§lSERVIDOR");
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getTo().getBlockY() < 1) {
			player.teleport(player.getWorld().getSpawnLocation());
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() != SpawnReason.CUSTOM) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWheater(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onJoinListener(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		Authenticator authenticator = SimpleManager.getAuthenticator(player.getUniqueId());
		authenticator.join();
		authenticator.sendAuthencateMessage();

		ItemBuilder builder = new ItemBuilder().name("§5§lLobby").type(Material.COMPASS);
		player.getInventory().clear();
		player.getInventory().setItem(0, builder.build());
		player.getInventory().setHeldItemSlot(0);

		Sidebar sidebar = new Sidebar(player.getScoreboard());
		sidebar.show();

		sidebar.setTitle("§6§lWEAVEN§f§lMC");
		sidebar.setText(6, "");
		sidebar.setText(5, "§7Utilize os §acomandos§7 que");
		sidebar.setText(4, "§7sao lhe mostrados no §achat");
		sidebar.setText(3, "§7para se §aautenticar§7.");
		sidebar.setText(2, "");
		sidebar.setText(1, "§6www.mc-weaven.com.br");
	}

	private final Set<UUID> cooldown = new HashSet<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType() == Material.COMPASS) {
			event.setCancelled(true);
			if (cooldown.contains(player.getUniqueId())) {
				player.sendMessage("§5§lLOBBY§f Aguarde...");
				return;
			}
			cooldown.add(player.getUniqueId());
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("FindServer");
			out.writeUTF("LOBBY");
			player.sendPluginMessage(BukkitMain.getInstance(), "Velocity", out.toByteArray());
		}
	}
	
	@EventHandler
	public void onUpdateListener(UpdateEvent event) {
		if (event.getCurrentTick() % 100 == 0) {
			for (Player o : Bukkit.getOnlinePlayers()) {
				Authenticator authenticator = SimpleManager.getAuthenticator(o.getUniqueId());
				if (authenticator.isLogged())
					continue;
				authenticator.sendAuthencateMessage();
			}
		}
	}

	@EventHandler
	public void onTimerListener(UpdateEvent event) {
		if (event.getCurrentTick() % 20 == 0) {
			cooldown.clear();
			for (Player o : Bukkit.getOnlinePlayers()) {
				Authenticator authenticator = SimpleManager.getAuthenticator(o.getUniqueId());
				if (authenticator.isLogged())
					continue;
				if (!authenticator.hasTimeOut())
					continue;
				authenticator.kickTimeOut();
			}
		}
	}
}
