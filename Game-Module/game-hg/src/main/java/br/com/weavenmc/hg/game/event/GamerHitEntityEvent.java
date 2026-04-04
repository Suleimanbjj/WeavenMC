package br.com.weavenmc.hg.game.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import br.com.weavenmc.hg.player.events.CustomEvent;
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

public class GamerHitEntityEvent extends CustomEvent {

	private Player player;
	private LivingEntity entity;
	private double damage;
	private boolean cancel;

	public GamerHitEntityEvent(Player player, LivingEntity entity, double damage) {
		this.player = player;
		this.entity = entity;
		this.damage = damage;
	}

	public Player getPlayer() {
		return player;
	}

	public Player getDamager() {
		return player;
	}

	public LivingEntity getEntity() {
		return this.entity;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public boolean isCancelled() {
		return this.cancel;
	}
}
