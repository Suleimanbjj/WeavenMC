package br.com.weavenmc.commons.velocity.command.register;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.command.CommandFramework.Completer;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.data.punish.AccountBan;
import br.com.weavenmc.commons.core.data.punish.IpBan;
import br.com.weavenmc.commons.core.data.punish.MacBan;
import br.com.weavenmc.commons.core.data.punish.Mute;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.profile.Profile;
import br.com.weavenmc.commons.core.twitter.Twitter;
import br.com.weavenmc.commons.core.twitter.TwitterAccount;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.commons.util.string.StringUtils;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;
import com.velocitypowered.api.proxy.Player;
import twitter4j.TwitterException;
import java.util.ArrayList;
import java.util.List;

public class PunishCommand implements CommandClass {

	@Command(name = "unban", aliases = { "desbanir", "pardon" }, groupToUse = Group.DIRETOR, runAsync = true)
	public void unban(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§3§lUNBAN§f Utililize: §b§l/unban§f <player> <reason ...>");
			return;
		}
		Profile profile = WeavenMC.getProfileCommon().getProfile(args[0]);
		if (profile == null) {
			sender.sendMessage("§3§lUNBAN§f Esta conta não possui nenhum perfil registrado no servidor.");
			return;
		}

		ProxyPlayer pP = new ProxyPlayer(profile.getId(), profile.getName());
		if (!pP.load(DataCategory.CONNECTION)) {
			sender.sendMessage("§3§lUNBAN§f Não foi possível carregar dados da conta banida, tente novamente mais tarde.");
			return;
		}

		boolean atLeastOneBan = false;
		AccountBan ban = WeavenMC.getPunishHistory().getAccountBan(pP.getUniqueId());
		if (ban != null) {
			atLeastOneBan = true;
			WeavenMC.getPunishHistory().unbanAccount(ban);
		}
		if (!pP.getIpAddress().isEmpty()) {
			IpBan ipBan = WeavenMC.getPunishHistory().getIpBan(pP.getIpAddress());
			if (ipBan != null) {
				atLeastOneBan = true;
				WeavenMC.getPunishHistory().unbanIp(ipBan);
			}
		}
		if (!pP.getComputerAddress().isEmpty()) {
			MacBan macBan = WeavenMC.getPunishHistory().getMacBan(pP.getComputerAddress());
			if (macBan != null) {
				atLeastOneBan = true;
				WeavenMC.getPunishHistory().unbanMac(macBan);
			}
		}
		if (!atLeastOneBan) {
			sender.sendMessage("§3§lUNBAN§f O jogador " + pP.getName() + "(" + pP.getUniqueId()
					+ ") não possui nenhum §b§lBANIMENTO ATIVO.");
			return;
		}

