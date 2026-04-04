package br.com.weavenmc.commons.velocity.listeners;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import br.com.weavenmc.commons.util.string.StringMaker;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.checker.CheckingConnection;
import br.com.weavenmc.commons.velocity.checker.CheckingConnectionAPI;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;

public class JoinListener {

	private final VelocityMain plugin;
	private final String mainMotd;
	private final List<String> motds;
	private final Random random = new Random();

	public JoinListener(VelocityMain plugin) {
		this.plugin = plugin;
		this.mainMotd = plugin.getConfig().getString("velocity.motds.main");
		this.motds = plugin.getConfig().getStringList("velocity.motds.random");
	}

	@Subscribe
	public void onProxyPing(ProxyPingEvent event) {
		if (event.getConnection().getRemoteAddress() instanceof InetSocketAddress) {
			String ipAddress = ((InetSocketAddress) event.getConnection().getRemoteAddress()).getHostString();
			CheckingConnection checking = CheckingConnectionAPI.getOrCreateProxie(ipAddress);
			if (!checking.hasPinged()) {
				checking.ping();
			}
		}

		String line2 = motds.isEmpty() ? "&fServidor online" : motds.get(random.nextInt(motds.size()));
		String motd = StringMaker.makeCenteredMotd(mainMotd.replace("&", "§")) + "\n"
				+ StringMaker.makeCenteredMotd(line2.replace("&", "§"));
		event.setPing(event.getPing().asBuilder().description(VelocityText.of(motd))
				.onlinePlayers(plugin.getProxy().getPlayerCount()).maximumPlayers(1000).build());
	}
}
