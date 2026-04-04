package br.com.weavenmc.commons.core.backend.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.com.weavenmc.commons.core.backend.Backend;
import br.com.weavenmc.commons.core.data.player.category.DataCategory;
import br.com.weavenmc.commons.core.data.player.type.DataType;
import lombok.Getter;

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

@Getter
public class MySQLBackend implements Backend {

	private MySQL mySQL;
	private Connection connection;

	public MySQLBackend(MySQL mySQL) {
		this.mySQL = mySQL;
	}

	private String createIfNotExistsQuery(DataCategory category, DataType... dataTypes) {
		StringBuilder b = new StringBuilder();
		b.append("CREATE TABLE IF NOT EXISTS `" + category.getTableName() + "` (");

		int inx = 0;
		int max = dataTypes.length;

		b.append("`uniqueId` VARCHAR(50)");

		while (inx < max) {
			DataType current = dataTypes[inx];

			b.append(", `" + current.getField() + "` " + current.getTableType());

			inx++;
		}

		b.append(");");
		return b.toString();
	}

	public void createTablesIfNotExists() throws SQLException {
		for (DataCategory category : DataCategory.values()) {
			PreparedStatement s = preparedStatement(createIfNotExistsQuery(category, category.getDataTypes()));
			s.execute();
			s.close();
		}
	}

	@Override
	public void openConnection() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + mySQL.getHostname() + ":" + mySQL.getPort() + "/"
				+ mySQL.getDatabase() + "?autoReconnect=true", mySQL.getUsername(), mySQL.getPassword());
	}

	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	@Override
	public void closeConnection() throws SQLException {
		if (connection == null)
			return;
		connection.close();
	}

	@Override
	public boolean isConnected() {
		if (connection == null)
			return false;

		try {
			return !connection.isClosed();
		} catch (SQLException ex) {
			return false;
		}
	}

	@Override
	public void recallConnection() throws SQLException {
		if (isConnected())
			return;
		openConnection();
	}
}
