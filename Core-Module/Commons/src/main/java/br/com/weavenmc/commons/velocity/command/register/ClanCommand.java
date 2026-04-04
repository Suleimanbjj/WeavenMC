package br.com.weavenmc.commons.velocity.command.register;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.clan.Clan;
import br.com.weavenmc.commons.core.clan.ClanMessage;
import br.com.weavenmc.commons.core.clan.ClanMessage.MessageType;
import br.com.weavenmc.commons.core.clan.ClanRank;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.profile.Profile;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;
import com.velocitypowered.api.proxy.Player;

public class ClanCommand implements CommandClass {

	private static final int CREATE_COST = 5000;
	private static final int CHANGE_ABB_COST = 3000;
	private static final long INVITE_DURATION = 60_000L;
	private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,16}$");
	private static final Pattern ABB_PATTERN = Pattern.compile("^[A-Za-z0-9_]{2,6}$");

	private final Map<String, Map<String, Long>> invites = new ConcurrentHashMap<>();

	@Command(name = "clan", aliases = { "gang" }, runAsync = true)
	public void clan(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}

		Player player = sender.getPlayer();
		ProxyPlayer account = ProxyPlayer.getPlayer(player.getUniqueId());
		if (account == null) {
			player.disconnect(VelocityText.of("§4§lERRO§f Sua conta está com problemas!"));
			return;
		}
		Clan clan = WeavenMC.getClanCommon().getClanFromName(account.getClanName());

		if (args.length == 0) {
			sendClanInfo(sender, clan);
			return;
		}

		String sub = args[0].toLowerCase();
		switch (sub) {
		case "help":
			sendHelp(sender);
			return;
		case "info":
			sendClanInfo(sender, args.length >= 2 ? WeavenMC.getClanCommon().getClanFromName(args[1]) : clan);
			return;
		case "list":
			listClans(sender);
			return;
		case "chat":
		case "c":
			toggleClanChat(sender, account, clan);
			return;
		case "leave":
			leaveClan(sender, player, account, clan);
			return;
		case "disband":
		case "deletar":
			disbandClan(sender, player, clan);
			return;
		case "create":
			createClan(sender, player, account, args);
			return;
		case "join":
			joinClan(sender, player, account, args);
			return;
		case "invite":
			invitePlayer(sender, player, clan, args);
			return;
		case "removeinvite":
			removeInvite(sender, player, clan, args);
			return;
		case "kick":
			kickPlayer(sender, player, clan, args);
			return;
		case "promote":
		case "promover":
			promotePlayer(sender, player, clan, args);
			return;
		case "demote":
		case "rebaixar":
			demotePlayer(sender, player, clan, args);
			return;
		case "changeabb":
			changeAbbreviation(sender, player, account, clan, args);
			return;
		default:
			sendHelp(sender);
		}
	}

	private void sendClanInfo(VelocityCommandSender sender, Clan clan) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan, para criar ou mais informações digite §c§l/clan help");
			return;
		}
		ClanRank rank = ClanRank.fromXp(clan.getXp());
		sender.sendMessage("§f======= §4§l" + clan.getName() + " §f[§8§l" + clan.getAbbreviation() + "§f] =======");
		sender.sendMessage("");
		sender.sendMessage("§e§lXP§f: " + clan.getXp());
		sender.sendMessage("§3§lLIGA " + rank.getColor() + rank.name());
		sender.sendMessage("");
		sender.sendMessage("§4§lDONO: " + color(clan.getOwnerName()));
		sender.sendMessage("§c§lADMINISTRADORES: "
				+ (clan.getAdminsSize() > 0 ? String.join("§f, ", colorList(clan.getAdminsNamesList()))
						: "§7Este Clan não possui administradores"));
		sender.sendMessage("§f§lPARTICIPANTES: "
				+ (clan.getMembersSize() > 0 ? String.join("§f, ", colorList(clan.getMembersNamesList()))
						: "§7Este Clan não possui participantes"));
		sender.sendMessage("");
		sender.sendMessage("§1§lSLOT: §f(" + clan.getActualSlot() + "/" + clan.getMaxSlot() + ")");
		sender.sendMessage("");
		sender.sendMessage("§fPara mais informações digite: §c§l/clan help");
	}

	private void sendHelp(VelocityCommandSender sender) {
		sender.sendMessage("§3§l/clan create §f<Nome> <Sigla> - Cria um clan por §6§l5000 MOEDAS");
		sender.sendMessage("§3§l/clan join §f<Nome> - Entra em um clan com convite ativo");
		sender.sendMessage("§3§l/clan invite §f<player> - Convida jogador para o clan");
		sender.sendMessage("§3§l/clan removeinvite §f<player> - Remove convite do jogador");
		sender.sendMessage("§3§l/clan kick §f<player> - Remove jogador do clan");
		sender.sendMessage("§3§l/clan promote §f<player> - Promove participante para admin");
		sender.sendMessage("§3§l/clan demote §f<player> - Rebaixa admin para participante");
		sender.sendMessage("§3§l/clan changeabb §f<Sigla> - Troca a sigla por §6§l3000 MOEDAS");
		sender.sendMessage("§3§l/clan disband §f- Deleta o clan");
		sender.sendMessage("§3§l/clan leave §f- Sai do clan");
		sender.sendMessage("§3§l/clan chat §f- Alterna o chat de clan");
		sender.sendMessage("§3§l/clan info §f[Nome] - Informações do clan");
		sender.sendMessage("§3§l/clan list §f- Lista os clans");
	}

	private void listClans(VelocityCommandSender sender) {
		Collection<Clan> clans = WeavenMC.getClanCommon().getClans();
		if (clans.isEmpty()) {
			sender.sendMessage("§4§lCLANS§f Nenhum Clan foi encontrado!");
			return;
		}
		for (Clan c : clans) {
			ClanRank r = ClanRank.fromXp(c.getXp());
			sender.sendMessage("§f[§4§l" + c.getName() + "§f] §9- §f[§8§l" + c.getAbbreviation() + "§f] §9- §bXp: §f"
					+ c.getXp() + " §9- §bRank: " + r.getColor() + r.name());
		}
	}

	private void toggleClanChat(VelocityCommandSender sender, ProxyPlayer account, Clan clan) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan.");
			return;
		}
		account.setClanChatEnabled(!account.isClanChatEnabled());
		sender.sendMessage(account.isClanChatEnabled() ? "§4§lCLAN§f Você §a§lATIVOU§f o chat do Clan"
				: "§4§lCLAN§f Você §c§lDESATIVOU§f o chat do Clan");
	}

	private void leaveClan(VelocityCommandSender sender, Player player, ProxyPlayer account, Clan clan) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan.");
			return;
		}
		if (clan.isOwner(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você é o §4§lDONO§f do Clan e não pode sair, digite §c§l/clan disband");
			return;
		}
		clan.kickMember(player.getUniqueId());
		WeavenMC.getClanCommon().updateClan(clan);
		account.setClanChatEnabled(false);
		account.setClanName("Nenhum");
		account.save(DataCategory.ACCOUNT);
		publishClanMessage(new ClanMessage().writeClanName(clan.getName()).writeClanTag(clan.getAbbreviation())
				.writeResponse(sender.getName()).writeUserToLeave(sender.getName()).writeType(MessageType.LEAVE));
		sendClanMessage(clan.getName(),
				"§4§l[" + clan.getName() + "] §c§l[LEAVE] §fO jogador §c" + sender.getName() + "§f saiu do Clan!");
		sender.sendMessage("§4§lCLAN§f Você saiu do Clan " + clan.getName() + ".");
	}

	private void disbandClan(VelocityCommandSender sender, Player player, Clan clan) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan.");
			return;
		}
		if (!clan.isOwner(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser o §4§lDONO§f do Clan para §c§lDELETA-LO");
			return;
		}
		if (!WeavenMC.getClanCommon().deletClan(clan)) {
			sender.sendMessage("§4§lCLAN§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
			return;
		}
		for (Player o : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer online = ProxyPlayer.getPlayer(o.getUniqueId());
			if (online == null || !online.getClanName().equalsIgnoreCase(clan.getName())) {
				continue;
			}
			o.sendMessage(VelocityText.of("§4§l[" + clan.getName() + "] §c§l[DISBAND] §fO Clan foi §c§lDELETADO§f pelo §4§lDONO"));
			online.setClanName("Nenhum");
			online.save(DataCategory.ACCOUNT);
		}
		publishClanMessage(new ClanMessage().writeResponse(sender.getName()).writeClanName(clan.getName())
				.writeClanTag(clan.getAbbreviation()).writeType(MessageType.DISBAND));
		WeavenMC.getClanCommon().unloadClan(clan.getName());
		sender.sendMessage("§4§lCLAN§f O Clan " + clan.getName() + " foi deletado com §a§lSUCESSO");
	}

	private void createClan(VelocityCommandSender sender, Player player, ProxyPlayer account, String[] args) {
		if (!account.getClanName().equalsIgnoreCase("Nenhum")) {
			sender.sendMessage("§4§lCLAN§f Você já possui um Clan.");
			return;
		}
		if (args.length < 3) {
			sender.sendMessage("§4§lCLAN§f Utilize: §c§l/clan create <Nome> <Sigla>");
			return;
		}
		String name = args[1];
		String abb = args[2];
		if (!NAME_PATTERN.matcher(name).matches() || !ABB_PATTERN.matcher(abb).matches()) {
			sender.sendMessage("§4§lCLAN§f O nome/sigla do clan é invalido.");
			return;
		}
		if (WeavenMC.getClanCommon().getClanFromName(name) != null
				|| WeavenMC.getClanCommon().getClanFromAbbreviation(abb) != null) {
			sender.sendMessage("§4§lCLAN§f Já existe um Clan com este nome ou sigla.");
			return;
		}
		if (account.getMoney() < CREATE_COST) {
			sender.sendMessage("§4§lCLAN§f Você não possui moedas suficientes para criar um Clan.");
			return;
		}
		Clan clan = new Clan(name, abb, 0, player.getUniqueId(), new HashMap<>(), new HashMap<>());
		clan.setOwnerName(player.getUsername());
		if (!WeavenMC.getClanCommon().createClan(player.getUniqueId(), clan)) {
			sender.sendMessage("§4§lCLAN§f Erro ao tentar criar o Clan.");
			return;
		}
		account.removeMoney(CREATE_COST);
		account.setClanName(name);
		account.save(DataCategory.ACCOUNT, DataCategory.BALANCE);
		publishClanMessage(new ClanMessage().writeResponse(sender.getName()).writeClanName(name).writeClanTag(abb)
				.writeMoneyUsed(CREATE_COST).writeType(MessageType.CREATE));
		sender.sendMessage("§4§lCLAN§f Você criou o Clan " + name + " com a sigla " + abb + ".");
	}

	private void joinClan(VelocityCommandSender sender, Player player, ProxyPlayer account, String[] args) {
		if (!account.getClanName().equalsIgnoreCase("Nenhum")) {
			sender.sendMessage("§4§lCLAN§f Você já possui um Clan.");
			return;
		}
		if (args.length < 2) {
			sender.sendMessage("§4§lCLAN§f Utilize: §c§l/clan join <Nome>");
			return;
		}
		Clan clan = WeavenMC.getClanCommon().getClanFromName(args[1]);
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Este Clan não foi encontrado.");
			return;
		}
		if (clan.isFull()) {
			sender.sendMessage("§4§lCLAN§f Este Clan está lotado.");
			return;
		}
		if (!hasInvite(clan.getName(), player.getUsername())) {
			sender.sendMessage("§4§lCLAN§f Você não possui convite para este Clan.");
			return;
		}
		removeInviteInternal(clan.getName(), player.getUsername());
		clan.joinParticipant(player.getUniqueId(), player.getUsername());
		WeavenMC.getClanCommon().updateClan(clan);
		account.setClanName(clan.getName());
		account.save(DataCategory.ACCOUNT);
		publishClanMessage(new ClanMessage().writeResponse(sender.getName()).writeClanName(clan.getName())
				.writeClanTag(clan.getAbbreviation()).writeUserJoined(sender.getName()).writeType(MessageType.JOIN));
		sendClanMessage(clan.getName(),
				"§4§l[" + clan.getName() + "] §a§l[JOIN] §fO jogador §a" + sender.getName() + "§f entrou no Clan!");
		sender.sendMessage("§4§lCLAN§f Você entrou no Clan " + clan.getName() + ".");
	}

	private void invitePlayer(VelocityCommandSender sender, Player player, Clan clan, String[] args) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan.");
			return;
		}
		if (!clan.isOwner(player.getUniqueId()) && !clan.isAdministrator(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser admin ou dono do Clan.");
			return;
		}
		if (args.length < 2) {
			sender.sendMessage("§4§lCLAN§f Utilize: §c§l/clan invite <player>");
			return;
		}
		Player target = VelocityMain.getInstance().getProxy().getPlayer(args[1]).orElse(null);
		if (target == null) {
			sender.sendMessage("§4§lCLAN§f O jogador não foi encontrado.");
			return;
		}
		ProxyPlayer targetAccount = ProxyPlayer.getPlayer(target.getUniqueId());
		if (targetAccount == null) {
			sender.sendMessage("§4§lCLAN§f O jogador está com problemas na conta.");
			return;
		}
		if (!targetAccount.getClanName().equalsIgnoreCase("Nenhum")) {
			sender.sendMessage("§4§lCLAN§f Este jogador já possui um Clan.");
			return;
		}
		invites.computeIfAbsent(clan.getName().toLowerCase(), k -> new ConcurrentHashMap<>())
				.put(target.getUsername().toLowerCase(), System.currentTimeMillis() + INVITE_DURATION);
		target.sendMessage(VelocityText.of("§4§lCLAN§f Você foi convidado para o Clan §c" + clan.getName()
				+ "§f. Digite §c§l/clan join " + clan.getName() + "§f para entrar."));
		sender.sendMessage("§4§lCLAN§f Convite enviado para " + target.getUsername() + ".");
	}

	private void removeInvite(VelocityCommandSender sender, Player player, Clan clan, String[] args) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan.");
			return;
		}
		if (!clan.isOwner(player.getUniqueId()) && !clan.isAdministrator(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser admin ou dono do Clan.");
			return;
		}
		if (args.length < 2) {
			sender.sendMessage("§4§lCLAN§f Utilize: §c§l/clan removeinvite <player>");
			return;
		}
		if (removeInviteInternal(clan.getName(), args[1])) {
			sender.sendMessage("§4§lCLAN§f Convite removido.");
		} else {
			sender.sendMessage("§4§lCLAN§f Este jogador não possui convite.");
		}
	}

	private void kickPlayer(VelocityCommandSender sender, Player player, Clan clan, String[] args) {
		if (clan == null) {
			sender.sendMessage("§4§lCLAN§f Você não possui nenhum Clan.");
			return;
		}
		if (!clan.isOwner(player.getUniqueId()) && !clan.isAdministrator(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser admin ou dono do Clan.");
			return;
		}
		ProxyPlayer target = findProxyPlayer(args, 1, DataCategory.ACCOUNT);
		if (target == null) {
			sender.sendMessage("§4§lCLAN§f Jogador não encontrado.");
			return;
		}
		if (!clan.isParticipant(target.getUniqueId()) && !clan.isAdministrator(target.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f O jogador não pertence ao seu Clan.");
			return;
		}
		if (clan.isOwner(target.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você não pode expulsar o dono.");
			return;
		}
		clan.kickMember(target.getUniqueId());
		WeavenMC.getClanCommon().updateClan(clan);
		target.setClanName("Nenhum");
		target.save(DataCategory.ACCOUNT);
		publishClanMessage(new ClanMessage().writeResponse(sender.getName()).writeClanName(clan.getName())
				.writeClanTag(clan.getAbbreviation()).writeUserKicked(target.getName()).writeType(MessageType.KICK));
		sendClanMessage(clan.getName(),
				"§4§l[" + clan.getName() + "] §c§l[KICK] §fO jogador §c" + target.getName() + "§f foi expulso do Clan!");
		sender.sendMessage("§4§lCLAN§f O jogador foi removido do Clan.");
	}

	private void promotePlayer(VelocityCommandSender sender, Player player, Clan clan, String[] args) {
		if (clan == null || !clan.isOwner(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser o dono do Clan.");
			return;
		}
		ProxyPlayer target = findProxyPlayer(args, 1, DataCategory.ACCOUNT);
		if (target == null || !clan.isParticipant(target.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f O jogador precisa ser participante do Clan.");
			return;
		}
		clan.removeParticipant(target.getUniqueId());
		clan.addAdministrator(target);
		WeavenMC.getClanCommon().updateClan(clan);
		sendClanMessage(clan.getName(),
				"§4§l[" + clan.getName() + "] §a§l[PROMOTE] §fO jogador §a" + target.getName() + "§f agora é administrador.");
		sender.sendMessage("§4§lCLAN§f O jogador foi promovido.");
	}

	private void demotePlayer(VelocityCommandSender sender, Player player, Clan clan, String[] args) {
		if (clan == null || !clan.isOwner(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser o dono do Clan.");
			return;
		}
		ProxyPlayer target = findProxyPlayer(args, 1, DataCategory.ACCOUNT);
		if (target == null || !clan.isAdministrator(target.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f O jogador precisa ser administrador do Clan.");
			return;
		}
		clan.removeAdministrator(target.getUniqueId());
		clan.addParticipant(target);
		WeavenMC.getClanCommon().updateClan(clan);
		sendClanMessage(clan.getName(),
				"§4§l[" + clan.getName() + "] §e§l[DEMOTE] §fO jogador §e" + target.getName() + "§f voltou a ser participante.");
		sender.sendMessage("§4§lCLAN§f O jogador foi rebaixado.");
	}

	private void changeAbbreviation(VelocityCommandSender sender, Player player, ProxyPlayer account, Clan clan,
			String[] args) {
		if (clan == null || !clan.isOwner(player.getUniqueId())) {
			sender.sendMessage("§4§lCLAN§f Você precisa ser o dono do Clan.");
			return;
		}
		if (args.length < 2) {
			sender.sendMessage("§4§lCLAN§f Utilize: §c§l/clan changeabb <Sigla>");
			return;
		}
		String abb = args[1];
		if (!ABB_PATTERN.matcher(abb).matches()) {
			sender.sendMessage("§4§lCLAN§f Sigla invalida.");
			return;
		}
		Clan exists = WeavenMC.getClanCommon().getClanFromAbbreviation(abb);
		if (exists != null && !exists.getName().equalsIgnoreCase(clan.getName())) {
			sender.sendMessage("§4§lCLAN§f Esta sigla já está em uso.");
			return;
		}
		if (account.getMoney() < CHANGE_ABB_COST) {
			sender.sendMessage("§4§lCLAN§f Você não possui moedas suficientes.");
			return;
		}
		clan.setAbbreviation(abb);
		account.removeMoney(CHANGE_ABB_COST);
		account.save(DataCategory.BALANCE);
		WeavenMC.getClanCommon().updateClan(clan);
		publishClanMessage(new ClanMessage().writeResponse(sender.getName()).writeClanName(clan.getName())
				.writeClanTag(abb).writeMoneyUsed(CHANGE_ABB_COST).writeType(MessageType.CHANGEABB));
		sendClanMessage(clan.getName(),
				"§4§l[" + clan.getName() + "] §b§l[CHANGEABB] §fA nova sigla do clan é §b" + abb + "§f.");
		sender.sendMessage("§4§lCLAN§f A sigla do Clan foi alterada para " + abb + ".");
	}

	private ProxyPlayer findProxyPlayer(String[] args, int index, DataCategory... categories) {
		if (args.length <= index) {
			return null;
		}
		Player online = VelocityMain.getInstance().getProxy().getPlayer(args[index]).orElse(null);
		if (online != null) {
			return ProxyPlayer.getPlayer(online.getUniqueId());
		}
		Profile profile = WeavenMC.getProfileCommon().getProfile(args[index]);
		if (profile == null) {
			return null;
		}
		ProxyPlayer target = new ProxyPlayer(profile.getId(), profile.getName());
		if (categories.length > 0 && !target.load(categories)) {
			return null;
		}
		return target;
	}

	private boolean hasInvite(String clanName, String username) {
		Map<String, Long> clanInvites = invites.get(clanName.toLowerCase());
		if (clanInvites == null) {
			return false;
		}
		Long expiresAt = clanInvites.get(username.toLowerCase());
		if (expiresAt == null) {
			return false;
		}
		if (expiresAt < System.currentTimeMillis()) {
			clanInvites.remove(username.toLowerCase());
			return false;
		}
		return true;
	}

	private boolean removeInviteInternal(String clanName, String username) {
		Map<String, Long> clanInvites = invites.get(clanName.toLowerCase());
		return clanInvites != null && clanInvites.remove(username.toLowerCase()) != null;
	}

	private void publishClanMessage(ClanMessage message) {
		WeavenMC.getCommonRedis().getJedis().publish(WeavenMC.CLAN_FIELD_UPDATE, WeavenMC.getGson().toJson(message));
	}

	private void sendClanMessage(String clanName, String message) {
		for (Player player : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer account = ProxyPlayer.getPlayer(player.getUniqueId());
			if (account == null || !account.getClanName().equalsIgnoreCase(clanName)) {
				continue;
			}
			player.sendMessage(VelocityText.of(message));
		}
	}

	private String color(String name) {
		Player online = VelocityMain.getInstance().getProxy().getPlayer(name).orElse(null);
		return (online == null ? "§7" : "§a") + name;
	}

	private java.util.List<String> colorList(java.util.List<String> names) {
		java.util.List<String> colored = new java.util.ArrayList<>();
		for (String name : names) {
			colored.add(color(name));
		}
		return colored;
	}
}
