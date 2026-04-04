package br.com.weavenmc.lobby.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.EnumParticle;

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

@AllArgsConstructor
@Getter
public enum Particles {

	HEART("Corações", EnumParticle.HEART, new ItemBuilder().type(Material.APPLE).name("§eCorações").build()),

	FOGUETE("Foguetinhos", EnumParticle.FIREWORKS_SPARK,
			new ItemBuilder().type(Material.FIREWORK).name("§eFoguetinhos").build()),
	FOGO("Fogo", EnumParticle.FLAME,
			new ItemBuilder().type(Material.LAVA_BUCKET).name("§eFogo").build());

	String name;
	EnumParticle particle;
	ItemStack item;

	public static Particles getParticleByName(String nameOfParticle) {
		for (Particles p : Particles.values())
			if (p.getName().equalsIgnoreCase(nameOfParticle))
				return p;
		return null;
	}

}
