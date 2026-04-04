package br.com.weavenmc.commons.bukkit.account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.bukkit.api.item.ItemBuilder;
import br.com.weavenmc.commons.core.account.League;
import br.com.weavenmc.commons.core.clan.Clan;
import br.com.weavenmc.commons.core.data.player.type.DataType;
import br.com.weavenmc.commons.core.data.punish.AccountBan;
import br.com.weavenmc.commons.core.data.punish.Mute;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.util.string.StringTimeUtils;

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

public class AccountMenu implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClickListener(InventoryClickEvent event) {
		if (event.getInventory().getName().startsWith("§7Conta")) {
			event.setCancelled(true);
		}
	}

	protected String getTimerFormat(long millis) {
		long days = TimeUnit.DAYS.convert(millis, TimeUnit.MILLISECONDS);
		millis -= TimeUnit.DAYS.toMillis(days);

		long hours = TimeUnit.HOURS.convert(millis, TimeUnit.MILLISECONDS);
		millis -= TimeUnit.HOURS.toMillis(hours);

		long minutes = TimeUnit.MINUTES.convert(millis, TimeUnit.MILLISECONDS);
		millis -= TimeUnit.MINUTES.toMillis(minutes);

		long seconds = TimeUnit.SECONDS.convert(millis, TimeUnit.MILLISECONDS);

		StringBuilder sb = new StringBuilder();
		if (days != 0L)
			sb.append(days + (days == 1 ? " dia, " : " dias, "));
		if (hours != 0L && hours != 0)
			sb.append(hours + (hours == 1 ? " hora, " : " horas, "));
		if (minutes != 0L && minutes != 0)
			sb.append(minutes + (minutes == 1 ? " minuto, " : " minutos, "));
		if (seconds != 0L && seconds != 0)
			sb.append(seconds + (seconds == 1 ? " segundo" : " segundos"));

		return sb.toString().endsWith(", ") ? sb.toString().substring(0, sb.toString().length() - 2) : sb.toString();
	}

	public static void open(Player player, BukkitPlayer opener, BukkitPlayer bP) {
		Inventory menu = Bukkit.createInventory(player, 54, "§7Conta - " + bP.getName());

		ItemBuilder builder = new ItemBuilder().type(Material.SKULL_ITEM).durability(3).name("§7" + bP.getName());

		if (opener.getUniqueId() == bP.getUniqueId()) {
			builder.lore("§7Senha: " + bP.getData(DataType.PASSWORD));
		}

		ItemStack stack = builder.build();
		SkullMeta meta = (SkullMeta) stack.getItemMeta();
		meta.setOwner(bP.getName());
		stack.setItemMeta(meta);
		menu.setItem(13, stack);
		String clanName = bP.getClanName();
		if (!clanName.equals("Nenhum")) {
			Clan clan = WeavenMC.getClanCommon().getClanFromName(clanName);
			// if (clan == null)
			// clan = DataClan.getClanByName(clanName);
			if (clan != null) {
				int xp = clan.getXp();
				stack = new ItemBuilder().type(Material.BLAZE_POWDER).name("§6§lClan")
						.lore("§4§lNOME: §f" + clanName, "§8§lTAG: §f" + clan.getAbbreviation(), "§e§lXP: §f" + xp,
								"§3§lLIGA: " + "-", "§4§lDONO: §f" + clan.getOwnerName(),
								"§c§lADMINISTRADORES: §f" + clan.getAdminsNamesList().toString(),
								"§f§lPARTICIPANTES: §f" + clan.getMembersNamesList().toString())
						.build();
			} else {
				stack = new ItemBuilder().type(Material.BLAZE_POWDER).name("§6§lClan")
						.lore("§7Este jogador nao possui um Clan").build();
			}
		} else {
			stack = new ItemBuilder().type(Material.BLAZE_POWDER).name("§6§lClan")
					.lore("§7Este jogador nao possui um Clan").build();
		}
		menu.setItem(19, stack);
		stack = new ItemBuilder().type(Material.GOLD_INGOT).name("§6§lMoedas").lore("§6" + bP.getMoney()).build();
		menu.setItem(21, stack);
		stack = new ItemBuilder().type(Material.PAPER).name("§b§lTickets").lore("§b" + bP.getTickets()).build();
		menu.setItem(22, stack);
		stack = new ItemBuilder().type(Material.EXP_BOTTLE).name("§c§lXp")
				.lore("§c" + bP.getXp(), "§3§lDoubleXp", "§3" + bP.getDoubleXpMultiplier()).build();
		menu.setItem(23, stack);
		builder = new ItemBuilder().type(Material.WATCH).name("§9§lInformaçoes de login");
		Date date = new Date(bP.getFirstLoggedIn());
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		List<String> loreBuilder = new ArrayList<>();
		loreBuilder.add("§7Primeiro login: §f" + df.format(date));
		date = new Date(bP.getLastLoggedIn());
		loreBuilder.add("§7Último login: §f" + df.format(date));
		builder.lore(loreBuilder);
		menu.setItem(25, builder.build());
		builder = new ItemBuilder().type(Material.NAME_TAG).name("§6§lLigas");
		loreBuilder = new ArrayList<>();
		for (int i = League.values().length; i > 0; i--) {
			League league = League.values()[i - 1];
			String str = league.getColor() + league.getSymbol() + " " + league.toString();
			String seta = "§7";
			if (bP.getLeague() == league) {
				seta = "§6";
				loreBuilder.add(seta + "§l" + "➟ " + str);
			} else {
				loreBuilder.add(seta + "§l" + "➟ " + str);
			}
		}
		builder.lore(loreBuilder);
		menu.setItem(31, builder.build());
		if (opener.getUniqueId() == bP.getUniqueId()) {
			menu.setItem(47, new ItemBuilder().type(Material.REDSTONE_COMPARATOR).name("§e§lPreferencias")
					.lore("§7Menu indisponivel no momento").build());
		}
		builder = new ItemBuilder().type(Material.COMPASS).name("§8§lLocalizaçao");
		if (!opener.getUniqueId().equals(bP.getUniqueId()) && !opener.hasGroupPermission(Group.DONO)) {
			loreBuilder = new ArrayList<>();
			loreBuilder.add("§7Esta informaçao nao esta mais disponivel publicamente.");
			menu.setItem(46, builder.lore(loreBuilder).build());
		} else {
			loreBuilder = new ArrayList<>();
			loreBuilder.add("§7Cidade localizado: §f" + bP.getData(DataType.CITY).asString());
			loreBuilder.add("§7País de origem: §f" + bP.getData(DataType.COUNTRY).asString());
			menu.setItem(46, builder.lore(loreBuilder).build());
		}
		builder = new ItemBuilder().type(Material.EYE_OF_ENDER).name("§6§lRanks");
		loreBuilder = new ArrayList<>();
		for (HashMap<Group, Long> map : bP.getRanks()) {
			for (Entry<Group, Long> entrie : map.entrySet()) {
				Group group = entrie.getKey();
				Long time = entrie.getValue();
				loreBuilder.add(group.getTagToUse().getName().replace("§l", "")
						+ (time == -1 ? " §f- §f§nETERNO" : " §f- §f§n" + StringTimeUtils.formatDifference(time)));
			}
		}
		menu.setItem(49, builder.lore(loreBuilder).build());
		AccountBan ban = WeavenMC.getPunishHistory().getAccountBan(bP.getUniqueId());
		if (ban != null) {
			if (!ban.hasExpired()) {
				menu.setItem(51,
						new ItemBuilder()
								.type(Material.BARRIER).name(
										"§c§lBanimentos")
								.lore("§7Banido por: §f" + ban.getAuthor(), "§7Data: §f" + ban.toDateApplied(),
										"§7Motivo: §f" + ban.getReason(),
										"§7Duração do banimento: §f" + (ban.isPermanent() ? "Permanente"
												: StringTimeUtils.formatDifference(ban.getDuration())))
								.build());
			} else {
				menu.setItem(51, new ItemBuilder().type(Material.BARRIER).name("§c§lBanimentos")
						.lore("§7Este jogador não está banido.").build());
			}
		} else {
			menu.setItem(51, new ItemBuilder().type(Material.BARRIER).name("§c§lBanimentos")
					.lore("§7Este jogador nao está banido.").build());
		}
		Mute mute = WeavenMC.getPunishHistory().getMute(bP.getUniqueId());
		if (mute != null) {
			if (!mute.hasExpired()) {
				menu.setItem(52,
						new ItemBuilder()
								.type(Material.BOOK_AND_QUILL).name(
										"§9§lMutes")
								.lore("§7Mutado por: §f" + mute.getMutedBy(), "§7Data: §f" + mute.toDateApplied(),
										"§7Motivo: §f" + mute.getReason(),
										"§7Duraço do mute: §f" + (mute.getMuteTime() == -1 ? "Permanente"
												: StringTimeUtils.formatDifference(mute.getMuteTime())))
								.build());
			} else {
				menu.setItem(52, new ItemBuilder().type(Material.BOOK_AND_QUILL).name("§9§lMutes")
						.lore("§7Este jogador nao está mutado").build());
			}
		} else {
			menu.setItem(52, new ItemBuilder().type(Material.BOOK_AND_QUILL).name("§9§lMutes")
					.lore("§7Este jogador nao está mutado").build());
		}
		if (opener.getUniqueId().equals(bP.getUniqueId())) {
			menu.setItem(8,
					new ItemBuilder().type(Material.NETHER_STAR).name("§e§lInformações do IP")
							.lore("§7Endereço de IP atual: §f" + bP.getIpAddress(),
									"§7Último endereço de IP: §f" + bP.getLastIpAddress())
							.build());
		} else {
			menu.setItem(8,
					new ItemBuilder().type(Material.NETHER_STAR).name("§e§lInformações do IP")
							.lore("§7Esta informaçao é privada.")
							.build());
		}
		menu.setItem(17,
				new ItemBuilder().type(Material.BOOKSHELF).name("§3§lContas sociais")
						.lore("§7Canal do YouTube: §f" + bP.getYoutubeChannel(), "§7Twitter: §f" + bP.getTwitter(),
								"§7Skype: §f" + bP.getSkype(), "§7Discord: §f" + bP.getDiscord(),
								"§7Twitch: §f" + bP.getTwitch())
						.build());
		ItemStack nullItem = new ItemBuilder().type(Material.STAINED_GLASS_PANE).durability(15).name(" ").build();
		for (int j = 0; j < menu.getSize(); j++) {
			if (menu.getItem(j) == null)
				menu.setItem(j, nullItem);
		}
		player.openInventory(menu);
	}
}
