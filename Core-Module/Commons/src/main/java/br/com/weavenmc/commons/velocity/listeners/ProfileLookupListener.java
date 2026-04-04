package br.com.weavenmc.commons.velocity.listeners;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.profile.Profile;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import br.com.weavenmc.commons.velocity.checker.CheckingConnectionAPI;
import br.com.weavenmc.commons.velocity.login.LoginCache;
import br.com.weavenmc.commons.velocity.login.LoginCacheAPI;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.util.GameProfile;
import com.velocitypowered.api.proxy.Player;

public class ProfileLookupListener {

	public static boolean joinEnabled = true;
	private final AtomicInteger atomicInteger = new AtomicInteger();
	private final Pattern validName = Pattern.compile("[a-zA-Z0-9_]{1,16}");
	private final Map<String, UUID> crackedProfiles = new ConcurrentHashMap<>();

	public ProfileLookupListener(VelocityMain plugin) {
		plugin.getProxy().getScheduler().buildTask(plugin, () -> {
			if (!joinEnabled) {
				if (atomicInteger.incrementAndGet() > 80) {
					atomicInteger.set(0);
					joinEnabled = true;
				}
				return;
			}
			atomicInteger.set(0);
		}).repeat(1, TimeUnit.SECONDS).schedule();
	}

	@Subscribe
	public void onPreLogin(PreLoginEvent event) {
		String name = event.getUsername();

		if (!WeavenMC.getClanCommon().isClansLoaded() || !WeavenMC.getProfileCommon().isProfilesLoaded()
				|| !CheckingConnectionAPI.fullyLoaded.get()) {
			event.setResult(PreLoginEvent.PreLoginComponentResult.denied(
					VelocityText.of("§c§lSTARTUP§f Aguarde o término da inicialização do sistema...")));
			return;
		}

		if (joinEnabled && atomicInteger.incrementAndGet() > 5) {
			joinEnabled = false;
			atomicInteger.set(0);
			sendOwnerMessage(
					"§cA autenticaçao para piratas foi temporariamente desativada! Muitas conexoes foram detectadas.");
		}

		if (name.length() < 3 || name.length() > 16 || name.contains(" ") || !validName.matcher(name).matches()) {
			event.setResult(PreLoginEvent.PreLoginComponentResult
					.denied(VelocityText.of("§4§lERRO§f O nome de usuário '" + name + "' é §c§lINVALIDO!")));
			return;
		}

		UUID onlineUuid = WeavenMC.getUUIDOf(name);
		if (onlineUuid != null) {
			crackedProfiles.remove(name.toLowerCase());
			event.setResult(PreLoginEvent.PreLoginComponentResult.forceOnlineMode());
			return;
		}

		String ipAddress = event.getConnection().getRemoteAddress().getHostString();
		boolean authenticated = false;
		LoginCache cache = LoginCacheAPI.getCache(ipAddress);
		if (cache != null) {
			authenticated = cache.hasSession(name);
		}

		if (!joinEnabled && !authenticated) {
			event.setResult(PreLoginEvent.PreLoginComponentResult.denied(VelocityText.of(
					"§4§lPIRATE AUTHENTICATION\n\n§fA §c§lAUTENTICAÇAO§f para piratas está §c§lTEMPORARIAMENTE§f desativada!\n§fHá muitas autenticações sendo feitas por segundo!\n\n§4§lwww.mc-weaven.com.br")));
			return;
		}

		Profile profile = WeavenMC.getProfileCommon().createCrackedIfNotExists(name);
		if (profile == null) {
			event.setResult(PreLoginEvent.PreLoginComponentResult
					.denied(VelocityText.of("§4§lERRO§f Ocorreu um erro ao tentar criar ou carregar seu perfil!")));
			return;
		}

		crackedProfiles.put(name.toLowerCase(), profile.getId());
		event.setResult(PreLoginEvent.PreLoginComponentResult.forceOfflineMode());
	}

	@Subscribe
	public void onGameProfileRequest(GameProfileRequestEvent event) {
		if (event.isOnlineMode()) {
			crackedProfiles.remove(event.getUsername().toLowerCase());
			return;
		}

		UUID crackedUuid = crackedProfiles.remove(event.getUsername().toLowerCase());
		if (crackedUuid == null) {
			return;
		}

		event.setGameProfile(new GameProfile(crackedUuid, event.getUsername(),
				event.getGameProfile() == null ? java.util.Collections.emptyList() : event.getGameProfile().getProperties()));
	}

	private void sendOwnerMessage(String msg) {
		for (Player player : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer pP = ProxyPlayer.getPlayer(player.getUniqueId());
			if (pP != null && pP.hasGroupPermission(Group.DONO)) {
				player.sendMessage(VelocityText.of(msg));
			}
		}
	}
}
