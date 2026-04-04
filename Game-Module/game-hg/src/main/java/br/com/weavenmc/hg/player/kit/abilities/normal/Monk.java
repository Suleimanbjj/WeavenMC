package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.kit.Kit;
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

public class Monk extends Kit {

	public Monk(Manager manager) {
		super(manager);
		
		setPrice(40000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.BLAZE_ROD));
		setFree(false);
		setDescription("Use suas habilidades para bagunçar o inventário do seu inimigo.");
		setRecent(false);
		setIcon(createItemStack("§aMonk", Material.BLAZE_ROD));
		setItems(new ItemStack(Material.BLAZE_ROD));
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player && isKitItem(e.getPlayer().getItemInHand(), Material.BLAZE_ROD, "§aMonk") && hasKit(e.getPlayer())) {
			Player clicked = (Player) e.getRightClicked();
			Player player = e.getPlayer();
			if (getManager().getGameManager().isInvencibility()) {
				player.sendMessage("§6§lMONK §fVocê não pode usar seu kit na §e§lINVENCIBILIDADE");
				return;
			}
			if (inCooldown(player)) {
				sendCooldown(player);
				return;
			}
			addCooldown(player);

			int randomNumber = getManager().getRandom().nextInt(36);

			ItemStack atual = (clicked.getItemInHand() != null ? clicked.getItemInHand().clone() : null);
			ItemStack random = (clicked.getInventory().getItem(randomNumber) != null ? clicked.getInventory().getItem(randomNumber).clone() : null);
			if (random == null) {
				clicked.getInventory().setItem(randomNumber, atual);
				clicked.setItemInHand(null);
			} else {
				clicked.getInventory().setItem(randomNumber, atual);
				clicked.getInventory().setItemInHand(random);
			}
			player.sendMessage("§6§lMONK §fO player §e§l" + clicked.getName() + "§f foi monkado com sucesso.");
		}
	}

}
