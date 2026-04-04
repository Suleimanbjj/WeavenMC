package br.com.weavenmc.commons.velocity.checker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import br.com.weavenmc.commons.WeavenMC;

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

public class CheckingConnectionAPI {

	private final static Map<String, CheckingConnection> connections = new ConcurrentHashMap<>();
	public final static AtomicBoolean fullyLoaded = new AtomicBoolean();
	private final static Object sync = new Object();

	public static CheckingConnection getOrCreateProxie(String ipAddress) {
		if (connections.containsKey(ipAddress))
			return connections.get(ipAddress);
		return connections.computeIfAbsent(ipAddress, p -> new CheckingConnection(ipAddress));
	}

	public static CheckingConnection getConnection(String ipAddress) {
		return connections.get(ipAddress);
	}

	public static Collection<CheckingConnection> getConnections() {
		return connections.values();
	}

	public static void saveProxy(CheckingConnection proxy, boolean update) {
		saveProxie(proxy, update);
	}

	public static void saveProxie(CheckingConnection proxy, boolean i) {
		WeavenMC.getAsynchronousExecutor().runAsync(() -> {
			try {
				if (i) {
					PreparedStatement update = WeavenMC.getCommonMysql()
							.preparedStatement("UPDATE `proxies` SET `allowed`='" + String.valueOf(proxy.isAllowed())
									+ "' WHERE `address`='" + proxy.getIpAddress() + "';");
					update.execute();
					update.close();
				} else {
					PreparedStatement insert = WeavenMC.getCommonMysql()
							.preparedStatement("INSERT INTO `proxies` (`address`, `allowed`) VALUES ('"
									+ proxy.getIpAddress() + "', '" + String.valueOf(proxy.isAllowed()) + "');");
					insert.execute();
					insert.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
	}

	public static void load() throws SQLException {
		synchronized (sync) {
			WeavenMC.debug("Iniciando o carregamento de Todos os proxies...");

			PreparedStatement create = WeavenMC.getCommonMysql().preparedStatement(
					"CREATE TABLE IF NOT EXISTS `proxies` (`address` VARCHAR(40), `allowed` VARCHAR(5));");
			create.execute();
			create.close();

			PreparedStatement selecting = WeavenMC.getCommonMysql().preparedStatement("SELECT * FROM `proxies`");
			ResultSet selectResult = selecting.executeQuery();
			while (selectResult.next()) {
				String ipAddress = selectResult.getString("address");
				boolean allowed = Boolean.valueOf(selectResult.getString("allowed"));
				CheckingConnection connection = new CheckingConnection(ipAddress);
				if (allowed)
					connection.allow();
				else
					connection.disallow();
				connection.setChecked(true);
				connection.error(null);
				connection.saved = true;
				connections.put(ipAddress, connection);
			}
			selectResult.close();
			selecting.close();

			WeavenMC.debug("Todos os proxies foram carregados com sucesso!");
			fullyLoaded.set(true);
		}
	}
}
