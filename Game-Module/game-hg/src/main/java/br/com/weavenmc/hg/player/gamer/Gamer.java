package br.com.weavenmc.hg.player.gamer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

import br.com.weavenmc.hg.HungerGames;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.kit.Kit;
import br.com.weavenmc.hg.player.scoreboard.constructor.ScoreboardConstructor;
import br.com.weavenmc.commons.bukkit.api.admin.AdminMode;
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

public class Gamer {

	@Deprecated
	private static final List<Kit> playerKits = new ArrayList<>();
	private static final HashMap<UUID, Boolean> winnerMap = new HashMap<>();

	private Player player;

	private Kit kit, kit2;
	private GamerMode mode;

	private UUID uuid;
	private boolean blocked, blockFunction;

	private ScoreboardConstructor scoreboardHandler;

	private Kit surpriseKit = null;
	private String rank = null;

	private int gameKills, fighting, credits;
	private boolean online, isloaded, chatspec, itemsGive, pvpPregame, specs;

	public Gamer(Player player) {
		this.uuid = player.getUniqueId();

		this.scoreboardHandler = null;

		mode = GamerMode.LOADING;

		gameKills = 0;
		fighting = 0;
		credits = 0;

		kit = getManager().getKitManager().getKit("Nenhum");
		kit2 = getManager().getKitManager().getKit("Nenhum");

		isloaded = false;
		online = true;
		pvpPregame = false;
		chatspec = false;
		itemsGive = false;
		pvpPregame = false;
		blocked = false;
		specs = false;
		blockFunction = false;
	}

	public void setWinner(boolean bool) {
		winnerMap.put(uuid, bool);
	}

	public boolean isWinner() {
		if (winnerMap.containsKey(uuid))
			return winnerMap.get(uuid);
		return false;
	}

	public boolean isWinnerChecked() {
		return winnerMap.containsKey(uuid);
	}

	public String getRank() {
		return rank;
	}

	public ScoreboardConstructor getScoreboardHandler() {
		return scoreboardHandler;
	}

	public void setScoreboardHandler(ScoreboardConstructor scoreboardHandler) {
		this.scoreboardHandler = scoreboardHandler;
	}

	public boolean isBlockFunction() {
		return blockFunction;
	}

	public void setBlockFunction(boolean blockFunction) {
		this.blockFunction = blockFunction;
	}

	public Kit getKit2() {
		return kit2;
	}

	public void setKit2(Kit kit2) {
		this.kit2 = kit2;
	}

	public boolean isSpecs() {
		return specs;
	}

	public void setSpecs(boolean specs) {
		this.specs = specs;
	}

	public Kit getSurpriseKit() {
		return surpriseKit;
	}

	public void setSurpriseKit(Kit surpriseKit) {
		this.surpriseKit = surpriseKit;
	}

	public void updatePlayer(Player player) {
		this.player = player;
	}

	public void unload() {
		kit = getManager().getKitManager().getKit("Nenhum");
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Boolean isFighting() {
		return fighting > 0;
	}

	public Boolean isLoaded() {
		return isloaded;
	}

	public Boolean isOnline() {
		return online;
	}

	public UUID getUUID() {
		return uuid;
	}

	public Integer getGameKills() {
		return gameKills;
	}

	public GamerMode getMode() {
		return mode;
	}

	public Kit getKit() {
		return kit;
	}

	public Boolean isSpectating() {
		if (AdminMode.getInstance().isAdmin(player))
			return true;
		return mode == GamerMode.SPECTING;
	}

	public Boolean isAlive() {
		return mode == GamerMode.ALIVE;
	}

	public Boolean isDead() {
		return mode == GamerMode.DEAD;
	}

	public Player getPlayer() {
		return player;
	}

	public Integer getCredits() {
		return credits;
	}

	public Boolean isOnPvpPregame() {
		return pvpPregame;
	}

	public Boolean hasKit(String kit) {
		return (kit.equals(getManager().getGamerManager().getGamer(player).getKit().getName()));
	}

	public Boolean inChatSpec() {
		return chatspec;
	}

	public Boolean getItemsGive() {
		return itemsGive;
	}

	@Deprecated
	public List<Kit> getKits() {
		return playerKits;
	}

	public List<Kit> getMatchKits() {
		return Arrays.asList(getKit());
	}

	public Manager getManager() {
		return HungerGames.getManager();
	}

	public String getNick() {
		return this.player.getName();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public void setFighting(Integer fighting) {
		this.fighting = fighting;
	}

	public void setGameKills(Integer gameKills) {
		this.gameKills = gameKills;
	}

	public void setLoaded(Boolean bool) {
		this.isloaded = bool;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public void setMode(GamerMode mode) {
		this.mode = mode;
	}

	public void sendMessage(String string) {
		getPlayer().sendMessage(string);
	}

	public void refreshFighting() {
		if (fighting > 0) {
			fighting -= 1;
		}
	}

	public void setItemsGive(Boolean itemsGive) {
		this.itemsGive = itemsGive;
	}

	public void setPvpPregame(Boolean pvpPregame) {
		this.pvpPregame = pvpPregame;
	}

	public void setChatspec(Boolean chatspec) {
		this.chatspec = chatspec;
	}
}
