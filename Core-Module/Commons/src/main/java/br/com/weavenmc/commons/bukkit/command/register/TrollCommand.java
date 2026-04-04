package br.com.weavenmc.commons.bukkit.command.register;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.permission.Group;

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

public class TrollCommand implements CommandClass {

	@Command(name = "troll", aliases = { "trolar" }, groupToUse = Group.GERENTE)
	public void troll(BukkitCommandSender sender, String label, String[] args) {
		if (sender.isPlayer()) {
			if (args.length <= 1) {
				sender.sendMessage(
						"§c§lTROLL§f  Utilize: §c§l/troll§f <player> <demo, mob, bed, pingsong, rain, demotutojump, credits>");
			} else {
				Player t = Bukkit.getPlayer(args[0]);
				if (t != null) {
					if (args[1].equalsIgnoreCase("mob")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(10));
						packet.getFloat().write(0, Float.valueOf(0));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lMOB APPEARANCE!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else if (args[1].equalsIgnoreCase("bed")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(0));
						packet.getFloat().write(0, Float.valueOf(0));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lBED MISSING!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else if (args[1].equalsIgnoreCase("pingsong")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(6));
						packet.getFloat().write(0, Float.valueOf(0));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lARROW PING SONG!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else if (args[1].equalsIgnoreCase("rain")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(2));
						packet.getFloat().write(0, Float.valueOf(0));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lSHOW RAIN!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else if (args[1].equalsIgnoreCase("demotutojump")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(5));
						packet.getFloat().write(0, Float.valueOf(102));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lHOW TO JUMP DEMO TUTORIAL!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else if (args[1].equalsIgnoreCase("credits")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(4));
						packet.getFloat().write(0, Float.valueOf(0));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lENDING CREDITS!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else if (args[1].equalsIgnoreCase("demo")) {
						PacketContainer packet = new PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE);
						packet.getIntegers().write(0, Integer.valueOf(5));
						packet.getFloat().write(0, Float.valueOf(0));
						try {
							BukkitMain.getInstance().getProcotolManager().sendServerPacket(t, packet);
							sender.sendMessage("§c§lTROLL§f Você §c§lTROLOU§f o jogador §c§l" + t.getName()
									+ "§f com o §4§lDEMO MENU!");
						} catch (Exception e) {
							e.printStackTrace();
							sender.sendMessage(
									"§c§lTROLL§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
						}
					} else {
						sender.sendMessage("§c§lTROLL§f  Utilize: §c§l/troll§f <player> <demo>");
					}
				} else {
					sender.sendMessage("§c§lTROLL§f O jogador §c§l" + args[0] + "§f não foi encontrado!");
				}
			}
		} else {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}
}
