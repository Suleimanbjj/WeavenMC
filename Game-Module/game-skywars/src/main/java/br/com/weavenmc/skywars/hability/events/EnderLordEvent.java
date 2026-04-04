package br.com.weavenmc.skywars.hability.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import br.com.weavenmc.skywars.WeavenSkywars;
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

public class EnderLordEvent implements Listener {
	
    @EventHandler
    public void onEntityDamage(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
 
        if (WeavenSkywars.getGameManager().getHabilityAPI().isHabilidade(player, Hability.ENDER_LORD)) {
        	if (event.getCause() == TeleportCause.ENDER_PEARL) {
                event.setCancelled(true);
                player.teleport(event.getTo());
            }
        	return;
        }
    }

}
