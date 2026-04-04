package br.com.weavenmc.hg.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.weavenmc.hg.HungerGames;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.hg.player.gamer.GamerMode;
import br.com.weavenmc.hg.player.kit.Kit;
import br.com.weavenmc.hg.player.kit.abilities.Nenhum;
import br.com.weavenmc.hg.utilitaries.Utils;
import br.com.weavenmc.commons.bukkit.api.admin.AdminMode;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandSender;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.permission.Group;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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

public class KitCommand implements CommandClass {

	private Set<Combination> combinations;

	public KitCommand() {
		combinations.add(new Combination("Stomper", "Grappler"));
		combinations.add(new Combination("Stomper", "Phantom"));
		combinations.add(new Combination("Stomper", "Launcher"));
		combinations.add(new Combination("Viking", "Boxer"));
		combinations.add(new Combination("Demoman", "Tank"));
	}

	@Command(name = "removekit2", aliases = { "removerkit2" }, groupToUse = Group.YOUTUBERPLUS)
	public void removekit2(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			if (args.length != 1) {
				commandSender.sendMessage("§4§lREMOVE §fUse: /removekit <player>");
				return;
			}

			Player removed = Bukkit.getPlayer(args[0]);
			if (removed == null) {
				commandSender.sendMessage("§4§lREMOVE §fO player está §c§lOFFLINE");
				return;
			}

			commandSender.sendMessage("§4§lREMOVE §fVocê removeu o kit §c§l"
					+ getManager().getGamerManager().getGamer(removed).getKit2().getName() + "§F do player §C§l"
					+ removed.getName());
			getManager().getGamerManager().getGamer(removed).setKit2(getManager().getKitManager().getKit("Nenhum"));
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "removekit", aliases = { "removerkit" }, groupToUse = Group.YOUTUBERPLUS)
	public void removekit(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			if (args.length != 1) {
				commandSender.sendMessage("§4§lREMOVE §fUse: /removekit <player>");
				return;
			}

			Player removed = Bukkit.getPlayer(args[0]);
			if (removed == null) {
				commandSender.sendMessage("§4§lREMOVE §fO player está §c§lOFFLINE");
				return;
			}

			commandSender.sendMessage("§4§lREMOVE §fVocê removeu o kit §c§l"
					+ getManager().getGamerManager().getGamer(removed).getKit().getName() + "§F do player §C§l"
					+ removed.getName());
			getManager().getGamerManager().getGamer(removed).setKit(getManager().getKitManager().getKit("Nenhum"));
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "givekit", aliases = { "darkit" }, groupToUse = Group.YOUTUBERPLUS)
	public void givekit(BukkitCommandSender commandSender, String label, String[] args) {
		if (args.length == 0) {
			sendHelp(commandSender);
		} else if (args.length == 1) {
			Bukkit.getScheduler().runTaskAsynchronously(getManager().getPlugin(),
					new AsyncPermissionSetTask(commandSender, args));
		} else {
			sendHelp(commandSender);
		}
	}

	public boolean validString(String str) {
		return (str.matches("[a-zA-Z0-9_]+")) && str.length() >= 2 && str.length() <= 6;
	}

	private void sendHelp(BukkitCommandSender commandSender) {
		commandSender.sendMessage("§3§lGIVEKIT §fUse: /givekit <nick/all> ");
	}

	private final class AsyncPermissionSetTask implements Runnable {

		private final CommandSender commandSender;
		private final String[] args;

		private AsyncPermissionSetTask(CommandSender sender, String[] args) {
			this.commandSender = sender;
			this.args = args;
		}

		public void run() {

			String name = args[0];

			Player suject = null;

			if (args[0].equalsIgnoreCase("all")) {

				for (Player all : Bukkit.getOnlinePlayers()) {
					Gamer gamer = getManager().getGamerManager().getGamer(all);

					if (gamer.isAlive()) {
						all.getInventory().addItem(gamer.getKit().getItems());
					}
				}

				commandSender.sendMessage("§3§lGIVEKIT §fFoi adicionado ao PLAYERS");

			} else {

				if (Bukkit.getPlayer(name) != null) {
					suject = Bukkit.getPlayer(name);
				}

				Gamer gamer = getManager().getGamerManager().getGamer(suject);
				if (!gamer.isAlive()) {
					commandSender.sendMessage("§3§lGIVEKIT §fO PLAYER nao esta vivo!");
					return;
				}
				suject.getInventory().addItem(gamer.getKit().getItems());

				commandSender.sendMessage("§3§lGIVEKIT §fFoi adicionado ao PLAYER");
			}
		}
	}

