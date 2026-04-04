package br.com.weavenmc.hg.game.handler.listeners;

import br.com.weavenmc.hg.game.custom.HungerListener;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.commons.bukkit.api.bossbar.BossBarAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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

public class KitMoveListener extends HungerListener {

	@EventHandler
	public void onPlayerKit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (!getManager().getGameManager().isGame()) {
			return;
		}
		final Player player = (Player) e.getEntity();
		final Player playerDamager = (Player) e.getDamager();

		Gamer gamer = getManager().getGamerManager().getGamer(player);
		if (player == playerDamager) {
			return;
		}

		BossBarAPI.setBar(playerDamager,
				"§f" + player.getName() + " - " + gamer.getKit().getName() + " e " + gamer.getKit2().getName(), 6);
		BossBarAPI.setBar(player,
				"§f" + playerDamager.getName() + " - "
						+ getManager().getGamerManager().getGamer(playerDamager).getKit().getName() + " e "
						+ getManager().getGamerManager().getGamer(playerDamager).getKit2().getName(), 6);
	}
}
