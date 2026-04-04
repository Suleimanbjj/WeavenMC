package br.com.weavenmc.hg.game.handler.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import javax.swing.ImageIcon;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.weavenmc.hg.bo2.BO2Constructor.FutureBlock;
import br.com.weavenmc.hg.game.custom.HungerListener;
import br.com.weavenmc.hg.game.handler.item.CacheItems;
import br.com.weavenmc.hg.game.stage.GameStage;
import br.com.weavenmc.hg.player.gamer.Gamer;
import br.com.weavenmc.hg.player.gamer.GamerMode;
import br.com.weavenmc.hg.utilitaries.MessagesConstructor;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.BukkitMain;
import br.com.weavenmc.commons.bukkit.account.BukkitPlayer;
import br.com.weavenmc.commons.bukkit.account.Medal;
import br.com.weavenmc.commons.bukkit.event.admin.PlayerAdminModeEnterEvent;
import br.com.weavenmc.commons.bukkit.event.admin.PlayerAdminModeQuitEvent;
import br.com.weavenmc.commons.core.account.League;
import br.com.weavenmc.commons.core.account.Tag;
import br.com.weavenmc.commons.core.clan.Clan;
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

@SuppressWarnings("deprecation")
public class GameListener extends HungerListener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat(AsyncPlayerChatEvent event) {
		if (event.isCancelled())
			return;

		Gamer gamer = getManager().getGamerManager().getGamer(event.getPlayer());
		BukkitPlayer bP = BukkitPlayer.getPlayer(gamer.getUUID());

		if (gamer.isSpectating()) {
			if (!gamer.isAlive() && bP.getGroup().getId() < Group.INVESTIDOR.getId()) {
				event.setCancelled(true);
				StringBuilder b = new StringBuilder();
				Medal medal = BukkitMain.getInstance().getTagManager().getMedal(event.getPlayer());
				if (medal != null)
					b.append(medal.getColor() + "§l" + medal.getSymbol() + " ");
				Clan clan = WeavenMC.getClanCommon().getClanFromName(bP.getClanName());
				if (clan != null)
					b.append("§7[§8" + clan.getAbbreviation() + "§7] ");
				Tag currentTag = BukkitMain.getInstance().getTagManager().getTag(event.getPlayer());
				if (currentTag == null)
					currentTag = Tag.MEMBRO;
				b.append(currentTag.getPrefix() + event.getPlayer().getName());
				League league = bP.getLeague();
				b.append(" §7(" + league.getColor() + league.getSymbol() + "§7)");
				for (Player players : event.getRecipients()) {
					if (!getManager().getGamerManager().getGamer(players).isAlive()) {
						players.sendMessage("§7[SPECTATE] " + b.toString() + "§f: " + event.getMessage());
					}
				}
			}
		}
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			player.setSaturation(3.0F);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();
		BukkitPlayer bP = BukkitPlayer.getPlayer(player.getUniqueId());

		getManager().getScoreListener().createScoreboard(player);

		Gamer gamer = getManager().getGamerManager().getGamer(player);

		PermissionAttachment attachment = player.addAttachment(getManager().getPlugin());

		if (bP.getGroup().getId() >= Group.YOUTUBERPLUS.getId()) {
			attachment.getPermissions().put("minecraft.command.enchant", true);
			attachment.getPermissions().put("minecraft.command.effect", true);
		}
		
		if (gamer.getSurpriseKit() != null) {
			if (getManager().isDoubleKit()) {
				attachment.getPermissions().put("hgkit." + gamer.getSurpriseKit().getName().toLowerCase(), true);
				attachment.getPermissions().put("hgkit2." + gamer.getSurpriseKit().getName().toLowerCase(), true);
			} else {
				attachment.getPermissions().put("hgkit." + gamer.getSurpriseKit().getName().toLowerCase(), true);
			}
		}

