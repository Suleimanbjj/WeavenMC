package br.com.weavenmc.skywars.hability.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import br.com.weavenmc.skywars.WeavenSkywars;
import br.com.weavenmc.skywars.game.GameManager.GameState;
import br.com.weavenmc.skywars.hability.HabilityAPI.Hability;

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

public class KingHellEvent implements Listener{

	@EventHandler
	public void onHell(EntityDamageEvent event) {
		if (WeavenSkywars.getGameManager().getState() != GameState.GAME) {
			return;
		}
		Entity entity = event.getEntity();
		if (!(entity instanceof Player)) {
			return;
		}
		Player fireman = (Player) entity;
		if (WeavenSkywars.getGameManager().getHabilityAPI().getHabilidade(fireman) != Hability.KING_HELL) {
			return;
		}
		EntityDamageEvent.DamageCause fire = event.getCause();
		if ((fire == EntityDamageEvent.DamageCause.FIRE) || (fire == EntityDamageEvent.DamageCause.LAVA) || (fire == EntityDamageEvent.DamageCause.FIRE_TICK) || (fire == EntityDamageEvent.DamageCause.LIGHTNING)) {
			event.setCancelled(true);
		}
	}

}
