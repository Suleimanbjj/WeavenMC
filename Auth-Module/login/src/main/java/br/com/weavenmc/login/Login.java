package br.com.weavenmc.login;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.weavenmc.commons.bukkit.command.BukkitCommandFramework;
import br.com.weavenmc.commons.core.command.CommandLoader;
import br.com.weavenmc.login.listener.AuthenticatorListener;
import lombok.Getter;

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

public class Login extends JavaPlugin {
	@Getter
	private static Login plugin;
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new AuthenticatorListener(), this);
		new CommandLoader(new BukkitCommandFramework(this)).loadCommandsFromPackage("br.com.weavenmc.login.commands");
	}
	
	@Override
	public File getFile() {
		return super.getFile();
	}
}
