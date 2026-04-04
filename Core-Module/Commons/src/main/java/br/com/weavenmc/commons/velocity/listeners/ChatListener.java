package br.com.weavenmc.commons.velocity.listeners;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.clan.Clan;
import br.com.weavenmc.commons.core.data.punish.Mute;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;

public class ChatListener {

	@Subscribe
	public void onChat(PlayerChatEvent event) {
		Player p = event.getPlayer();
		ProxyPlayer player = ProxyPlayer.getPlayer(p.getUniqueId());
		if (player == null) {
			p.disconnect(VelocityText.of("§4§lERRO§f Sua conta está com problemas!"));
			event.setResult(PlayerChatEvent.ChatResult.denied());
			return;
		}

		if (player.isStaffChatEnabled()) {
			if (player.isStaffer() && player.getServerConnectedType() != ServerType.LOGIN) {
				event.setResult(PlayerChatEvent.ChatResult.denied());
				sendStaffMessage(player, event.getMessage());
				return;
			}
			player.setStaffChatEnabled(false);
		}

		if (player.isClanChatEnabled()) {
			Clan clan = WeavenMC.getClanCommon().getClanFromName(player.getClanName());
			if (clan != null && clan.isMemberOfClan(player.getUniqueId())
					&& player.getServerConnectedType() != ServerType.LOGIN) {
				event.setResult(PlayerChatEvent.ChatResult.denied());
				sendClanMessage(clan, player, event.getMessage());
				return;
			}
			player.setClanChatEnabled(false);
		}

		Mute recent = player.getRecentMute();
		if (recent != null) {
			if (recent.isPermanent()) {
				event.setResult(PlayerChatEvent.ChatResult.denied());
				p.sendMessage(VelocityText.of("§9§lMUTE§f Você foi §3§lPERMANENTEMENTE MUTADO§f por " + recent.getMutedBy()
						+ "! Motivo: " + recent.getReason() + " - Compre seu §5§lUNMUTE§f em http://loja.mc-weaven.com.br"));
			} else if (!recent.hasExpired()) {
				event.setResult(PlayerChatEvent.ChatResult.denied());
				p.sendMessage(VelocityText.of("§9§lMUTE§f Você foi §3§lTEMPORARIAMENTE MUTADO§f por " + recent.getMutedBy()
						+ "! Motivo: " + recent.getReason() + " - Compre seu §5§lUNMUTE§f em http://loja.mc-weaven.com.br"));
				p.sendMessage(VelocityText.of("§9§lMUTE§f Expira em: " + StringTimeUtils.formatDifference(recent.getMuteTime())));
			} else {
				player.setRecentMute(null);
			}
		}
	}

	private void sendClanMessage(Clan clan, ProxyPlayer sender, String message) {
		String tag = clan.isOwner(sender.getUniqueId()) ? " §f- §4DONO"
				: (clan.isAdministrator(sender.getUniqueId()) ? " §f- §cADMIN" : "");
		String format = "§4[CLAN" + tag.toUpperCase() + "§4] §7" + sender.getName() + "§f: ";
		for (Player o : br.com.weavenmc.commons.velocity.VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer online = ProxyPlayer.getPlayer(o.getUniqueId());
			if (online == null || !clan.isMemberOfClan(o.getUniqueId())) {
				continue;
			}
			o.sendMessage(VelocityText.of(format + message));
		}
	}

	private void sendStaffMessage(ProxyPlayer sender, String message) {
		String format = sender.getGroup().getTagToUse().getPrefix() + sender.getName() + "§f: ";
		for (Player o : br.com.weavenmc.commons.velocity.VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer online = ProxyPlayer.getPlayer(o.getUniqueId());
			if (online == null || online.getGroup().getId() < Group.TRIAL.getId()
					|| online.getServerConnectedType() == ServerType.LOGIN || !online.isStaffChatMessages()) {
				continue;
			}
			o.sendMessage(VelocityText.of("§e§l[STAFF] " + format + message));
		}
	}
}
