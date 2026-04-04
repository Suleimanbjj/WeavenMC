package br.com.weavenmc.lobby.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.lobby.Lobby;
import br.com.weavenmc.lobby.Management;
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
public class CosmeticManager extends Management implements Listener {

	private Inventory cosmeticMenu;
	private List<UUID> cantCloseInv;

	private List<ItemStack> crateSilverItems, crateGoldItems, crateDiamondItems;

	public CosmeticManager(Lobby plugin) {
		super(plugin);
	}

	@Override
	public void enable() {
		cantCloseInv = new ArrayList<>();
		cosmeticMenu = Bukkit.createInventory(null, 9, "§aCosmeticos");
		ItemBuilder builder = new ItemBuilder().name("§5§lCaixas §7(Clique para abrir)").type(Material.ENDER_CHEST);
		cosmeticMenu.setItem(2, builder.build());
		builder = new ItemBuilder().name("§2§lApostas §7(Clique para abrir)").type(Material.STAINED_CLAY).durability(5);
		cosmeticMenu.setItem(6, builder.build());
		registerListener(this);
		crateSilverItems = new ArrayList<>();
		int i = 1;
		while (i <= 4) {
			builder = new ItemBuilder().name("§6§l3000 MOEDAS").type(Material.GOLD_INGOT);
			crateSilverItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 3) {
			builder = new ItemBuilder().name("§6§l5000 MOEDAS").type(Material.GOLD_INGOT);
			crateSilverItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 3) {
			builder = new ItemBuilder().name("§9§lX3 DOUBLEXPs").type(Material.EXP_BOTTLE);
			crateSilverItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 2) {
			builder = new ItemBuilder().name("§3§lX1 TICKETs").type(Material.DIAMOND);
			crateSilverItems.add(builder.build());
			++i;
		}
		builder = new ItemBuilder().name("§6§l1 CAIXA DE OURO").type(Material.ENDER_CHEST);
		crateSilverItems.add(builder.build());
		builder = new ItemBuilder().name("§a§l1 VIP LIGHT POR 3 HORAS").type(Material.INK_SACK).durability(10);
		crateSilverItems.add(builder.build());
		builder = new ItemBuilder().name("§6§l3000 MOEDAS").type(Material.GOLD_INGOT);
		crateSilverItems.add(builder.build());
		//
		crateGoldItems = new ArrayList<>();
		i = 1;
		while (i <= 4) {
			builder = new ItemBuilder().name("§6§l7000 MOEDAS").type(Material.GOLD_INGOT);
			crateGoldItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 3) {
			builder = new ItemBuilder().name("§6§l9000 MOEDAS").type(Material.GOLD_INGOT);
			crateGoldItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 3) {
			builder = new ItemBuilder().name("§9§lX6 DOUBLEXPs").type(Material.EXP_BOTTLE);
			crateGoldItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 2) {
			builder = new ItemBuilder().name("§3§lX2 TICKETs").type(Material.DIAMOND);
			crateGoldItems.add(builder.build());
			++i;
		}
		builder = new ItemBuilder().name("§b§l1 CAIXA DE DIAMANTE").type(Material.ENDER_CHEST);
		crateGoldItems.add(builder.build());
		builder = new ItemBuilder().name("§6§l1 VIP PREMIUM POR 3 HORAS").type(Material.INK_SACK).durability(1);
		crateGoldItems.add(builder.build());
		builder = new ItemBuilder().name("§6§l7000 MOEDAS").type(Material.GOLD_INGOT);
		crateGoldItems.add(builder.build());
		//
		crateDiamondItems = new ArrayList<>();
		i = 1;
		while (i <= 4) {
			builder = new ItemBuilder().name("§6§l10000 MOEDAS").type(Material.GOLD_INGOT);
			crateDiamondItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 3) {
			builder = new ItemBuilder().name("§6§l15000 MOEDAS").type(Material.GOLD_INGOT);
			crateDiamondItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 3) {
			builder = new ItemBuilder().name("§9§lX10 DOUBLEXPs").type(Material.EXP_BOTTLE);
			crateDiamondItems.add(builder.build());
			++i;
		}
		i = 1;
		while (i <= 2) {
			builder = new ItemBuilder().name("§3§lX4 TICKETs").type(Material.DIAMOND);
			crateDiamondItems.add(builder.build());
			++i;
		}
		builder = new ItemBuilder().name("§b§l+1 CAIXA DE DIAMANTE").type(Material.ENDER_CHEST);
		crateDiamondItems.add(builder.build());
		builder = new ItemBuilder().name("§1§l1 VIP BETA POR 3 HORAS").type(Material.INK_SACK).durability(4);
		crateDiamondItems.add(builder.build());
		builder = new ItemBuilder().name("§d§l1 VIP ULTIMATE POR 3 HORAS").type(Material.INK_SACK).durability(13);
		crateDiamondItems.add(builder.build());
		builder = new ItemBuilder().name("§6§l10000 MOEDAS").type(Material.GOLD_INGOT);
		crateDiamondItems.add(builder.build());
		//
		builder = null;
		i = 0;
	}

