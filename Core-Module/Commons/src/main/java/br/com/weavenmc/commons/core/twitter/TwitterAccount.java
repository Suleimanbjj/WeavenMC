package br.com.weavenmc.commons.core.twitter;

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
public enum TwitterAccount {

	WEAVENBANS("YM1192AiDsOpClWFUxHcS7i9h ", "DqTxOxD7aLnxGjjoGTYbveObhtjFdAVFUXBzyqJMs1HDhl02Cg ",
			"944744897239797761-Ijkhw5ejt8ZcvmRc1PrlPrYAXZxE592", "uDUregorBz5XtFVi5wsKV50hLvI3Adpy1QwalrYEmKkKi ");

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessSecret;

	TwitterAccount(String consumerKey, String consumerSecret, String accessToken, String accessSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessSecret = accessSecret;
		this.accessToken = accessToken;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

}
