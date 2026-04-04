package br.com.weavenmc.commons.bukkit.command.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.bukkit.event.account.PlayerChangeGroupEvent;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.permission.GroupMessage;
import br.com.weavenmc.commons.core.profile.Profile;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.commons.util.string.StringUtils;

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

public class GroupCommand implements CommandClass {

	@Command(name = "group", aliases = { "grupo" }, groupToUse = Group.GERENTE, runAsync = true)
	public void group(BukkitCommandSender sender, String label, String[] args) {
		if (args.length <= 1) {
			sender.sendMessage("§6§lGROUPSET§f Utilize: /group <player> <add, remove> <rank, id> <tempo>");
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("add")) {
				sender.sendMessage("§6§lGROUPSET§f Utilize: /group " + args[0] + " add <rank> <tempo>");
			} else if (args[1].equalsIgnoreCase("remove")) {
				sender.sendMessage("§6§lGROUPSET§f Utilize: /group " + args[0] + " remove <id>");
			} else {
				sender.sendMessage("§6§lGROUPSET§f Utilize: /group <player> <add, remove> <rank, id> <tempo>");
			}
		} else {
			Profile profile = WeavenMC.getProfile(args[0]);
			if (profile == null)
				profile = WeavenMC.getProfileCommon().createCracked(args[0]);
			if (profile != null) {
				Player t = Bukkit.getPlayer(args[0]);
				BukkitPlayer player = null;
				if (t != null)
					player = BukkitPlayer.getPlayer(t.getUniqueId());
				else {
					player = new BukkitPlayer(profile.getId(), profile.getName());
					if (!player.load(DataCategory.ACCOUNT)) {
						player = null;
						sender.sendMessage(
								"§6§lSETGROUP§f Erro ao tentar efetuar a operação, tente novamente mais tarde. O comando sera salvo na config.yml");

						if (BukkitMain.getInstance().getInConfig("setgroup.failed") == null) {
							BukkitMain.getInstance().registerInConfig("setgroup.failed", new ArrayList<String>());
							BukkitMain.getInstance().saveConfig();
						}

						List<String> failedCommands = BukkitMain.getInstance().getConfig()
								.getStringList("setgroup.failed");
						failedCommands.add("/group " + StringUtils.createArgs(0, args, "", false) + " ; sender = "
								+ sender.getName());

						BukkitMain.getInstance().registerInConfig("setgroup.failed", failedCommands);
						BukkitMain.getInstance().saveConfig();
					}
				}
				if (player != null) {
					if (args[1].equalsIgnoreCase("add")) {
						Group group = Group.fromString(args[2]);
						if (group != null) {
							if (sender.isPlayer()) {
								Player p = sender.getPlayer();
								BukkitPlayer bp = BukkitPlayer.getPlayer(p.getUniqueId());
								if (bp.getGroup().getId() < group.getId() && !p.isOp()) {
									sender.sendMessage(
											"§6§lSETGROUP§f Você não possui §c§lPERMISSAO§f para manipular o grupo "
													+ group.getTagToUse().getName());
									bp = null;
									group = null;
									p = null;
									player = null;
									t = null;
									return;
								}
								bp = null;
								p = null;
							}
							long groupTime = -1;
							if (args.length >= 4) {
								if (!args[3].equals("-1") && !args[3].startsWith("c")) {
									try {
										groupTime = StringTimeUtils.parseDateDiff(args[3], true);
									} catch (Exception e) {
										sender.sendMessage("§6§lGROUPSET§f O §e§lTEMPO§f está incorreto!");
										group = null;
										player = null;
										t = null;
										return;
									}
								}
								if (args[3].startsWith("c")) {
									try {
										groupTime = Long.valueOf(args[3].replace("c", ""));
									} catch (Exception e) {
										sender.sendMessage("§6§lGROUPSET§f O §e§lTEMPO§f está incorreto!");
										group = null;
										player = null;
										t = null;
										return;
									}
								}
							}
							player.organizeGroups();
							player.addGroup(group, groupTime);
							player.save(DataCategory.ACCOUNT);
							GroupMessage message = new GroupMessage().writeUser(player.getName())
									.writeResponse(sender.getName()).writeGroup(group).writeTime(groupTime)
									.writeAdd(true);
							sender.sendMessage("§6§lGROUPSET§f Você adicionou o grupo " + group.getTagToUse().getName()
									+ "§f na conta do jogador " + player.getName() + "("
									+ player.getUniqueId().toString().replace("-", "") + ") com duração "
									+ (groupTime == -1 ? "§e§lETERNA"
											: "de §e§l" + StringTimeUtils.formatDifference(groupTime)));
							if (t != null) {
								Bukkit.getPluginManager().callEvent(new PlayerChangeGroupEvent(t, group, groupTime));
								message.writeFindPlayer(false);
							} else {
								message.writeFindPlayer(true);
							}
							WeavenMC.getCommonRedis().getJedis().publish(WeavenMC.GROUP_MANAGEMENT_CHANNEL,
									WeavenMC.getGson().toJson(message));
							group = null;
						} else {
							sender.sendMessage("§6§lGROUPSET§f O grupo " + args[2] + " não foi encontrado");
						}
					} else if (args[1].equalsIgnoreCase("remove")) {
						final int id;
						try {
							id = Integer.valueOf(args[2]);
							HashMap<Group, Long> map = player.getGroupById(id);
							if (map != null) {
								Group group = map.keySet().toArray(new Group[] {})[0];
								player.removeGroup(id);
								player.save(DataCategory.ACCOUNT);
								GroupMessage message = new GroupMessage().writeUser(player.getName())
										.writeResponse(sender.getName()).writeGroup(group).writeGroupId(id)
										.writeAdd(false);
								sender.sendMessage("§6§lSETGROUP§f Você removeu o grupo "
										+ group.getTagToUse().getName() + "§f da conta " + player.getName() + "("
										+ player.getUniqueId().toString().replace("-", "") + ")");
								if (t != null) {
									player.updateTags();
									if (BukkitMain.getInstance().getTagManager().currentTag(t, group.getTagToUse())) {
										BukkitMain.getInstance().getTagManager().setTag(t, player.getTags().get(0));
									}
									t.sendMessage("§6§lGRUPO§f O grupo " + group.getTagToUse().getName()
											+ "§f foi §c§lREMOVIDO§f de sua conta!");
									message.writeFindPlayer(false);
								} else {
									message.writeFindPlayer(true);
								}
								WeavenMC.getCommonRedis().getJedis().publish(WeavenMC.GROUP_MANAGEMENT_CHANNEL,
										WeavenMC.getGson().toJson(message));
							} else {
								sender.sendMessage(
										"§6§lGROUPSET§f Não foi encontrado um grupo com este §e§lID§f na conta "
												+ player.getName() + "("
												+ player.getUniqueId().toString().replace("-", "") + ")");
							}
						} catch (NumberFormatException e) {
							sender.sendMessage("§6§lSETGROUP§f O §e§lID§f precisa ser um número!");
						}
					} else {
						sender.sendMessage("§6§lGROUPSET§f Utilize: /group <player> <add, remove> <rank, id> <tempo>");
					}
				}
			} else {
				sender.sendMessage(
						"§6§lSETGROUP§f Não foi possivel criar ou carregar o perfil para este jogador! O comando sera salvo na config.yml.");

				if (BukkitMain.getInstance().getInConfig("setgroup.failed") == null) {
					BukkitMain.getInstance().registerInConfig("setgroup.failed", new ArrayList<String>());
					BukkitMain.getInstance().saveConfig();
				}

				List<String> failedCommands = BukkitMain.getInstance().getConfig().getStringList("setgroup.failed");
				failedCommands.add(
						"/group " + StringUtils.createArgs(0, args, "", false) + " ; sender = " + sender.getName());

				BukkitMain.getInstance().registerInConfig("setgroup.failed", failedCommands);
				BukkitMain.getInstance().saveConfig();
			}
		}
	}
}
