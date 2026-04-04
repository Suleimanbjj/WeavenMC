package br.com.weavenmc.commons.bukkit;

import org.bukkit.Server;
import br.com.weavenmc.commons.core.AsynchronousExecutor;
import lombok.AllArgsConstructor;
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

@Getter
@AllArgsConstructor
public class BukkitAsynchronousExecutor implements AsynchronousExecutor {
	
	private Server server;
	private BukkitMain plugin;

	@Override
	public void runAsync(Runnable r) {
		getServer().getScheduler().runTaskAsynchronously(getPlugin(), r);
	}
}
