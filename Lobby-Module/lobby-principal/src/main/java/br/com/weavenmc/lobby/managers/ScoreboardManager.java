package br.com.weavenmc.lobby.managers;

import org.bukkit.event.*;
import br.com.weavenmc.lobby.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import br.com.weavenmc.commons.bukkit.*;
import br.com.weavenmc.commons.bukkit.account.*;
import br.com.weavenmc.commons.*;
import java.util.*;
import br.com.weavenmc.lobby.gamer.*;
import br.com.weavenmc.commons.bukkit.scoreboard.*;
import org.bukkit.plugin.*;
import br.com.weavenmc.commons.bukkit.event.update.*;
import br.com.weavenmc.commons.core.account.*;
import br.com.weavenmc.commons.bukkit.api.player.*;
import br.com.weavenmc.commons.bukkit.api.tablist.*;
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

public class ScoreboardManager extends Management implements Listener
{
    public ScoreboardManager(final Lobby plugin) {
        super(plugin);
    }
    
    @Override
    public void enable() {
        for (final Player o : Bukkit.getOnlinePlayers()) {
            this.createScoreboard(o);
        }
        new BukkitRunnable() {
            public void run() {
                final int onlineCount = BukkitMain.getInstance().getNetworkManager().getNetworkOnlineCount();
                for (final Player o : Bukkit.getOnlinePlayers()) {
                    Gamer gamer = ScoreboardManager.this.getPlugin().getGamerManager().getGamer(o.getUniqueId());
                    if (gamer == null) {
                        return;
                    }
                    Sidebar sidebar = gamer.getSidebar();
                    if (sidebar == null) {
                        continue;
                    }
                    BukkitPlayer bP = (BukkitPlayer)WeavenMC.getAccountCommon().getWeavenPlayer(o.getUniqueId());
                    League league = bP.getLeague();
                    if (sidebar.isHided()) {
                        sidebar.show();
                    }
                    sidebar.setTitle("§6§lLOBBY");
                    sidebar.setText(16, "    §7mc-weaven.com.br    ");
                    sidebar.setText(14, "§fGrupo: " + bP.getGroup().getTagToUse().getName());
                    sidebar.setText(13, "§fMoedas: §b" + bP.getMoney());
                    sidebar.setText(12, "§fDoubleXP: §b" + bP.getDoubleXpMultiplier());
                    sidebar.setText(10, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.toString());
                    sidebar.setText(9, "§fXP: §6" + bP.getXp());
                    sidebar.setText(6, "§fLobby: §e" + ScoreboardManager.this.getPlugin().getLobbyId());
                    sidebar.setText(7, "§fOnline: §e" + onlineCount + " jogadores");
                    ScoreboardManager.this.updateTab(o, onlineCount);
                    league = null;
                    sidebar = null;
                    gamer = null;
                    bP = null;
                }
            }
        }.runTaskTimer((Plugin)this.getPlugin(), 15L, 15L);
    }
    
    public void createScoreboard(final Player p) {
        Gamer gamer = this.getPlugin().getGamerManager().getGamer(p.getUniqueId());
        if (gamer == null) {
            return;
        }
        BukkitPlayer bP = (BukkitPlayer)WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
        if (bP == null) {
            return;
        }
        League league = bP.getLeague();
        Sidebar sidebar = gamer.getSidebar();
        if (sidebar == null) {
            sidebar = new Sidebar(p.getScoreboard());
        }
        gamer.setSidebar(sidebar);
        sidebar.show();
        sidebar.setTitle("§6§lLOBBY");
        sidebar.setText(16, "    §7mc-weaven.com.br    ");
        sidebar.setText(15, "");
        sidebar.setText(14, "§fGrupo: " + bP.getGroup().getTagToUse().getName());
        sidebar.setText(13, "§fMoedas: §b" + bP.getMoney());
        sidebar.setText(12, "§fDoubleXP: §b" + bP.getDoubleXpMultiplier());
        sidebar.setText(11, "");
        sidebar.setText(10, "§fRank: " + league.getColor() + league.getSymbol() + " " + league.toString());
        sidebar.setText(9, "§fXP: §6" + bP.getXp());
        sidebar.setText(8, "");
        sidebar.setText(7, "§fOnline: §e...");
        sidebar.setText(6, "§fLobby: §e" + this.getPlugin().getLobbyId());
        sidebar.setText(5, "");
        sidebar.setText(4, "    §6www.mc-weaven.com.br    ");
        this.updateTab(p, 0);
        league = null;
        sidebar = null;
        gamer = null;
        bP = null;
    }
    
    public void onUpdate(final UpdateEvent event) {
    }
    
    public String format(final Tag tag) {
        final String part1 = tag.name().substring(0, 1);
        final String part2 = tag.name().toLowerCase().substring(1);
        return String.valueOf(tag.getPrefix().substring(0, 2)) + part1 + part2;
    }
    
    public void updateTab(final Player p, final int onlineCount) {
        final BukkitPlayer bP = (BukkitPlayer)WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
        final int ping = PingAPI.getPing(p);
        TabListAPI.setHeaderAndFooter(p, "§6§lWEAVEN§f§lMC §e§lLOBBY\n§eMoedas: §f" + bP.getMoney() + " §9- §eTickets: §f" + bP.getTickets() + " §9- §ePing: §f" + ping + "\n" + "§eTemos §f" + onlineCount + "§e jogadores online!", "§bNick: §f" + bP.getName() + " §9- §bLiga: §f" + bP.getLeague().name() + " §9- §bXP: §f" + bP.getXp() + "\n§bMais informações em: §fwww.mc-weaven.com.br");
    }
    
    @Override
    public void disable() {
    }
}
