package org.inventivetalent.hologram.touch;

import javax.annotation.Nonnull;
import org.bukkit.entity.Player;
import org.inventivetalent.hologram.Hologram;

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

public abstract interface TouchHandler {
	
	public abstract void onTouch(@Nonnull Hologram hologram, @Nonnull Player player, @Nonnull TouchAction touchAction);
}
