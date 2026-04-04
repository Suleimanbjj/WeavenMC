package br.com.weavenmc.commons.core.backend;

import java.sql.SQLException;
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

public interface Backend {

	void openConnection() throws SQLException;
	
	void closeConnection()  throws SQLException;
	
	boolean isConnected();
	
	void recallConnection() throws SQLException;
}
