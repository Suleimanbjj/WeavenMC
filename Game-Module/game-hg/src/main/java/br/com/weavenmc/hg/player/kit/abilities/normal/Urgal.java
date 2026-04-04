package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.item.ItemBuilder;
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

public class Urgal extends Kit {

	private static final ItemBuilder ITEM_BUILDER = new ItemBuilder(Material.POTION).setAmount(2).setDurability(8201).setName("§r§5Urgal Potion");

	public Urgal(Manager manager) {
		super(manager);
		
		this.setActive(false);
		setPrice(48000);
		setCooldownTime(0D);
		setIcon(new ItemBuilder(Material.POTION).setAmount(2).setDurability(8201).getStack());
		setFree(false);
		setDescription("Use suas poções para ganhar força e derrotar seus inimigos mais facilmente.");
		setRecent(false);
		setItems(ITEM_BUILDER.getStack());
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().name().contains("RIGHT") && isKitItem(event.getItem(), "§r§5Urgal Potion")) {
			event.setCancelled(true);
			if (event.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
				event.getPlayer().sendMessage("§c§lURGAL §fVocê está está sob §4§lEFEITO§f do §c§lURGAL");
				return;
			}
			event.getItem().setAmount(event.getItem().getAmount() - 1);
			if (event.getItem().getAmount() == 0) {
				event.getPlayer().setItemInHand(null);
			}
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 60, 0), true);
		}
	}
}
