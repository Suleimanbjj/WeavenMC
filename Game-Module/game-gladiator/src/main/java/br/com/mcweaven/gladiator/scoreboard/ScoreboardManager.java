package br.com.mcweaven.gladiator.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import br.com.mcweaven.gladiator.Gladiator;
import br.com.mcweaven.gladiator.gamer.Gamer;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.player.PingAPI;
import br.com.weavenmc.commons.bukkit.api.tablist.TabListAPI;
import br.com.weavenmc.commons.bukkit.scoreboard.Sidebar;
import br.com.weavenmc.commons.bukkit.scoreboard.StringScroller;

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

public class ScoreboardManager implements Listener {

	private static String title = "";
	private static StringScroller scrollerGladiator;

	public ScoreboardManager() {
		scrollerGladiator = new StringScroller("WeavenMC - Gladiator -", 12, 1);
		title = scrollerGladiator.next();

	}

	static {

		new BukkitRunnable() {

			@Override
			public void run() {
				title = scrollerGladiator.next();

				if (Gladiator.getInstance().getGamerManager().getGamers().size() > 0)

					for (Gamer gamer : Gladiator.getInstance().getGamerManager().getGamers()) {
						if (gamer.getSidebar() != null)
							update(gamer);
					}

			}
		}.runTaskTimerAsynchronously(Gladiator.getInstance(), 0, 6L);
	}

	public void create(Player player) {

		Gamer gamer = Gladiator.getInstance().getGamerManager().getGamer(player.getUniqueId());

		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		if (gamer.getSidebar() == null)
			gamer.setSidebar(new Sidebar(player.getScoreboard()));
		gamer.getSidebar().hide();
		gamer.getSidebar().show();

		Sidebar gamerSidebar = gamer.getSidebar();

		gamerSidebar.setTitle("§8§l>>> §6§l" + title + " §8§l<<<");

		if (gamer.getFight() == null) {

			gamerSidebar.setText(9, "");
			gamerSidebar.setText(8, "§7Vitórias: §b" + gamer.getWins());
			gamerSidebar.setText(7, "§7Derrotas: §b" + gamer.getDeaths());
			gamerSidebar.setText(6, "§7WinStreak: §b" + gamer.getWinStreak());
			gamerSidebar.setText(5, "");
			gamerSidebar.setText(4, "§7Batalhando contra: ");
			gamerSidebar.setText(3, "§3Ninguém");
			gamerSidebar.setText(2, "");
			gamerSidebar.setText(1, "§6www.mc-weaven.com.br ");
		} else {

			gamerSidebar.setText(11, "");
			gamerSidebar.setText(10, "§7Vitórias: §b" + gamer.getWins());
			gamerSidebar.setText(9, "§7Derrotas: §b" + gamer.getDeaths());
			gamerSidebar.setText(8, "§7WinStreak: §b" + gamer.getWinStreak());
			gamerSidebar.setText(7, "");
			gamerSidebar.setText(6, "§7Batalhando contra: ");
			String duel = gamer.getGladiatorEnemy() == null ? "§3Ninguém"
					: "§3" + Bukkit.getPlayer(gamer.getGladiatorEnemy()).getName();
			gamerSidebar.setText(5, "§3" + duel);
			gamerSidebar.setText(4, "");
			gamerSidebar.setText(3, "§7Tempo: §e" + formatOldTime(gamer.getFight().getFightTime()));
			gamerSidebar.setText(2, "");
			gamerSidebar.setText(1, "§6www.mc-weaven.com.br ");

		}

	}

	private static void update(Gamer gamer) {
		if (gamer == null)
			return;

		Sidebar gamerSidebar = gamer.getSidebar();

		gamerSidebar.setTitle("§8§l>>> §6§l" + title + " §8§l<<<");

		if (gamer.getGladiatorEnemy() == null) {

			gamerSidebar.setText(9, "");
			gamerSidebar.setText(8, "§7Vitórias: §b" + gamer.getWins());
			gamerSidebar.setText(7, "§7Derrotas: §b" + gamer.getDeaths());
			gamerSidebar.setText(6, "§7WinStreak: §b" + gamer.getWinStreak());
			gamerSidebar.setText(5, "");
			gamerSidebar.setText(4, "§7Batalhando contra: ");
			gamerSidebar.setText(3, "§3Ninguém");
			gamerSidebar.setText(2, "");
			gamerSidebar.setText(1, "§6www.mc-weaven.com.br ");

		} else {

			gamerSidebar.setText(11, "");
			gamerSidebar.setText(10, "§7Vitórias: §b" + gamer.getWins());
			gamerSidebar.setText(9, "§7Derrotas: §b" + gamer.getDeaths());
			gamerSidebar.setText(8, "§7WinStreak: §b" + gamer.getWinStreak());
			gamerSidebar.setText(7, "");
			gamerSidebar.setText(6, "§7Batalhando contra: ");
			gamerSidebar.setText(5, "§3" + (gamer.getGladiatorEnemy() == null ? "Ninguém"
					: Bukkit.getPlayer(gamer.getGladiatorEnemy()).getName()));
			gamerSidebar.setText(4, "");
			gamerSidebar.setText(3, "§7Tempo: §e" + formatOldTime(gamer.getFight().getFightTime()));
			gamerSidebar.setText(2, "");
			gamerSidebar.setText(1, "§6www.mc-weaven.com.br ");

		}

		updateTab(Bukkit.getPlayer(gamer.getPlayerUuid()),
				Gladiator.getInstance().getGamerManager().getGamers().size());

	}

	private static String formatOldTime(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;
		return formattedTime;
	}

	private static void updateTab(Player p, int onlineCount) {
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		int ping = PingAPI.getPing(p);
		TabListAPI.setHeaderAndFooter(p,
				"§6§lWEAVEN§f§lMC §e§lGLADIATOR\n§eMoedas: §f" + bP.getMoney() + " §9- §eTickets: §f" + bP.getTickets()
						+ " §9- §ePing: §f" + ping + "\n" + "§eTemos §f" + onlineCount + "§e jogadores online!",
				"§bNick: §f" + bP.getName() + " §9- §bLiga: §f" + bP.getLeague().name() + " §9- §bXP: §f" + bP.getXp()
						+ "\n§bMais informações em: §fwww.mc-weaven.com.br");
	}

}
