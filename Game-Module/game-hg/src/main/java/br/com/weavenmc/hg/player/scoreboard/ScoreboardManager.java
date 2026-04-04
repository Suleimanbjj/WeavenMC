package br.com.weavenmc.hg.player.scoreboard;

import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.hg.player.scoreboard.constructor.ScoreboardConstructor;
import br.com.weavenmc.hg.player.scoreboard.constructor.ScoreboardScroller;
import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.core.account.League;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
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

public class ScoreboardManager extends Management {

	private static final long SCOREBOARD_UPDATE_TICKS = 20L;
	private static final long TITLE_UPDATE_TICKS = 3L;

	private String title = "";
	private ScoreboardScroller scroller;
	private int ticks;
	private int cachedAlivePlayers;
	private String cachedTime;

	public ScoreboardManager(Manager manager) {
		super(manager, "Scoreboard");
	}

	public boolean initialize() {
		this.scroller = new ScoreboardScroller(" WEAVENHG ", "§e§l", "§e§l", "§6§l", 3);
		this.ticks = 0;
		this.cachedAlivePlayers = 0;
		this.cachedTime = getManager().getUtils().formatOldTime(getManager().getGameManager().getGameTime());

		new BukkitRunnable() {
			@Override
			public void run() {
				boolean titleUpdated = (ticks++ % TITLE_UPDATE_TICKS) == 0;
				boolean shouldRefresh = (ticks % SCOREBOARD_UPDATE_TICKS) == 0;
				if (!titleUpdated && !shouldRefresh) {
					return;
				}

				if (titleUpdated) {
					title = scroller.next();
				}
				if (shouldRefresh) {
					cachedAlivePlayers = getManager().getGamerManager().getAliveCount();
					cachedTime = getManager().getUtils().formatOldTime(getManager().getGameManager().getGameTime());
				}

				for (Player p : Bukkit.getOnlinePlayers()) {
					Gamer g = getManager().getGamerManager().getGamer(p);
					if (g == null) {
						continue;
					}

					if (g.getScoreboardHandler() != null) {
						if (shouldRefresh || titleUpdated) {
							updateScoreboard(p, cachedTime, cachedAlivePlayers, titleUpdated, shouldRefresh);
						}
					} else {
						createScoreboard(p, cachedTime, cachedAlivePlayers);
					}

					if (shouldRefresh) {
						getManager().getGamerManager().updateTab(p, cachedAlivePlayers, cachedTime);
					}
				}
			}
		}.runTaskTimer(getManager().getPlugin(), 1L, 1L);

		return true;
	}

	public void createScoreboard(Player player) {
		createScoreboard(player, getManager().getUtils().formatOldTime(getManager().getGameManager().getGameTime()),
				getManager().getGamerManager().getAliveCount());
	}

	private void createScoreboard(Player player, String time, int alivePlayers) {
		Gamer account = getManager().getGamerManager().getGamer(player);

		ScoreboardConstructor sidebar = new ScoreboardConstructor(player);
		sidebar.initialize(title);

		// sidebar.setDisplayName("§8§l>> §6§l" + title + " §8§l<<");
		League league = BukkitPlayer.getPlayer(player.getUniqueId()).getLeague();

		if (getManager().isDoubleKit()) {
			if (getManager().getGameManager().isPreGame()) {
				sidebar.setText(9, "");
				sidebar.setText(8, "§fIniciando em: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(6, "");
				sidebar.setText(5, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(4, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isInvencibility()) {
				sidebar.setText(9, "");
				sidebar.setText(8, "§fInvencível por: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(6, "");
				sidebar.setText(5, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(4, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isGame()) {
				sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(8, "");
				sidebar.setText(7, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(6, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			} else {
				sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(8, "");
				sidebar.setText(7, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(6, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			}
		} else {
			if (getManager().getGameManager().isPreGame()) {
				sidebar.setText(9, "");
				sidebar.setText(8, "§fIniciando em: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(6, "");
				sidebar.setText(5, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(3, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isInvencibility()) {
				sidebar.setText(9, "");
				sidebar.setText(8, "§fInvencível por: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(6, "");
				sidebar.setText(5, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(3, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isGame()) {
				sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(8, "");
				sidebar.setText(7, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(5, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			} else {
				sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				sidebar.setText(8, "");
				sidebar.setText(7, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(5, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				sidebar.setText(2, "");
				sidebar.setText(1, "   §eweavenmc.com.br");
			}
		}
		account.setScoreboardHandler(sidebar);
		BukkitMain.getInstance().getTagManager().updateCurrent(player);
	}

	public void updateScoreboard(Player player) {
		updateScoreboard(player, getManager().getUtils().formatOldTime(getManager().getGameManager().getGameTime()),
				getManager().getGamerManager().getAliveCount(), true, true);
	}

	private void updateScoreboard(Player player, String time, int alivePlayers, boolean updateTitle, boolean updateLines) {
		Gamer account = getManager().getGamerManager().getGamer(player);
		ScoreboardConstructor sidebar = account.getScoreboardHandler();
		
		if (sidebar == null)
			return;

		if (updateTitle) {
			sidebar.setDisplayName(title);
		}
		if (!updateLines) {
			return;
		}

		League league = BukkitPlayer.getPlayer(player.getUniqueId()).getLeague();

		if (getManager().isDoubleKit()) {
			if (getManager().getGameManager().isPreGame()) {
				//sidebar.setText(9, "");
				sidebar.setText(8, "§fIniciando em: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(6, "");
				sidebar.setText(5, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(4, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(3, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isInvencibility()) {
				//sidebar.setText(9, "");
				sidebar.setText(8, "§fInvencível por: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(6, "");
				sidebar.setText(5, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(4, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(3, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isGame()) {
				//sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(8, "");
				sidebar.setText(7, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(6, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(5, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			} else {
				//sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(8, "");
				sidebar.setText(7, "§fKit 1: §a" + account.getKit().getName());
				sidebar.setText(6, "§fKit 2: §a" + account.getKit2().getName());
				sidebar.setText(5, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			}
		} else {
			if (getManager().getGameManager().isPreGame()) {
				//sidebar.setText(9, "");
				sidebar.setText(8, "§fIniciando em: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(6, "");
				sidebar.setText(5, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(3, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isInvencibility()) {
				//sidebar.setText(9, "");
				sidebar.setText(8, "§fInvencível por: §a" + time);
				sidebar.setText(7, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(6, "");
				sidebar.setText(5, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(3, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			} else if (getManager().getGameManager().isGame()) {
				//sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(8, "");
				sidebar.setText(7, "§fKit: §a" + account.getKit().getName());
				sidebar.setText(5, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			} else {
				//sidebar.setText(11, "");
				sidebar.setText(10, "§fTempo: §a" + time);
				sidebar.setText(9, "§fJogadores: §a" + alivePlayers);
				//sidebar.setText(8, "");
				sidebar.setText(7, "§fKit: §e" + account.getKit().getName());
				sidebar.setText(5, "§fLiga: " + league.getColor() + league.getSymbol() + " " + league.name());
				//sidebar.setText(4, "");
				sidebar.setText(3, "§fKills: §a" + player.getStatistic(Statistic.PLAYER_KILLS));
				//sidebar.setText(2, "");
				//sidebar.setText(1, "   §eweavenmc.com.br");
			}
		}
	}
}
