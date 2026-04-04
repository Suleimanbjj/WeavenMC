package br.com.weavenmc.hg.player.inventories.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import br.com.weavenmc.hg.game.custom.HungerListener;
import br.com.weavenmc.hg.game.handler.item.CacheItems;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.hg.player.inventories.inventory.KitSelectorHandler.KitType;
import br.com.weavenmc.hg.player.item.ItemBuilder;
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

public class InventoryOpener extends HungerListener {

	private static final ItemBuilder itemBuilder = new ItemBuilder(Material.AIR);

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getItem() == null)
			return;
		if (getManager().getGameManager().isPreGame()) {
			event.setCancelled(true);
			if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
				return;
			event.setCancelled(true);

			Player player = event.getPlayer();

			if (itemBuilder.checkItem(event.getItem(),
					CacheItems.JOIN_ONEKIT.getItem(0).getStack().getItemMeta().getDisplayName())) {
				getManager().getInventoryManager().getKitSelectorHandler().generate(player, KitType.YOUR_KITS);
			}

			if (itemBuilder.checkItem(event.getItem(),
					CacheItems.JOIN_SECKIT.getItem(1).getStack().getItemMeta().getDisplayName())) {
				getManager().getInventoryManager().getKitSelectorHandler().generate(player, KitType.SECONDARY_KITS);
			}

			if (itemBuilder.checkItem(event.getItem(),
					CacheItems.JOIN_ONEKIT.getItem(1).getStack().getItemMeta().getDisplayName())) {
				Gamer gamer = getManager().getGamerManager().getGamer(player);

				if (player.hasPermission("hgkit2.*")) {
					player.sendMessage("§6§lKIT DA PARTIDA §fVocê já tem todos os kits!");
					return;
				}

				if (getManager().getSurpriseKitManager().suject.contains(player)) {
					gamer.sendMessage("§6§lKIT DA PARTIDA §fVocê já usou o kit dessa partida!");
					return;
				}

				if (gamer.getSurpriseKit() == null) {
					getManager().getSurpriseKitManager().generate(player);
				} else {
					gamer.sendMessage("§6§lKIT DA PARTIDA §fVocê já usou o kit dessa partida!");
				}
			}
		}
	}

}
