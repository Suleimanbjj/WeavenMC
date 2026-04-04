package br.com.weavenmc.login.commands;

import org.bukkit.entity.Player;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.data.player.type.DataType;
import br.com.weavenmc.login.account.Authenticator;
import br.com.weavenmc.login.account.SimpleManager;

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

public class AuthenticatorCommand implements CommandClass {

	@Command(name = "register", aliases = { "registrar" })
	public void register(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			Player player = sender.getPlayer();
			Authenticator authenticator = SimpleManager.getAuthenticator(player.getUniqueId());
			if (!authenticator.isLogged()) {
				if (!authenticator.hasPassword()) {
					if (args.length <= 2) {
						sender.sendMessage("§4§lREGISTRO§f Utilize: §c§l/register§f <senha> <confirmar senha> <"
								+ authenticator.getCaptcha() + ">");
					} else {
						if (args[0].length() < 8) {
							player.sendMessage("§4§lREGISTRO§f Sua §c§lSENHA§f precisa ter no §c§lMINIMO§f 8 caracteres");
							return;
						}
						if (args[0].length() > 16) {
							player.sendMessage("§4§lREGISTRO§f Sua §c§lSENHA§f precisa ter no §c§lMAXIMO§f 16 caracteres");
							return;
						}
						if (args[0].equals(args[1])) {
							if (args[2].equals(authenticator.getCaptcha())) {
								BukkitPlayer bP = BukkitPlayer.getPlayer(player.getUniqueId());
								bP.getData(DataType.PASSWORD).setValue(args[0]);
								bP.save(DataCategory.ACCOUNT);
								authenticator.authenticate();
								ByteArrayDataOutput out = ByteStreams.newDataOutput();
								out.writeUTF("AuthenticationRequest");
								player.sendPluginMessage(BukkitMain.getInstance(), "Velocity", out.toByteArray());
							} else {
								player.sendMessage("§4§lREGISTRO§f O §c§lVALOR§f do numero digitado está §c§lINCORRETO");
							}
						} else {
							player.sendMessage("§4§lREGISTRO§f As §c§lSENHAS§f não §c§lCOINCIDEM");
						}
					}
				} else {
					sender.sendMessage("§4§lREGISTRO§f Você já possui §c§lREGISTRO§f, utilize §c§l/login");
				}
			} else {
				sender.sendMessage("§4§lREGISTRO§f Você já está §c§lLOGADO!§f Conecte-se ao §c§lLOBBY");
			}
		} else {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "login", aliases = { "logar" })
	public void login(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			Player player = sender.getPlayer();
			Authenticator authenticator = SimpleManager.getAuthenticator(player.getUniqueId());
			if (!authenticator.isLogged()) {
				if (authenticator.hasPassword()) {
					if (args.length <= 1) {
						sender.sendMessage(
								"§4§lLOGIN§f Utilize: §c§l/login§f <senha> <" + authenticator.getCaptcha() + ">");
					} else {
						if (args[0].equals(authenticator.getPassword())) {
							if (args[1].equals(authenticator.getCaptcha())) {
								authenticator.authenticate();
								ByteArrayDataOutput out = ByteStreams.newDataOutput();
								out.writeUTF("AuthenticationRequest");
								player.sendPluginMessage(BukkitMain.getInstance(), "Velocity", out.toByteArray());
							} else {
								player.sendMessage("§4§lLOGIN§f O §c§lVALOR§f do numero digitado está §c§lINCORRETO");
							}
						} else {
							player.sendMessage("§4§lLOGIN§f A §c§lSENHA§f digitada está §c§lINCORRETA");
						}
					}
				} else {
					sender.sendMessage("§4§lLOGIN§f Você não possui §c§lREGISTRO§f, utilize §c§l/register");
				}
			} else {
				sender.sendMessage("§4§lLOGIN§f Você já está §c§lLOGADO!§f Conecte-se ao §c§lLOBBY");
			}
		} else {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}
}
