package br.com.weavenmc.hg.player.kitaward.constructors;

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
public class Reward {

	public int ammount;
	public String reward;
	public RewardType type;

	public Reward(int ammount, RewardType type) {
		this.ammount = ammount;
		this.type = type;
		this.reward = "";
	}

	public Reward(String reward, int ammount,RewardType type) {
		this.ammount = ammount;
		this.type = type;
		this.reward = reward;
	}

	public int getAmmount() {
		return ammount;
	}

	public String getReward() {
		return reward;
	}

	public RewardType getType() {
		return type;
	}

	public static enum RewardType {
		COINS("Coins"),
		VIP("Vips"),
		KIT("Kit"),
		DOUBLE("Double XP");

		private String name;

		private RewardType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