		String reason = StringUtils.createArgs(1, args, "Unbanned", false);
		sender.sendMessage("§3§lUNBAN§f Você §b§lDESBANIU§f o jogador " + pP.getName() + "(" + pP.getUniqueId() + ")");
		broadcastToStaff("§3§lUNBAN§f O jogador " + pP.getName() + "(" + pP.getUniqueId()
				+ ") foi §b§lDESBANIDO§f por " + sender.getName() + "! Motivo: " + reason);
	}

	@Command(name = "computerban", aliases = { "macban" }, groupToUse = Group.TRIAL, runAsync = true)
	public void computerban(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§4§lMACBAN§f Utilize: §4§l/macban§f <player> <reason>");
			return;
		}
		TargetData target = resolveTarget(args[0], sender);
		if (target == null || target.account == null) {
			sender.sendMessage(
					"§4§lMACBAN§f Este jogador não é original ou não possui registros suficientes para punição.");
			return;
		}
		if (!target.account.getComputerAddress().isEmpty() && !target.loadedConnection) {
			target.loadedConnection = target.account.load(DataCategory.CONNECTION);
		}
		if (target.account.getComputerAddress().isEmpty()) {
			sender.sendMessage("§4§lMACBAN§f Este jogador não possui nenhum endereço mac registrado no servidor.");
			return;
		}
		if (WeavenMC.getPunishHistory().getMacBan(target.account.getComputerAddress()) != null) {
			sender.sendMessage("§4§lMACBAN§f O endereço mac do jogador já está banido.");
			return;
		}
		String reason = StringUtils.createArgs(1, args, "não informado", false);
		MacBan ban = new MacBan(target.account.getComputerAddress(), sender.getName(), reason, System.currentTimeMillis());
		if (!WeavenMC.getPunishHistory().banMac(ban)) {
			sender.sendMessage("§4§lMACBAN§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
			return;
		}
		broadcastToStaff("§4§lMACBAN§f O jogador " + target.account.getName() + "(" + target.account.getUniqueId() + ") ["
				+ target.account.getComputerAddress() + "] foi §c§lBANIDO§f por " + sender.getName() + "! Motivo: " + reason);
		VelocityMain.getInstance().getProxy()
				.sendMessage(VelocityText.of("§fO jogador " + target.account.getName() + " foi §c§lBANIDO PERMANENTEMENTE§f do servidor."));
		if (target.online != null) {
			target.online.disconnect(VelocityText.of(permanentBanMessage(ban.getAuthor(), ban.toDateApplied(), ban.getReason())));
		}
		tweetBan(target.account.getName(), sender.getName(), ban.getReason(), target.serverName);
	}

	@Command(name = "ipban", aliases = { "addressban" }, groupToUse = Group.TRIAL, runAsync = true)
	public void ipban(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§4§lIPBAN§f Utilize: §4§l/ipban§f <player> <reason>");
			return;
		}
		TargetData target = resolveTarget(args[0], sender);
		if (target == null || target.account == null) {
			sender.sendMessage("§4§lIPBAN§f Jogador não encontrado.");
			return;
		}
		if (!target.loadedConnection) {
			target.loadedConnection = target.account.load(DataCategory.ACCOUNT, DataCategory.CONNECTION);
		}
		if (target.account.getIpAddress().isEmpty()) {
			sender.sendMessage("§4§lIPBAN§f Este jogador não possui endereço de IP registrado.");
			return;
		}
		if (WeavenMC.getPunishHistory().getIpBan(target.account.getIpAddress()) != null) {
			sender.sendMessage("§4§lIPBAN§f O endereço IP do jogador já está banido.");
			return;
		}
		String reason = StringUtils.createArgs(1, args, "não informado", false);
		IpBan ban = new IpBan(target.account.getIpAddress(), sender.getName(), reason, System.currentTimeMillis());
		if (!WeavenMC.getPunishHistory().banIp(ban)) {
			sender.sendMessage("§4§lIPBAN§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
			return;
		}
		broadcastToStaff("§4§lIPBAN§f O jogador " + target.account.getName() + "(" + target.account.getUniqueId() + ") ["
				+ target.account.getIpAddress() + "] foi §c§lBANIDO§f por " + sender.getName() + "! Motivo: " + reason);
		VelocityMain.getInstance().getProxy()
				.sendMessage(VelocityText.of("§fO jogador " + target.account.getName() + " foi §c§lBANIDO PERMANENTEMENTE§f do servidor."));
		if (target.online != null) {
			target.online.disconnect(VelocityText.of(permanentBanMessage(ban.getAuthor(), ban.toDateApplied(), ban.getReason())));
		}
		tweetBan(target.account.getName(), sender.getName(), ban.getReason(), target.serverName);
	}

	@Command(name = "ban", aliases = { "banir", "punish", "punir" }, groupToUse = Group.INVESTIDOR, runAsync = true)
	public void ban(VelocityCommandSender sender, String label, String[] args) {
		banAccount(sender, args, -1, "§4§lBAN");
	}

	@Command(name = "tempban", aliases = { "tempbanir" }, groupToUse = Group.INVESTIDOR, runAsync = true)
	public void tempban(VelocityCommandSender sender, String label, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("§4§lTEMPBAN§f Utilize: §4§l/tempban§f <player> <tempo> <reason>");
			return;
		}
		long duration;
		try {
			duration = StringTimeUtils.parseDateDiff(args[1], true);
		} catch (Exception ex) {
			sender.sendMessage("§4§lTEMPBAN§f Tempo invalido.");
			return;
		}
		banAccount(sender, new String[] { args[0], StringUtils.createArgs(2, args, "não informado", false) }, duration, "§4§lTEMPBAN");
	}

	@Command(name = "mute", aliases = { "mutar" }, groupToUse = Group.TRIAL, runAsync = true)
	public void mute(VelocityCommandSender sender, String label, String[] args) {
		muteAccount(sender, args, -1, "§9§lMUTE");
	}

	@Command(name = "tempmute", aliases = { "tempmutar" }, groupToUse = Group.TRIAL, runAsync = true)
	public void tempmute(VelocityCommandSender sender, String label, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("§9§lTEMPMUTE§f Utilize: §3§l/tempmute§f <player> <tempo> <reason>");
			return;
		}
		long duration;
		try {
			duration = StringTimeUtils.parseDateDiff(args[1], true);
		} catch (Exception ex) {
			sender.sendMessage("§9§lTEMPMUTE§f Tempo invalido.");
			return;
		}
		muteAccount(sender, new String[] { args[0], StringUtils.createArgs(2, args, "não informado", false) }, duration,
				"§9§lTEMPMUTE");
	}

	@Command(name = "unmute", aliases = { "desmutar" }, groupToUse = Group.MODPLUS, runAsync = true)
	public void unmute(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§9§lUNMUTE§f Utilize: §3§l/unmute§f <player>");
			return;
		}
		Profile profile = WeavenMC.getProfileCommon().getProfile(args[0]);
		if (profile == null) {
			sender.sendMessage("§9§lUNMUTE§f Jogador não encontrado.");
			return;
		}
		if (!WeavenMC.getPunishHistory().pardonMute(profile.getId())) {
			sender.sendMessage("§9§lUNMUTE§f Não foi possível desmutar o jogador.");
			return;
		}
		Player online = VelocityMain.getInstance().getProxy().getPlayer(profile.getName()).orElse(null);
		if (online != null) {
			ProxyPlayer p = ProxyPlayer.getPlayer(online.getUniqueId());
			if (p != null) {
				p.setRecentMute(null);
			}
			online.sendMessage(VelocityText.of("§9§lUNMUTE§f Você foi desmutado por " + sender.getName() + "."));
		}
		broadcastToStaff("§9§lUNMUTE§f O jogador " + profile.getName() + " foi desmutado por " + sender.getName() + ".");
	}

	private void banAccount(VelocityCommandSender sender, String[] args, long duration, String prefix) {
		if (args.length == 0) {
			sender.sendMessage(prefix + "§f Utilize: comando <player> <reason>");
			return;
		}
		TargetData target = resolveTarget(args[0], sender);
		if (target == null || target.account == null) {
			sender.sendMessage(prefix + "§f Jogador não encontrado.");
			return;
		}
		String reason = args.length > 1 ? args[1] : "não informado";
		AccountBan existing = WeavenMC.getPunishHistory().getAccountBan(target.account.getUniqueId());
		if (existing != null && (existing.isPermanent() || !existing.hasExpired())) {
			sender.sendMessage(prefix + "§f O jogador já possui banimento ativo.");
			return;
		}
		AccountBan ban = new AccountBan(target.account.getUniqueId(), sender.getName(), reason, System.currentTimeMillis(), duration);
		if (!WeavenMC.getPunishHistory().banAccount(ban)) {
			sender.sendMessage(prefix + "§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
			return;
		}
		String durationText = duration == -1 ? "PERMANENTEMENTE" : "TEMPORARIAMENTE";
		VelocityMain.getInstance().getProxy()
				.sendMessage(VelocityText.of("§fO jogador " + target.account.getName() + " foi §c§lBANIDO " + durationText + "§f do servidor."));
		broadcastToStaff(prefix + "§f " + target.account.getName() + "(" + target.account.getUniqueId() + ") foi punido por "
				+ sender.getName() + "! Motivo: " + reason);
		if (target.online != null) {
			target.online.disconnect(VelocityText.of(duration == -1
					? permanentBanMessage(ban.getAuthor(), ban.toDateApplied(), ban.getReason())
					: temporaryBanMessage(ban.getAuthor(), ban.toDateApplied(), ban.getReason(), ban.getDuration())));
		}
		tweetBan(target.account.getName(), sender.getName(), ban.getReason(), target.serverName);
	}

	private void muteAccount(VelocityCommandSender sender, String[] args, long duration, String prefix) {
		if (args.length == 0) {
			sender.sendMessage(prefix + "§f Utilize: comando <player> <reason>");
			return;
		}
		Profile profile = WeavenMC.getProfileCommon().getProfile(args[0]);
		if (profile == null) {
			sender.sendMessage(prefix + "§f Jogador não encontrado.");
			return;
		}
		String reason = args.length > 1 ? args[1] : "não informado";
		Mute mute = new Mute(profile.getId(), sender.getName(), duration, reason, System.currentTimeMillis());
		if (!WeavenMC.getPunishHistory().mute(mute)) {
			sender.sendMessage(prefix + "§f Erro ao tentar efetuar a operação, tente novamente mais tarde.");
			return;
		}
		Player online = VelocityMain.getInstance().getProxy().getPlayer(profile.getName()).orElse(null);
		if (online != null) {
			ProxyPlayer p = ProxyPlayer.getPlayer(online.getUniqueId());
			if (p != null) {
				p.setRecentMute(mute);
			}
			online.sendMessage(VelocityText.of(duration == -1
					? "§9§lMUTE§f Você foi §3§lPERMANENTEMENTE MUTADO§f por " + sender.getName() + "! Motivo: " + reason
					: "§9§lMUTE§f Você foi §3§lTEMPORARIAMENTE MUTADO§f por " + sender.getName() + "! Motivo: " + reason));
		}
		broadcastToStaff(prefix + "§f " + profile.getName() + " foi mutado por " + sender.getName() + "! Motivo: " + reason);
	}

	private void broadcastToStaff(String message) {
		for (Player o : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer p = ProxyPlayer.getPlayer(o.getUniqueId());
			if (p == null || !p.isStaffer()) {
				continue;
			}
			o.sendMessage(VelocityText.of(message));
		}
	}

	private void tweetBan(String playerName, String staffName, String reason, String serverName) {
		try {
			Twitter.tweet(TwitterAccount.WEAVENBANS,
					"Jogador banido: " + playerName + "\nBanido por: " + staffName + "\nMotivo: " + reason + "\n\nServidor: " + serverName);
		} catch (TwitterException ex) {
			WeavenMC.debug("Não foi possivel atualizar o Twitter de Banimentos");
		}
	}

	private String permanentBanMessage(String author, String date, String reason) {
		return "§fVocê foi §4§lBANIDO PERMANENTEMENTE\n§fPor " + author + " no dia " + date + "\n§c§lMotivo§f: "
				+ reason
				+ "\n\n§fFoi §e§lbanido incorretamente§f? Peça §e§lAPPEAL§f em: http://bit.ly/WeavenDiscord"
				+ "\n§fCompre seu §3§lUNBAN§f em http://loja.mc-weaven.com.br para ter o §3§lACESSO§f liberado.";
	}

	private String temporaryBanMessage(String author, String date, String reason, long duration) {
		return "§fVocê foi §c§lBANIDO TEMPORARIAMENTE\n§fPor " + author + " no dia " + date + "\n§c§lMotivo§f: "
				+ reason + "\n§fDuração do §e§lBANIMENTO§f: " + StringTimeUtils.formatDifference(duration)
				+ "\n\n§fFoi §e§lbanido incorretamente§f? Peça §e§lAPPEAL§f em: http://bit.ly/WeavenDiscord"
				+ "\n§fCompre seu §3§lUNBAN§f em http://loja.mc-weaven.com.br para ter o §3§lACESSO§f liberado.";
	}

	private TargetData resolveTarget(String name, VelocityCommandSender sender) {
		TargetData data = new TargetData();
		data.online = VelocityMain.getInstance().getProxy().getPlayer(name).orElse(null);
		if (data.online != null) {
			data.account = ProxyPlayer.getPlayer(data.online.getUniqueId());
			data.loadedConnection = true;
			data.serverName = data.online.getCurrentServer().map(sc -> sc.getServerInfo().getName().toLowerCase() + ".mc-weaven.com.br")
					.orElse("informação não disponivel");
			return data;
		}

		Profile profile = WeavenMC.getProfileCommon().getProfile(name);
		if (profile == null) {
			return null;
		}
		data.account = new ProxyPlayer(profile.getId(), profile.getName());
		data.serverName = sender.isPlayer() && sender.getPlayer().getCurrentServer().isPresent()
				? sender.getPlayer().getCurrentServer().get().getServerInfo().getName().toLowerCase() + ".mc-weaven.com.br"
				: "informação não disponivel";
		return data;
	}

	@Completer(name = "ban", aliases = { "ipban", "addressban", "computerban", "macban", "banir", "tempbanir", "mute",
			"mutar", "tempmute", "tempmutar" })
	public List<String> banCompleter(Player player, String label, String[] args) {
		return onlineNameSuggestions(args);
	}

	@Completer(name = "tempban", aliases = { "tempbanir" })
	public List<String> tempbanCompleter(Player player, String label, String[] args) {
		return onlineNameSuggestions(args);
	}

	private List<String> onlineNameSuggestions(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 1) {
			for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
				if (online.getUsername().toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(online.getUsername());
				}
			}
		}
		return list;
	}

	private static class TargetData {
		private Player online;
		private ProxyPlayer account;
		private boolean loadedConnection;
		private String serverName;
	}
}
