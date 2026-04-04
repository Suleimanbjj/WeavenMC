package br.com.weavenmc.ypvp.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.player.PingAPI;
import br.com.weavenmc.commons.bukkit.api.tablist.TabListAPI;
import br.com.weavenmc.commons.bukkit.event.update.UpdateEvent;
import br.com.weavenmc.commons.bukkit.scoreboard.Sidebar;
import br.com.weavenmc.commons.bukkit.scoreboard.StringScroller;
import br.com.weavenmc.commons.core.account.League;
import br.com.weavenmc.commons.core.data.player.type.DataType;
import br.com.weavenmc.ypvp.Management;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.gamer.Gamer;
import br.com.weavenmc.ypvp.minigame.BattleMinigame;
import br.com.weavenmc.ypvp.minigame.Minigame;
import br.com.weavenmc.ypvp.minigame.VoidChallengeMinigame;

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

public class ScoreboardManager extends Management implements Listener {

	private StringScroller scrollerSpawn, scroller1v1, scrollerFps, scrollerChallenge, scrollerVoid;
	private String titleSpawn, title1v1, titleFps, titleChallenge, titleVoid;

	public ScoreboardManager(yPvP plugin) {
		super(plugin);
	}

	@Override
	public void enable() {
		scrollerSpawn = new StringScroller("WeavenPvP - Spawn - " + getPlugin().getPvpType().name() + " -", 12, 1);
		titleSpawn = scrollerSpawn.next();
		scroller1v1 = new StringScroller("WeavenPvP - 1v1 - " + getPlugin().getPvpType().name() + " -", 12, 1);
		title1v1 = scroller1v1.next();
		scrollerFps = new StringScroller("WeavenPvP - Fps - " + getPlugin().getPvpType().name() + " -", 12, 1);
		titleFps = scrollerFps.next();
		scrollerChallenge = new StringScroller("WeavenPvP - Lava Challenge - " + getPlugin().getPvpType().name() + " -", 12, 1);
		titleChallenge = scrollerChallenge.next();
		scrollerVoid = new StringScroller("WeavenPvP - Void Challenge - " + getPlugin().getPvpType().name() + " -", 12, 1);
		titleVoid = scrollerVoid.next();
		registerListener(this);
	}

