package br.com.mcweaven.gladiator.fight.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.mcweaven.gladiator.fight.Fight;

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

public class FightManager {

	List<Fight> fights;

	public FightManager() {
		fights = new ArrayList<>();
	}

	public boolean fightExists(Fight fight) {
		return fights.contains(fight);
	}

	public Collection<Fight> getFights() {
		return fights;
	}

	public void newFight(Fight fight) {
		fights.add(fight);
	}

	public void endFight(Fight fight) {
		fights.remove(fight);
	}

}
