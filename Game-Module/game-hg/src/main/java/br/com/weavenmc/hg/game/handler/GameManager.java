package br.com.weavenmc.hg.game.handler;

import br.com.weavenmc.hg.game.stage.GameStage;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
import br.com.weavenmc.hg.utilitaries.ServerOptions;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
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

public class GameManager extends Management {
	
	private GameStage gameStage;
	private ServerOptions pvpOption;
	private ServerOptions buildOption;
	private ServerOptions damageOption;
	private Timer timer;
	private boolean vipsCmds;
	private boolean isEnded;
	private int gameTime;
	private int borderTime;

	public GameManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		this.timer = new Timer(getManager());
		if (!this.timer.correctlyStart()) {
			return false;
		}
		this.buildOption = ServerOptions.BUILD;
		this.pvpOption = ServerOptions.GLOBAL_PVP;
		this.damageOption = ServerOptions.DAMAGE;

		this.gameTime = getManager().getConfig().getInt("game.start");
		this.borderTime = 300;
		this.gameStage = GameStage.PREGAME;

		this.vipsCmds = true;
		this.isEnded = false;

		getManager().registerListener(this.timer);

		World world = Bukkit.getWorld("world");
		world.setDifficulty(Difficulty.NORMAL);
		if (world.hasStorm()) {
			world.setStorm(false);
		}
		world.setTime(0L);

		return true;
	}
	
	public ServerOptions getDamage() {
		return this.damageOption;
	}

	public ServerOptions getBuild() {
		return this.buildOption;
	}

	public ServerOptions getPvP() {
		return this.pvpOption;
	}

	public int getBorderTime() {
		return this.borderTime;
	}

	public void setBorderTime(int borderTime) {
		this.borderTime = borderTime;
	}

	public void setGameStage(GameStage gameStage) {
		getManager().getLogger()
				.log("The stage of the game is changing of " + this.gameStage + " to " + gameStage + ".");
		this.gameStage = gameStage;
	}

	public void setGameTime(Integer gameTime) {
		this.gameTime = gameTime.intValue();
	}

	public boolean isPreGame() {
		return this.gameStage == GameStage.PREGAME;
	}

	public boolean isInvencibility() {
		return this.gameStage == GameStage.INVENCIBILITY;
	}

	public boolean isGame() {
		return this.gameStage == GameStage.GAME;
	}

	public Integer getGameTime() {
		return Integer.valueOf(this.gameTime);
	}

	public GameStage getGameStage() {
		return this.gameStage;
	}

	public Timer getTimer() {
		return this.timer;
	}

	public Boolean isEnded() {
		return Boolean.valueOf(this.isEnded);
	}

	public void setEnded(Boolean isEnded) {
		this.isEnded = isEnded.booleanValue();
	}

	public Boolean isVipCmds() {
		return Boolean.valueOf(this.vipsCmds);
	}

	public void setVipsCmds(Boolean vipsCmds) {
		this.vipsCmds = vipsCmds.booleanValue();
	}
}
