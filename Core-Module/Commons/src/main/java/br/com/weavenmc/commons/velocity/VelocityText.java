package br.com.weavenmc.commons.velocity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class VelocityText {

	private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacySection();

	private VelocityText() {
	}

	public static Component of(String message) {
		return LEGACY.deserialize(message.replace("&", "§"));
	}
}
