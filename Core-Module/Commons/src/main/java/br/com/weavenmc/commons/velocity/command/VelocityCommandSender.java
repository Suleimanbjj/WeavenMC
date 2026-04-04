package br.com.weavenmc.commons.velocity.command;

import java.util.UUID;
import br.com.weavenmc.commons.core.command.CommandSender;
import br.com.weavenmc.commons.velocity.VelocityText;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VelocityCommandSender implements CommandSender {

	private final CommandSource source;

	public void sendMessage(String message) {
		source.sendMessage(VelocityText.of(message));
	}

	public String getName() {
		return isPlayer() ? getPlayer().getUsername() : "Console";
	}

	public boolean hasPermission(String permission) {
		return source.hasPermission(permission);
	}

	@Override
	public UUID getUniqueId() {
		return isPlayer() ? getPlayer().getUniqueId() : UUID.randomUUID();
	}

	@Override
	public boolean isPlayer() {
		return source instanceof Player;
	}

	public Player getPlayer() {
		return (Player) source;
	}
}
