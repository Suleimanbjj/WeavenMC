package br.com.weavenmc.commons.core.command;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.util.ClassGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class CommandLoader {

	@NonNull
	private CommandFramework framework;
	
	public int loadCommandsFromPackage(String pkgname) {
		int i = 0;
		for (Class<?> clazz : ClassGetter.getClassesForPackageByPlugin(framework.getPlugin(), pkgname)) {
			if (CommandClass.class.isAssignableFrom(clazz)) {
				try {
					CommandClass command = (CommandClass) clazz.newInstance();
					framework.registerCommands(command);
					command = null;
				} catch (Exception e) {
					WeavenMC.getLogger().warning("Erro ao carregar comando da classe " + clazz.getSimpleName() + "!");
				}
				++i;
			}
		}
		return i;
	}
}
