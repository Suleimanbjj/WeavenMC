package br.com.weavenmc.hg.manager;

import br.com.weavenmc.hg.HungerGames;
import br.com.weavenmc.hg.bo2.BO2Constructor;
import br.com.weavenmc.hg.game.arena.ArenaManager;
import br.com.weavenmc.hg.game.handler.GameManager;
import br.com.weavenmc.hg.game.handler.listeners.SoupListener;
import br.com.weavenmc.hg.logger.Logger;
import br.com.weavenmc.hg.manager.managers.ClassManager;
import br.com.weavenmc.hg.manager.managers.FileManager;
import br.com.weavenmc.hg.player.events.ServerTimeEvent;
import br.com.weavenmc.hg.player.gamer.GamerManager;
import br.com.weavenmc.hg.player.inventories.InventoryManager;
import br.com.weavenmc.hg.player.kit.KitManager;
import br.com.weavenmc.hg.player.kitaward.AwardManager;
import br.com.weavenmc.hg.player.scoreboard.ScoreboardManager;
import br.com.weavenmc.hg.utilitaries.Utils;
import java.io.File;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
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

public class Manager {
	
	private HungerGames plugin;
	private Utils utils;
	private BO2Constructor bo2;
	private final String serverName;
	private final String serverIP;
	private FileManager fileManager;
	private GamerManager gamerManager;
	private GameManager gameManager;
	private KitManager kitManager;
	private InventoryManager inventoryManager;
	private ScoreboardManager scoreboardManager;
	private AwardManager awardManager;
	private ArenaManager arenaManager;
	private ClassManager classManager;
	private Random random;
	private int time;
	private boolean doubleKit = true;
	public static String serverId;
	public static long delay;
	public static boolean send = true;

	public Manager(HungerGames hungerGames) {
		this.plugin = ((HungerGames) HungerGames.getPlugin(HungerGames.class));
		this.plugin.saveDefaultConfig();
		
		doubleKit = this.plugin.getConfig().getBoolean("doublekit");

		serverId = this.plugin.getConfig().getString("server");
		getPlugin().loadChunks();

		getLogger().log("Starting the plugin " + this.plugin.getName() + " version "
				+ this.plugin.getDescription().getVersion() + "...");
		if (!this.plugin.TEST_SERVER()) {
			getLogger().log("Starting to loading all the chunks of the world.");

			getLogger().log("The chunks that will be used, was loaded.");
		}
		this.serverName = new File(getPlugin().getDataFolder().getParentFile().getAbsolutePath()).getParentFile()
				.getName().toUpperCase();

		this.serverIP = (serverId + ".mc-weaven.com.br");

		getLogger().log("Making connection with plugin " + getPlugin().getName() + " version "
				+ getPlugin().getDescription().getVersion() + ".");

		this.utils = new Utils();

		this.random = new Random();

		this.bo2 = new BO2Constructor(this);
		if (!this.bo2.correctlyStart()) {
			return;
		}
		this.fileManager = new FileManager(this);
		if (!this.fileManager.correctlyStart()) {
			return;
		}
		this.kitManager = new KitManager(this);
		if (!this.kitManager.correctlyStart()) {
			return;
		}
		this.scoreboardManager = new ScoreboardManager(this);
		if (!this.scoreboardManager.correctlyStart()) {
			return;
		}
		this.gamerManager = new GamerManager(this);
		if (!this.gamerManager.correctlyStart()) {
			return;
		}
		this.gameManager = new GameManager(this);
		if (!this.gameManager.correctlyStart()) {
			return;
		}
		this.awardManager = new AwardManager(this);
		if (!this.awardManager.correctlyStart()) {
			return;
		}
		this.arenaManager = new ArenaManager(this);
		if (!this.arenaManager.correctlyStart()) {
			return;
		}
		this.inventoryManager = new InventoryManager(this);
		if (!this.inventoryManager.correctlyStart()) {
			return;
		}

		this.classManager = new ClassManager(this);
		if (!this.classManager.correctlyStart()) {
			return;
		}
		SoupListener.createSoups();
		getPlugin().getServer().setWhitelist(false);

		Random random = new Random();
		int rnd = random.nextInt(600);

		this.time = (3600 + rnd);

		new BukkitRunnable() {
			public void run() {
				if ((time <= 0) && (getGameManager().isPreGame())
						&& (Bukkit.getOnlinePlayers().size() == 0)) {
					Bukkit.shutdown();
					cancel();
					return;
				}
				time -= 1;
			}
		}.runTaskTimer(getPlugin(), 0L, 20L);

		new BukkitRunnable() {
			public void run() {
				Bukkit.getPluginManager().callEvent(new ServerTimeEvent());
			}
		}.runTaskTimer(getPlugin(), 20L, 20L);

		getLogger().log("The plugin " + this.plugin.getName() + " version " + this.plugin.getDescription().getVersion()
				+ " was started correcly.");
	}

	public void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, getPlugin());
	}

	public String getServerIP() {
		return this.serverIP;
	}

	public boolean isDoubleKit() {
		return doubleKit;
	}

	public BO2Constructor getBO2() {
		return this.bo2;
	}

	public String getServerName() {
		return this.serverName;
	}

	public ArenaManager getArenaManager() {
		return this.arenaManager;
	}

	public AwardManager getSurpriseKitManager() {
		return this.awardManager;
	}

	public Logger getLogger() {
		return getPlugin().getLoggerSecon();
	}

	public FileConfiguration getConfig() {
		return this.plugin.getConfig();
	}

	public HungerGames getPlugin() {
		return this.plugin;
	}

	public Utils getUtils() {
		return this.utils;
	}

	public ScoreboardManager getScoreListener() {
		return this.scoreboardManager;
	}

	public GamerManager getGamerManager() {
		return this.gamerManager;
	}

	public GameManager getGameManager() {
		return this.gameManager;
	}

	public KitManager getKitManager() {
		return this.kitManager;
	}

	public InventoryManager getInventoryManager() {
		return this.inventoryManager;
	}

	public Random getRandom() {
		return this.random;
	}

	public ClassManager getClassLoader() {
		return this.classManager;
	}

	public FileManager getFileManager() {
		return this.fileManager;
	}
}
