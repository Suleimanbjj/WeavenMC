package br.com.weavenmc.hg.manager.constructor;

import java.util.logging.Level;
import br.com.weavenmc.hg.logger.Logger;
import br.com.weavenmc.hg.manager.Manager;
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

public abstract class Management {

	private final String name;
	private final Logger formattedLogger;
	private final Manager manager;
	private boolean correctlyStart = true;

	public Management(Manager manager) {
		this.name = getClass().getSimpleName().replace("Manager", "");
		this.manager = manager;
		this.formattedLogger = new Logger(manager.getLogger(), name);

		formattedLogger.log("Trying to start the '" + name + "' handler.");

		checkStart(initialize());
	}

	public Management(Manager manager, String name) {
		this.name = name;
		this.manager = manager;
		this.formattedLogger = new Logger(manager.getLogger(), name);

		formattedLogger.log("Trying to start the '" + name + "' manager.");

		checkStart(initialize());
	}

	public Management(Management manager, String name) {
		this.name = name;
		this.manager = manager.getManager();
		this.formattedLogger = new Logger(manager.getLogger(), name);

		formattedLogger.log("Trying to start the '" + name + "' manager.");

		checkStart(initialize());
	}

	public abstract boolean initialize();

	protected boolean checkStart(boolean bool) {
		if (bool) {
			getManager().getLogger().log("The " + name + " manager has been started correctly.");
		} else {
			getManager().getLogger().log(Level.SEVERE, "The '" + name + "' manager has been not started correctly, stopping the server.");
			getManager().getLogger().log(Level.SEVERE, "The server is going to stop because the manager '" + name + "' it was not started.");
			getManager().getPlugin().getServer().shutdown();
			correctlyStart = false;
		}
		return bool;
	}

	public boolean correctlyStart() {
		return correctlyStart;
	}

	public Manager getManager() {
		return manager;
	}

	protected String getName() {
		return name;
	}

	public Logger getLogger() {
		return formattedLogger;
	}

}
