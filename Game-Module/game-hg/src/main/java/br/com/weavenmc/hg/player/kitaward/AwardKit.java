package br.com.weavenmc.hg.player.kitaward;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.kit.Kit;
import br.com.weavenmc.hg.player.kitaward.constructors.Reward;
import br.com.weavenmc.hg.player.kitaward.constructors.Surprise;
import br.com.weavenmc.hg.player.kitaward.constructors.Reward.RewardType;

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

public class AwardKit extends Surprise {

	public List<String> hgKits = Arrays.asList("Achilles", "Anchor", "Barbarian", "Blink", "Boxer", "Camel",
			"Cannibal, Cultivator", "Demoman", "Endermage", "Fireman", "Fisherman", "Forger", "Gladiator", "Grandpa",
			"Grappler", "Hulk", "JackHammer", "Kangaroo", "Launcher", "LumberJack", "Madman", "Magma", "Miner", "Monk",
			"Ninja", "Phantom", "Poseidon", "Pyro", "Reaper", "Scout", "Snail", "Specialist", "Stomper", "Surprise",
			"Switcher", "Tank", "Thor", "Timelord", "Turtle", "Urgal", "Viking", "Viper", "Worm");

	public AwardKit(Manager manager) {
		super(manager, null, new ItemStack(Material.AIR));

		Reward reward = null;

		String kit = hgKits.get(getRandom().nextInt(hgKits.size()));
		reward = new Reward(kit, 1, RewardType.KIT);

		Kit kitExample = getManager().getKitManager().getKit("Endermage");

		if (getManager().getKitManager().getKit(kit) != null) {
			kitExample = getManager().getKitManager().getKit(kit);
		}

		setRewardIcon(kitExample.getIcon());

		setReward(reward);
	}

}
