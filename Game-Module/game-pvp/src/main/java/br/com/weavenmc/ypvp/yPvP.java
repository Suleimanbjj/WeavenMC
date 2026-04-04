package br.com.weavenmc.ypvp;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.weavenmc.commons.bukkit.command.BukkitCommandFramework;
import br.com.weavenmc.commons.core.command.CommandLoader;
import br.com.weavenmc.ypvp.listeners.DamagerFixer;
import br.com.weavenmc.ypvp.listeners.PlayerListener;
import br.com.weavenmc.ypvp.listeners.SignListener;
import br.com.weavenmc.ypvp.managers.AbilityManager;
import br.com.weavenmc.ypvp.managers.CooldownManager;
import br.com.weavenmc.ypvp.managers.GamerManager;
import br.com.weavenmc.ypvp.managers.ScoreboardManager;
import br.com.weavenmc.ypvp.managers.WarpManager;
import br.com.weavenmc.ypvp.minigame.LocationManager;
import br.com.weavenmc.ypvp.tournament.Tournament;
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

@Getter
public class yPvP extends JavaPlugin {
	@Getter
	private static yPvP plugin;

	private PvPType pvpType;

	private Tournament tournament;

	// managers
	private GamerManager gamerManager;
	private WarpManager warpManager;
	private LocationManager locationManager;
	private AbilityManager abilityManager;
	private ScoreboardManager scoreboardManager;
	private CooldownManager cooldownManager;

	@Override
	public void onLoad() {
		plugin = this;
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		pvpType = PvPType.valueOf(getConfig().getString("type"));
		getLogger().info("PvP Type: " + pvpType.name());
		tournament = new Tournament();
		registerManagements();
		enableManagements();
		//locationManager.saveLocation("spawn", getServer().getWorlds().get(0).getSpawnLocation());
		getServer().getPluginManager().registerEvents(new DamagerFixer(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		new CommandLoader(new BukkitCommandFramework(this)).loadCommandsFromPackage("br.com.weavenmc.ypvp.commands");
	}

	@Override
	public void onDisable() {
		disableManagements();
	}

	private void registerManagements() {
		gamerManager = new GamerManager(this);
		warpManager = new WarpManager(this);
		locationManager = new LocationManager(this);
		abilityManager = new AbilityManager(this);
		scoreboardManager = new ScoreboardManager(this);
		cooldownManager = new CooldownManager(this);
	}
	
	private void enableManagements() {
		gamerManager.enable();
		warpManager.enable();
		locationManager.enable();
		abilityManager.enable();
		scoreboardManager.enable();
		cooldownManager.enable();
	}

	private void disableManagements() {
		gamerManager.disable();
		warpManager.disable();
		locationManager.disable();
		abilityManager.disable();
		scoreboardManager.disable();
		cooldownManager.disable();
	}

	@Override
	public File getFile() {
		return super.getFile();
	}

	public enum PvPType {
		SIMULATOR, FULLIRON
	}
}
