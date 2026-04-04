package br.com.weavenmc.lobby.listeners;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import br.com.weavenmc.commons.bukkit.api.title.TitleAPI;
import br.com.weavenmc.commons.bukkit.api.vanish.VanishAPI;
import br.com.weavenmc.commons.bukkit.event.vanish.PlayerShowEvent;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.util.string.StringMaker;
import br.com.weavenmc.lobby.Lobby;
import br.com.weavenmc.lobby.gamer.Gamer;
import br.com.weavenmc.lobby.managers.MenuManager.MenuType;

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

public class PlayerListener implements Listener {

	private ArrayList<String> novidades;
	private ArrayList<UUID> vanish;

	public PlayerListener() {
		novidades = new ArrayList<String>();
		vanish = new ArrayList<>();
		novidades.add("Seja bem vindo!;%player%");
        novidades.add("Siga-nos no Twitter;§b§l@WeavenNetwork");
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		Player p = event.getPlayer();
		BukkitPlayer bP = BukkitPlayer.getPlayer(p.getUniqueId());
		if (bP != null) {
			Lobby.getPlugin().getGamerManager().loadGamer(bP.getUniqueId(), new Gamer(bP));
			WeavenMC.getAsynchronousExecutor().runAsync(() -> {
				bP.load(DataCategory.CRATES);
			});
		} else {
			event.disallow(Result.KICK_OTHER, "§4§lERRO§f Ocorreu um erro ao tentar carregar sua conta.");
		}
	}

