package br.com.weavenmc.hg.game.handler.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import br.com.weavenmc.hg.game.custom.HungerListener;
import br.com.weavenmc.hg.player.item.ItemBuilder;
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

public class SoupListener extends HungerListener {

	@EventHandler
	public void onSoup(PlayerInteractEvent event) {
		if (event.hasItem() && event.getItem().getType() == Material.MUSHROOM_SOUP
				&& event.getAction().name().contains("RIGHT")) {
			event.setCancelled(true);

			boolean bool = false;

			if (((Damageable) event.getPlayer()).getHealth() < ((Damageable) event.getPlayer()).getMaxHealth()) {
				double hp = ((Damageable) event.getPlayer()).getHealth() + 7;
				if (hp > ((Damageable) event.getPlayer()).getMaxHealth()) {
					hp = ((Damageable) event.getPlayer()).getMaxHealth();
				}
				event.getPlayer().setHealth(hp);
				bool = true;
			} else if (event.getPlayer().getFoodLevel() < 20) {
				event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 7);
				bool = true;
			}
			if (bool) {
				event.getPlayer().setItemInHand(new ItemStack(Material.BOWL));
				event.getPlayer().updateInventory();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void createSoups() {
		ItemBuilder soup = new ItemBuilder().setMaterial(Material.MUSHROOM_SOUP);
		ShapelessRecipe cocoa = new ShapelessRecipe(soup.getStack());
		ShapelessRecipe cactus = new ShapelessRecipe(soup.getStack());
		ShapelessRecipe pumpkin = new ShapelessRecipe(soup.getStack());
		ShapelessRecipe melon = new ShapelessRecipe(soup.getStack());
		ShapelessRecipe flower = new ShapelessRecipe(soup.getStack());
		ShapelessRecipe nether = new ShapelessRecipe(soup.getStack());

		cocoa.addIngredient(Material.BOWL);
		cocoa.addIngredient(Material.INK_SACK, 3);

		cactus.addIngredient(Material.BOWL);
		cactus.addIngredient(2, Material.CACTUS);

		pumpkin.addIngredient(Material.BOWL);
		pumpkin.addIngredient(2, Material.PUMPKIN_SEEDS);

		melon.addIngredient(Material.BOWL);
		melon.addIngredient(2, Material.MELON_SEEDS);

		nether.addIngredient(Material.BOWL);
		nether.addIngredient(Material.getMaterial(372));
		
		flower.addIngredient(Material.BOWL);
		flower.addIngredient(Material.RED_ROSE);
		flower.addIngredient(Material.YELLOW_FLOWER);

		Bukkit.addRecipe(cocoa);
		Bukkit.addRecipe(cactus);
		Bukkit.addRecipe(pumpkin);
		Bukkit.addRecipe(melon);
		Bukkit.addRecipe(nether);
		Bukkit.addRecipe(flower);
	}

}
