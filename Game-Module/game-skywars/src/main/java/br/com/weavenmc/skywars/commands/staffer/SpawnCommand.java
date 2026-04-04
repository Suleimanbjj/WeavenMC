package br.com.weavenmc.skywars.commands.staffer;

import java.util.Arrays;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.skywars.WeavenSkywars;
import br.com.weavenmc.skywars.commands.BaseCommand;

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

public class SpawnCommand extends BaseCommand {

	public SpawnCommand() {
		super("setspawn", "Set spawns of map", Arrays.asList("spawnset"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String label, String[] args) {
		if (isPlayer(commandSender)) {
			Player player = getPlayer(commandSender);
			if (!isPermission(player, Group.DIRETOR)) {
				sendError(player, "§fVocê não tem permissão para usar esse comando.");
				return true;
			}
			if (args.length == 0) {
				player.sendMessage("§3§lSPAWN §fUtilize o comando: /setspawn (1/12)");
				return true;
			}
			if (args.length == 1) {
				if (!isInteger(args[0])) {
					player.sendMessage("§c§lSPAWN §fUtilize somente números para setar!");
					return true;
				}
				int i = Integer.valueOf(args[0]);
				if (i <= 0) {
					player.sendMessage("§c§lSPAWN §fNão pode ser menor ou igual à zero.");
					return true;
				}
				if (i > 12) {
					player.sendMessage("§c§lSPAWN §fNão há possibilidade de ser maior que 12 spawns.");
					return true;
				}
				WeavenSkywars.getSpawnsManager().addSpawn(player, i);
				player.sendMessage("§a§lSPAWN §f");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

}