	public void loadVip(String username) {
		Group group = null;
		long time = -1;

		if (Lobby.getPlugin().getConfig().get("LIGHT." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("LIGHT." + username.toLowerCase());
			if (time == -1) {
				group = Group.LIGHT;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.LIGHT;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add light " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("LIGHT." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}

		if (Lobby.getPlugin().getConfig().get("SAPPHIRE." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("SAPPHIRE." + username.toLowerCase());
			if (time == -1) {
				group = Group.PREMIUM;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.PREMIUM;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add premium " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("SAPPHIRE." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}

		if (Lobby.getPlugin().getConfig().get("ELITE." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("ELITE." + username.toLowerCase());
			if (time == -1) {
				group = Group.PREMIUM;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.PREMIUM;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add premium " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("ELITE." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}

		if (Lobby.getPlugin().getConfig().get("RUBY." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("RUBY." + username.toLowerCase());
			if (time == -1) {
				group = Group.BETA;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.BETA;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add beta " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("RUBY." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}

		if (Lobby.getPlugin().getConfig().get("BETA." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("BETA." + username.toLowerCase());
			if (time == -1) {
				group = Group.BETA;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.BETA;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add beta " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("BETA." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}

		if (Lobby.getPlugin().getConfig().get("WEAVEN." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("WEAVEN." + username.toLowerCase());
			if (time == -1) {
				group = Group.ULTIMATE;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.ULTIMATE;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add ultimate " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("WEAVEN." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}

		if (Lobby.getPlugin().getConfig().get("COPA." + username.toLowerCase()) != null) {
			time = Lobby.getPlugin().getConfig().getLong("COPA." + username.toLowerCase());
			if (time == -1) {
				group = Group.COPA;
			} else if (time >= System.currentTimeMillis()) {
				group = Group.COPA;
			}
			if (group != null) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
						"group " + username + " add copa " + (time == -1 ? "-1" : "c" + time));
				Lobby.getPlugin().getConfig().set("COPA." + username.toLowerCase(), null);
				Lobby.getPlugin().saveConfig();
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);

		Player p = event.getPlayer();
		Lobby.getPlugin().getScoreboardManager().createScoreboard(p);

		for (UUID vanished : vanish) {
			Player v = Bukkit.getPlayer(vanished);
			if (v != null) {
				v.hidePlayer(p);
			}
		}

		p.teleport(getSpawn(p));

		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setFireTicks(0);
		p.setHealth(20.0D);
		p.setFoodLevel(20);
		p.setAllowFlight(false);
		p.setGameMode(GameMode.ADVENTURE);

		joinItems(p);

		p.sendMessage("");
		p.sendMessage(StringMaker.makeCenteredMessage("§6§lWEAVEN§f§lMC"));
		p.sendMessage("");
		p.sendMessage(StringMaker.makeCenteredMessage(
				"§7Seja bem-vindo ao §e§lLobby§7! Aqui você pode se conectar á todos nossos §e§lmodos de jogos§7 e também pode se §e§ldivertir§7 enquanto espera para jogar! Para obter maiores informações acesse nosso §esite§7!"));
		p.sendMessage("");
		p.sendMessage(StringMaker.makeCenteredMessage("§6§lwww.mc-weaven.com.br"));
		p.sendMessage("");

		loadVip(p.getName());

		for (int i = 0; i < novidades.size(); i++) {
			String novidade = novidades.get(i);
			String title = novidade.split(";")[1].replace("%player%", "§6§l" + p.getName());
			String subtitle = novidade.split(";")[0];
			Bukkit.getScheduler().runTaskLater(Lobby.getPlugin(), () -> {
				TitleAPI.setTitle(p, title, subtitle);
			}, i * 40);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Lobby.getPlugin().getGamerManager().unloadGamer(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onShow(PlayerShowEvent event) {
		if (vanish.contains(event.getToShow().getUniqueId())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteractListener(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (event.getAction().toString().contains("RIGHT")) {
			ItemStack item = event.getItem();
			if (item != null) {
				if (item.getType() == Material.COMPASS) {
					event.setCancelled(true);
					p.openInventory(Lobby.getPlugin().getMenuManager().getMenu(MenuType.GAMES_MODE));
				} else if (item.getType() == Material.SKULL_ITEM) {
					event.setCancelled(true);
					p.performCommand("account");
				} else if (item.getType() == Material.ENDER_CHEST) {
					event.setCancelled(true);
					p.openInventory(Lobby.getPlugin().getCosmeticManager().getCosmeticMenu());
				} else if (item.getType() == Material.INK_SACK) {
					ItemMeta itemMeta = item.getItemMeta();
					if (itemMeta != null) {
						if (itemMeta.getDisplayName().equals("§e§lEsconder Jogadores §7(Clique)")) {
							p.setItemInHand(new ItemBuilder().type(Material.INK_SACK).durability(10)
									.name("§a§lMostrar Jogadores §7(Clique)").build());
							if (!vanish.contains(p.getUniqueId()))
								vanish.add(p.getUniqueId());
							for (Player o : Bukkit.getOnlinePlayers()) {
								if (o.getUniqueId() == p.getUniqueId())
									continue;
								p.hidePlayer(o);
							}
							p.sendMessage(StringMaker
									.makeCenteredMessage("§7Você deixou todos os §e§ljogadores invisiveis§7!"));
							p.updateInventory();
						} else {
							p.setItemInHand(new ItemBuilder().type(Material.INK_SACK).durability(8)
									.name("§e§lEsconder Jogadores §7(Clique)").build());
							if (vanish.contains(p.getUniqueId()))
								vanish.remove(p.getUniqueId());
							VanishAPI.getInstance().updateVanishToPlayer(p);
							p.sendMessage(StringMaker
									.makeCenteredMessage("§7Você deixou todos os §a§ljogadores visiveis§7!"));
							p.updateInventory();
						}
						itemMeta = null;
					}
				} else if (item.getType() == Material.FEATHER) {
					event.setCancelled(true);
					p.openInventory(Lobby.getPlugin().getStoreManager().getCategoryMenu());
				} else if (item.getType() == Material.NETHER_STAR) {
					event.setCancelled(true);
					p.openInventory(Lobby.getPlugin().getMenuManager().getMenu(MenuType.LOBBYS));
				}
				item = null;
			}
		}
		p = null;
	}

	public Location getSpawn(Player p) {
		Location spawn = p.getWorld().getSpawnLocation();
		return new Location(spawn.getWorld(), spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(),
				spawn.getPitch());
	}

	private void joinItems(Player p) {
		ItemBuilder builder = null;
		try {
			builder = new ItemBuilder().name("§9§lModos de Jogo §7(Clique)").type(Material.COMPASS)
					.enchantment(Enchantment.DURABILITY);
		} catch (Exception e) {
			builder = new ItemBuilder().name("§9§lModos de Jogo §7(Clique)").type(Material.COMPASS);
		}
		p.getInventory().setItem(0, builder.build());

		builder = new ItemBuilder().name("§6§lPerfil §7(Clique)").type(Material.SKULL_ITEM).skin(p.getName())
				.durability(3);
		p.getInventory().setItem(1, builder.build());

		builder = new ItemBuilder().name("§a§lCosmeticos §7(Clique)").type(Material.ENDER_CHEST);
		p.getInventory().setItem(3, builder.build());

		builder = new ItemBuilder().name("§e§lEsconder Jogadores §7(Clique)").type(Material.INK_SACK).durability(8);
		p.getInventory().setItem(6, builder.build());

		builder = new ItemBuilder().name("§b§lLoja §7(Clique)").type(Material.FEATHER);
		p.getInventory().setItem(7, builder.build());

		builder = new ItemBuilder().name("§5§lLobbys §7(Clique)").type(Material.NETHER_STAR);
		p.getInventory().setItem(8, builder.build());

		p.updateInventory();
		p.getInventory().setHeldItemSlot(0);

		builder = null;
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getTo().getBlockY() < 1) {
			player.teleport(getSpawn(player));
		}
	}

	@EventHandler
	public void onBucket(PlayerBucketEmptyEvent event) {
		Material bucket = event.getBucket();
		if (bucket.toString().contains("LAVA")) {
			event.setCancelled(true);
		} else if (bucket.toString().contains("WATER")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		BukkitPlayer bP = BukkitPlayer.getPlayer(player.getUniqueId());
		if (!bP.hasGroupPermission(Group.GERENTE)) {
			event.setCancelled(true);
			return;
		}
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		BukkitPlayer bP = BukkitPlayer.getPlayer(player.getUniqueId());
		if (!bP.hasGroupPermission(Group.GERENTE)) {
			event.setCancelled(true);
			return;
		}
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		if (event.getSpawnReason() != SpawnReason.CUSTOM) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onWheater(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	public static final ArrayList<UUID> toggled = new ArrayList<UUID>();
	public static final ArrayList<UUID> cooldown = new ArrayList<UUID>();

	@EventHandler
	public void onToggleFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		Gamer gamer = Lobby.getPlugin().getGamerManager().getGamer(player.getUniqueId());
		if (gamer == null)
			return;
		if (gamer.isFlying()) {
			return;
		}
		if (!toggled.contains(event.getPlayer().getUniqueId())) {
			if (!cooldown.contains(event.getPlayer().getUniqueId())) {
				if (player.getGameMode() == GameMode.CREATIVE) {
					return;
				}
				event.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				Vector vec = player.getLocation().getDirection().multiply(5).setY(1.6);
				player.setVelocity(vec);
				cooldown.add(event.getPlayer().getUniqueId());
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 1, 0);

				new BukkitRunnable() {
					public void run() {
						cooldown.remove(event.getPlayer().getUniqueId());
					}
				}.runTaskLater(Lobby.getPlugin(), 1 * 15);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!toggled.contains(event.getPlayer().getUniqueId())) {
			Player player = event.getPlayer();
			Gamer gamer = Lobby.getPlugin().getGamerManager().getGamer(player.getUniqueId());
			if (gamer == null)
				return;
			if (gamer.isFlying()) {
				return;
			}
			if (!cooldown.contains(event.getPlayer().getUniqueId())) {
				if ((player.getGameMode() != GameMode.CREATIVE)
						&& (player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR)
						&& (!player.isFlying())) {
					player.setAllowFlight(true);
				}
			}
		}
	}
}
