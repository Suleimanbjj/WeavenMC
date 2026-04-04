package br.com.weavenmc.commons.velocity.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import br.com.weavenmc.commons.core.command.CommandClass;
import br.com.weavenmc.commons.core.command.CommandFramework;
import br.com.weavenmc.commons.core.server.ServerType;
import br.com.weavenmc.commons.velocity.VelocityMain;
import br.com.weavenmc.commons.velocity.account.ProxyPlayer;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class VelocityCommandFramework implements CommandFramework {

	private final VelocityMain plugin;
	private final Map<String, Entry<Method, Object>> commandMap = new HashMap<>();
	private final Map<String, Entry<Method, Object>> completerMap = new HashMap<>();

	public VelocityCommandFramework(VelocityMain plugin) {
		this.plugin = plugin;
	}

	@Override
	public void registerCommands(CommandClass cls) {
		for (Method method : cls.getClass().getMethods()) {
			if (method.getAnnotation(Command.class) != null) {
				Command command = method.getAnnotation(Command.class);
				registerCommand(command.name(), method, cls);
				for (String alias : command.aliases()) {
					registerCommand(alias, method, cls);
				}
			} else if (method.getAnnotation(Completer.class) != null) {
				Completer completer = method.getAnnotation(Completer.class);
				registerCompleter(completer.name(), method, cls);
				for (String alias : completer.aliases()) {
					registerCompleter(alias, method, cls);
				}
			}
		}
	}

	private void registerCommand(String label, Method method, Object owner) {
		commandMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<>(method, owner));
		CommandManager manager = plugin.getProxy().getCommandManager();
		CommandMeta meta = manager.metaBuilder(label.toLowerCase()).plugin(plugin).build();
		manager.register(meta, new SimpleCommand() {
			@Override
			public void execute(Invocation invocation) {
				handleCommand(invocation.source(), label, invocation.arguments());
			}

			@Override
			public List<String> suggest(Invocation invocation) {
				return handleSuggestion(invocation.source(), label, invocation.arguments());
			}
		});
	}

	private void registerCompleter(String label, Method method, Object owner) {
		completerMap.put(label.toLowerCase(), new AbstractMap.SimpleEntry<>(method, owner));
	}

	private void handleCommand(com.velocitypowered.api.command.CommandSource source, String label, String[] args) {
		for (int i = args.length; i >= 0; i--) {
			StringBuilder key = new StringBuilder(label.toLowerCase());
			for (int x = 0; x < i; x++) {
				key.append(".").append(args[x].toLowerCase());
			}

			Entry<Method, Object> entry = commandMap.get(key.toString());
			if (entry == null) {
				continue;
			}

			Command command = entry.getKey().getAnnotation(Command.class);
			if (source instanceof Player) {
				Player player = (Player) source;
				ProxyPlayer proxyPlayer = ProxyPlayer.getPlayer(player.getUniqueId());
				if (proxyPlayer != null) {
					if (!proxyPlayer.hasGroupPermission(command.groupToUse())) {
						player.sendMessage(br.com.weavenmc.commons.velocity.VelocityText.of(
								"§c§lPERMISSAO§f Você não tem §c§lPERMISSAO§f para executar este comando!"));
						return;
					}
					if (proxyPlayer.getServerConnectedType() == ServerType.LOGIN) {
						return;
					}
				}
			}

			Runnable action = () -> {
				try {
					entry.getKey().invoke(entry.getValue(), new VelocityCommandSender(source), label, args);
				} catch (IllegalAccessException | InvocationTargetException ex) {
					ex.printStackTrace();
				}
			};
			if (command.runAsync()) {
				plugin.getProxy().getScheduler().buildTask(plugin, action).schedule();
			} else {
				action.run();
			}
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> handleSuggestion(com.velocitypowered.api.command.CommandSource source, String label, String[] args) {
		if (!(source instanceof Player)) {
			return java.util.Collections.emptyList();
		}
		Player player = (Player) source;
		for (int i = args.length; i >= 0; i--) {
			StringBuilder key = new StringBuilder(label.toLowerCase());
			for (int x = 0; x < i; x++) {
				key.append(".").append(args[x].toLowerCase());
			}

			Entry<Method, Object> entry = completerMap.get(key.toString());
			if (entry == null) {
				continue;
			}

			try {
				return (List<String>) entry.getKey().invoke(entry.getValue(), player, label, args);
			} catch (IllegalAccessException | InvocationTargetException ex) {
				ex.printStackTrace();
				return java.util.Collections.emptyList();
			}
		}
		return java.util.Collections.emptyList();
	}

	@Override
	public Object getPlugin() {
		return plugin;
	}
}