	public void createScoreboard(Player p) {
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		Gamer gamer = getPlugin().getGamerManager().getGamer(p.getUniqueId());
		Sidebar sidebar = gamer.getSidebar();
		if (sidebar == null) {
			gamer.setSidebar(sidebar = new Sidebar(p.getScoreboard()));
			sidebar.show();
		}
		if (sidebar.isHided())
			return;
		sidebar.hide();
		sidebar.show();
		Minigame minigame = gamer.getWarp();
		if (minigame.getName().equalsIgnoreCase("spawn")) {
			League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(12, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(11, "");
			sidebar.setText(10, "§fKills: §6" + bP.getData(DataType.PVP_KILLS).asInt());
			sidebar.setText(9, "§fDeaths: §e" + bP.getData(DataType.PVP_DEATHS).asInt());
			sidebar.setText(8, "§fKillStreak: §6" + bP.getData(DataType.PVP_KILLSTREAK).asInt());
			sidebar.setText(7, "");
			sidebar.setText(6, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(5, "§fXP: §b" + bP.getXp());
			sidebar.setText(4, "");
			sidebar.setText(3, "§fKit: §b" + gamer.getAbility().getName());
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
		} else if (minigame.getName().equalsIgnoreCase("fps")) {
			League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(12, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(11, "");
			sidebar.setText(10, "§fKills: §6" + bP.getData(DataType.PVP_KILLS).asInt());
			sidebar.setText(9, "§fDeaths: §e" + bP.getData(DataType.PVP_DEATHS).asInt());
			sidebar.setText(8, "§fKillStreak: §6" + bP.getData(DataType.PVP_KILLSTREAK).asInt());
			sidebar.setText(7, "");
			sidebar.setText(6, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(5, "§fXP: §b" + bP.getXp());
			sidebar.setText(4, "");
			sidebar.setText(3, "§fKit: §b" + gamer.getAbility().getName());
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
		} else if (minigame.getName().equalsIgnoreCase("1v1")) {
			League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(13, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(12, "");
			sidebar.setText(11, "§fKills: §6" + bP.getData(DataType.PVP_KILLS).asInt());
			sidebar.setText(10, "§fDeaths: §e" + bP.getData(DataType.PVP_DEATHS).asInt());
			sidebar.setText(9, "§fKillStreak: §6" + bP.getData(DataType.PVP_KILLSTREAK).asInt());
			sidebar.setText(8, "");
			sidebar.setText(7, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(6, "§fXP: §b" + bP.getXp());
			sidebar.setText(5, "");
			sidebar.setText(4, "§fBatalhando contra:");
			sidebar.setText(3, "§3" + ((BattleMinigame) minigame).getBattlePlayer(p));
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
		} else if (minigame.getName().equalsIgnoreCase("lava")) {
			League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(6, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(5, "");
			sidebar.setText(4, "§fXP: §b" + bP.getXp());
			sidebar.setText(3, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br    ");
		} else if (minigame.getName().equalsIgnoreCase("void")) {
			League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(9, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(8, "");
			sidebar.setText(7, "§fXP: §b" + bP.getXp());
			sidebar.setText(6, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(5, "");
			if (p.getLocation().getY() <= -64d) {
				sidebar.setText(4, "§fSobreviveu:");
				sidebar.setText(3, "§a" + ((VoidChallengeMinigame) minigame).getTimeSurviving(p));
			} else {
				sidebar.setText(4, "§fPara §ainiciar§7 o desafio");
				sidebar.setText(3, "§fdesafio pule no §5void§7!");
			}
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br    ");
		}
		minigame = null;
		sidebar = null;
		gamer = null;
		bP = null;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onUpdate(UpdateEvent event) {
		if (event.getCurrentTick() % 10 != 0)
			return;
		titleSpawn = scrollerSpawn.next();
		title1v1 = scroller1v1.next();
		titleFps = scrollerFps.next();
		titleChallenge = scrollerChallenge.next();
		titleVoid = scrollerVoid.next();
		int count = Bukkit.getOnlinePlayers().size();
		for (Player o : Bukkit.getOnlinePlayers()) {
			Gamer gamer = getPlugin().getGamerManager().getGamer(o.getUniqueId());
			Sidebar sidebar = gamer.getSidebar();
			if (sidebar == null)
				continue;
			if (sidebar.isHided())
				continue;
			BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(o.getUniqueId());
			Minigame minigame = gamer.getWarp();
			if (minigame.getName().equalsIgnoreCase("spawn")) {
				League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(12, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(11, "");
			sidebar.setText(10, "§fKills: §6" + bP.getData(DataType.PVP_KILLS).asInt());
			sidebar.setText(9, "§fDeaths: §e" + bP.getData(DataType.PVP_DEATHS).asInt());
			sidebar.setText(8, "§fKillStreak: §6" + bP.getData(DataType.PVP_KILLSTREAK).asInt());
			sidebar.setText(7, "");
			sidebar.setText(6, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(5, "§fXP: §b" + bP.getXp());
			sidebar.setText(4, "");
			sidebar.setText(3, "§fKit: §b" + gamer.getAbility().getName());
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
			} else if (minigame.getName().equalsIgnoreCase("fps")) {
				League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			sidebar.setText(12, "    §7pvp.mc-weaven.com.br     ");
			sidebar.setText(11, "");
			sidebar.setText(10, "§fKills: §6" + bP.getData(DataType.PVP_KILLS).asInt());
			sidebar.setText(9, "§fDeaths: §e" + bP.getData(DataType.PVP_DEATHS).asInt());
			sidebar.setText(8, "§fKillStreak: §6" + bP.getData(DataType.PVP_KILLSTREAK).asInt());
			sidebar.setText(7, "");
			sidebar.setText(6, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(5, "§fXP: §b" + bP.getXp());
			sidebar.setText(4, "");
			sidebar.setText(3, "§fKit: §b" + gamer.getAbility().getName());
			sidebar.setText(2, "");
			sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
			//sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
			} else if (minigame.getName().equalsIgnoreCase("1v1")) {
				League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			//sidebar.setText(13, "    §7pvp.mc-weaven.com.br     ");
			//sidebar.setText(12, "");
			sidebar.setText(11, "§fKills: §6" + bP.getData(DataType.PVP_KILLS).asInt());
			sidebar.setText(10, "§fDeaths: §e" + bP.getData(DataType.PVP_DEATHS).asInt());
			sidebar.setText(9, "§fKillStreak: §6" + bP.getData(DataType.PVP_KILLSTREAK).asInt());
			//sidebar.setText(8, "");
			sidebar.setText(7, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			sidebar.setText(6, "§fXP: §b" + bP.getXp());
			//sidebar.setText(5, "");
			//sidebar.setText(4, "§fBatalhando contra:");
			sidebar.setText(3, "§3" + ((BattleMinigame) minigame).getBattlePlayer(o));
			//sidebar.setText(2, "");
			//sidebar.setText(1, "    §6www.mc-weaven.com.br     ");
			} else if (minigame.getName().equalsIgnoreCase("lava")) {
				League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			//sidebar.setText(6, "    §7pvp.mc-weaven.com.br     ");
			//sidebar.setText(5, "");
			sidebar.setText(4, "§fXP: §b" + bP.getXp());
			sidebar.setText(3, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			//sidebar.setText(2, "");
			//sidebar.setText(1, "    §6www.mc-weaven.com.br    ");
			} else if (minigame.getName().equalsIgnoreCase("void")) {
				League league = bP.getLeague();
			sidebar.setTitle("§6§lPVP");
			//sidebar.setText(9, "    §7pvp.mc-weaven.com.br     ");
			//sidebar.setText(8, "");
			sidebar.setText(7, "§fXP: §b" + bP.getXp());
			sidebar.setText(6, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.name());
			//sidebar.setText(5, "");
			if (o.getLocation().getY() <= -64d) {
				sidebar.setText(4, "§fSobreviveu:");
				sidebar.setText(3, "§a" + ((VoidChallengeMinigame) minigame).getTimeSurviving(o));
			} else {
				sidebar.setText(4, "§fPara §ainiciar§7 o desafio");
				sidebar.setText(3, "§fdesafio pule no §5void§7!");
			}
			//sidebar.setText(2, "");
			//sidebar.setText(1, "    §6www.mc-weaven.com.br    ");
			}
			updateTab(o, count);
			minigame = null;
			sidebar = null;
			gamer = null;
			bP = null;
		}
	}
	
	public void updateTab(Player p, int onlineCount) {
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		int ping = PingAPI.getPing(p);
		TabListAPI.setHeaderAndFooter(p,
				"§6§lWEAVEN§f§lMC §e§lKITPVP" + "\n" + "§eMoedas: §f" + bP.getMoney() + " §9- §eTickets: §f"
						+ bP.getTickets() + " §9- §ePing: §f" + ping + "\n" + "§eTemos §f" + onlineCount
						+ "§e jogadores online!",
				"§bNick: §f" + bP.getName() + " §9- §bLiga: §f" + bP.getLeague().name() + " §9- §bXP: §f" + bP.getXp()
						+ "\n§bMais informações em: §fwww.mc-weaven.com.br");
	}

	@Override
	public void disable() {
		scrollerSpawn = null;
		titleSpawn = null;
	}
}