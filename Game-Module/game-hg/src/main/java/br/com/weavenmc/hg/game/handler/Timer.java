package br.com.weavenmc.hg.game.handler;

import br.com.weavenmc.hg.HungerGames;
import br.com.weavenmc.hg.game.stage.GameStage;
import br.com.weavenmc.hg.game.structures.types.BonusFeast;
import br.com.weavenmc.hg.game.structures.types.Feast;
import br.com.weavenmc.hg.game.structures.types.FinalArena;
import br.com.weavenmc.hg.game.structures.types.MiniFeast;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
import br.com.weavenmc.hg.manager.managers.FileManager;
import br.com.weavenmc.hg.player.events.ServerTimeEvent;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.hg.utilitaries.MessagesConstructor;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.actionbar.BarAPI;
import br.com.weavenmc.commons.bukkit.api.admin.AdminMode;
import br.com.weavenmc.commons.bukkit.event.redis.ServerStatusUpdateEvent;
import br.com.weavenmc.commons.core.server.HardcoreGames;
import br.com.weavenmc.commons.core.server.HardcoreGames.GameState;
import br.com.weavenmc.commons.core.server.NetworkServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

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

public class Timer extends Management implements Listener {

	protected Boolean star;
	private Feast feast;
	private BonusFeast bonusFeast;
	private FinalArena finalArena;
	private boolean start;
	private boolean event;
	private boolean prizeEvent;
	public boolean startg;

	public Timer(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		this.finalArena = new FinalArena(getManager(), new Location(FileManager.getWorld(), 0.0D, 10.0D, 0.0D));
		this.feast = null;
		this.bonusFeast = null;
		this.start = false;
		this.event = false;
		this.prizeEvent = false;

		borderSpawn(500);

		FileManager.getWorld().setSpawnLocation(0, FileManager.getWorld().getHighestBlockYAt(0, 0) + 15, 0);
		return true;
	}

	public boolean isEvent() {
		return this.event;
	}

	public void setEvent(boolean event) {
		this.event = event;
	}

	public boolean isPrizeEvent() {
		return this.prizeEvent;
	}

	public void setPrizeEvent(boolean prizeEvent) {
		this.prizeEvent = prizeEvent;
	}

	public boolean borderSpawn(int borderSize) {
		for (int y = 0; y <= 200; y++) {
			for (int x = -borderSize; x <= borderSize; x++) {
				Location loc = new Location(Bukkit.getWorld("world"), x, y, borderSize);
				if (!loc.getChunk().isLoaded()) {
					loc.getChunk().load(true);
				}
				getManager().getBO2().setBlockFast(loc, getBorderMaterial(), (byte) 0);
			}
			for (int x = -borderSize; x <= borderSize; x++) {
				Location loc = new Location(Bukkit.getWorld("world"), x, y, -borderSize);
				if (!loc.getChunk().isLoaded()) {
					loc.getChunk().load(true);
				}
				getManager().getBO2().setBlockFast(loc, getBorderMaterial(), (byte) 0);
			}
			for (int z = -borderSize; z <= borderSize; z++) {
				Location loc = new Location(Bukkit.getWorld("world"), borderSize, y, z);
				if (!loc.getChunk().isLoaded()) {
					loc.getChunk().load(true);
				}
				getManager().getBO2().setBlockFast(loc, getBorderMaterial(), (byte) 0);
			}
			for (int z = -borderSize; z <= borderSize; z++) {
				Location loc = new Location(Bukkit.getWorld("world"), -borderSize, y, z);
				if (!loc.getChunk().isLoaded()) {
					loc.getChunk().load(true);
				}
				getManager().getBO2().setBlockFast(loc, getBorderMaterial(), (byte) 0);
			}
		}
		return true;
	}

