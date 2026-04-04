package br.com.weavenmc.commons.velocity.command.register;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.ArrayList;
import java.util.List;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework.Command;
import br.com.weavenmc.commons.core.command.CommandFramework.Completer;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.permission.Group;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.util.string.StringUtils;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.VelocityText;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import br.com.weavenmc.commons.velocity.command.VelocityCommandSender;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class ServerCommand implements CommandClass {

	private static final LegacyChannelIdentifier COMMONS_CHANNEL = new LegacyChannelIdentifier("Commons");

	@Command(name = "weavenmc")
	public void weavenmc(VelocityCommandSender sender, String label, String[] args) {
		sender.sendMessage("§3§l/hg §f- Encontre a melhor partida de hg doublekit se disponivel");
		sender.sendMessage("§3§l/teamhg §f- Encontre a melhor partida de teamhg se disponivel");
		sender.sendMessage("§3§l/winner §f- Veja informacoes sobre a tag §2§lWINNER");
		sender.sendMessage("§3§l/clan help §f- Veja informações sobre o sistema de Clan");
		sender.sendMessage("§3§l/play §f<server> - Te conecta diretamente á um servidor da Rede");
		sender.sendMessage("§3§l/perfil §f<yt, twitter, skype, discord, twitch> [...] - Sete o seu canal do YouTube, Twitter, Skype, Discord ou Twitch");
		sender.sendMessage("§3§l/lobby §f- Encontre o primeiro lobby disponivel para conectar");
		sender.sendMessage("§3§l/gravar §f- Anuncie em toda a Rede que voce ira gravar no servidor que está");
	}

	@Command(name = "regras")
	public void regras(VelocityCommandSender sender, String label, String[] args) {
		sender.sendMessage("§4§lDiretrizes do servidor:");
		sender.sendMessage("");
		sender.sendMessage("Banimentos permanentes:");
		sender.sendMessage("");
		sender.sendMessage("1. Admitir o uso de hack;");
		sender.sendMessage("2. Ameaça;");
		sender.sendMessage("3. Assédio;");
		sender.sendMessage("4. Bullying;");
		sender.sendMessage("5. Calunia;");
		sender.sendMessage("6. Chantagem;");
		sender.sendMessage("7. Chargeback;");
		sender.sendMessage("8. Conta alternativa;");
		sender.sendMessage("9. Cúmplice de FreeKill;");
		sender.sendMessage("10. Deslogar em ScreenShare;");
		sender.sendMessage("11. Difamação;");
		sender.sendMessage("12. Discriminação;");
		sender.sendMessage("13. Divulgação de outros servidores;");
		sender.sendMessage("14. Estelionato;");
		sender.sendMessage("15. Extorsão;");
		sender.sendMessage("16. Falsidade ideológica (Blacklist);");
		sender.sendMessage("17. Falsificação de provas;");
		sender.sendMessage("18. Forjar o uso de hack;");
		sender.sendMessage("19. Fraude;");
		sender.sendMessage("20. FreeKill;");
		sender.sendMessage("21. Gravar ScreenShare;");
		sender.sendMessage("22. Griffing (Blacklist);");
		sender.sendMessage("23. Homofobia;");
		sender.sendMessage("24. Justa causa;");
		sender.sendMessage("25. Negar ScreenShare;");
		sender.sendMessage("26. Nick impróprio;");
		sender.sendMessage("27. Racismo;");
		sender.sendMessage("28. Tentativa de Suborno;");
		sender.sendMessage("29. Testar staff;");
		sender.sendMessage("30. Trollar ScreenShare;");
		sender.sendMessage("31. Uso de hack (vantagens injustas)/Cheating.");
		sender.sendMessage("");
		sender.sendMessage("Banimentos temporários:");
		sender.sendMessage("");
		sender.sendMessage("1. Abuso de bugs (3 dias);");
		sender.sendMessage("2. Anti-Jogo (10 minutos);");
		sender.sendMessage("3. Flood no /tell (30 minutos);");
		sender.sendMessage("4. Mal uso do /report (15 minutos);");
		sender.sendMessage("5. Rastro de programas ilícitos (programa que possa te dar alguma vantagem injusta) (7 dias);");
		sender.sendMessage("6. Spam no /tell (30 minutos);");
		sender.sendMessage("7. Uso de nick impróprio no /fake (15 minutos).");
		sender.sendMessage("Silenciamentos permanentes:");
		sender.sendMessage("1. Divulgação de vídeos em outros servidores;");
		sender.sendMessage("2. Ofensa ao servidor;");
		sender.sendMessage("3. Xenofobia.");
		sender.sendMessage("");
		sender.sendMessage("Silenciamentos temporários:");
		sender.sendMessage("");
		sender.sendMessage("1. Citação de outros servidores (20 minutos);");
		sender.sendMessage("2. Comércio (30 minutos);");
		sender.sendMessage("3. Divulgação de fake (30 minutos);");
		sender.sendMessage("4. Divulgação de links pornográficos (1 hora);");
		sender.sendMessage("5. Flood (assim que começar) (30 minutos);");
	}

	@Command(name = "rg")
	public void rg(VelocityCommandSender sender, String label, String[] args) {
		sender.sendMessage("§e§lREGRAS DE EVENTOS:");
		sender.sendMessage("");
		sender.sendMessage(" É proibido fechamento de cxc sem permissão de um Event Manager.");
		sender.sendMessage("Eventos fora de horário devem ser informados para um Event Manager.");
		sender.sendMessage("");
		sender.sendMessage("Sistema de Eventos: Recebera Kick o jogador que:");
		sender.sendMessage("");
		sender.sendMessage("Fizer panela durante um evento.");
		sender.sendMessage("Quebrar as regras do evento.");
		sender.sendMessage("Perturbar ou atrapalhar o desempenho do evento.");
		sender.sendMessage("");
		sender.sendMessage("Realização de Eventos:");
		sender.sendMessage("");
		sender.sendMessage("Estar presente no IP de eventos com antecedência de 10 minutos para organização do mesmo.");
		sender.sendMessage("Avisar com antecedência outro Promotor caso não puder realizar seu evento.");
		sender.sendMessage("");
		sender.sendMessage("Abuso:");
		sender.sendMessage("");
		sender.sendMessage("É permanentemente proibido qualquer tipo de abuso ou vantagem para qualquer jogador.");
	}

	@Command(name = "winner")
	public void winner(VelocityCommandSender sender, String label, String[] args) {
		sender.sendMessage("§2§lWINNER§f O WeavenHG é o unico servidor de HG que possui sistema de tag §2§lWINNER§f, onde você recebe um §d§lVIP§f após vencer uma §6§lPARTIDA§f. Você poderá escolher qualquer kit e a tag §2§lWINNER§f irá sobrepor sua tag atual. Mas §c§lTOME CUIDADO§f, a tag §2§lWINNER§f ficará somente no próximo servidor de HG que você entrar após vencer, não importa se a partida iniciar ou não.");
	}

	@Command(name = "perfil", aliases = { "perfilset" })
	public void perfil(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}

		Player player = sender.getPlayer();
		ProxyPlayer proxyPlayer = ProxyPlayer.getPlayer(player.getUniqueId());
		if (proxyPlayer == null) {
			sender.sendMessage("§4§lERRO§f Dados da conta não carregados.");
			return;
		}

		if (args.length <= 1) {
			sender.sendMessage("§6§lPERFIL§f Utilize: §6§l/perfil§f <yt, twitter, skype, discord, twitch> [...]");
			return;
		}

		String statusType;
		String value = StringUtils.createArgs(1, args, "", false);
		if (args[0].equalsIgnoreCase("yt") || args[0].equalsIgnoreCase("youtube")) {
			proxyPlayer.setYoutubeChannel(value);
			statusType = "YoutubeChannel";
			sender.sendMessage("§6§lPERFIL§f O seu canal do §4§lYOUTUBE§f foi alterado para §c§l" + value);
		} else if (args[0].equalsIgnoreCase("twitter") || args[0].equalsIgnoreCase("tt")) {
			proxyPlayer.setTwitter(value);
			statusType = "Twitter";
			sender.sendMessage("§6§lPERFIL§f O seu §b§lTWITTER§f foi alterado para §3§l" + value);
		} else if (args[0].equalsIgnoreCase("skype")) {
			proxyPlayer.setSkype(value);
			statusType = "Skype";
			sender.sendMessage("§6§lPERFIL§f O seu §b§lSKYPE§f foi alterado para §3§l" + value);
		} else if (args[0].equalsIgnoreCase("discord") || args[0].equalsIgnoreCase("dc")) {
			proxyPlayer.setDiscord(value);
			statusType = "Discord";
			sender.sendMessage("§6§lPERFIL§f O seu §9§lDISCORD§f foi alterado para §1§l" + value);
		} else if (args[0].equalsIgnoreCase("twitch")) {
			proxyPlayer.setTwitch(value);
			statusType = "Twitch";
			sender.sendMessage("§6§lPERFIL§f O seu §3§lTWITCH§f foi alterado para §b§l" + value);
		} else {
			sender.sendMessage("§6§lPERFIL§f Utilize: §6§l/perfil§f <yt, twitter, skype, discord, twitch> [...]");
			return;
		}

		proxyPlayer.save(DataCategory.PERSONAL_DATA);
		sendPersonalStatus(player, statusType, value);
	}

	@Command(name = "hungergames", aliases = { "hg", "dk", "doublekit" })
	public void hg(VelocityCommandSender sender, String label, String[] args) {
		connectToAvailable(sender, ServerType.DOUBLEKITHG, "DOUBLEKITHG");
	}

	@Command(name = "teamhg", aliases = { "team" })
	public void teamhg(VelocityCommandSender sender, String label, String[] args) {
		connectToAvailable(sender, ServerType.TEAMHG, "TEAMHG");
	}

	@Command(name = "lobby", aliases = { "hub", "l" })
	public void lobby(VelocityCommandSender sender, String label, String[] args) {
		connectToAvailable(sender, ServerType.LOBBY, "LOBBY");
	}

	@Command(name = "address")
	public void address(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		String current = sender.getPlayer().getCurrentServer().map(sc -> sc.getServerInfo().getName().toLowerCase()).orElse("network");
		sender.sendMessage("§6§lWeaven§8§l>> §eVocê está conectado ao ip: §6§l" + current + ".mc-weaven.com.br");
	}

	@Command(name = "record", aliases = { "gravar", "rec" }, groupToUse = Group.YOUTUBER)
	public void record(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		ProxyPlayer player = ProxyPlayer.getPlayer(sender.getUniqueId());
		if (player == null) {
			sender.sendMessage("§4§lERRO§f Dados da conta não carregados.");
			return;
		}

		String format = player.hasGroupPermission(Group.YOUTUBERPLUS)
				? "§3§lYOUTUBER+ §3" + player.getName()
				: "§b§lYOUTUBER §b" + player.getName();
		String serverConnected = player.getServerConnected().toUpperCase();

		VelocityMain.getInstance().getProxy().sendMessage(VelocityText.of(""));
		VelocityMain.getInstance().getProxy().sendMessage(VelocityText.of(
				"§4§lYOUTUBE§f O youtuber " + format + "§f esta gravando no servidor §c§l" + serverConnected + "."));
		VelocityMain.getInstance().getProxy()
				.sendMessage(VelocityText.of("§6§lConecte-se com §e/connect " + player.getServerConnected()));
		VelocityMain.getInstance().getProxy().sendMessage(VelocityText.of(""));
	}

	@Command(name = "connect", aliases = { "server", "con", "play", "jogar" })
	public void connect(VelocityCommandSender sender, String label, String[] args) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		ProxyPlayer player = ProxyPlayer.getPlayer(sender.getUniqueId());
		if (args.length == 0) {
			sender.sendMessage("§6§lWeaven§8§l>> §fUtilize: §e§l/play§f <servidor> §6(digite '/play ' e aperte tab para ver todos os servidores)");
			return;
		}

		RegisteredServer server = VelocityMain.getInstance().getProxy().getServer(args[0]).orElse(null);
		if (server == null) {
			sender.sendMessage("§4§lERRO §fNenhum servidor disponivel de §c§l" + args[0] + "§f foi encontrado!");
			return;
		}
		if ("screenshare".equalsIgnoreCase(server.getServerInfo().getName()) && (player == null || !player.isStaffer())) {
			sender.sendMessage("§a§lSCREENSHARE§f Este servidor é §c§lRESTRITO");
			return;
		}
		if (!"screenshare".equalsIgnoreCase(server.getServerInfo().getName()) || player == null || !player.isStaffer()) {
			sender.sendMessage("");
			sender.sendMessage("§6§lWeaven§8§l>> §fTentando conectar a um servidor disponivel de §e§l" + server.getServerInfo().getName().toUpperCase());
			sender.sendMessage("");
		}
		sender.getPlayer().createConnectionRequest(server).connect();
	}

	@Command(name = "broadcast", aliases = { "bc", "aviso", "alert" }, groupToUse = Group.MOD)
	public void broadcast(VelocityCommandSender sender, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§4§lBROADCAST§f Utilize: §c§l/broadcast§f <mensagem>");
			return;
		}

		String message = StringUtils.createArgs(0, args, "", true);
		VelocityMain.getInstance().getProxy().sendMessage(VelocityText.of("§1§lWeavenMC§8§l>> §f" + message));
		for (Player online : VelocityMain.getInstance().getProxy().getAllPlayers()) {
			ProxyPlayer proxyPlayer = ProxyPlayer.getPlayer(online.getUniqueId());
			if (proxyPlayer == null || !proxyPlayer.hasGroupPermission(Group.DONO)) {
				continue;
			}
			online.sendMessage(VelocityText.of("§7§o(" + sender.getName() + " enviou um aviso)"));
		}
	}

	@Completer(name = "connect", aliases = { "server", "con", "play", "jogar" })
	public List<String> connectCompleter(Player player, String label, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 1) {
			for (RegisteredServer server : VelocityMain.getInstance().getProxy().getAllServers()) {
				String name = server.getServerInfo().getName();
				if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
					list.add(name);
				}
			}
		}
		return list;
	}

	private void connectToAvailable(VelocityCommandSender sender, ServerType type, String displayName) {
		if (!sender.isPlayer()) {
			sender.sendMessage("§4§lERRO§f Comando disponivel apenas §c§lin-game");
			return;
		}
		Player player = sender.getPlayer();
		RegisteredServer server = VelocityMain.getInstance().getServerManager().getAvailableServer(type);
		if (server == null) {
			sender.sendMessage("§4§lERRO §fNenhum servidor disponivel de §c§l" + displayName + "§f foi encontrado!");
			return;
		}
		player.createConnectionRequest(server).connect();
	}

	private void sendPersonalStatus(Player player, String statusType, String value) {
		player.getCurrentServer().ifPresent(serverConnection -> {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("PersonalStatus");
			out.writeUTF(statusType);
			out.writeUTF(value);
			serverConnection.sendPluginMessage(COMMONS_CHANNEL, out.toByteArray());
		});
	}
}
