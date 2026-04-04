package br.com.weavenmc.hg.manager.managers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import br.com.weavenmc.hg.game.custom.HungerCommand;
import br.com.weavenmc.hg.game.custom.HungerListener;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.manager.constructor.Management;
import br.com.weavenmc.hg.player.inventories.CustomInventory;
import br.com.weavenmc.hg.player.kit.Kit;
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

public class ClassManager extends Management {

	public ClassManager(Manager manager) {
		super(manager, "ClassManager");
	}

	public boolean initialize() {
		return load();
	}

	public boolean load() {
		getLogger().log("Starting trying to load all the classes of commands and listeners of the plugin.");

		for (Class<?> classes : Getter.getClassesForPackage(getManager().getPlugin(), "br.com.weavenmc.hg")) {
			try {
				Listener listener = null;
				if (!Listener.class.isAssignableFrom(classes)) {
					continue;
				} else if (Kit.class.isAssignableFrom(classes)) {
					continue;
				} else if (classes.getSimpleName().equals("Timer")) {
					continue;
				} else if (classes.getSimpleName().equals("GladiatorFight")) {
					continue;
				} else if (classes.getSimpleName().startsWith("GladiatorFight")) {
					continue;
				} else if (classes.getSimpleName().equals("")) {
					continue;
				} else if (classes.getSimpleName().equals("CustomInventory")) {
					continue;
				} else if (HungerCommand.class.isAssignableFrom(classes)) {
					listener = (Listener) classes.getConstructor().newInstance();
				} else if (HungerListener.class.isAssignableFrom(classes)) {
					listener = (Listener) classes.getConstructor().newInstance();
				} else if (CustomInventory.class.isAssignableFrom(classes)) {
					listener = (Listener) classes.getConstructor(Manager.class).newInstance(getManager());
				} else {
					listener = (Listener) classes.getConstructor(Manager.class).newInstance(getManager());
				}

				Bukkit.getPluginManager().registerEvents(listener, getManager().getPlugin());
				getLogger().debug("The listener " + listener.getClass().getSimpleName() + " was loaded correcly!");

			} catch (Exception exception) {
				getLogger().error("Error to load the listener " + classes.getSimpleName() + ", stopping the process!", exception);
				return false;
			}
		}
		return true;
	}
}
