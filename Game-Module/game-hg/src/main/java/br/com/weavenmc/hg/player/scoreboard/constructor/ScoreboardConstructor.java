package br.com.weavenmc.hg.player.scoreboard.constructor;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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

public class ScoreboardConstructor {
	protected final Player player;
	protected final Scoreboard handle;
	protected Objective sidebarObjective;
	protected int index;

	public ScoreboardConstructor(Player tHolder) {
		this.player = tHolder;
		this.index = 1;
		this.handle = Bukkit.getScoreboardManager().getNewScoreboard();
	}

	public final void initialize(String name) {
		this.player.getPlayer().setScoreboard(this.handle);
		
		this.sidebarObjective = this.handle.registerNewObjective("sidebar", "dummy");
		
		this.sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.sidebarObjective.setDisplayName(name);
	}

	public final void setDisplayName(String name) {
		this.sidebarObjective.setDisplayName(name);
	}

	public final void setScore(String name, String prefix, String suffix) {
		setScore(name, prefix, suffix, this.index++);
	}

	public void resetScore(int index) {
		this.handle.resetScores(color(index));
	}

	public void setText(int index, String text) {
		Preconditions.checkArgument((index > 0) && (index < 16), "Parameter 'index' must be between 1 and 15");
		if (text.length() > 32) {
			text = text.substring(0, 32);
		}
		Team team = this.handle.getTeam("score-" + index);
		String str = color(index);
		if (team == null) {
			team = this.handle.registerNewTeam("score-" + index);
			this.sidebarObjective.getScore(str).setScore(index);
		}
		if (!team.hasEntry(str)) {
			team.addEntry(str);
		}
		Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
		String prefix = (String) iterator.next();
		if (prefix.endsWith("§")) {
			prefix = cutStr(prefix);
			if (!prefix.equals(team.getPrefix())) {
				team.setPrefix(prefix);
			}
			if (iterator.hasNext()) {
				String suffix = cutStr((String) iterator.next());
				if (!suffix.equals(team.getSuffix())) {
					team.setSuffix(suffix);
				}
			} else if (!"".equals(team.getSuffix())) {
				team.setSuffix("");
			}
		} else if (iterator.hasNext()) {
			String suffix = (String) iterator.next();
			if (!suffix.startsWith("§")) {
				suffix = cutStr(ChatColor.getLastColors(prefix) + suffix);
			}
			if (!prefix.equals(team.getPrefix())) {
				team.setPrefix(prefix);
			}
			if (!suffix.equals(team.getSuffix())) {
				team.setSuffix(suffix);
			}
		} else {
			if (!prefix.equals(team.getPrefix())) {
				team.setPrefix(prefix);
			}
			if (!"".equals(team.getSuffix())) {
				team.setSuffix("");
			}
		}
	}

	public void reset() {
		this.index = 1;
	}

	protected String cutStr(String string) {
		int length = string.length();
		if (length > 16) {
			string = string.substring(0, 16);
		}
		if (string.endsWith("§")) {
			string = string.substring(0, length - 1);
		}
		return string;
	}

	public String color(int i) {
		return ChatColor.values()[(i - 1)].toString();
	}

	public final void setScore(String name, String prefix, String suffix, int value) {
		if ((name == null) || (name.length() > 14)) {
			return;
		}
		name = name + ChatColor.RESET;
		Team scoreTeam = this.handle.getTeam(name);
		if (scoreTeam != null) {
			return;
		}
		scoreTeam = this.handle.registerNewTeam(name);
		scoreTeam.addEntry(name);
		if ((prefix != null) && (prefix.length() <= 32)) {
			scoreTeam.setPrefix(prefix);
		}
		if ((suffix != null) && (suffix.length() <= 32)) {
			scoreTeam.setSuffix(suffix);
		}
		this.sidebarObjective.getScore(name).setScore(value);
	}

	public final void updateScore(String name, String prefix, String suffix) {
		if ((name == null) || (name.length() > 14)) {
			return;
		}
		name = name + ChatColor.RESET;
		Team scoreTeam = this.handle.getTeam(name);
		if (scoreTeam == null) {
			return;
		}
		scoreTeam.setPrefix((prefix != null) && (prefix.length() <= 32) ? prefix : "");
		scoreTeam.setSuffix((suffix != null) && (suffix.length() <= 32) ? suffix : "");
	}

	public final void unsetScore(String name) {
		if ((name == null) || (name.length() > 15)) {
			return;
		}
		name = name + ChatColor.RESET;
		Team scoreTeam = this.handle.getTeam(name);
		if (scoreTeam == null) {
			return;
		}
		scoreTeam.unregister();
		this.handle.resetScores(name);
	}

	public final Scoreboard getHandle() {
		return this.handle;
	}

	public final Objective getSidebarObjective() {
		return this.sidebarObjective;
	}
}
