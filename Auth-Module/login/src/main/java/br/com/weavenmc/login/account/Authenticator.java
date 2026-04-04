package br.com.weavenmc.login.account;

import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.core.data.player.type.DataType;
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
public class Authenticator {

	private UUID id;
	private String username;
	private String captcha;
	private String password;
	private boolean logged = false;
	private long timeOut;

	public Authenticator(BukkitPlayer bP) {
		id = bP.getUniqueId();
		username = bP.getName();
		captcha = String.valueOf(new Random().nextInt(999));
		password = bP.getData(DataType.PASSWORD).asString();
	}

	public void join() {
		timeOut = (25 * 1000L) + System.currentTimeMillis();
	}

	public boolean hasTimeOut() {
		return timeOut < System.currentTimeMillis();
	}
	
	public boolean hasPassword() {
		return !password.isEmpty() && !password.equals("*");
	}
	
	public void sendAuthencateMessage() {
		Player user = Bukkit.getPlayer(id);
		if (user != null) {
			if (hasPassword()) {
				user.sendMessage("§4§lLOGIN§f Utilize: §c§l/login§f <senha> <" + captcha + ">");
			} else {
				user.sendMessage("§4§lREGISTRO§f Utilize: §c§l/register§f <senha> <confirmar senha> <" + captcha + ">");
			}
		}
	}
	
	public void authenticate() {
		logged = true;
	}

	public void kickTimeOut() {
		Player user = Bukkit.getPlayer(id);
		if (user != null) {
			user.kickPlayer(
					"§4§lAUTHENTICATION\n\n§fVocê §c§lDEMOROU MUITO§f para se autenticar!\n\n§4§lwww.mc-weaven.com.br");
		}
	}
}
