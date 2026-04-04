package br.com.weavenmc.commons.velocity.listeners;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.clan.Clan;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.data.punish.AccountBan;
import br.com.weavenmc.commons.core.data.punish.IpBan;
import br.com.weavenmc.commons.core.data.punish.MacBan;
import br.com.weavenmc.commons.core.server.NetworkServer;
import br.com.weavenmc.commons.util.GeoIpUtils;
import br.com.weavenmc.commons.util.string.StringTimeUtils;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import br.com.weavenmc.commons.velocity.login.LoginCache;
import br.com.weavenmc.commons.velocity.login.LoginCacheAPI;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class ProxyAccountListener {

	@Subscribe
	public void onLogin(LoginEvent event) {
		Player player = event.getPlayer();
		ProxyPlayer pP = new ProxyPlayer(player.getUniqueId(), player.getUsername());
		WeavenMC.getAccountCommon().loadWeavenPlayer(pP.getUniqueId(), pP);

		if (!pP.load(DataCategory.ACCOUNT, DataCategory.BALANCE, DataCategory.CONNECTION, DataCategory.PERSONAL_DATA)) {
			event.setResult(ResultedEvent.ComponentResult
					.denied(VelocityText.of("§4§lERRO§f Ocorreu um erro ao tentar carregar sua conta.")));
			WeavenMC.getAccountCommon().unloadWeavenPlayer(player.getUniqueId());
			return;
		}

		pP.organizeGroups();
		pP.loadPermissions();
		pP.setLastIpAddress(pP.getIpAddress());
		pP.setIpAddress(player.getRemoteAddress().getHostString());
		if (pP.getFirstLoggedIn() == 0L) {
			pP.setFirstLoggedIn(System.currentTimeMillis());
		}
		pP.setLastLoggedIn(System.currentTimeMillis());

		try {
			NetworkInterface network = NetworkInterface.getByInetAddress(player.getRemoteAddress().getAddress());
			if (network != null && network.getHardwareAddress() != null) {
				byte[] macAddress = network.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < macAddress.length; i++) {
					sb.append(String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "-" : ""));
				}
				if (!pP.getComputerAddress().isEmpty()) {
					pP.setLastComputerAddress(pP.getComputerAddress());
				}
				pP.setComputerAddress(sb.toString());
			}
		} catch (SocketException ignored) {
		}

		if (!pP.getClanName().equals("Nenhum")) {
			Clan clan = WeavenMC.getClanCommon().tryInCacheFromName(pP.getClanName());
			if (clan == null && WeavenMC.getClanCommon().tryInDatabaseFromName(pP.getClanName()) == null) {
				pP.setClanName("Nenhum");
			}
		}

		try {
			pP.setIpInfo(GeoIpUtils.getIpInfo(pP.getIpAddress()));
			pP.setCity(pP.getIpInfo().getCity());
			pP.setCountry(pP.getIpInfo().getCountry_name());
		} catch (IOException ignored) {
		}

		pP.save(DataCategory.ACCOUNT, DataCategory.CONNECTION);
		pP.setRecentMute(WeavenMC.getPunishHistory().getMute(pP.getUniqueId()));

		String deny = checkBanMessage(pP);
		if (deny != null) {
			event.setResult(ResultedEvent.ComponentResult.denied(VelocityText.of(deny)));
			WeavenMC.getAccountCommon().unloadWeavenPlayer(player.getUniqueId());
		}
	}

	@Subscribe
	public void onPostLogin(PostLoginEvent event) {
		Player p = event.getPlayer();
		ProxyPlayer pP = ProxyPlayer.getPlayer(p.getUniqueId());
		if (pP == null) {
			p.disconnect(VelocityText.of("§4§lERRO§f Sua conta está com problemas!"));
			return;
		}
		VelocityMain.getInstance().getProxy().getScheduler().buildTask(VelocityMain.getInstance(), () -> {
			if (p.isOnlineMode()) {
				p.sendMessage(VelocityText.of("§aAutenticado como jogador original."));
			}
		}).delay(3, TimeUnit.SECONDS).schedule();
	}

	@Subscribe
	public void onServerConnected(ServerConnectedEvent event) {
		ProxyPlayer pP = ProxyPlayer.getPlayer(event.getPlayer().getUniqueId());
		if (pP == null) {
			return;
		}
		NetworkServer server = VelocityMain.getInstance().getServerManager().getWeavenServer(event.getServer());
		if (server != null) {
			pP.setServerConnected(server.getServerId());
			pP.setServerConnectedType(server.getServerType());
		}
	}

	@Subscribe
	public void onServerPreConnect(ServerPreConnectEvent event) {
		Player p = event.getPlayer();
		ProxyPlayer pP = ProxyPlayer.getPlayer(p.getUniqueId());
		if (pP == null) {
			p.disconnect(VelocityText.of("§4§lERRO§f Sua conta está com problemas!"));
			event.setResult(ServerPreConnectEvent.ServerResult.denied());
			return;
		}

		RegisteredServer target = event.getOriginalServer();
		RegisteredServer current = p.getCurrentServer().map(sc -> sc.getServer()).orElse(null);
		if (target.getServerInfo().getName().equalsIgnoreCase("login")) {
			if (!p.isOnlineMode()) {
				LoginCache cache = LoginCacheAPI.getCache(p.getRemoteAddress().getHostString());
				if (cache != null) {
					cache.removeSession(p.getUsername());
				}
			}
			return;
		}

		if (!p.isOnlineMode()) {
			LoginCache cache = LoginCacheAPI.getCache(p.getRemoteAddress().getHostString());
			boolean hasSession = cache != null && cache.hasSession(p.getUsername());
			if (!hasSession) {
				RegisteredServer login = VelocityMain.getInstance().getProxy().getServer("login").orElse(null);
				if (login == null || (current != null && current.getServerInfo().getName().equalsIgnoreCase("login"))) {
					event.setResult(ServerPreConnectEvent.ServerResult.denied());
				} else {
					event.setResult(ServerPreConnectEvent.ServerResult.allowed(login));
				}
				p.sendMessage(VelocityText.of("§4§lLOGIN§f Você não possui uma §c§lSESSAO§f ou ela §c§lEXPIROU!"));
			}
		}
	}

	@Subscribe
	public void onDisconnect(DisconnectEvent event) {
		WeavenMC.getAccountCommon().unloadWeavenPlayer(event.getPlayer().getUniqueId());
	}

	private String checkBanMessage(ProxyPlayer pP) {
		MacBan macBan = WeavenMC.getPunishHistory().getMacBan(pP.getComputerAddress());
		if (macBan != null) {
			return banMessage(macBan.getAuthor(), macBan.toDateApplied(), macBan.getReason());
		}
		IpBan ipBan = WeavenMC.getPunishHistory().getIpBan(pP.getIpAddress());
		if (ipBan != null) {
			return banMessage(ipBan.getAuthor(), ipBan.toDateApplied(), ipBan.getReason());
		}
		AccountBan ban = WeavenMC.getPunishHistory().getAccountBan(pP.getUniqueId());
		if (ban == null) {
			return null;
		}
		if (!ban.isPermanent() && ban.hasExpired()) {
			VelocityMain.getInstance().getProxy().getCommandManager()
					.executeAsync(VelocityMain.getInstance().getProxy().getConsoleCommandSource(),
							"unban " + pP.getName() + " AutoUnban - Ban expirado.");
			return null;
		}
		if (ban.isPermanent()) {
			return banMessage(ban.getAuthor(), ban.toDateApplied(), ban.getReason());
		}
		return "§fVocê foi §c§lBANIDO TEMPORARIAMENTE\n§fPor " + ban.getAuthor() + " no dia " + ban.toDateApplied()
				+ "\n§c§lMotivo§f: " + ban.getReason() + "\n§fDuração do §e§lBANIMENTO§f: "
				+ StringTimeUtils.formatDifference(ban.getDuration())
				+ "\n\n§fFoi §e§lbanido incorretamente§f? Peça §e§lAPPEAL§f em: http://bit.ly/WeavenDiscord"
				+ "\n§fCompre seu §3§lUNBAN§f em http://loja.mc-weaven.com.br para ter o §3§lACESSO§f liberado.";
	}

	private String banMessage(String author, String date, String reason) {
		return "§fVocê foi §4§lBANIDO PERMANENTEMENTE\n§fPor " + author + " no dia " + date + "\n§c§lMotivo§f: "
				+ reason
				+ "\n\n§fFoi §e§lbanido incorretamente§f? Peça §e§lAPPEAL§f em: http://bit.ly/WeavenDiscord"
				+ "\n§fCompre seu §3§lUNBAN§f em http://loja.mc-weaven.com.br para ter o §3§lACESSO§f liberado.";
	}
}
