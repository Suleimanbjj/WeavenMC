package br.com.weavenmc.hg.player.kit.abilities.normal;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.kit.Kit;
import br.com.weavenmc.hg.player.kit.abilities.Nenhum;


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

public class Surprise extends Kit {

	public Surprise(Manager manager) {
		super(manager);
		
		setPrice(49000);
		setCooldownTime(0D);
		setIcon(new ItemStack(Material.CAKE));
		setFree(false);
		setDescription("Deixe o sistema escolher um kit aleatório para você.");
		setRecent(false);
	}

	public Kit getRandomKit() {
		return getManager().getKitManager().getKits().get(getManager().getRandom().nextInt(getManager().getKitManager().getKits().size()));
	}

	public void give(Player p) {
		Kit kit = getRandomKit();

		while ((kit instanceof Nenhum) || (kit instanceof Surprise))
			kit = getRandomKit();

		if (getGamer(p).getKit().getName().equalsIgnoreCase("Surprise")) {
			if (kit.isActive() == false){
				kit = getRandomKit();
				getGamer(p).setKit(kit);
			} else {
				getGamer(p).setKit(kit);
			}
		}

		kit.give(p);
		p.sendMessage("§B§LSURPRISE §fO Surprise selecionou o kit §3§L" + kit.getName());
	}

}
