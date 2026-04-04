package br.com.weavenmc.ypvp.ability;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.ypvp.yPvP;
import br.com.weavenmc.ypvp.gamer.Gamer;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
public abstract class Ability implements Listener {

	private String name;
	private int price;
	private int tempPrice;
	private Group groupToUse;
	private double cooldownTime;
	private Material icon;
	private String[] description;
	private boolean hasItem;
	
	public abstract void eject(Player p);
	
	public boolean inCooldown(Player p) {
		return yPvP.getPlugin().getCooldownManager().hasCooldown(p);
	}
	
	public void sendCooldown(Player p) {
		String millis = StringTimeUtils.toMillis(yPvP.getPlugin().getCooldownManager().getCooldown(p));
		p.sendMessage("§6§lCOOLDOWN§f Aguarde mais §e§l" + millis + "§f para usar novamente!");
	}
	
	public void addCooldown(Player p, int cooldown) {
		yPvP.getPlugin().getCooldownManager().addCooldown(p, cooldown);
	}
	
	public Gamer gamer(Player p) {
		return yPvP.getPlugin().getGamerManager().getGamer(p.getUniqueId());
	}
	
	public boolean hasKit(Player p) {
		Gamer gamer = yPvP.getPlugin().getGamerManager().getGamer(p.getUniqueId());
		return gamer.getAbility() == this;
	}
	
	public List<Location> circle(Location loc, int radius, int height, boolean hollow, boolean sphere, int plusY) {
		List<Location> circleblocks = new ArrayList<Location>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		for (int x = cx - radius; x <= cx + radius; x++) {
			for (int z = cz - radius; z <= cz + radius; z++) {
				for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
						Location l = new Location(loc.getWorld(), x, y + plusY, z);
						circleblocks.add(l);
					}
				}
			}
		}

		return circleblocks;
	}
	
	public boolean isItem(ItemStack stack) {
		if (stack == null)
			return false;
		if (icon == null)
			return false;
		return stack.getType() == icon;
	}
}
