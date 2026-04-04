package br.com.weavenmc.hg.player.kit.abilities.normal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

public class Camel extends Kit {

	private ShapelessRecipe shapelessRecipe;

	public Camel(Manager manager) {
		super(manager);

		setPrice(37500);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.SAND));
		setFree(false);
		setDescription("Tenha habilidades no deserto, ganhe velocidade e use cactos para fazer sopa.");
		setRecent(false);

		ItemStack itemStack = new ItemStack(Material.MUSHROOM_SOUP);
		shapelessRecipe = new ShapelessRecipe(itemStack);
		shapelessRecipe.addIngredient(Material.BOWL);
		shapelessRecipe.addIngredient(Material.CACTUS);
		Bukkit.getServer().addRecipe(shapelessRecipe);
	}

	@EventHandler
	public void move(PlayerMoveEvent event) {
		if (event.getTo() == null || event.getFrom() == null) {
			return;
		}

		if (event.getFrom().getBlockX() == event.getTo().getBlockX()
				&& event.getFrom().getBlockY() == event.getTo().getBlockY()
				&& event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
			return;
		}

		if (!hasKit(event.getPlayer())) {
			return;
		}

		if (event.getPlayer().getLocation().getBlock().getBiome() == Biome.DESERT) {
			PotionEffect active = getPotionEffect(event.getPlayer(), PotionEffectType.SPEED);
			if (active == null || active.getDuration() <= 40 || active.getAmplifier() < 0) {
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0), true);
			}
		}
	}

	private PotionEffect getPotionEffect(Player player, PotionEffectType type) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			if (effect.getType().equals(type)) {
				return effect;
			}
		}
		return null;
	}

	@EventHandler
	public void blockHit(BlockDamageEvent event) {
		if (hasKit(event.getPlayer()) && event.getBlock().getType() == Material.CACTUS) {
			event.getBlock().breakNaturally();
		}
	}

}
