package br.com.weavenmc.hg.player.admin.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import br.com.weavenmc.hg.game.custom.HungerListener;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.admin.AdminMode;
import br.com.weavenmc.commons.bukkit.event.vanish.PlayerShowEvent;
import br.com.weavenmc.commons.core.permission.Group;
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

public class AdminListener extends HungerListener {

	@EventHandler
	public void onShow(PlayerShowEvent event) {
		Player player = event.getToShow();
		Gamer gamer = getManager().getGamerManager().getGamer(player);
		if (getManager().getGameManager().isInvencibility() || getManager().getGameManager().isGame()) {
			if (gamer.isAlive()) {
				Gamer spec = getManager().getGamerManager().getGamer(event.getPlayer());
				if (spec.isSpectating() || AdminMode.getInstance().isAdmin(event.getPlayer())) {
					event.setCancelled(true);
					player.hidePlayer(event.getPlayer());
				}
			}
		}
	}

	@EventHandler
	private void onCancelBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		BukkitPlayer bP = BukkitPlayer.getPlayer(player.getUniqueId());
		if (AdminMode.getInstance().isAdmin(player) && bP.getGroup().getId() < Group.YOUTUBERPLUS.getId())
			event.setCancelled(true);
	}

	@EventHandler
	private void onInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Player) {
			Player player = event.getPlayer();

			if (AdminMode.getInstance().isAdmin(player)) {
				Player clicked = (Player) event.getRightClicked();
				ItemStack item = player.getInventory().getItemInHand();

				if (item.getType().equals(Material.AIR)) {
					player.openInventory(clicked.getInventory());
				}
			}
		}
	}
}
