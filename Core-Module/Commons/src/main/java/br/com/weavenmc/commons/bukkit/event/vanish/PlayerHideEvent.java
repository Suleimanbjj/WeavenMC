package br.com.weavenmc.commons.bukkit.event.vanish;

import org.bukkit.entity.Player;
import br.com.weavenmc.commons.bukkit.event.PlayerCancellableEvent;

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

/**
 * 
 * Evento chamado quando um jogador vai executar o metodo hidePlayer()
 * durante o sistema de Vanish do modo admin
 *
 */
public class PlayerHideEvent extends PlayerCancellableEvent {
	
	private Player toHide;

	public PlayerHideEvent(Player player, Player toHide) {
		super(player);
		this.toHide = toHide;
	}

	/**
	 * Player que irá executar o metodo hidePlayer()
	 * Ex: toHide.hidePlayer( player )
	 */
	public Player getToHide() {
		return toHide;
	}
}
