package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
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

public class Switcher extends Kit {

	public Switcher(Manager manager) {
		super(manager);
		
		setPrice(45000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.SNOW_BALL));
		setFree(false);
		setDescription("Use sua habilidade para trocar de localização com seus oponentes.");
		setRecent(false);
		setItems(new ItemBuilder(Material.SNOW_BALL).setAmount(10).getStack());
	}

	@EventHandler
	public void snowball(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Snowball && event.getEntity() instanceof LivingEntity) {
			Snowball snowball = (Snowball) event.getDamager();
			Player shooter = (Player) snowball.getShooter();
			if (!hasKit(shooter))
				return;

			if (isInvencibility()) {
				shooter.sendMessage("§6§lSWITCHER §fVocê não pode usar seu kit na §e§lINVENCIBILIDADE");
				return;
			}

			if (!(snowball.getShooter() instanceof Player))
				return;

			if (Gladiator.inGladiator(shooter)) {
				shooter.sendMessage("§6§lSWITCHER §fVocê não pode usar seu kit no §e§lGLADIATOR");
				event.setCancelled(true);
				shooter.updateInventory();
				return;
			}

			Location shooterLoc = shooter.getLocation();
			shooter.teleport(event.getEntity().getLocation());
			event.getEntity().teleport(shooterLoc);
		}
	}

}
