package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

public class Reaper extends Kit {

	public Reaper(Manager manager) {
		super(manager);
		
		setPrice(45000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.WOOD_HOE));
		setFree(false);
		setDescription("Ceife a vida de seus inimgos, deixando-os com um veneno mortal.");
		setRecent(false);
		setItems(createItemStack("§4Reaper", Material.WOOD_HOE));
	}

	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (player.getItemInHand().getType() == Material.WOOD_HOE && hasKit(player)) {
				if (isInvencibility()) {
					player.sendMessage("§6§lREAPER §fVocê não pode usar seu kit na §e§lINVENCIBILIDADE");
					return;
				}
				if (event.getEntity() instanceof LivingEntity) {
					((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 6 * 20, 0), true);
				}
			}
		}
	}

}
