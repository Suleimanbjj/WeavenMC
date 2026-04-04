package br.com.weavenmc.ypvp.managers;

import java.util.HashSet;
import java.util.Set;
import br.com.weavenmc.ypvp.Management;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.minigame.BattleMinigame;
import br.com.weavenmc.ypvp.minigame.FramesMinigame;
import br.com.weavenmc.ypvp.minigame.LavaChallengeMinigame;
import br.com.weavenmc.ypvp.minigame.Minigame;
import br.com.weavenmc.ypvp.minigame.SpawnMinigame;
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

public class WarpManager extends Management {

	private Set<Minigame> minigames = new HashSet<>();

	public WarpManager(yPvP plugin) {
		super(plugin);
	}

	@Override
	public void enable() {
		minigames.add(new BattleMinigame());
		minigames.add(new FramesMinigame());
		minigames.add(new LavaChallengeMinigame());
		minigames.add(new SpawnMinigame());
		minigames.add(new VoidChallengeMinigame());
		for (Minigame minigame : minigames) {
			registerListener(minigame);
			getPlugin().getLogger().info("Registrado a Warp: " + minigame.getName());
		}
	}

	public Minigame getWarp(Class<?> clazz) {
		for (Minigame minigame : minigames) {
			if (!minigame.getClass().getSimpleName().equalsIgnoreCase(clazz.getSimpleName()))
				continue;
			return minigame;
		}
		return null;
	}
	
	public Minigame getWarp(String name) {
		for (Minigame minigame : minigames) {
			if (!minigame.getName().equalsIgnoreCase(name)) {
				for (String other : minigame.getOtherNames()) {
					if (!other.equalsIgnoreCase(name))
						continue;
					return minigame;
				}
			} else {
				return minigame;
			}
		}
		return null;
	}
	
	@Override
	public void disable() {
		minigames.clear();
		minigames = null;
	}
}