	public Material getBorderMaterial() {
		if (getManager().getRandom().nextBoolean()) {
			return Material.GLASS;
		}
		if (getManager().getRandom().nextBoolean()) {
			return Material.QUARTZ_BLOCK;
		}
		return Material.GLASS;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onServerUpdate(ServerStatusUpdateEvent event) {
		if (!WeavenMC.getClanCommon().isClansLoaded() || !WeavenMC.getProfileCommon().isProfilesLoaded()) {
			event.setCancelled(true);
			return;
		}

		NetworkServer server = event.getWeavenServer();

		int time = getManager().getGameManager().getGameTime();

		GameState state = null;
		if (getManager().getGameManager().isPreGame()) {
			if (!this.start)
				state = GameState.WAITING;
			else
				state = GameState.PREGAME;
		} else if (getManager().getGameManager().isInvencibility()) {
			state = GameState.INVINCIBILITY;
		} else if (getManager().getGameManager().isGame()) {
			state = GameState.GAMETIME;
		} else if (getManager().getGameManager().isEnded()) {
			state = GameState.ENDING;
		} else {
			state = GameState.NONE;
		}

		int leftPlayers = 0;
		if (state == GameState.WAITING) {
			leftPlayers = 5 - getManager().getGamerManager().getAliveCount();
		}

		String winner = getManager().getGamerManager().getWinner();

		event.setWeavenServer(new HardcoreGames(server.getServerId(), server.getPort(), server.getServerType(),
				server.getMaxPlayers(), server.getPlayers(), server.isJoinEnabled(), true,
				(8 * 1000L) + System.currentTimeMillis(), time, leftPlayers, winner, state));
		event.setMessageChannel(WeavenMC.HG_SERVER_INFO_CHANNEL);
	}

	@EventHandler
	public void onTimer(ServerTimeEvent event) {
		if (getManager().getGameManager().isPreGame()) {
			int alivePlayers = getManager().getGamerManager().getAliveCount();

			((World) Bukkit.getWorlds().get(0)).setTime(0L);

			if (this.start) {
				if (alivePlayers < 5) {
					this.start = false;
					getManager().getGameManager().setGameTime(Integer.valueOf(300));
					return;
				}
			}

			if (!this.start) {
				if (alivePlayers >= 5) {
					this.start = true;
				}
			}

			if (!this.start) {
				return;
			}

			getManager().getGameManager().setGameTime(Integer.valueOf(getTime().intValue() - 1));
			if ((getTime().intValue() > 40) && (Bukkit.getOnlinePlayers().size() >= 80) && (!this.startg)) {
				this.startg = true;

				getManager().getGameManager().setGameTime(Integer.valueOf(30));
				Bukkit.broadcastMessage("§3§lTORNEIO §fO tempo foi reduzido pois a partida est§ lotada!");
			}
			if (60 <= getBorderTime().intValue()) {
				getManager().getGameManager().setBorderTime(getBorderTime().intValue() - 5);
			}
			if (((getTime().intValue() % 30 == 0) || (getTime().intValue() % 60 == 0))
					&& (getTime().intValue() >= 30)) {
				Bukkit.broadcastMessage("§3§lTORNEIO §fO torneio iniciará em §c§l"
						+ getManager().getUtils().formatTime(getTime().intValue()));
				playSound(Sound.CLICK);
			}
			if ((getTime().intValue() % 5 == 0) && (getTime().intValue() >= 10) && (getTime().intValue() <= 20)) {
				Bukkit.broadcastMessage("§3§lTORNEIO §fO torneio iniciará em §c§l"
						+ getManager().getUtils().formatTime(getTime().intValue()));
				playSound(Sound.CLICK);
			}
			if ((getTime().intValue() % 1 == 0) && (getTime().intValue() <= 5) && (getTime().intValue() != 0)) {
				Bukkit.broadcastMessage("§3§lTORNEIO §fO torneio iniciará em §c§l"
						+ getManager().getUtils().formatTime(getTime().intValue()));
				playSound(Sound.NOTE_PLING);
			}
			if (getTime().intValue() <= 0) {
				if (Bukkit.getOnlinePlayers().size() < 5) {
					getManager().getGameManager().setGameTime(Integer.valueOf(300));
					return;
				}
				for (Player data : Bukkit.getOnlinePlayers()) {
					BukkitPlayer bP = BukkitPlayer.getPlayer(data.getUniqueId());
					Gamer gamer = getManager().getGamerManager().getGamer(data);
					gamer.setBlockFunction(true);
					if (!bP.isStaffer()) {
						data.closeInventory();
					}
				}
				startGame();
			}
		} else if (getManager().getGameManager().isInvencibility()) {
			if (((getTime().intValue() % 30 == 0) || (getTime().intValue() % 60 == 0)) && (getTime().intValue() >= 30)
					&& (getTime().intValue() != getManager().getConfig().getInt("game.invencibility"))) {
				Bukkit.broadcastMessage(
						"§e§lINVENCIBILIDADE§f acabará em " + getManager().getUtils().formatTime(getTime().intValue()));
			}
			if ((getTime().intValue() % 1 == 0) && (getTime().intValue() <= 5) && (getTime().intValue() != 0)) {
				playSound(Sound.CLICK);
				Bukkit.broadcastMessage(
						"§e§lINVENCIBILIDADE§f acabará em " + getManager().getUtils().formatTime(getTime().intValue()));
			}
			getManager().getGameManager().setGameTime(Integer.valueOf(getTime().intValue() - 1));

			for (Player player : Bukkit.getOnlinePlayers()) {
				BarAPI.send(player, "§e§lINVENCIBILIDADE §c§l"
						+ getManager().getUtils().formatOldTime(getManager().getGameManager().getGameTime()));
			}
			if (getTime().intValue() <= 0) {
				Bukkit.broadcastMessage("§e§lINVENCIBILIDADE§c§l ACABOU");
				getManager().getGameManager().setGameStage(GameStage.GAME);
				for (Gamer gamer : getManager().getGamerManager().getGamers().values()) {
					gamer.setScoreboardHandler(null);
				}
				getManager().getGameManager().setGameTime(Integer.valueOf(120));
			}
		} else if (getManager().getGameManager().isGame()) {
			getManager().getGameManager().setGameTime(Integer.valueOf(getTime().intValue() + 1));
			getManager().getGamerManager().checkWinner();
			if (getTime().intValue() % 300 == 0) {
				new MiniFeast(getManager(), 10).spawnMiniFeast();
			}
			if (getTime().intValue() == 600) {
				this.feast = new Feast(getManager(), 15);
				this.feast.spawnFeast();
			}
			if (getTime().intValue() == 1320) {
				this.bonusFeast = new BonusFeast(getManager(), 15);
				this.bonusFeast.spawnFeast();
			}
			if (getTime().intValue() == 2400) {
				this.finalArena.cleanPlayer();

				Location loc = this.finalArena.getLocation();
				for (Gamer gamers : getManager().getGamerManager().getAliveGamers()) {
					gamers.getPlayer().teleport(loc);
				}
			}
		} else {
			getManager().getGameManager().setGameTime(Integer.valueOf(getTime().intValue() + 1));
		}
	}

	public void startGame() {
		HungerGames.openColiseu();
		getManager().getKitManager().registerKits();

		getManager().getGameManager().setGameStage(GameStage.INVENCIBILITY);
		getManager().getGameManager()
				.setGameTime(Integer.valueOf(getManager().getConfig().getInt("game.invencibility")));

		Bukkit.broadcastMessage("§cO torneio iniciou!");
		Bukkit.broadcastMessage("§cTodos estão invencíveis por "
				+ getManager().getUtils().formatTime(getManager().getConfig().getInt("game.invencibility")));
		Bukkit.broadcastMessage("§bQue a sorte esteja ao seu favor");

		playSound(Sound.ENDERDRAGON_GROWL);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			MessagesConstructor.sendTitleMessage(player, "§6§lPartida iniciou", "§fEntrando na INVENCIBILIDADE");
			getManager().getGamerManager().getGamer(player).setScoreboardHandler(null);
		}
		for (Gamer gamer : getManager().getGamerManager().getAliveGamers()) {	
			startGamer(gamer);
			gamer.setGameKills(Integer.valueOf(0));
			if (!AdminMode.getInstance().isAdmin(gamer.getPlayer()))
				continue;
			getManager().getGamerManager().setDied(gamer);
			getManager().getGamerManager().checkWinner();
		}
		
		new BukkitRunnable() {

			@Override
			public void run() {
				HungerGames.removeColiseu();
			}			
		}.runTaskLater(getManager().getPlugin(), 1200L);
	}

