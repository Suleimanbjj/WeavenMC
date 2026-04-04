package br.com.weavenmc.hg.player.kit.abilities.normal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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

public class Launcher extends Kit {

	private static HashMap<Block, SpongeDirection> spongesDirections = new HashMap<>();
	private static List<Player> noFall = new ArrayList<>();

	public Launcher(Manager manager) {
		super(manager);
		
		setPrice(46000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.SPONGE));
		setFree(false);
		setDescription("Use suas esponjas para ser lançado em uma direção.");
		setRecent(false);
		setItems(new ItemStack(Material.SPONGE, 20));
	}

	private enum SpongeDirection {
		SKY;
	}

	@EventHandler
	public void launcher(BlockPlaceEvent event) {
		if (getManager().getGameManager().isPreGame()) {
			return;
		}
		if (event.getBlock().getType() == Material.SPONGE) {
			Player player = event.getPlayer();
			if (getDirection(player) == null) {
				event.setCancelled(true);
				player.sendMessage("§6§lLAUNCHER §fOcorreu um problema com a §e§lDIRECAO§f da sua §6§lSPONGE");
				return;
			}
			spongesDirections.put(event.getBlock(), getDirection(player));
		}
	}

	private SpongeDirection getDirection(Player p) {
		return SpongeDirection.SKY;
	}

	@EventHandler
	public void launcher(EntityExplodeEvent event) {
		for (Block blayer : event.blockList()) {
			if (spongesDirections.containsKey(blayer)) {
				spongesDirections.remove(blayer);
			}
		}
	}

	@EventHandler
	public void launcher(BlockBreakEvent event) {
		if (spongesDirections.containsKey(event.getBlock())) {
			spongesDirections.remove(event.getBlock());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void launcher(final PlayerMoveEvent event) {
		if (event.getTo() == null || event.getFrom() == null) {
			return;
		}
		if (event.getFrom().getBlockX() == event.getTo().getBlockX()
				&& event.getFrom().getBlockY() == event.getTo().getBlockY()
				&& event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
			return;
		}

		final Player player = event.getPlayer();
		if (!player.isOnGround()) {
			return;
		}

		Block spongeBelow = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (spongeBelow.getType() != Material.SPONGE) {
			return;
		}

		SpongeDirection direction = spongesDirections.get(spongeBelow);
		if (direction != null) {
			Vector vector = player.getVelocity();
			boolean cancel = false;
			float v_x = 0.0F;
			float v_y = 0.333F;
			float v_z = 0.0F;
			if (player.getLocation().getBlock().getType() == Material.AIR) {
				cancel = true;
			}
			Block sponge = spongeBelow;
			for (int x = 1; x <= 5; x++) {
				Block b = sponge;
				if (b.getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
					v_y = v_y + 0.33F;
					if (spongesDirections.containsKey(b.getRelative(BlockFace.DOWN))) {
						SpongeDirection Dir = spongesDirections.get(b.getRelative(BlockFace.DOWN));
						if (Dir == SpongeDirection.SKY) {
							v_y = v_y + 0.25F;
						}
					}
				}
				b = b.getRelative(BlockFace.DOWN);
			}

			if (direction == SpongeDirection.SKY) {
				v_y = v_y + 2.3F;
			}

			vector.setX(v_x);
			vector.setY(v_y);
			vector.setZ(v_z);
			player.setVelocity(vector);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getManager().getPlugin(), new Runnable() {
				public void run() {
					player.setVelocity(vector.setX(vector.getX() / 2).setZ(vector.getZ() / 2));
				}
			}, 5L);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getManager().getPlugin(), new Runnable() {
				public void run() {
					player.setVelocity(vector.setX(vector.getX() / 2).setZ(vector.getZ() / 2));
				}
			}, 10L);
			if (cancel && !noFall.contains(player)) {
				noFall.add(player);
			}
		}
	}

	@EventHandler
	public void launcher(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player
				&& noFall.contains(event.getEntity())) {
			if (getManager().getGamerManager().getGamer((Player) event.getEntity()).getKit().getName()
					.equalsIgnoreCase("Stomper")
					|| getManager().getGamerManager().getGamer((Player) event.getEntity()).getKit().getName()
							.equalsIgnoreCase("Tower")) {
				return;
			}
			noFall.remove(event.getEntity());
			event.setCancelled(true);
		}
	}

	public static List<Player> getNoFallList() {
		return noFall;
	}
}
