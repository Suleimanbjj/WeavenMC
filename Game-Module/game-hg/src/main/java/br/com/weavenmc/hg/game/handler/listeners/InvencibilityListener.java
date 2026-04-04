package br.com.weavenmc.hg.game.handler.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import br.com.weavenmc.hg.game.custom.HungerListener;
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

public class InvencibilityListener extends HungerListener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (getManager().getGameManager().isInvencibility())
			if (event.getEntity() instanceof Player)
				event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (getManager().getGameManager().isInvencibility())
			if (event.getEntity() instanceof Player)
				event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
		if (getManager().getGameManager().isInvencibility())
			if (event.getEntity() instanceof Player)
				event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player)
			if (getManager().getGameManager().isInvencibility())
				event.setCancelled(true);
	}
}
