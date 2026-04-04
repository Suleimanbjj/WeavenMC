package br.com.weavenmc.hg.player.kit.abilities.normal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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

public class Kangaroo extends Kit {

	private static final HashMap<UUID, Long> hitNerf = new HashMap<>();
	private static final Set<UUID> kangarooUses = new HashSet<>();

	public Kangaroo(Manager manager) {
		super(manager);
		
		setPrice(41000);
		setCooldownTime(30D);
		setIcon(new ItemStack(Material.FIREWORK));
		setFree(false);
		setDescription("Transforme-se em um canguru dê pulos duplos e mova-se mais rapido");
		setRecent(false);
		setItems(new ItemBuilder(Material.FIREWORK).setName("§aKangaroo").getStack());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (hasKit(player) && event.getAction() != Action.PHYSICAL && isKitItem(event.getItem(), Material.FIREWORK, "§aKangaroo")) {
			event.setCancelled(true);

			if (kangarooUses.contains(player.getUniqueId()))
				return;

			if (isInCombatCooldown(player.getUniqueId(), System.currentTimeMillis())) {
				player.setVelocity(new Vector(0, -1.0, 0));
				player.sendMessage("§c§lKANGAROO §fVocê está em §4§lCOMBATE§f, aguarde para usar sua habilidade.");
				return;
			}

			Vector vector = player.getEyeLocation().getDirection();
			if (player.isSneaking()) {
				vector = vector.multiply(1.8F).setY(0.5F);
			} else {
				vector = vector.multiply(0.5F).setY(1F);
			}

			player.setFallDistance(-1.0F);
			player.setVelocity(vector);
			kangarooUses.add(player.getUniqueId());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (!kangarooUses.contains(player.getUniqueId())) {
			return;
		}
		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR || player.isOnGround()) {
			kangarooUses.remove(player.getUniqueId());
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		if (!hasKit((Player) event.getEntity()))
			return;
		if (event.isCancelled())
			return;
		if (!(event.getDamager() instanceof LivingEntity))
			return;
		if (getManager().getGameManager().isInvencibility())
			return;
		if (event.getDamager() instanceof Player && getGamer((Player) event.getDamager()).isSpectating())
			return;

		hitNerf.put(event.getEntity().getUniqueId(), System.currentTimeMillis() + 3000L);
	}

	private boolean isInCombatCooldown(UUID uuid, long now) {
		Long expiresAt = hitNerf.get(uuid);
		if (expiresAt == null) {
			return false;
		}
		if (expiresAt <= now) {
			hitNerf.remove(uuid);
			return false;
		}
		return true;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && hasKit((Player) e.getEntity()) && e.getCause() == DamageCause.FALL && e.getDamage() > 7.0D) {
			e.setDamage(7.0D);
		}
	}
}
