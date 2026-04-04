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
 * Evento chamado quando um jogador vai executar o metodo showPlayer()
 * durante o sistema de Vanish do modo admin
 *
 */
public class PlayerShowEvent extends PlayerCancellableEvent {
	
	private Player toShow;

	public PlayerShowEvent(Player player, Player toShow) {
		super(player);
		this.toShow = toShow;
	}

	/**
	 * Player que irá executar o metodo showPlayer()
	 * Ex: toShow.showPlayer( player )
	 */
	public Player getToShow() {
		return toShow;
	}
}
