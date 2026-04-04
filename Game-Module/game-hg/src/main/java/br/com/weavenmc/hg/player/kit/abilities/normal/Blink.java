package br.com.weavenmc.hg.player.kit.abilities.normal;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
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

public class Blink extends Kit {

	public static HashMap<UUID, Integer> usesBlink = new HashMap<>();

	public Blink(Manager manager) {
		super(manager);
		
		setPrice(42500);
		setCooldownTime(15D);
		setIcon(new ItemStack(Material.NETHER_STAR));
		setFree(false);
		setDescription("Crie estruturas em lugares altos.");
		setRecent(false);
		setItems(createItemStack("§aBlink", Material.NETHER_STAR));
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItem();
		if ((event.getAction().name().contains("RIGHT")) && (hasKit(player)) && (isKitItem(itemStack, Material.NETHER_STAR, "§aBlink"))) {
			event.setCancelled(true);

			if (inCooldown(player)) {
				sendCooldown(player);
				return;
			}

			Block block = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(5.0D)).getBlock();

			while (block.getY() > 128) {
				player.sendMessage("§b§lBLINK §fVocê não pode usar seu kit nessa §3§lALTURA");
				return;
			}

			int use = 0;

			if (usesBlink.containsKey(player.getUniqueId())) {
				use = usesBlink.get(player.getUniqueId());
			}

			use++;

			if (use == 3) {
				addCooldown(player);
				usesBlink.remove(player.getUniqueId());
			} else {
				usesBlink.put(player.getUniqueId(), use);
			}

			if (block.getRelative(BlockFace.DOWN).getType() == Material.AIR) {
				block.getRelative(BlockFace.DOWN).setType(Material.LEAVES);
			}

			player.teleport(new Location(player.getWorld(), block.getX(), block.getY(), block.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
			player.setFallDistance(0.0F);
			player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 50.0F);
		}
	}
}