	@Override
	public void disable() {
		cantCloseInv.clear();
		cantCloseInv = null;
		cosmeticMenu.clear();
		cosmeticMenu = null;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onLeaveListener(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (cantCloseInv.contains(p.getUniqueId())) {
			cantCloseInv.remove(p.getUniqueId());
			p.closeInventory();
		}
		if (luckyTask.containsKey(p.getUniqueId())) {
			luckyTask.get(p.getUniqueId()).cancel();
			luckyTask.remove(p.getUniqueId());
		}
		if (luckyType.containsKey(p.getUniqueId())) {
			luckyType.remove(p.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) {
			Player p = (Player) event.getPlayer();
			Inventory menu = event.getInventory();
			if (cantCloseInv.contains(p.getUniqueId())) {
				new BukkitRunnable() {
					@Override
					public void run() {
						p.openInventory(menu);
					}
				}.runTaskLater(getPlugin(), 1L);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
			if (event.getInventory().getName().contains("Sorteando")) {
				event.setCancelled(true);
			} else if (event.getInventory().getName().equals("§aCosmeticos")) {
				event.setCancelled(true);
				if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.ENDER_CHEST) {
					openBoxesMenuToPlayer(p);
				} else if (event.getCurrentItem() != null
						&& event.getCurrentItem().getType() == Material.STAINED_CLAY) {
					openLuckyMenuToPlayer(p);
				}
			} else if (event.getInventory().getName().equals("§eCaixas")) {
				event.setCancelled(true);
				if (event.getCurrentItem() == null)
					return;
				if (event.getCurrentItem().getType() == Material.ARROW) {
					p.openInventory(cosmeticMenu);
					return;
				}
				if (!event.getCurrentItem().hasItemMeta())
					return;
				String name = event.getCurrentItem().getItemMeta().getDisplayName();
				if (name.equals("§7Caixa de §7§lPRATA")) {
					int amount = bP.getSilverCrates();
					if (amount > 0) {
						bP.removeSilverCrates(1);
						bP.save(DataCategory.CRATES);
						useCrateSilver(p);
					} else {
						p.sendMessage("§5§lCAIXAS§f Você não possui nenhuma caixa de prata.");
					}
				} else if (name.equals("§7Caixa de §6§lOURO")) {
					int amount = bP.getGoldCrates();
					if (amount > 0) {
						bP.removeGoldCrates(1);
						bP.save(DataCategory.CRATES);
						useCrateGold(p);
					} else {
						p.sendMessage("§5§lCAIXAS§f Você não possui nenhuma caixa de ouro.");
					}
				} else if (name.equals("§7Caixa de §b§lDIAMANTE")) {
					int amount = bP.getDiamondCrates();
					if (amount > 0) {
						bP.removeDiamondCrates(1);
						bP.save(DataCategory.CRATES);
						useCrateDiamond(p);
					} else {
						p.sendMessage("§5§lCAIXAS§f Você não possui nenhuma caixa de diamante.");
					}
				}
			} else if (event.getInventory().getName().equals("§7Escolha uma aposta")) {
				event.setCancelled(true);
				if (event.getCurrentItem() == null)
					return;
				if (event.getCurrentItem().getType() == Material.ARROW) {
					p.openInventory(cosmeticMenu);
					return;
				}
				if (!event.getCurrentItem().hasItemMeta())
					return;
				String name = event.getCurrentItem().getItemMeta().getDisplayName();
				if (name.equals("§2§lX3 GREEN BLOCKS")) {
					int amount = bP.getMoney();
					if (amount >= 10000) {
						bP.removeMoney(10000);
						bP.save(DataCategory.BALANCE);
						play3Green(p);
					} else {
						p.sendMessage("§2§lAPOSTAS§f Você precisa de mais §6§l" + (10000 - amount)
								+ " MOEDAS§f para esta aposta.");
					}
				} else if (name.equals("§2§lX6 GREEN BLOCKS")) {
					int amount = bP.getMoney();
					if (amount >= 20000) {
						bP.removeMoney(20000);
						bP.save(DataCategory.BALANCE);
						play6Green(p);
					} else {
						p.sendMessage("§2§lAPOSTAS§f Você precisa de mais §6§l" + (20000 - amount)
								+ " MOEDAS§f para esta aposta.");
					}
				} else if (name.equals("§2§lX9 GREEN BLOCKS")) {
					int amount = bP.getMoney();
					if (amount >= 30000) {
						bP.removeMoney(30000);
						bP.save(DataCategory.BALANCE);
						play9Green(p);
					} else {
						p.sendMessage("§2§lAPOSTAS§f Você precisa de mais §6§l" + (30000 - amount)
								+ " MOEDAS§f para esta aposta.");
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private List<ItemStack> luckys = Arrays.asList(
			new ItemBuilder().name("§2§lVERDE").type(Material.getMaterial(159)).durability(5).build(),
			new ItemBuilder().name("§2§lVERDE").type(Material.getMaterial(159)).durability(5).build(),
			new ItemBuilder().name("§c§lVERMELHO").type(Material.getMaterial(159)).durability(14).build(),
			new ItemBuilder().name("§c§lVERMELHO").type(Material.getMaterial(159)).durability(14).build(),
			new ItemBuilder().name("§c§lVERMELHO").type(Material.getMaterial(159)).durability(14).build(),
			new ItemBuilder().name("§c§lVERMELHO").type(Material.getMaterial(159)).durability(14).build(),
			new ItemBuilder().name("§C§lVERMELHO").type(Material.getMaterial(159)).durability(14).build());

	public void play3Green(Player p) {
		Inventory menu = Bukkit.createInventory(p, 9, "§7Sorteando...");
		p.closeInventory();
		cantCloseInv.add(p.getUniqueId());
		p.openInventory(menu);
		luckyType.put(p.getUniqueId(), 3);

		AtomicInteger current = new AtomicInteger();
		current.set(1);
		AtomicInteger played = new AtomicInteger();

		luckyTask.put(p.getUniqueId(), new BukkitRunnable() {
			@Override
			public void run() {
				if (current.get() == 1) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(0, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(2);
						played.set(0);
					}
				} else if (current.get() == 2) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(1, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(3);
						played.set(0);
					}
				} else if (current.get() == 3) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(2, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(4);
						played.set(0);
					}
				} else if (current.get() >= 4) {
					getResult(p, menu);
					cancel();
					return;
				}
				p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
			}
		}.runTaskTimer(getPlugin(), 2L, 2L));
	}

	public void play6Green(Player p) {
		Inventory menu = Bukkit.createInventory(p, 9, "§7Sorteando...");
		p.closeInventory();
		cantCloseInv.add(p.getUniqueId());
		p.openInventory(menu);
		luckyType.put(p.getUniqueId(), 6);

		AtomicInteger current = new AtomicInteger();
		current.set(1);
		AtomicInteger played = new AtomicInteger();

		luckyTask.put(p.getUniqueId(), new BukkitRunnable() {
			@Override
			public void run() {
				if (current.get() == 1) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(0, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(2);
						played.set(0);
					}
				} else if (current.get() == 2) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(1, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(3);
						played.set(0);
					}
				} else if (current.get() == 3) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(2, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(4);
						played.set(0);
					}
				} else if (current.get() == 4) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(3, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(5);
						played.set(0);
					}
				} else if (current.get() == 5) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(4, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(6);
						played.set(0);
					}
				} else if (current.get() == 6) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(5, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(7);
						played.set(0);
					}

				} else if (current.get() >= 7) {
					getResult(p, menu);
					cancel();
					return;
				}
				p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
			}
		}.runTaskTimer(getPlugin(), 2L, 2L));
	}

	public void play9Green(Player p) {
		Inventory menu = Bukkit.createInventory(p, 9, "§7Sorteando...");
		p.closeInventory();
		cantCloseInv.add(p.getUniqueId());
		p.openInventory(menu);
		luckyType.put(p.getUniqueId(), 9);

		AtomicInteger current = new AtomicInteger();
		current.set(1);
		AtomicInteger played = new AtomicInteger();

		luckyTask.put(p.getUniqueId(), new BukkitRunnable() {
			@Override
			public void run() {
				if (current.get() == 1) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(0, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(2);
						played.set(0);
					}
				} else if (current.get() == 2) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(1, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(3);
						played.set(0);
					}
				} else if (current.get() == 3) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(2, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(4);
						played.set(0);
					}
				} else if (current.get() == 4) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(3, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(5);
						played.set(0);
					}
				} else if (current.get() == 5) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(4, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(6);
						played.set(0);
					}
				} else if (current.get() == 6) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(5, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(7);
						played.set(0);
					}
				} else if (current.get() == 7) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(6, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(8);
						played.set(0);
					}
				} else if (current.get() == 8) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(7, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(9);
						played.set(0);
					}
				} else if (current.get() == 9) {
					if (played.incrementAndGet() <= 50) {
						menu.setItem(8, luckys.get(new Random().nextInt(luckys.size() - 1)));
					} else {
						current.set(10);
						played.set(0);
					}
				} else if (current.get() >= 10) {
					getResult(p, menu);
					cancel();
					return;
				}
				p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
			}
		}.runTaskTimer(getPlugin(), 2L, 2L));
	}

	public void getResult(Player p, Inventory menu) {
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		new BukkitRunnable() {
			@Override
			public void run() {
				cantCloseInv.remove(p.getUniqueId());
				p.closeInventory();
				if (luckyType.get(p.getUniqueId()) == 3) {
					int result = 0;
					for (int i = 0; i < 3; i++) {
						String name = menu.getItem(i).getItemMeta().getDisplayName();
						if (name.equals("§2§lVERDE")) {
							++result;
						}
					}
					if (result > 0) {
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.5F, 1.5F);
						int toAdd = 8000 * result;
						bP.addMoney(toAdd);
						bP.save(DataCategory.BALANCE);
						p.sendMessage("§2§lAPOSTAS§f Você acertou §a§l" + result + " VEZES§f e recebeu §6§l" + toAdd
								+ " MOEDAS");
					} else {
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.5F, 1.5F);
						p.sendMessage("§2§lAPOSTAS§f Você §c§lNAO ACERTOU NENHUMA VEZ D:");
					}
				} else if (luckyType.get(p.getUniqueId()) == 6) {
					int result = 0;
					for (int i = 0; i < 6; i++) {
						String name = menu.getItem(i).getItemMeta().getDisplayName();
						if (name.equals("§2§lVERDE")) {
							++result;
						}
					}
					if (result > 0) {
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.5F, 1.5F);
						int toAdd = 8000 * result;
						bP.addMoney(toAdd);
						bP.save(DataCategory.BALANCE);
						p.sendMessage("§2§lAPOSTAS§f Você acertou §a§l" + result + " VEZES§f e recebeu §6§l" + toAdd
								+ " MOEDAS");
					} else {
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.5F, 1.5F);
						p.sendMessage("§2§lAPOSTAS§f Você §c§lNAO ACERTOU NENHUMA VEZ D:");
					}
				} else if (luckyType.get(p.getUniqueId()) == 9) {
					int result = 0;
					for (int i = 0; i < 9; i++) {
						String name = menu.getItem(i).getItemMeta().getDisplayName();
						if (name.equals("§2§lVERDE")) {
							++result;
						}
					}
					if (result > 0) {
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.5F, 1.5F);
						int toAdd = 8000 * result;
						bP.addMoney(toAdd);
						bP.save(DataCategory.BALANCE);
						p.sendMessage("§2§lAPOSTAS§f Você acertou §a§l" + result + " VEZES§f e recebeu §6§l" + toAdd
								+ " MOEDAS");
					} else {
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_DEATH, 1.5F, 1.5F);
						p.sendMessage("§2§lAPOSTAS§f Você §c§lNAO ACERTOU NENHUMA VEZ D:");
					}
				}
				luckyType.remove(p.getUniqueId());
				luckyTask.remove(p.getUniqueId());
			}
		}.runTaskLater(getPlugin(), 10L);
	}

	public HashMap<UUID, BukkitTask> luckyTask = new HashMap<>();

	private HashMap<UUID, Integer> luckyType = new HashMap<>();
	private HashMap<UUID, Integer> blocks = new HashMap<>();

	public void useCrateDiamond(Player p) {
		Inventory menu = Bukkit.createInventory(p, 9, "§7Sorteando caixa de Diamante...");
		p.closeInventory();
		cantCloseInv.add(p.getUniqueId());
		p.openInventory(menu);
		//
		for (int i = 1; i < 57; i++) {
			final int current = i;
			new BukkitRunnable() {
				@Override
				public void run() {
					if (current != 56) {
						ItemStack next = crateDiamondItems.get(new Random().nextInt(crateDiamondItems.size() - 1));
						for (int i = 1; i < 9; i++) {
							ItemStack item = menu.getItem(i);
							if (item != null) {
								menu.setItem(i - 1, item);
							}
						}
						menu.setItem(8, next);
						p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
					} else {
						int i = 0;
						while (i <= 3) {
							menu.setItem(i, null);
							i++;
						}
						i = 5;
						while (i <= 8) {
							menu.setItem(i, null);
							i++;
						}
						p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
						new BukkitRunnable() {
							@Override
							public void run() {
								getCrateDiamondResult(p, menu.getItem(4));
							}
						}.runTaskLater(getPlugin(), 20L);
					}
				}
			}.runTaskLater(getPlugin(), i * 3L);
		}
	}

	@SuppressWarnings("deprecation")
	public void openLuckyMenuToPlayer(Player p) {
		Inventory menu = Bukkit.createInventory(p, 18, "§7Escolha uma aposta");
		ItemBuilder builder = new ItemBuilder().name("§2§lX3 GREEN BLOCKS")
				.lore("§7Apostar 3x blocos verdes", "", "§aPode acertar até 3 blocos verdes.", "",
						"§f- §68000 moedas x quantidade de acertos", "",
						"§cVocê paga 10000 moedas para jogar.")
				.type(Material.getMaterial(159)).durability(5).amount(3);
		menu.setItem(10, builder.build());
		builder = new ItemBuilder().name("§2§lX6 GREEN BLOCKS")
				.lore("§7Apostar 6x blocos verdes", "", "§aPode acertar até 6 blocos verdes.", "",
						"§f- §68000 moedas x quantidade de acertos", "",
						"§cVocê paga 20000 moedas para jogar.")
				.type(Material.getMaterial(159)).durability(5).amount(6);
		menu.setItem(13, builder.build());
		builder = new ItemBuilder().name("§2§lX9 GREEN BLOCKS")
				.lore("§7Apostar 9x blocos verdes", "", "§aPode acertar até 9 blocos verdes.", "",
						"§f- §68000 moedas x quantidade de acertos", "",
						"§cVocê paga 30000 para jogar.")
				.type(Material.getMaterial(159)).durability(5).amount(9);
		menu.setItem(16, builder.build());
		builder = new ItemBuilder().name("§5§lVoltar").lore("§7Clique para voltar ao menu de Cosmeticos")
				.type(Material.ARROW);
		menu.setItem(4, builder.build());
		p.openInventory(menu);
		builder = null;
	}

	public void useCrateGold(Player p) {
		Inventory menu = Bukkit.createInventory(p, 9, "§7Sorteando caixa de Ouro...");
		p.closeInventory();
		cantCloseInv.add(p.getUniqueId());
		p.openInventory(menu);
		//
		for (int i = 1; i < 57; i++) {
			final int current = i;
			new BukkitRunnable() {
				@Override
				public void run() {
					if (current != 56) {
						ItemStack next = crateGoldItems.get(new Random().nextInt(crateGoldItems.size() - 1));
						for (int i = 1; i < 9; i++) {
							ItemStack item = menu.getItem(i);
							if (item != null) {
								menu.setItem(i - 1, item);
							}
						}
						menu.setItem(8, next);
						p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
					} else {
						int i = 0;
						while (i <= 3) {
							menu.setItem(i, null);
							i++;
						}
						i = 5;
						while (i <= 8) {
							menu.setItem(i, null);
							i++;
						}
						p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
						new BukkitRunnable() {
							@Override
							public void run() {
								getCrateGoldResult(p, menu.getItem(4));
							}
						}.runTaskLater(getPlugin(), 20L);
					}
				}
			}.runTaskLater(getPlugin(), i * 3L);
		}
	}

	public void useCrateSilver(Player p) {
		Inventory menu = Bukkit.createInventory(p, 9, "§7Sorteando caixa de Prata...");
		p.closeInventory();
		cantCloseInv.add(p.getUniqueId());
		p.openInventory(menu);
		//
		for (int i = 1; i < 57; i++) {
			final int current = i;
			new BukkitRunnable() {
				@Override
				public void run() {
					if (current != 56) {
						ItemStack next = crateSilverItems.get(new Random().nextInt(crateSilverItems.size() - 1));
						for (int i = 1; i < 9; i++) {
							ItemStack item = menu.getItem(i);
							if (item != null) {
								menu.setItem(i - 1, item);
							}
						}
						menu.setItem(8, next);
						p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
					} else {
						int i = 0;
						while (i <= 3) {
							menu.setItem(i, null);
							i++;
						}
						i = 5;
						while (i <= 8) {
							menu.setItem(i, null);
							i++;
						}
						p.playSound(p.getLocation(), Sound.CLICK, 1.5F, 1.5F);
						new BukkitRunnable() {
							@Override
							public void run() {
								getCrateSilverResult(p, menu.getItem(4));
							}
						}.runTaskLater(getPlugin(), 20L);
					}
				}
			}.runTaskLater(getPlugin(), i * 3L);
		}
	}

	private void getCrateDiamondResult(Player p, ItemStack result) {
		if (p == null || result == null)
			return;
		cantCloseInv.remove(p.getUniqueId());
		p.closeInventory();
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		String name = result.getItemMeta().getDisplayName();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.5F, 1.5F);
		if (name.equals("§6§l10000 MOEDAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l10000 MOEDAS");
			bP.addMoney(10000);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§6§l15000 MOEDAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l15000 MOEDAS");
			bP.addMoney(15000);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§9§lX10 DOUBLEXPs")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §9§l10 DOUBLEXPs");
			bP.addDoubleXpMultiplier(10);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§3§lX4 TICKETs")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §3§l4 TICKET");
			bP.addTickets(4);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§b§l+1 CAIXA DE DIAMANTE")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §b§l+1 CAIXA DE DIAMANTE");
			bP.addDiamondCrates(1);
			bP.save(DataCategory.CRATES);
		} else if (name.equals("§1§l1 VIP BETA POR 3 HORAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §1§l1 VIP BETA COM DURAÇAO DE 3 HORAS");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "group " + bP.getName() + " add beta 3h");
		} else if (name.equals("§d§l1 VIP ULTIMATE POR 3 HORAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §d§l1 VIP ULTIMATE COM DURAÇAO DE 3 HORAS");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "group " + bP.getName() + " add ultimate 3h");
		} else {
			p.sendMessage(
					"§5§lCAIXAS§f O resultado da prêmiação não foi identificado! Você receberá sua caixa de volta.");
			bP.addDiamondCrates(1);
			bP.save(DataCategory.CRATES);
		}
	}

	private void getCrateGoldResult(Player p, ItemStack result) {
		if (p == null || result == null)
			return;
		cantCloseInv.remove(p.getUniqueId());
		p.closeInventory();
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		String name = result.getItemMeta().getDisplayName();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.5F, 1.5F);
		if (name.equals("§6§l7000 MOEDAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l7000 MOEDAS");
			bP.addMoney(7000);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§6§l9000 MOEDAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l9000 MOEDAS");
			bP.addMoney(9000);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§9§lX6 DOUBLEXPs")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §9§l6 DOUBLEXPs");
			bP.addDoubleXpMultiplier(6);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§3§lX2 TICKETs")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §3§l2 TICKET");
			bP.addTickets(2);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§b§l1 CAIXA DE DIAMANTE")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §b§l1 CAIXA DE DIAMANTE");
			bP.addDiamondCrates(1);
			bP.save(DataCategory.CRATES);
		} else if (name.equals("§6§l1 VIP PREMIUM POR 3 HORAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l1 VIP PREMIUM COM DURAÇAO DE 3 HORAS");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "group " + bP.getName() + " add premium 3h");
		} else {
			p.sendMessage(
					"§5§lCAIXAS§f O resultado da prêmiação não foi identificado! Você receberá sua caixa de volta.");
			bP.addGoldCrates(1);
			bP.save(DataCategory.CRATES);
		}
	}

	private void getCrateSilverResult(Player p, ItemStack result) {
		if (p == null || result == null)
			return;
		cantCloseInv.remove(p.getUniqueId());
		p.closeInventory();
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		String name = result.getItemMeta().getDisplayName();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.5F, 1.5F);
		if (name.equals("§6§l3000 MOEDAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l3000 MOEDAS");
			bP.addMoney(3000);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§6§l5000 MOEDAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l3000 MOEDAS");
			bP.addMoney(5000);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§9§lX3 DOUBLEXPs")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §9§l3 DOUBLEXPs");
			bP.addDoubleXpMultiplier(3);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§3§lX1 TICKETs")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §3§l1 TICKET");
			bP.addTickets(1);
			bP.save(DataCategory.BALANCE);
		} else if (name.equals("§6§l1 CAIXA DE OURO")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §6§l1 CAIXA DE OURO");
			bP.addGoldCrates(1);
			bP.save(DataCategory.CRATES);
		} else if (name.equals("§a§l1 VIP LIGHT POR 3 HORAS")) {
			p.sendMessage("§5§lCAIXAS§f Você ganhou §a§l1 VIP LIGHT COM DURAÇAO DE 3 HORAS");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "group " + bP.getName() + " add light 3h");
		} else {
			p.sendMessage(
					"§5§lCAIXAS§f O resultado da prêmiação não foi identificado! Você receberá sua caixa de volta.");
			bP.addSilverCrates(1);
			bP.save(DataCategory.CRATES);
		}
	}

	public void openBoxesMenuToPlayer(Player p) {
		BukkitPlayer bP = (BukkitPlayer) WeavenMC.getAccountCommon().getWeavenPlayer(p.getUniqueId());
		int boxes = bP.getSilverCrates();
		Inventory menu = Bukkit.createInventory(p, 27, "§eCaixas");
		ItemBuilder builder = new ItemBuilder().name("§7Caixa de §7§lPRATA").amount(boxes)
				.lore("§7Premiações desta caixa:", "", "§f- §6§l3000 MOEDAS", "§f- §6§l5000 MOEDAS",
						"§f- §9§lX3 DOUBLEXPs", "§f- §3§lX1 TICKETs", "§f- §6§l1 CAIXA DE OURO",
						"§f- §a§l1 VIP LIGHT POR 3 HORAS", "",
						"§7Você possui " + boxes + " desta caixa.")
				.type(Material.ENDER_CHEST);
		menu.setItem(11, builder.build());
		boxes = bP.getGoldCrates();
		builder = new ItemBuilder().name("§7Caixa de §6§lOURO").amount(boxes)
				.lore("§7Premiações desta caixa:", "", "§f- §6§l7000 MOEDAS", "§f- §6§l9000 MOEDAS",
						"§f- §9§lX6 DOUBLEXPs", "§f- §3§lX2 TICKETs", "§f- §b§l1 CAIXA DE DIAMANTE",
						"§f- §6§l1 VIP PREMIUM POR 3 HORAS", "",
						"§7Você possui " + boxes + " desta caixa.")
				.type(Material.ENDER_CHEST);
		menu.setItem(13, builder.build());
		boxes = bP.getDiamondCrates();
		builder = new ItemBuilder().name("§7Caixa de §b§lDIAMANTE").amount(boxes)
				.lore("§7Premiações desta caixa:", "", "§f- §6§l10000 MOEDAS", "§f- §6§l15000 MOEDAS",
						"§f- §9§lX10 DOUBLEXPs", "§f- §3§lX4 TICKETs", "§f- §b§l+1 CAIXA DE DIAMANTE",
						"§f- §1§l1 VIP BETA POR 3 HORAS", "§f- §d§l1 VIP ULTIMATE POR 3 HORAS", "",
						"§7Você possui " + boxes + " desta caixa.")
				.type(Material.ENDER_CHEST);
		menu.setItem(15, builder.build());
		builder = new ItemBuilder().name("§5§lVoltar").lore("§7Clique para voltar ao menu de Cosmeticos")
				.type(Material.ARROW);
		menu.setItem(4, builder.build());
		builder = null;
		bP = null;
		p.openInventory(menu);
	}
}
