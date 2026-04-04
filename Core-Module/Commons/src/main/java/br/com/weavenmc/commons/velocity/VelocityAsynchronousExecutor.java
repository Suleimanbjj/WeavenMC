package br.com.weavenmc.commons.velocity;

import br.com.weavenmc.commons.core.AsynchronousExecutor;

public class VelocityAsynchronousExecutor implements AsynchronousExecutor {

	private final VelocityMain plugin;

	public VelocityAsynchronousExecutor(VelocityMain plugin) {
		this.plugin = plugin;
	}

	@Override
	public void runAsync(Runnable r) {
		plugin.getProxy().getScheduler().buildTask(plugin, r).schedule();
	}
}
