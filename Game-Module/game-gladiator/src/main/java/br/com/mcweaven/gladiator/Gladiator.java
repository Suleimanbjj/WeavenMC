package br.com.mcweaven.gladiator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.mcweaven.gladiator.fight.controller.FightManager;
import br.com.mcweaven.gladiator.gamer.controller.GamerManager;
import br.com.mcweaven.gladiator.listener.DamagerFixer;
import br.com.mcweaven.gladiator.listener.FightListener;
import br.com.mcweaven.gladiator.listener.PlayerListener;
import br.com.mcweaven.gladiator.scoreboard.ScoreboardManager;
import lombok.Getter;

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

public class Gladiator extends JavaPlugin {

	@Getter
	private static Gladiator instance;

	@Getter
	private GamerManager gamerManager;
	@Getter
	private GladiatorManager gladiatorManager;
	@Getter
	private FightManager fightManager;
	@Getter
	private ScoreboardManager scoreboardManager;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {

		/*
		 * Registering listeners
		 */

		PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new PlayerListener(), getInstance());
		pluginManager.registerEvents(new DamagerFixer(), getInstance());
		pluginManager.registerEvents(new FightListener(), getInstance());

		/*
		 * Constructors
		 */

		gamerManager = new GamerManager();
		gladiatorManager = new GladiatorManager();
		fightManager = new FightManager();
		scoreboardManager = new ScoreboardManager();

		/*
		 * Other
		 */

		gladiatorManager.registerRecipes();

		super.onEnable();
	}

	public void loadChunks() {
		World world = Bukkit.getWorld("world");
		for (int x = -1423; x <= 1423; x += 16) {
			for (int z = -1423; z <= 1423; z += 16) {
				if (!world.getBlockAt(x, 64, z).getChunk().isLoaded())
					world.getBlockAt(x, 64, z).getChunk().load(true);

			}
		}
	}

}
