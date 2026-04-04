package br.com.weavenmc.hg.player.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
import br.com.weavenmc.hg.player.item.ItemBuilder;
import br.com.weavenmc.hg.utilitaries.loader.Getter;
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

public class KitManager extends Management {

	private static final HashMap<Integer, List<Integer>> groupKits = new HashMap<>();

	private static final ArrayList<Kit> kits = new ArrayList<>();

	private static final List<String> items = new ArrayList<>();
	
	int i = 1;

	public KitManager(Manager manager) {
		super(manager);
	}

	public boolean initialize() {
		return loadKits();
	}

	public Kit getKit(String string) {
		for (Kit kit : kits)
			if (kit.getName().equalsIgnoreCase(string))
				return kit;
		return null;
	}

	public Kit getKit(int i) {
		for (Kit kit : kits)
			if (kit.getID() == i)
				return kit;
		return null;
	}

	public Kit getKitBySize(int i) {
		return kits.get(i);
	}

	public boolean loadKits() {
		getLogger().log("Starting trying to load all the kits of the plugin.");

		for (Class<?> c : Getter.getClassesForPackage(getManager().getPlugin(),
				"br.com.weavenmc.hg.player.kit.abilities")) {
			if (Kit.class.isAssignableFrom(c) && (c != Kit.class)) {
				try {
					Kit kit = (Kit) c.getConstructor(Manager.class).newInstance(getManager());

					if (registerKitData(kit) == null) {
						throw new Exception("Error to register the kit " + kit.getName());
					}

					if (kit.isActive()) {

						kits.add(kit);

						getLogger().debug("The kit " + kit.getName() + "(" + kit.getID() + "," + kit.toString()
								+ ") was added to list of kits!");
					}

					if (kit.getItems() != null) {
						for (ItemStack item : kit.getItems()) {
							if (item != null) {
								if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
									items.add(item.getItemMeta().getDisplayName());
								}
							}
						}
					}
					getLogger().debug("The kit " + kit.getName() + "(" + kit.getID() + "," + kit.toString()
							+ ") was correctly loaded!");
				} catch (Exception exception) {
					getLogger().error("Error to load the kit " + c.getCanonicalName() + "(" + c.toString()
							+ "), stopping the process!", exception);
					return false;
				}
			}

		}

		return true;
	}

	public Kit registerKitData(Kit kit) {
		kit.setID(i);
		i++;
		return kit;

	}

	public void registerKits() {
		for (Kit kit : kits) {
			Bukkit.getPluginManager().registerEvents(kit, getManager().getPlugin());
		}
	}

	public ArrayList<Kit> getKits() {
		return kits;
	}

	public List<Kit> getPlayerKits(Player player) {
		List<Kit> playerKits = new ArrayList<>();
		for (Kit kit : kits) {
			if (hasKit(player, kit)) {
				playerKits.add(kit);
			}
		}
		return playerKits;
	}

	public List<Kit> getPlayerDontKits(Player player) {
		List<Kit> playerKits = new ArrayList<>();
		for (Kit kit : kits) {
			if (!hasKit(player, kit)) {
				playerKits.add(kit);
			}
		}
		return playerKits;
	}

	public List<Kit> getPlayerSecondaryKits(Player player) {
		List<Kit> playerKits = new ArrayList<>();
		for (Kit kit : kits) {
			if (hasKitSecondary(player, kit)) {
				playerKits.add(kit);
			}
		}
		return playerKits;
	}

	public boolean hasKit(Player player, Kit kit) {
		return true;
	}

	public boolean hasKitSecondary(Player player, Kit kit) {
		return true;
	}

	public boolean hasKit(Player player, String kit) {
		return hasKit(player, getKit(kit));
	}

	public boolean isItemKit(ItemStack item) {
		for (String string : items) {
			if (new ItemBuilder(Material.AIR).checkItem(item, string)) {
				return true;
			}
		}
		return false;
	}

	public HashMap<Integer, List<Integer>> getGroupKits() {
		return groupKits;
	}
}
