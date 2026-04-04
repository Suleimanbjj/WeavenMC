package br.com.weavenmc.ypvp.ability.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.ypvp.ability.Ability;

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

public class AntiStomperAbility extends Ability {

	public AntiStomperAbility() {
		setName("AntiStomper");
		setHasItem(false);
		setGroupToUse(Group.MEMBRO);
		setIcon(Material.DIAMOND_HELMET);
		setDescription(new String[] { "§7Receba dano reduzido de stompers." });
		setPrice(30000);
		setTempPrice(3500);
	}

	@Override
	public void eject(Player p) {
		
	}
}
