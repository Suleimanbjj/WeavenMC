package br.com.weavenmc.hg.player.inventories.inventory;

import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.inventories.CustomInventory;
import br.com.weavenmc.hg.player.kit.Kit;
import br.com.weavenmc.hg.utilitaries.Utils;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

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

public class KitSelectorHandler extends CustomInventory {
	public static final HashMap<UUID, Integer> inventoryIndex = new HashMap<>();

	public KitSelectorHandler(Manager manager) {
		super(manager);
	}

	public void generate(Player player, KitType mode) {
		if (mode.equals(KitType.YOUR_KITS)) {
			setTitle("Selecione seu kit");
		} else if (mode.equals(KitType.SECONDARY_KITS)) {
			setTitle("Selecione seu kit 2");
		}
		Inventory inventory = Bukkit.createInventory(player, 54, getTitle());
		int index = inventoryIndex.containsKey(player.getUniqueId())
				? ((Integer) inventoryIndex.get(player.getUniqueId())).intValue()
				: 1;
		update(player, mode, inventory, index);
		player.openInventory(inventory);
	}

	public void update(Player player, KitType mode, Inventory menu, int index) {
		inventoryIndex.put(player.getUniqueId(), Integer.valueOf(index));

		menu.clear();

		ItemBuilder builder = new ItemBuilder().type(Material.INK_SACK).name("§7Página anterior").durability(8);
		menu.setItem(0, builder.build());
		builder = new ItemBuilder().type(Material.WOOL).durability(3).name("§eSeus Kits").lore("Em breve!");
		menu.setItem(2, builder.build());
		builder = new ItemBuilder().type(Material.WOOL).durability(5).name("§aKits Gratuitos do Mes").lore("Em breve!");
		menu.setItem(3, builder.build());
		builder = new ItemBuilder().type(Material.DIAMOND).name("§bLoja de Kits").lore("Em breve!");
		menu.setItem(4, builder.build());
		builder = new ItemBuilder().type(Material.WOOL).durability(14).name("§6Kits Favoritos").lore("Em breve!");
		menu.setItem(5, builder.build());
		builder = new ItemBuilder().type(Material.WOOL).durability(11).name("§cTodos os Kits").lore("Em breve!");
		menu.setItem(6, builder.build());
		builder = new ItemBuilder().type(Material.INK_SACK).durability(8).name("§7Próxima página");
		menu.setItem(8, builder.build());
		for (int i = 9; i < 18; i++) {
			builder = new ItemBuilder().type(Material.STAINED_GLASS_PANE).name("§e§l-").durability(4);
			menu.setItem(i, builder.build());
		}
		int i = 18;
		for (Kit kit : getManager().getKitManager().getKits()) {
			if (i >= 53)
				break;
			if (kit.getName().equals("Nenhum"))
				continue;
			boolean has = mode == KitType.YOUR_KITS ? getManager().getKitManager().hasKit(player, kit)
					: getManager().getKitManager().hasKitSecondary(player, kit);
			List<String> list = new ArrayList<>();
			if (has)
				list.add("§aVocê possui este kit.");
			else
				list.add("§cVocê não possui este kit.");
			list.add("");
			for (String lore : Utils.getFormattedLore(kit.getDescription()))
				list.add("§7" + lore);
			new ItemBuilder().type(kit.getIcon().getType()).name("§e§l" + kit.getName()).lore(list);
			menu.setItem(i, builder.build());
			i++;
		}
		player.playSound(player.getLocation(), Sound.CLICK, 5.0F, 5.0F);
		player.updateInventory();
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getClickedInventory() != null) {
			if (event.getClickedInventory().getTitle().equalsIgnoreCase("Selecione seu kit")) {
				event.setCancelled(true);
				if (event.getCurrentItem() != null) {
					if (!inventoryIndex.containsKey(player.getUniqueId())) {
						inventoryIndex.put(player.getUniqueId(), Integer.valueOf(1));
					}
					Material type = event.getCurrentItem().getType();
					if (type == Material.AIR) {
						return;
					}
					if ((!type.equals(Material.INK_SACK)) && (!type.equals(Material.CHEST))
							&& (!type.equals(Material.DIAMOND)) && (!type.equals(Material.ENDER_CHEST))
							&& (!type.equals(Material.STAINED_GLASS_PANE))) {
						Kit kit = getManager().getKitManager().getKit(event.getCurrentItem().getItemMeta()
								.getDisplayName().replace("§e§l", "").replace("§c", ""));
						player.chat("/kit " + kit.getName());
						player.closeInventory();
					} else if (type.equals(Material.ENDER_CHEST)) {
						player.closeInventory();
						player.sendMessage("§c§lKITS§f A loja de kits esta em §4§lDESENVOLVIMENTO§f!");
					}
				}
			} else if (event.getClickedInventory().getTitle().equalsIgnoreCase("Selecione seu kit 2")) {
				event.setCancelled(true);
				if (event.getCurrentItem() != null) {
					if (!inventoryIndex.containsKey(player.getUniqueId())) {
						inventoryIndex.put(player.getUniqueId(), Integer.valueOf(1));
					}
					Material type = event.getCurrentItem().getType();
					if (type == Material.AIR) {
						return;
					}
					if ((!type.equals(Material.INK_SACK)) && (!type.equals(Material.CHEST))
							&& (!type.equals(Material.DIAMOND)) && (!type.equals(Material.ENDER_CHEST))
							&& (!type.equals(Material.STAINED_GLASS_PANE))) {
						Kit kit = getManager().getKitManager().getKit(event.getCurrentItem().getItemMeta()
								.getDisplayName().replace("§e§l", "").replace("§c", ""));
						player.chat("/kit2 " + kit.getName());
						player.closeInventory();
					} else if (type.equals(Material.ENDER_CHEST)) {
						player.closeInventory();
						player.sendMessage("§c§lKITS§f A loja de kits esta em §4§lDESENVOLVIMENTO§f!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (inventoryIndex.containsKey(event.getPlayer().getUniqueId())) {
			inventoryIndex.remove(event.getPlayer().getUniqueId());
		}
	}

	public static enum KitType {
		YOUR_KITS, SECONDARY_KITS;
	}

	public int[] range(int start, int stop) {
		int[] result = new int[stop - start];
		for (int i = 0; i < stop - start; i++) {
			result[i] = (start + i);
		}
		return result;
	}
}
