package br.com.weavenmc.hg.player.kit.abilities.normal;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
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

public class Ninja extends Kit {

	private static HashMap<UUID, Player> target = new HashMap<>();

	public Ninja(Manager manager) {
		super(manager);
		
		setPrice(49000);
		setCooldownTime(6D);
		setIcon(new ItemStack(Material.NETHER_STAR));
		setFree(false);
		setDescription("Marque um inimigo e use suas habilidades de um ninja e teleporte para ele.");
		setRecent(false);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!event.isCancelled() && event.getEntity() instanceof Player && event.getDamager() instanceof Player && hasKit((Player) event.getDamager())) {
			target.put(event.getDamager().getUniqueId(), ((Player) event.getEntity()));
		}
	}

	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
		if (event.isSneaking() && target.containsKey(event.getPlayer().getUniqueId())) {

			if (inCooldown(event.getPlayer())) {
				sendCooldown(event.getPlayer());
				return;
			}

			Player targetPlayer = target.get(event.getPlayer().getUniqueId());

			if (event.getPlayer().getLocation().getY() > 140) {
				return;
			}

			if (targetPlayer == null || !targetPlayer.isOnline() || getGamer(targetPlayer).isSpectating()) {
				event.getPlayer().sendMessage("§c§lNINJA §fO player não está §4§lONLINE");
			} else if (targetPlayer.getLocation().distance(event.getPlayer().getLocation()) > 100.0D) {
				event.getPlayer().sendMessage("§c§lNINJA §fO player está muito §4§lLONGE§f de você!");
			} else if (Launcher.getNoFallList().contains(event.getPlayer())) {
				event.getPlayer().sendMessage("§c§lNINJA §fVocê não pode usar em um §4§lLAUNCHER");
			} else if (Gladiator.inGladiator(targetPlayer) && !Gladiator.inGladiator(event.getPlayer())) {
				event.getPlayer().sendMessage("§c§lNINJA §fO player está em um §4§lGLADIATOR!");
			} else {
				event.getPlayer().teleport(targetPlayer.getLocation());
				target.remove(event.getPlayer().getUniqueId());
				addCooldown(event.getPlayer());
			}

		}
	}
}
