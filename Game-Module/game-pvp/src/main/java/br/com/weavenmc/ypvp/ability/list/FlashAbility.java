package br.com.weavenmc.ypvp.ability.list;

import java.util.HashSet;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.ypvp.ability.Ability;

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

public class FlashAbility extends Ability {

	public FlashAbility() {
		setName("Flash");
		setHasItem(true);
		setGroupToUse(Group.LIGHT);
		setIcon(Material.REDSTONE_TORCH_ON);
		setDescription(new String[] { "§7Mire para um lugar com seu item", "§7e teleporte-se para lá." });
		setPrice(35000);
		setTempPrice(2000);
	}

	@Override
	public void eject(Player p) {

	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (hasKit(p)) {
			if (isItem(p.getItemInHand())) {
				event.setCancelled(true);
				if (!inCooldown(p)) {
					addCooldown(p, 18);
					p.getWorld().strikeLightningEffect(p.getLocation());
					Block target = ((LivingEntity) p).getTargetBlock((HashSet<Byte>) null, 200)
							.getRelative(BlockFace.UP);
					p.teleport(target.getLocation());
					p.sendMessage("§5§lFLASH§f Você §9§lTELEPORTOU§f para o bloco §9§l" + target.getType().name());
					target = null;
				} else {
					sendCooldown(p);
				}
			}
		}
		p = null;
	}
}
