package br.com.weavenmc.hg.player.kitaward.constructors;

import java.util.Random;
import org.bukkit.inventory.ItemStack;

import br.com.weavenmc.hg.manager.Manager;

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

public class Surprise {

	private Manager manager;
	private String name;
	private Reward reward;
	private ItemStack rewardIcon;
	private Random random;

	public Surprise(Manager manager,Reward reward, ItemStack rewardIcon) {
		this.manager = manager;
		this.reward = reward;
		this.rewardIcon = rewardIcon;
		this.random = new Random();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public ItemStack getRewardIcon() {
		return rewardIcon;
	}

	public void setRewardIcon(ItemStack rewardIcon) {
		this.rewardIcon = rewardIcon;
	}

	public Random getRandom() {
		return random;
	}

}