	@Command(name = "skit", aliases = { "specialkit" }, groupToUse = Group.YOUTUBERPLUS)
	public void skit(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			Player player = commandSender.getPlayer();
			if (args.length <= 1) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("lista")) {
						player.sendMessage("§3§lSKIT§f Lista de todos os kits criados:");
						for (SpecialKit kit : specialKits) {
							player.sendMessage("§7- §b" + kit.getName());
						}
					} else {
						player.sendMessage("§3§lSKIT§f Utilize: §3§l/skit§f <aplicar|criar> <nome> <raio>");
					}
				} else {
					player.sendMessage("§3§lSKIT§f Utilize: §3§l/skit§f <aplicar|criar> <nome> <raio>");
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("criar")) {
					SpecialKit kit = getKit(args[1]);
					if (kit == null) {
						SpecialKit k = new SpecialKit(args[1], player.getInventory().getContents(),
								player.getInventory().getArmorContents());
						createKit(k);
						player.sendMessage("§3§lSKIT§f Você §3§lCRIOU§f o skit §b§l" + k.getName());
					} else {
						player.sendMessage("§3§lSKIT§f O skit §b§l" + kit.getName() + "§f já existe!");
					}
				} else if (args[0].equalsIgnoreCase("aplicar") || args[0].equalsIgnoreCase("apply")) {
					player.sendMessage(
							"§3§lSKIT§f Sintaxe incompleta: §3§l/skit <" + args[0] + "> <" + args[1] + ">§f <raio>");
				} else {
					player.sendMessage("§3§lSKIT§f Utilize: §3§l/skit§f <aplicar|criar> <nome> <raio>");
				}
			} else {
				if (args[0].equalsIgnoreCase("aplicar") || args[0].equalsIgnoreCase("apply")) {
					SpecialKit k = getKit(args[1]);
					if (k != null) {
						try {
							int radius = Integer.valueOf(args[2]);
							if (radius <= 1000) {
								ArrayList<Player> inRadius = new ArrayList<>();
								Location loc = player.getLocation();
								for (Player o : Bukkit.getOnlinePlayers()) {
									if (o.getLocation().distance(loc) > radius)
										continue;
									if (o.getUniqueId() == player.getUniqueId())
										continue;
									Gamer gamer = getManager().getGamerManager().getGamer(o);
									if (gamer.isSpectating() || AdminMode.getInstance().isAdmin(o))
										continue;
									inRadius.add(o);
								}
								int finded = inRadius.size();
								if (finded > 0) {
									for (Player find : inRadius) {
										find.getInventory().setContents(k.getContents());
										find.getInventory().setArmorContents(k.getArmourContents());
										find.sendMessage("§3§lSKIT§f Você recebeu o kit §b§l" + k.getName());
									}
									player.sendMessage("§3§lSKIT§f Você §3§lAPLICOU§f o kit §b§l" + k.getName()
											+ "§f em um raio de §b§l" + radius + "§f onde §b§l" + finded
											+ "§f jogadores receberam o kit.");
								} else {
									player.sendMessage("§3§lSKIT§f Nenhum jogador foi encontrado neste raio.");
								}
							} else {
								player.sendMessage("§3§lSKIT§f O raio não pode ser maior que 1000.");
							}
						} catch (NumberFormatException ex) {
							player.sendMessage("§3§lSKIT§f O raio precisa ser um valor válido!");
							return;
						}
					} else {
						player.sendMessage("§3§lSKIT§f O kit §b§l" + args[1] + "§f não foi encontrado!");
					}
				} else {
					player.sendMessage("§3§lSKIT§f Utilize: §3§l/skit§f <aplicar|criar> <nome> <raio>");
				}
			}
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "forcekit2", groupToUse = Group.YOUTUBERPLUS)
	public void forcekit2(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			if (args.length != 2) {
				commandSender.sendMessage("§d§lFORCEKIT§f Use: /forcekit2 <player/all> <kit>");
				return;
			}
			boolean all = false;
			Player player = null;
			if (args[0].equalsIgnoreCase("all")) {
				all = true;
			} else {
				player = Bukkit.getPlayer(args[0]);
			}
			if ((player == null) && (!all)) {
				commandSender.sendMessage("§d§lFORCEKIT§f O sujeito está §5§OFFLINE");
				return;
			}
			Kit kit = getManager().getKitManager().getKit(args[1]);
			if (kit == null) {
				commandSender.sendMessage("§d§lFORCEKIT§f O kit §5§l" + args[1] + "§f não existe!");
				return;
			}
			if (all) {
				for (Gamer players : getManager().getGamerManager().getAliveGamers()) {
					players.setKit2(kit);
					players.getKit2().give(players.getPlayer());
				}
			} else {
				getManager().getGamerManager().getGamer(player).setKit2(kit);
				getManager().getGamerManager().getGamer(player).getKit2().give(player);
			}
			if (args[0].equalsIgnoreCase("all")) {
				commandSender
						.sendMessage("§d§lFORCEKIT§f Você setou o kit §5§l" + kit.getName() + "§f para os §5§lPLAYERS");
			} else {
				commandSender.sendMessage("§d§lFORCEKIT§f Voc§ setou o kit §5§l" + kit.getName()
						+ "§f para o player §5§l" + player.getName());
			}
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "forcekit", groupToUse = Group.YOUTUBERPLUS)
	public void forcekit(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			if (args.length != 2) {
				commandSender.sendMessage("§d§lFORCEKIT§f Use: /forcekit <player/all> <kit>");
				return;
			}

			boolean all = false;
			Player player = null;

			if (args[0].equalsIgnoreCase("all"))
				all = true;
			else {
				player = Bukkit.getPlayer(args[0]);
			}

			if (player == null && !all) {
				commandSender.sendMessage("§d§lFORCEKIT§f O sujeito está §5§OFFLINE");
				return;
			}

			Kit kit = getManager().getKitManager().getKit(args[1]);
			if (kit == null) {
				commandSender.sendMessage("§d§lFORCEKIT§f O kit §5§l" + args[1] + "§f não existe!");
				return;
			}

			if (all) {
				for (Gamer players : getManager().getGamerManager().getAliveGamers()) {
					players.setKit(kit);
					players.getKit().give(players.getPlayer());
				}

			} else {
				getManager().getGamerManager().getGamer(player).setKit(kit);
				getManager().getGamerManager().getGamer(player).getKit().give(player);

			}

			if (args[0].equalsIgnoreCase("all"))
				commandSender
						.sendMessage("§d§lFORCEKIT§f Você setou o kit §5§l" + kit.getName() + "§f para os §5§lPLAYERS");
			else {
				commandSender.sendMessage("§d§lFORCEKIT§f Você setou o kit §5§l" + kit.getName()
						+ "§f para o player §5§l" + player.getName());
			}
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "kit")
	public void kit(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			Player player = commandSender.getPlayer();
			List<String> kits = new ArrayList<>();
			for (Kit kit : getManager().getKitManager().getPlayerKits(player)) {
				kits.add(kit.getName());
			}
			if (args.length == 0) {
				TextComponent tagsMessage = new TextComponent(
						"§eVocê tem §9§n" + getManager().getKitManager().getPlayerKits(player).size() + "§e kits: ");
				for (int i = 0; i < kits.size(); i++) {
					String kit = (String) kits.get(i);
					tagsMessage.addExtra(i == 0 ? "" : ", ");
					tagsMessage.addExtra(buildKitComponent(getManager().getKitManager().getKit(kit)));
				}
				player.spigot().sendMessage(tagsMessage);
				player.sendMessage("§eMais kits em: §9loja.mc-weaven.com.br");
				player.sendMessage("§7§nDICA: §7Escolha o kit clicando no chat");

				return;
			}
			String name = args[0];
			Kit kit = isInteger(name) ? getManager().getKitManager().getKit(Integer.valueOf(name).intValue())
					: getManager().getKitManager().getKit(name);
			if (kit == null) {
				commandSender.sendMessage("§b§lKITS §fO kit §3§l" + name + "§f não existe!");
				return;
			}
			String kitname = getManager().getGamerManager().getGamer(player).getKit2().getName();
			for (Combination combination : combinations) {
				if (!combination.isCombination(kit.getName(), kitname))
					continue;
				commandSender.sendMessage("§b§lKITS§f Você não pode usar esta combinação!");
				return;
			}
			if (!kit.isActive()) {
				commandSender.sendMessage("§b§lKITS §fO kit §3§l" + kit.getName() + "§f n§o está ativado!");
				return;
			}
			if ((!getManager().isDoubleKit()) && (!getManager().getKitManager().hasKit(player, kit))) {
				commandSender.sendMessage("§b§lKITS §fVoce não possui o kit §3§l" + kit.getName()
						+ "§f, compre-o em nossa loja §fwww.mc-weaven.com.br");
				return;
			}
			Gamer gamer = getManager().getGamerManager().getGamer(player);
			if (gamer.getKit().getName().equalsIgnoreCase(kit.getName())) {
				commandSender.sendMessage("§b§lKITS §fVocê já está utilizando o kit " + gamer.getKit().getName() + "!");
				return;
			}
			if (gamer.getKit2().getName().equalsIgnoreCase(kit.getName())) {
				commandSender
						.sendMessage("§b§lKITS §fVocê já está utilizando o kit " + gamer.getKit2().getName() + "!");
				return;
			}
			if ((gamer.getKit().getName().equalsIgnoreCase(gamer.getKit2().getName()))
					&& (!(gamer.getKit() instanceof Nenhum)) && (!(gamer.getKit2() instanceof Nenhum))) {
				commandSender
						.sendMessage("§b§lKITS §fVocê já está utilizando o kit " + gamer.getKit2().getName() + "!");
				return;
			}
			if (!getManager().getGameManager().isPreGame()) {
				if (!gamer.getMode().equals(GamerMode.ALIVE)) {
					commandSender.sendMessage(
							"§b§lKITS §fVocê já está mais participando da partida, portanto Você nao pode pegar kits!");
					return;
				}
				if (!gamer.getKit().getName().equals("Nenhum")) {
					commandSender.sendMessage("§b§lKITS §fVocê já está com um kit!");
					return;
				}
				if ((getManager().getGameManager().getGameTime().intValue() > 300)
						&& (!player.hasPermission("hunger.cmd.kit"))) {
					commandSender.sendMessage("§b§lKITS §fVoce nao pode pegar kit agora!");
					return;
				}
				gamer.setKit(kit);
				gamer.getKit().give(player);
				player.sendMessage("§b§lKITS §fVoce selecionou o kit §3§l" + kit.getName());
			} else {
				player.sendMessage("§b§lKITS §fVoce selecionou o kit §3§l" + kit.getName());
				gamer.setKit(kit);
			}
		}
	}

	@Command(name = "kit2")
	public void kit2(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			Player player = commandSender.getPlayer();
			List<String> kits = new ArrayList<>();

			for (Kit kit : getManager().getKitManager().getPlayerKits(player)) {
				kits.add(kit.getName());
			}

			if (!getManager().isDoubleKit()) {
				return;
			}

			if (args.length == 0) {
				TextComponent tagsMessage = new TextComponent(
						"§eVocê tem §9§n" + getManager().getKitManager().getPlayerKits(player).size() + "§e kits: ");
				for (int i = 0; i < kits.size(); i++) {
					String kit = kits.get(i);
					tagsMessage.addExtra(i == 0 ? "" : ", ");
					tagsMessage.addExtra(buildKit2Component(getManager().getKitManager().getKit(kit)));
				}

				player.spigot().sendMessage(tagsMessage);
				player.sendMessage("§eMais kits em: §9loja.mc-weaven.com.br");
				player.sendMessage("§7§nDICA: §7Escolha o kit clicando no chat");

				return;
			}
			String name = args[0];
			Kit kit = isInteger(name) ? getManager().getKitManager().getKit(Integer.valueOf(name))
					: getManager().getKitManager().getKit(name);

			if (kit == null) {
				commandSender.sendMessage("§b§lKITS §fO kit §3§l" + name + "§f não existe!");
				return;
			}

			String kitname = getManager().getGamerManager().getGamer(player).getKit().getName();
			for (Combination combination : combinations) {
				if (!combination.isCombination(kit.getName(), kitname))
					continue;
				commandSender.sendMessage("§b§lKITS§f Você não pode usar esta combinação!");
				return;
			}

			if (!kit.isActive()) {
				commandSender.sendMessage("§b§lKITS §fO kit §3§l" + kit.getName() + "§f não está ativado!");
				return;
			}

			if (!getManager().getKitManager().hasKitSecondary(player, kit)) {
				commandSender.sendMessage("§b§lKITS §fVocê não possui o kit §3§l" + kit.getName()
						+ "§f, compre-o em nossa loja §fwww.mc-weaven.com.br");
				return;
			}

			Gamer gamer = getManager().getGamerManager().getGamer(player);

			if (gamer.getKit().getName().equalsIgnoreCase(kit.getName())) {
				commandSender.sendMessage("§b§lKITS §fVocê já está utilizando o kit " + gamer.getKit().getName() + "!");
				return;
			}

			if (gamer.getKit2().getName().equalsIgnoreCase(kit.getName())) {
				commandSender
						.sendMessage("§b§lKITS §fVocê já está utilizando o kit " + gamer.getKit2().getName() + "!");
				return;
			}

			if (gamer.getKit().getName().equalsIgnoreCase(gamer.getKit2().getName())
					&& (!(gamer.getKit() instanceof Nenhum)) && (!(gamer.getKit2() instanceof Nenhum))) {
				commandSender
						.sendMessage("§b§lKITS §fVocê já está utilizando o kit " + gamer.getKit2().getName() + "!");
				return;
			}

			if (!getManager().getGameManager().isPreGame()) {

				if (!gamer.getMode().equals(GamerMode.ALIVE)) {
					commandSender.sendMessage(
							"§b§lKITS §fVocê não está mais participando da partida, portanto Você não pode pegar kits!");
					return;
				}

				if (!gamer.getKit2().getName().equals("Nenhum")) {
					commandSender.sendMessage("§b§lKITS §fVocê já está com um kit!");
					return;
				}

				if (getManager().getGameManager().getGameTime() > 300 && !player.hasPermission("hunger.cmd.kit5m")) {
					commandSender.sendMessage("§b§lKITS §fVocê não pode pegar kit agora!");
					return;
				}

				gamer.setKit2(kit);
				gamer.getKit2().give(player);
				player.sendMessage("§b§lKITS §fVocê selecionou o kit §3§l" + kit.getName());

			} else {
				player.sendMessage("§b§lKITS §fVocê selecionou o kit §3§l" + kit.getName());
				gamer.setKit2(kit);
			}

		}
	}

	@Command(name = "togglekit2", groupToUse = Group.YOUTUBERPLUS)
	public void togglekit2(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			if (args.length != 2) {
				commandSender.sendMessage("§a§lTOGGLEKIT §fUse: /togglekit2 <kit/all> <on/off>");
				return;
			}
			Kit kit = getManager().getKitManager().getKit(args[0]);
			if ((!args[0].equalsIgnoreCase("all")) && (kit == null)) {
				commandSender.sendMessage("§a§lTOGGLEKIT §fO kit §a" + args[0] + "§f nao existe!");
				return;
			}
			boolean active = args[1].equalsIgnoreCase("on");
			if (args[0].equalsIgnoreCase("all")) {
				for (Kit kits : getManager().getKitManager().getKits()) {
					getManager().getKitManager().getKit(kits.getName()).setActive(Boolean.valueOf(active));
				}
				if (!active) {
					for (Gamer gamers : getManager().getGamerManager().getGamers().values()) {
						gamers.setKit2(getManager().getKitManager().getKit("Nenhum"));
					}
				}
				Bukkit.broadcastMessage("§7Todos os kits (2): "
						+ (active ? "§aativados".toLowerCase() : "§cdesativados".toLowerCase()));
			} else {
				if (!active) {
					for (Gamer gamers : getManager().getGamerManager().getGamers().values()) {
						if (gamers.getKit2().getName().equalsIgnoreCase(kit.getName())) {
							gamers.setKit2(getManager().getKitManager().getKit("Nenhum"));
						}
					}
				}
				getManager().getKitManager().getKit(kit.getName()).setActive(Boolean.valueOf(active));
				Bukkit.broadcastMessage("§7Kit (2) " + kit.getName() + ": "
						+ (active ? "§aativado".toUpperCase() : "§cdesativado".toUpperCase()));
			}
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	@Command(name = "togglekit", groupToUse = Group.YOUTUBERPLUS)
	public void togglekit(BukkitCommandSender commandSender, String label, String[] args) {
		if (commandSender.isPlayer()) {
			if (args.length != 2) {
				commandSender.sendMessage("§a§lTOGGLEKIT §fUse: /togglekit <kit/all> <on/off>");
				return;
			}

			Kit kit = getManager().getKitManager().getKit(args[0]);
			if (!args[0].equalsIgnoreCase("all") && kit == null) {
				commandSender.sendMessage("§a§lTOGGLEKIT §fO kit §a" + args[0] + "§f não existe!");
				return;
			}

			boolean active = args[1].equalsIgnoreCase("on") ? true : false;

			if (args[0].equalsIgnoreCase("all")) {
				for (Kit kits : getManager().getKitManager().getKits()) {
					getManager().getKitManager().getKit(kits.getName()).setActive(active);
				}
				if (!active) {
					for (Gamer gamers : getManager().getGamerManager().getGamers().values()) {
						gamers.setKit(getManager().getKitManager().getKit("Nenhum"));
					}
				}

				Bukkit.broadcastMessage(
						"§7Todos os kits: " + (active ? "§aativados".toLowerCase() : "§cdesativados".toLowerCase()));
			} else {
				if (!active) {
					for (Gamer gamers : getManager().getGamerManager().getGamers().values()) {
						if (gamers.getKit().getName().equalsIgnoreCase(kit.getName()))
							gamers.setKit(getManager().getKitManager().getKit("Nenhum"));
					}
				}
				getManager().getKitManager().getKit(kit.getName()).setActive(active);
				Bukkit.broadcastMessage("§7Kit " + kit.getName() + ": "
						+ (active ? "§aativado".toUpperCase() : "§cdesativado".toUpperCase()));
			}
		} else {
			commandSender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game!");
		}
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public Manager getManager() {
		return HungerGames.getManager();
	}

	private BaseComponent buildKit2Component(Kit kit) {
		BaseComponent baseComponent = new TextComponent((kit.isActive() ? "§e" : "§c") + kit.getName());
		BaseComponent descComponent = new TextComponent("§9Informações:");
		descComponent.addExtra("\n");
		for (String lore : Utils.getFormattedLore(kit.getDescription())) {
			descComponent.addExtra(lore + "\n");
		}
		baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { descComponent,
				new TextComponent("\n"), new TextComponent("§aClique para selecionar!") }));
		baseComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit2 " + kit.getName()));
		return baseComponent;
	}

	private BaseComponent buildKitComponent(Kit kit) {
		BaseComponent baseComponent = new TextComponent((kit.isActive() ? "§e" : "§c") + kit.getName());
		BaseComponent descComponent = new TextComponent("§9Informações:");
		descComponent.addExtra("\n");
		for (String lore : Utils.getFormattedLore(kit.getDescription())) {
			descComponent.addExtra(lore + "\n");
		}
		baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { descComponent,
				new TextComponent("\n"), new TextComponent("§aClique para selecionar!") }));
		baseComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit " + kit.getName()));
		return baseComponent;
	}

	private Set<SpecialKit> specialKits = new HashSet<>();

	private void createKit(SpecialKit kit) {
		specialKits.add(kit);
	}

	private SpecialKit getKit(String name) {
		for (SpecialKit kit : specialKits) {
			if (!kit.getName().equals(name))
				continue;
			return kit;
		}
		return null;
	}

	private class SpecialKit {

		private String name;
		private ItemStack[] contents, armourContents;

		public SpecialKit(String name, ItemStack[] contents, ItemStack[] armourContents) {
			this.name = name;
			this.contents = contents;
			this.armourContents = armourContents;
		}

		public String getName() {
			return name;
		}

		public ItemStack[] getContents() {
			return contents;
		}

		public ItemStack[] getArmourContents() {
			return armourContents;
		}
	}

	private class Combination {

		private String name1, name2;

		public Combination(String name1, String name2) {
			this.name1 = name1;
			this.name2 = name2;
		}

		public boolean isCombination(String a, String b) {
			if (this.name1.equalsIgnoreCase(a) && this.name2.equalsIgnoreCase(b))
				return true;
			if (this.name2.equalsIgnoreCase(a) && this.name1.equalsIgnoreCase(b))
				return true;
			return false;
		}
	}
}