		if (getManager().getGameManager().isPreGame()) {

			event.getPlayer().getInventory().clear();

			getManager().getGamerManager().givePreGameItems(player);
			player.setGameMode(GameMode.SURVIVAL);

			player.setHealth(20.0);
			player.setFoodLevel(20);
			player.getInventory().setArmorContents(null);
			player.setFireTicks(0);
			player.setFoodLevel(20);
			player.setFlying(false);
			player.setAllowFlight(false);
			player.setSaturation(3.2F);
			player.getActivePotionEffects().clear();

			player.sendMessage(" ");
			player.sendMessage("§6§lWEAVEN§f§lHG");
			player.sendMessage(" ");
			player.sendMessage("§fEscolha seu kit clicando no §e§lBAU§f da sua §e§lMAO");
			player.sendMessage(" ");
			player.sendMessage("§9§lTENHA UM BOM JOGO!");

			getManager().getGamerManager().teleportSpawn(player);
			getManager().getGamerManager().getGamer(player).setMode(GamerMode.ALIVE);

			MessagesConstructor.sendTitleMessage(player, "§6§lWeaven§f§lHG", "§7Seja o último player de pé");

		} else {

			if (gamer.getMode() == GamerMode.DEAD) {
				if (!bP.hasGroupPermission(Group.LIGHT)) {
					player.kickPlayer("\n§6§lTORNEIO §fVocê morreu!\n§6www.mc-weaven.com.br");
					return;
				}
			}

			gamer.setOnline(true);

			if (getManager().getGamerManager().getAFKGamers().contains(gamer)) {
				if (relogItems.containsKey(gamer.getUUID())) {
					HashMap<ItemStack[], ItemStack[]> cache = relogItems.get(gamer.getUUID());
					for (Entry<ItemStack[], ItemStack[]> entry : cache.entrySet()) {
						gamer.getPlayer().getInventory().setContents(entry.getKey());
						gamer.getPlayer().getInventory().setArmorContents(entry.getValue());
					}
					gamer.getPlayer().updateInventory();
					if (relogLocation.get(gamer.getUUID()) != null) {
						gamer.getPlayer().teleport(relogLocation.get(gamer.getUUID()));
					}
				}
				return;
			}

			if (!gamer.isAlive()) {
				if (gamer.getMode() == GamerMode.LOADING) {
					if (bP.hasGroupPermission(Group.LIGHT) && getManager().getGameManager().getGameTime() <= 300) {
						gamer.getPlayer().setHealth(20.0);
						gamer.getPlayer().setFoodLevel(20);
						gamer.getPlayer().getActivePotionEffects().clear();
						gamer.getPlayer().setFireTicks(0);
						gamer.getPlayer().setFoodLevel(20);
						gamer.getPlayer().setFlying(false);
						gamer.getPlayer().setAllowFlight(false);
						gamer.getPlayer().setSaturation(3.2F);
						gamer.getPlayer()
								.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 255));
						gamer.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));