	public void startGamer(Gamer gamer) {
		gamer.getPlayer().getInventory().clear();
		gamer.getPlayer().getActivePotionEffects().clear();
		gamer.getPlayer().getInventory().setArmorContents(null);
		gamer.getPlayer().setFireTicks(0);
		gamer.getPlayer().setFoodLevel(20);
		gamer.getPlayer().setFlying(false);
		gamer.getPlayer().setAllowFlight(false);
		gamer.getPlayer().setSaturation(3.2F);
		// gamer.getPlayer().addPotionEffect(new
		// PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 255));
		gamer.getPlayer().getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
		gamer.getKit().give(gamer.getPlayer());
		gamer.getKit2().give(gamer.getPlayer());
		gamer.setGameKills(Integer.valueOf(0));
		gamer.setItemsGive(Boolean.valueOf(true));
		gamer.getPlayer().updateInventory();
	}

	public Feast getFeast() {
		return this.feast;
	}

	public FinalArena getFinalArena() {
		return this.finalArena;
	}

	public int getBorderSize() {
		return 500;
	}

	public Integer getTime() {
		return getManager().getGameManager().getGameTime();
	}

	public Integer getBorderTime() {
		return Integer.valueOf(getManager().getGameManager().getBorderTime());
	}

	public void playSound(Sound sound) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), sound, 5.0F, 5.0F);
		}
	}
}
