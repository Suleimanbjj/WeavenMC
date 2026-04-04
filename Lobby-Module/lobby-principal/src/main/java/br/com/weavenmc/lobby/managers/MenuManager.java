package br.com.weavenmc.lobby.managers;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import br.com.weavenmc.commons.bukkit.event.update.UpdateEvent;
import br.com.weavenmc.commons.bukkit.event.update.UpdateEvent.UpdateType;
import br.com.weavenmc.commons.bukkit.messenger.NetworkManager;
import br.com.weavenmc.commons.core.server.HardcoreGames;
import br.com.weavenmc.commons.core.server.HardcoreGames.GameState;
import br.com.weavenmc.commons.core.server.NetworkServer;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.lobby.Lobby;
import br.com.weavenmc.lobby.Management;

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

public class MenuManager extends Management implements Listener {

	private HashMap<MenuType, Inventory> MENUS = new HashMap<>();

	public MenuManager(Lobby plugin) {
		super(plugin);
	}

	public Inventory getMenu(MenuType menuType) {
		return MENUS.get(menuType);
	}

	@Override
	public void enable() {
		int onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.PVP_FULLIRON);
		Inventory menu = Bukkit.createInventory(null, 27, "§7§nEscolha um Modo de Jogo");
		ItemBuilder builder = new ItemBuilder().name("§9§lPvP FullIron").amount(onlineCount)
				.type(Material.DIAMOND_SWORD).lore("§7Novo servidor de KitPvP com", "§7sopa feito para todos lutarem",
						"§7com armaduras de ferro, batalhando", "§7com espadas com mais dano", "",
						"§5§lAgora com Void Challenge", "", "§3§l" + onlineCount + "§7 jogadores online.", "",
						"§b§lClique para conectar.");
		menu.setItem(10, builder.build());
		//
		onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.PVP_SIMULATOR);
		builder = new ItemBuilder().name("§9§lPvP Simulator").amount(onlineCount).type(Material.WOOD_SWORD).lore(
				"§7Novo servidor de KitPvP com", "§7sopa feito para todos usarem",
				"§7estratégias e lutarem sem armadura", "§7em um estilo mais Hardcore", "§7simulando o estilo do HG",
				"", "§5§lAgora com Void Challenge", "", "§3§l" + onlineCount + "§7 jogadores online.", "",
				"§b§lClique para conectar.");
		menu.setItem(11, builder.build());
		//
		onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.DOUBLEKITHG);
		builder = new ItemBuilder().name("§9§lHungerGames").amount(onlineCount).type(Material.MUSHROOM_SOUP).lore(
				"§7Servidores de HardcoreGames.", "§7Seja o ultimo sobrevivente", "§7em uma batalha brutal com kits",
				"§7onde apenas um sera o campeao", "", "§3§l" + onlineCount + "§7 jogadores online.", "",
				"§b§lClique esquerdo §bpara", "§b§lconectar §ba um §b§lservidor.", "", "§e§lClique direito §epara ver",
				"§e§ltodos §eos §e§lservidores.");
		menu.setItem(12, builder.build());
		//
		onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.SKYWARS);
		builder = new ItemBuilder().name("§9§lSkywars").amount(onlineCount).type(Material.ARROW).lore(
				"§7Servidor de Skywars onde só 1", "§7guerreiro tem lugar no céu!", "§7Batalhe também na versão 1.7!",
				"", "§3§l" + onlineCount + "§7 jogadores online.", "", "§b§lClique para conectar.");
		menu.setItem(13, builder.build());
		//
		onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.GLADIATOR);
		builder = new ItemBuilder().name("§9§lGladiator").amount(onlineCount).type(Material.IRON_FENCE).lore(
				"§7Jogue neste modo Gladiator", "§7para alem de aprimorar o seu controle",
				"§7de inventário simulando um evento", "§7gladiator, torne-se o melhor Gladiador", "§7do servidor", "",
				"§3§l" + onlineCount + "§7 jogadores online.", "", "§b§lClique para conectar.");
		menu.setItem(14, builder.build());
		//
		onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.TOURNAMENT);
		builder = new ItemBuilder().name("§9§lTorneio").amount(onlineCount).type(Material.MAGMA_CREAM).lore(
				"§7Servidor reservado para eventos.", "", "§3§l" + onlineCount + "§7 jogadores conectados.", "",
				"§b§lClique para conectar.");
		menu.setItem(15, builder.build());
		MENUS.put(MenuType.GAMES_MODE, menu);// games mode end

		menu = Bukkit.createInventory(null, 36, "§7§nServidores do HG");// hunger games start
		NetworkManager networkManager = BukkitMain.getInstance().getNetworkManager();
		for (int i = 1; i < 11; i++) {
			NetworkServer server = networkManager.getServer("hg-a" + i);
			if (server != null) {
				if (server instanceof HardcoreGames) {
					HardcoreGames hg = (HardcoreGames) server;
					if (hg.getLastData() >= System.currentTimeMillis()) {
						if (hg.getState() == GameState.WAITING) {
							builder = new ItemBuilder().name("§9§l> §a§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7Aguardando §a§ljogadores§7 para iniciar!",
											"§7Precisa-se de mais §a§l" + hg.getPlayersLeft() + "§7 jogadores!", "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(10);
						} else if (hg.getState() == GameState.PREGAME) {
							builder = new ItemBuilder().name("§9§l> §a§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida §a§linicia§7 em §a§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(10);
						} else if (hg.getState() == GameState.INVINCIBILITY) {
							builder = new ItemBuilder().name("§9§l> §e§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A §e§linvencibilidade§7 acaba em §e§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(11);
						} else if (hg.getState() == GameState.GAMETIME) {
							builder = new ItemBuilder().name("§9§l> §6§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida está em §6§lprogresso§7!",
											"§7A partida está em §6§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(14);
						} else if (hg.getState() == GameState.ENDING) {
							builder = new ItemBuilder().name("§9§l> §2§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida está §2§lfinalizando§7!",
											"§7O vencedor foi §2§l" + hg.getWinner(), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(2);
						} else {
							builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
									.durability(1);
						}
					} else {
						builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<")
								.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
								.durability(1);
					}
				} else {
					builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<").type(Material.INK_SACK)
							.lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
				}
			} else {
				builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<").type(Material.INK_SACK)
						.lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
			}
			menu.setItem(slot(i), builder.build());
		}
		MENUS.put(MenuType.DOUBLEKITHG, menu);

		menu = Bukkit.createInventory(null, 36, "§7§nServidores de DoubleKit");
		for (int i = 1; i < 11; i++) {
			NetworkServer server = networkManager.getServer("ta" + i);
			if (server != null) {
				if (server instanceof HardcoreGames) {
					HardcoreGames hg = (HardcoreGames) server;
					if (hg.getLastData() >= System.currentTimeMillis()) {
						if (hg.getState() == GameState.WAITING) {
							builder = new ItemBuilder().name("§9§l> §a§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7Aguardando §a§ljogadores§7 para iniciar!",
											"§7Precisa-se de mais §a§l" + hg.getPlayersLeft() + "§7 jogadores!", "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(10);
						} else if (hg.getState() == GameState.PREGAME) {
							builder = new ItemBuilder().name("§9§l> §a§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida §a§linicia§7 em §a§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(10);
						} else if (hg.getState() == GameState.INVINCIBILITY) {
							builder = new ItemBuilder().name("§9§l> §e§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A §e§linvencibilidade§7 acaba em §e§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(11);
						} else if (hg.getState() == GameState.GAMETIME) {
							builder = new ItemBuilder().name("§9§l> §6§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida está em §6§lprogresso§7!",
											"§7A partida está em §6§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(14);
						} else if (hg.getState() == GameState.ENDING) {
							builder = new ItemBuilder().name("§9§l> §2§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida está §2§lfinalizando§7!",
											"§7O time vencedor foi §2§l" + hg.getWinner(), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(2);
						} else {
							builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
									.durability(1);
						}
					} else {
						builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
								.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
								.durability(1);
					}
				} else {
					builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
							.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
				}
			} else {
				builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<").type(Material.INK_SACK)
						.lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
			}
			menu.setItem(slot(i), builder.build());
		}
		MENUS.put(MenuType.DOUBLEKITHG, menu);

		menu = Bukkit.createInventory(null, 36, "§7§nServidores de DoubleKit");
		for (int i = 1; i < 11; i++) {
			NetworkServer server = networkManager.getServer("ta" + i);
			if (server != null) {
				if (server instanceof HardcoreGames) {
					HardcoreGames hg = (HardcoreGames) server;
					if (hg.getLastData() >= System.currentTimeMillis()) {
						if (hg.getState() == GameState.WAITING) {
							builder = new ItemBuilder().name("§9§l> §a§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7Aguardando §a§ljogadores§7 para iniciar!",
											"§7Precisa-se de mais §a§l" + hg.getPlayersLeft() + "§7 jogadores!", "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(10);
						} else if (hg.getState() == GameState.PREGAME) {
							builder = new ItemBuilder().name("§9§l> §a§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida §a§linicia§7 em §a§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(10);
						} else if (hg.getState() == GameState.INVINCIBILITY) {
							builder = new ItemBuilder().name("§9§l> §e§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A §e§linvencibilidade§7 acaba em §e§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(11);
						} else if (hg.getState() == GameState.GAMETIME) {
							builder = new ItemBuilder().name("§9§l> §6§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida está em §6§lprogresso§7!",
											"§7A partida está em §6§l" + getTimeFormat(hg.getTime()), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(14);
						} else if (hg.getState() == GameState.ENDING) {
							builder = new ItemBuilder().name("§9§l> §2§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).amount(hg.getPlayers())
									.lore("§7A partida está §2§lfinalizando§7!",
											"§7O time vencedor foi §2§l" + hg.getWinner(), "",
											"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
											"§b§lClique para conectar.")
									.durability(2);
						} else {
							builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
									.durability(1);
						}
					} else {
						builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
								.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
								.durability(1);
					}
				} else {
					builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
							.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
				}
			} else {
				builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<").type(Material.INK_SACK)
						.lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
			}
			menu.setItem(slot(i), builder.build());
		}
		
		
		
		
		MENUS.put(MenuType.TEAMHG, menu);

		menu = Bukkit.createInventory(null, 9, "§7Lobbys");
		int index = 0;
		for (NetworkServer lobby : BukkitMain.getInstance().getNetworkManager().getServers()) {
			if (index > 8)
				break;
			if (lobby.getServerType() != ServerType.LOBBY)
				continue;
			if (lobby.getLastData() < System.currentTimeMillis())
				continue;
			builder = new ItemBuilder().name("§a§l" + lobby.getServerId().toUpperCase())
					.lore("", "§3§l" + lobby.getPlayers() + "§7 jogadores conectados", "", "§b§lClique para conectar")
					.type(Material.INK_SACK);
			menu.setItem(index, builder.build());
			index++;
		}
		MENUS.put(MenuType.LOBBYS, menu);

		registerListener(this);
	}

	public String getTimeFormat(int time) {
		if (time >= 3600) {
			int hours = (time / 3600), minutes = (time % 3600) / 60, seconds = (time % 3600) % 60;
			return (hours == 1 ? hours + " hora" : hours + " horas") + " "
					+ (minutes == 1 ? minutes + " minuto" : minutes + " minutos")
					+ (seconds <= 0 ? "" : " e " + (seconds == 1 ? seconds + " segundo" : seconds + " segundos"));
		} else if (time < 60) {
			return (time == 1 ? time + " segundo" : time + " segundos");
		} else {
			int minutes = (time / 60), seconds = (time % 60);
			return (minutes == 1 ? minutes + " minuto" : minutes + " minutos")
					+ (seconds <= 0 ? "" : " e " + (seconds == 1 ? seconds + " segundo" : seconds + " segundos"));
		}
	}

	public int slot(int i) {
		if (i >= 1 && i <= 7)
			return 9 + i;
		if (i == 8)
			return 19;
		if (i == 9)
			return 20;
		if (i == 10)
			return 21;
		return i;
	}

	@EventHandler
	public void onUpdate(UpdateEvent event) {
		if (event.getType() != UpdateType.SECOND)
			return;
		update();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			ItemStack current = event.getCurrentItem();
			if (event.getInventory().getName().equals(getMenu(MenuType.GAMES_MODE).getName())
					&& event.getCurrentItem() != null) {
				event.setCancelled(true);

				if (current.getType() == Material.DIAMOND_SWORD) {
					connectMessage(p, "pvp");
				} else if (current.getType() == Material.WOOD_SWORD) {
					connectMessage(p, "simulator");
				} else if (current.getType() == Material.IRON_FENCE) {
					connectMessage(p, "gladiator");
				} else if (current.getType() == Material.MAGMA_CREAM) {
					connectMessage(p, "evento");
				} else {
					if (current.getType() == Material.MUSHROOM_SOUP) {
						if (event.getClick() != ClickType.LEFT) {
							p.openInventory(getMenu(MenuType.DOUBLEKITHG));
						} else {
							findServer(p, ServerType.DOUBLEKITHG);
						}
					} else if (current.getType() == Material.BOW) {
						connectMessage(p, "lobby-sw");
					}
				}
			} else if (event.getInventory().getName().equals(getMenu(MenuType.DOUBLEKITHG).getName())
					&& event.getCurrentItem() != null) {
				event.setCancelled(true);
				if (event.getCurrentItem().hasItemMeta()) {
					for (int i = 1; i < 11; i++) {
						if (event.getCurrentItem().getItemMeta().getDisplayName()
								.contains(String.valueOf(i))) {
							connectMessage(p, "hg-a" + (i == 10 ? 10 : i));
							return;
						}
					}
				}
			} else if (event.getInventory().getName().equals(getMenu(MenuType.LOBBYS).getName())
					&& event.getCurrentItem() != null) {
				event.setCancelled(true);
				if (event.getCurrentItem().hasItemMeta()) {
					connectMessage(p, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
			}
		}
	}

	public void findServer(Player p, ServerType serverType) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("FindServer");
		out.writeUTF(serverType.name());
		p.sendPluginMessage(BukkitMain.getInstance(), "Velocity", out.toByteArray());
	}

	public void connectMessage(Player p, String serverName) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(serverName);
		p.sendPluginMessage(BukkitMain.getInstance(), "Velocity", out.toByteArray());
	}

	public void update() {
		int onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.PVP_FULLIRON);
		Inventory menu = MENUS.get(MenuType.GAMES_MODE);
		ItemBuilder builder = new ItemBuilder().name("§9§lPvP FullIron").amount(onlineCount)
				.type(Material.DIAMOND_SWORD).lore("§7Novo servidor de KitPvP com", "§7sopa feito para todos lutarem",
						"§7com armaduras de ferro, batalhando", "§7com espadas com mais dano", "",
						"§5§lAgora com Void Challenge", "", "§3§l" + onlineCount + "§7 jogadores online.", "",
						"§b§lClique para conectar.");
		if (menu != null) {
			menu.setItem(10, builder.build());
			//
			onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.PVP_SIMULATOR);
			builder = new ItemBuilder().name("§9§lPvP Simulator").amount(onlineCount).type(Material.WOOD_SWORD).lore(
					"§7Novo servidor de KitPvP com", "§7sopa feito para todos usarem",
					"§7estratégias e lutarem sem armadura", "§7em um estilo mais Hardcore",
					"§7simulando o estilo do HG", "", "§5§lAgora com Void Challenge", "",
					"§3§l" + onlineCount + "§7 jogadores online.", "", "§b§lClique para conectar.");
			menu.setItem(11, builder.build());
			//
			onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.DOUBLEKITHG);
			builder = new ItemBuilder().name("§9§lWeaven-HG").amount(onlineCount).type(Material.MUSHROOM_SOUP).lore(
					"§7Servidores de HardcoreGames.", "§7Seja o ultimo sobrevivente",
					"§7em uma batalha brutal com kits", "§7onde apenas um sera o campeao", "",
					"§3§l" + onlineCount + "§7 jogadores online.", "", "§b§lClique esquerdo §bpara",
					"§b§lconectar §ba um §b§lservidor.", "", "§e§lClique direito §epara ver",
					"§e§ltodos §eos §e§lservidores.");
			menu.setItem(12, builder.build());
			//
			onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.SKYWARS);
			onlineCount += BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.SKYWARS_LOBBY);
			builder = new ItemBuilder().name("§9§lWeaven-Skywars").amount(onlineCount).type(Material.BOW).lore(
					"§7Servidor de Skywars onde só 1", "§7guerreiro tem lugar no céu!",
					"§7Batalhe também na versão 1.7!", "", "§3§l" + onlineCount + "§7 jogadores online.", "",
					"§b§lClique para conectar.");
			menu.setItem(13, builder.build());
			//
			onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.GLADIATOR);
			builder = new ItemBuilder().name("§9§lGladiator").amount(onlineCount).type(Material.IRON_FENCE).lore(
					"§7Jogue neste modo Gladiator", "§7para alem de aprimorar o seu controle",
					"§7de inventário simulando um evento", "§7gladiator, torne-se o melhor Gladiador", "§7do servidor",
					"", "§3§l" + onlineCount + "§7 jogadores online.", "", "§b§lClique para conectar.");
			menu.setItem(14, builder.build());
			//
			onlineCount = BukkitMain.getInstance().getNetworkManager().getOnlineCount(ServerType.TOURNAMENT);
			builder = new ItemBuilder().name("§9§lTorneio").amount(onlineCount).type(Material.MAGMA_CREAM).lore(
					"§7Servidor reservado para eventos.", "", "§3§l" + onlineCount + "§7 jogadores conectados.", "",
					"§b§lClique para conectar.");
			menu.setItem(15, builder.build());
			// games mode end
		}

		menu = MENUS.get(MenuType.DOUBLEKITHG);// hunger games start
		NetworkManager networkManager = BukkitMain.getInstance().getNetworkManager();
		if (menu != null) {
			for (int i = 1; i < 11; i++) {
				NetworkServer server = networkManager.getServer("hg-a" + i);
				if (server != null) {
					if (server instanceof HardcoreGames) {
						HardcoreGames hg = (HardcoreGames) server;
						if (hg.getLastData() >= System.currentTimeMillis()) {
							if (hg.getState() == GameState.WAITING) {
								builder = new ItemBuilder().name("§9§l> §a§la" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7Aguardando §a§ljogadores§7 para iniciar!",
												"§7Precisa-se de mais §a§l" + hg.getPlayersLeft() + "§7 jogadores!", "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(10);
							} else if (hg.getState() == GameState.PREGAME) {
								builder = new ItemBuilder().name("§9§l> §a§la" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A partida §a§linicia§7 em §a§l" + getTimeFormat(hg.getTime()), "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(10);
							} else if (hg.getState() == GameState.INVINCIBILITY) {
								builder = new ItemBuilder().name("§9§l> §e§la" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A §e§linvencibilidade§7 acaba em §e§l" + getTimeFormat(hg.getTime()),
												"", "§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(11);
							} else if (hg.getState() == GameState.GAMETIME) {
								builder = new ItemBuilder().name("§9§l> §6§la" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A partida está em §6§lprogresso§7!",
												"§7A partida está em §6§l" + getTimeFormat(hg.getTime()), "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(14);
							} else if (hg.getState() == GameState.ENDING) {
								builder = new ItemBuilder().name("§9§l> §2§la" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A partida está §2§lfinalizando§7!",
												"§7O vencedor foi §2§l" + hg.getWinner(), "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(2);
							} else {
								builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
										.durability(1);
							}
						} else {
							builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
									.durability(1);
						}
					} else {
						builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<")
								.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
								.durability(1);
					}
				} else {
					builder = new ItemBuilder().name("§9§l> §c§la" + i + ".weaven-hg.com §9§l<").type(Material.INK_SACK)
							.lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
				}
				menu.setItem(slot(i), builder.build());
			}
		}

		menu = MENUS.get(MenuType.TEAMHG);
		if (menu != null) {
			for (int i = 1; i < 11; i++) {
				NetworkServer server = networkManager.getServer("ta" + i);
				if (server != null) {
					if (server instanceof HardcoreGames) {
						HardcoreGames hg = (HardcoreGames) server;
						if (hg.getLastData() >= System.currentTimeMillis()) {
							if (hg.getState() == GameState.WAITING) {
								builder = new ItemBuilder().name("§9§l> §a§lta" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7Aguardando §a§ljogadores§7 para iniciar!",
												"§7Precisa-se de mais §a§l" + hg.getPlayersLeft() + "§7 jogadores!", "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(10);
							} else if (hg.getState() == GameState.PREGAME) {
								builder = new ItemBuilder().name("§9§l> §a§lta" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A partida §a§linicia§7 em §a§l" + getTimeFormat(hg.getTime()), "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(10);
							} else if (hg.getState() == GameState.INVINCIBILITY) {
								builder = new ItemBuilder().name("§9§l> §e§lta" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A §e§linvencibilidade§7 acaba em §e§l" + getTimeFormat(hg.getTime()),
												"", "§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(11);
							} else if (hg.getState() == GameState.GAMETIME) {
								builder = new ItemBuilder().name("§9§l> §6§lta" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A partida está em §6§lprogresso§7!",
												"§7A partida está em §6§l" + getTimeFormat(hg.getTime()), "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(14);
							} else if (hg.getState() == GameState.ENDING) {
								builder = new ItemBuilder().name("§9§l> §2§lta" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).amount(hg.getPlayers())
										.lore("§7A partida está §2§lfinalizando§7!",
												"§7O time vencedor foi §2§l" + hg.getWinner(), "",
												"§3§l" + hg.getPlayers() + "§7 jogadores conectados", "",
												"§b§lClique para conectar.")
										.durability(2);
							} else {
								builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
										.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
										.durability(1);
							}
						} else {
							builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
									.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
									.durability(1);
						}
					} else {
						builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
								.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...")
								.durability(1);
					}
				} else {
					builder = new ItemBuilder().name("§9§l> §c§lta" + i + ".weaven-hg.com §9§l<")
							.type(Material.INK_SACK).lore("§7Aguardando o envio de §c§linformaçoes§7...").durability(1);
				}
				menu.setItem(slot(i), builder.build());
			}
		}

		menu = MENUS.get(MenuType.LOBBYS);
		int index = 0;
		for (NetworkServer lobby : BukkitMain.getInstance().getNetworkManager().getServers()) {
			if (index > 8)
				break;
			if (lobby.getServerType() != ServerType.LOBBY)
				continue;
			if (lobby.getLastData() < System.currentTimeMillis())
				continue;
			builder = new ItemBuilder().name("§a§l" + lobby.getServerId().toUpperCase())
					.lore("", "§3§l" + lobby.getPlayers() + "§7 jogadores conectados", "", "§b§lClique para conectar")
					.type(Material.INK_SACK);
			menu.setItem(index, builder.build());
			index++;
		}
	}

	@Override
	public void disable() {
		//
	}

	public enum MenuType {
		GAMES_MODE, DOUBLEKITHG, TEAMHG, LOBBYS;
	}
}