						getManager().getGamerManager().setRespawn(gamer);
						if (!bP.isStaffer())
							Bukkit.broadcastMessage("§e" + gamer.getNick() + " entrou no servidor");
					} else {
						event.setJoinMessage(null);
						getManager().getGamerManager().setSpectator(gamer);
					}
				} else {
					if (gamer.isAlive()) {
						if (!bP.isStaffer()) {
							Bukkit.broadcastMessage("§e" + gamer.getNick() + " entrou no servidor");
						}
					} else {
						getManager().getGamerManager().setSpectator(gamer);
					}
				}
			}

		}
		getManager().getGamerManager().hideSpecs(player);

		Bukkit.getScheduler().runTaskLater(getManager().getPlugin(), () -> {
			if (gamer.isWinner()) {
				BukkitMain.getInstance().getTagManager().setTag(player, Tag.WINNER);
			}
		}, 10L);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onPlayerLogin(PlayerLoginEvent event) {
		if (event.getResult() != org.bukkit.event.player.PlayerLoginEvent.Result.ALLOWED)
			return;
		Player player = event.getPlayer();
		if (player == null)
			return;
		Gamer gamer = getManager().getGamerManager().getGamer(player);
		if (gamer == null) {
			player.kickPlayer("§4§lERRO§f Ocorreu um erro ao tentar carregar sua conta!F");
			return;
		}
		BukkitPlayer bP = BukkitPlayer.getPlayer(gamer.getUUID());

		if (bP == null) {
			player.kickPlayer("§4§lERRO§f Ocorreu um erro ao tentar carregar sua conta");
			return;
		}

		if (getManager().getGamerManager().isEndgame()) {
			event.disallow(Result.KICK_OTHER,
					"\n§6§lTORNEIO §fA partida acabou!\n§fO servidor está reiniciando!!\n§6www.mc-weaven.com.br");
		}
		if (!getManager().getGamerManager().getAFKGamers().contains(gamer)) {
			if (getManager().getGameManager().isInvencibility()
					|| (getManager().getGameManager().isGame() && getManager().getGameManager().getGameTime() <= 300))
				if (!bP.hasGroupPermission(Group.LIGHT) && !gamer.isWinner())
					event.disallow(Result.KICK_OTHER,
							"\n§6§lTORNEIO §fO torneio já iniciou!\n§fAdquira §e§lVIP§f para poder entrar após o inicio!\n§6www.mc-weaven.com.br");

			if (getManager().getGameManager().isGame())
				if (!bP.hasGroupPermission(Group.LIGHT) && !gamer.isWinner())
					event.disallow(Result.KICK_OTHER,
							"\n§6§lTORNEIO §fO torneio já iniciou!\n§fAdquira §e§lVIP§f para poder espectar!\n§6www.mc-weaven.com.br");
		}
	}

	@EventHandler
	public void onPortal(PlayerPortalEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.getCause() == TeleportCause.NETHER_PORTAL || event.getCause() == TeleportCause.END_PORTAL)
			event.setCancelled(true);
	}

	private HashMap<UUID, HashMap<ItemStack[], ItemStack[]>> relogItems = new HashMap<>();
	private HashMap<UUID, Location> relogLocation = new HashMap<>();

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Gamer gamer = getManager().getGamerManager().getGamer(event.getPlayer());
		BukkitPlayer bP = BukkitPlayer.getPlayer(gamer.getUUID());

		if (getManager().getGameManager().isGame()) {
			if (gamer.isAlive()) {
				if (gamer.isFighting()) {
					List<ItemStack> drop = new ArrayList<>();
					drop.addAll(Arrays.asList(event.getPlayer().getInventory().getContents()));
					drop.addAll(Arrays.asList(event.getPlayer().getInventory().getArmorContents()));

					for (ItemStack i : drop) {
						if (i == null)
							continue;

						if (i.getType() != Material.AIR)
							if (getManager().getKitManager().isItemKit(i))
								event.getPlayer().getWorld()
										.dropItemNaturally(event.getPlayer().getLocation().clone().add(0, 0.5, 0), i);

					}

					gamer.setMode(GamerMode.DEAD);
					getManager().getGamerManager().setDied(gamer);

					Bukkit.broadcastMessage("§e" + gamer.getNick() + " saiu do servidor");
					Bukkit.broadcastMessage("§e" + gamer.getPlayer().getName() + "[" + gamer.getKit().getName()
							+ "] deslogou em combate. §4[" + getManager().getGamerManager().getAlivePlayers().size()
							+ "]");
				} else {
					HashMap<ItemStack[], ItemStack[]> items = new HashMap<>();
					items.put(gamer.getPlayer().getInventory().getContents(),
							gamer.getPlayer().getInventory().getArmorContents());
					relogItems.put(gamer.getUUID(), items);
					relogLocation.put(gamer.getUUID(), gamer.getPlayer().getLocation());
					if (!bP.isStaffer())
						Bukkit.broadcastMessage("§e" + gamer.getNick() + " saiu do servidor");
					getManager().getGamerManager().getAFKGamers().add(gamer);
					gamer.setOnline(false);

					new BukkitRunnable() {
						public void run() {
							if (!gamer.isOnline() && gamer.isAlive()) {
								getManager().getGamerManager().getAFKGamers().remove(gamer);
								getManager().getGamerManager().setDied(gamer);
								getManager().getGamerManager().checkWinner();

								if (!bP.isStaffer()) {
									Bukkit.broadcastMessage("§e" + gamer.getPlayer().getName() + "["
											+ gamer.getKit().getName() + "] deslogou e não voltou. §4["
											+ getManager().getGamerManager().getAlivePlayers().size() + "]");
								}
							}
						}
					}.runTaskLater(getManager().getPlugin(), 20L * 300);
				}
			}
		} else if (!getManager().getGameManager().isPreGame()) {
			if (gamer.isAlive()) {
				HashMap<ItemStack[], ItemStack[]> items = new HashMap<>();
				items.put(gamer.getPlayer().getInventory().getContents(),
						gamer.getPlayer().getInventory().getArmorContents());
				relogItems.put(gamer.getUUID(), items);
				relogLocation.put(gamer.getUUID(), gamer.getPlayer().getLocation());
				if (!bP.isStaffer())
					Bukkit.broadcastMessage("§e" + gamer.getNick() + " saiu do servidor");
				getManager().getGamerManager().getAFKGamers().add(gamer);
				gamer.setOnline(false);

				new BukkitRunnable() {

					public void run() {
						if (!gamer.isOnline()) {
							Bukkit.broadcastMessage("§e" + gamer.getPlayer().getName() + "[" + gamer.getKit().getName()
									+ "] deslogou e não voltou. §4["
									+ getManager().getGamerManager().getAlivePlayers().size() + "]");
							getManager().getGamerManager().getAFKGamers().remove(gamer);
							getManager().getGamerManager().setDied(gamer);
							getManager().getGamerManager().checkWinner();
						} else {
							getManager().getGamerManager().getAFKGamers().remove(gamer);
						}
					}

				}.runTaskLater(getManager().getPlugin(), 20L * 300);
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (getManager().getKitManager().isItemKit(event.getItemDrop().getItemStack())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("§e§lDROP §fVocê não pode §6§lDROPAR§f este item no chão!");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (!getManager().getGameManager().getDamage().isActive()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBucket(PlayerBucketEmptyEvent event) {
		if (!getManager().getGameManager().getBuild().isActive()) {
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
				return;
			}
			BukkitPlayer bP = BukkitPlayer.getPlayer(event.getPlayer().getUniqueId());
			if (bP.getGroup().getId() >= Group.YOUTUBERPLUS.getId()) {
				return;
			}
			if (getManager().getGamerManager().getGamer(event.getPlayer()).isSpectating()) {
				event.setCancelled(true);
				return;
			}
			Material bucket = event.getBucket();
			if (bucket.toString().contains("LAVA")) {
				event.setCancelled(true);
			} else if (bucket.toString().contains("WATER")) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		BukkitPlayer bP = BukkitPlayer.getPlayer(event.getPlayer().getUniqueId());
		if (!getManager().getGameManager().getBuild().isActive()) {
			if (!getManager().getGameManager().getBuild().isActive()) {
				if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
					event.setCancelled(true);
					return;
				}
				if (bP.getGroup().getId() < Group.YOUTUBERPLUS.getId()) {
					event.setCancelled(true);
				}
			}
		}
		if (getManager().getGamerManager().getGamer(event.getPlayer()).isSpectating()
				&& bP.getGroup().getId() < Group.YOUTUBERPLUS.getId()) {
			event.setCancelled(true);
			return;
		}
		if (getManager().getKitManager().isItemKit(event.getItemInHand())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage("§e§lDROP §fVocê não pode §6§lCOLOCAR§f este item no chão!");
		}
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		if (event.getLeaveMessage().toLowerCase().contains("you logged in from another location")) {
			event.setCancelled(true);
		} else if (event.getLeaveMessage().toLowerCase().contains("flying is not enabled on this server")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {

		int border = getManager().getGameManager().getTimer().getBorderSize();

		Iterator<Block> blocks = event.blockList().iterator();
		while (blocks.hasNext()) {
			Block block = blocks.next();
			if (block.getLocation().getBlockX() == border || block.getLocation().getBlockZ() == border
					|| block.getLocation().getBlockX() == -border || block.getLocation().getBlockZ() == -border) {
				blocks.remove();
			}
			for (FutureBlock futureBlock : getManager().getGameManager().getTimer().getFinalArena().getBlocks()) {
				if (futureBlock.getLocation().equals(block.getLocation())) {
					blocks.remove();
				}
			}
		}
	}

	@EventHandler
	public void onBurn(BlockBurnEvent event) {
		for (FutureBlock futureBlock : getManager().getGameManager().getTimer().getFinalArena().getBlocks()) {
			if (futureBlock.getLocation().equals(event.getBlock().getLocation())) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBreakBlock(BlockDamageEvent event) {
		int border = getManager().getGameManager().getTimer().getBorderSize();
		Block block = event.getBlock();
		if (block.getLocation().getBlockX() == border || block.getLocation().getBlockZ() == border
				|| block.getLocation().getBlockX() == -border || block.getLocation().getBlockZ() == -border) {
			block.setType(Material.GLASS);
		}
	}

	@EventHandler
	public void onPlayerAdminEnterEvent(PlayerAdminModeEnterEvent event) {
		Player player = event.getPlayer();
		Gamer gamer = getManager().getGamerManager().getGamer(player);
		if (relogItems.containsKey(player.getUniqueId()) || relogLocation.containsKey(player.getUniqueId())) {
			event.setCancelled(true);
			relogItems.remove(gamer.getUUID());
			relogLocation.remove(gamer.getUUID());
			return;
		}
		if (getManager().getGameManager().isPreGame()) {
			player.getInventory().clear();
			player.updateInventory();
			getManager().getGamerManager().setDied(gamer);
		} else {
			if (gamer.isAlive()) {
				getManager().getGamerManager().setDied(gamer);
				getManager().getGamerManager().checkWinner();
			}

			player.getInventory().clear();

			player.updateInventory();
		}
	}

	@EventHandler
	public void onPlayerAdminQuitEvent(PlayerAdminModeQuitEvent event) {
		Player player = event.getPlayer();
		Gamer gamer = getManager().getGamerManager().getGamer(player);
		if (getManager().getGameManager().isPreGame()) {
			player.getInventory().clear();
			player.updateInventory();
			gamer.setMode(GamerMode.ALIVE);
			getManager().getGamerManager().givePreGameItems(player);
			gamer.setItemsGive(true);
		} else {
			if (gamer.isAlive()) {
				getManager().getGamerManager().setDied(gamer);
				getManager().getGamerManager().checkWinner();
			}

			player.getInventory().clear();
			CacheItems.SPEC.build(player);

			player.updateInventory();
		}
	}

	@EventHandler
	public void aoMap(MapInitializeEvent event) {
		if (getManager().getGameManager().getGameStage() == GameStage.WINNING) {
			event.getMap().getRenderers().clear();
			for (MapRenderer r : event.getMap().getRenderers()) {
				event.getMap().removeRenderer(r);
			}
			event.getMap().addRenderer(new MapRenderer() {
				public void render(MapView mapa, MapCanvas canvas, Player player) {
					canvas.drawText(17, 10, MinecraftFont.Font, "Parabéns, " + player.getName());
					canvas.drawText(9, 20, MinecraftFont.Font, "Você ganhou o torneio!");

					canvas.drawImage(30, 60,
							new ImageIcon(getManager().getFileManager().getServerImageFile().getPath()).getImage());

				}
			});
		}
	}

	@EventHandler
	public void onBrakBlock(BlockBreakEvent event) {
		BukkitPlayer bP = BukkitPlayer.getPlayer(event.getPlayer().getUniqueId());
		if (!getManager().getGameManager().getBuild().isActive()) {
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
				return;
			}
			if (bP.getGroup().getId() < Group.YOUTUBERPLUS.getId()) {
				event.setCancelled(true);
				return;
			}
		}

		if (getManager().getGamerManager().getGamer(event.getPlayer()).isSpectating()
				&& bP.getGroup().getId() < Group.YOUTUBERPLUS.getId()) {
			event.setCancelled(true);
			return;
		}

		for (FutureBlock futureBlock : getManager().getGameManager().getTimer().getFinalArena().getBlocks()) {
			if (futureBlock.getLocation().equals(event.getBlock().getLocation())) {
				event.setCancelled(true);
			}
		}

		if (event.getBlock().getLocation().getY() > 128)
			event.setCancelled(true);

		int border = getManager().getGameManager().getTimer().getBorderSize();

		Block block = event.getBlock();

		if (block.getType() == Material.COCOA || block.getType() == Material.getMaterial(351)) {
			event.setCancelled(true);
			block.setType(Material.AIR);
			event.getPlayer().getInventory().addItem(new ItemStack(Material.getMaterial(351), 4, (byte) 3));
			return;
		}

		if (!getManager().getGameManager().isPreGame()) {
			if (block.getType() != Material.BEDROCK) {

				Player player = event.getPlayer();
				ArrayList<ItemStack> items = new ArrayList<>(event.getBlock().getDrops());

				if (player.getInventory().firstEmpty() != -1) {
					for (ItemStack item : items) {

						float totalExp = player.getExp();
						int newExp = (int) (totalExp + event.getExpToDrop());

						// player.setLevel(0);
						// player.setExp(0);

						player.giveExp(newExp);
						player.getInventory().addItem(item);
					}

					event.setCancelled(true);

					if (event.getBlock().getType().equals(Material.getMaterial(115))
							|| event.getBlock().getType() == (Material.getMaterial(115))) {
						player.getInventory().addItem(new ItemStack(Material.NETHER_WARTS));
						player.getInventory().addItem(new ItemStack(event.getBlock().getType()));
						player.getInventory().addItem(new ItemStack(event.getBlock().getTypeId()));
						player.getInventory().addItem(new ItemStack(115));
						player.getInventory().addItem(new ItemStack(372));
					}
					event.getBlock().setType(Material.AIR);

				} else {
					for (ItemStack item : event.getBlock().getDrops()) {

						for (int i = 0; i < 35; i++) {
							if (player.getInventory().getItem(i).getAmount() + item.getAmount() <= 64) {
								if (player.getInventory().getItem(i).getType().equals(item.getType())) {
									player.getInventory().addItem(item);

									event.setCancelled(true);
									event.getBlock().setType(Material.AIR);
									break;
								}
							}
						}
					}
				}
			}
		}

		if (block.getLocation().getBlockX() == border || block.getLocation().getBlockZ() == border
				|| block.getLocation().getBlockX() == -border || block.getLocation().getBlockZ() == -border) {
			event.setCancelled(true);
		}

		if (getManager().getGameManager().getTimer().getFeast() != null) {
			for (FutureBlock ftb : getManager().getGameManager().getTimer().getFeast().getBlocks()) {
				if (block.getLocation().getBlockX() == ftb.getLocation().getBlockX()
						&& block.getLocation().getBlockY() == ftb.getLocation().getBlockY()
						&& block.getLocation().getBlockZ() == ftb.getLocation().getBlockZ()) {
					event.setCancelled(true);
				}
			}

			if (getManager().getGameManager().getTimer().getFeast().getBlockData().contains(block.getLocation())) {
				event.setCancelled(true);
			}
		}
	}

}
