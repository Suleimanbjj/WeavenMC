package br.com.weavenmc.commons.velocity.checker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import br.com.weavenmc.commons.WeavenMC;
import br.com.weavenmc.commons.velocity.VelocityMain;

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

public class RunnableConnectionChecker implements Runnable {

	private CheckingConnection connection;
	private String apiKey;

	public RunnableConnectionChecker(CheckingConnection connection) {
		this.connection = connection;
		this.apiKey = VelocityMain.getInstance().getConfig().getString("velocity.vpnblockerkey");
	}

	@Override
	public void run() {
		connection.initiCheck();
		long ms = System.currentTimeMillis();
		Callable<Long> callable = new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				Response response = getResponse(connection.getIpAddress(), apiKey, 5000);
				if (response.status.equals("success")) {
					boolean legit = !response.hostip;
					if (legit) {
						connection.allow();
						String input = requestConnection(
								"http://check.getipintel.net/check.php?ip=" + connection.getIpAddress()
										+ "&contact=suleimanweaven@gmail.com&flags=m",
								15000, "Java-VPNDetection Library");
						if (input.equals("1")) {
							connection.disallow();
							connection.error(null);
						} else {
							connection.error(null);
						}
					} else {
						connection.disallow();
						connection.error(null);
					}
				}
				return (System.currentTimeMillis() - ms);
			}
		};

		Exception error = null;

		try {
			callable.call();
		} catch (Exception exception) {
			WeavenMC.getLogger().info("[VPN BLOCKER] CHECK ERROR -> " + exception.getMessage());
			error = exception;
		}

		if (error != null) {
			if (connection.isAllowed()) {
				connection.error(null);
			} else {
				connection.error(error);
			}
		} else {
			connection.error(null);
		}

		connection.setChecked(true);
		connection.setChecking(false);
	}

	public String requestConnection(String url, int timeout, String userAgent)
			throws MalformedURLException, IOException {
		StringBuilder response = new StringBuilder();
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		connection.setConnectTimeout(timeout);
		connection.setRequestProperty("User-Agent", userAgent);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			while ((url = in.readLine()) != null) {
				response.append(url);
			}
			in.close();
		}
		return response.toString();
	}

	public Response getResponse(String ip, String apiKey, int timeout) throws IOException {
		String url = createVpnBlockerUrl(ip, apiKey);
		String result = requestConnection(url, timeout, "Java-VPNDetection Library");
		return new Gson().fromJson(result, Response.class);
	}

	protected String createVpnBlockerUrl(String ip, String apiKey) {
		return "http://api.vpnblocker.net/v2/json/" + ip + "/" + apiKey;
	}

	protected class Response {

		public String status;
		public String msg;

		@SerializedName("package")
		public String getPackage;

		public String remaining_requests;
		public String ipaddress;

		@SerializedName("host-ip")
		public boolean hostip;

		public String hostname;
		public String org;

		public CS country;
		public CS subdivision;

		public String city;
		public String postal;

		public latlon location;

		public class CS {
			public String name;
			public String code;
		}

		public class latlon {
			public double lat;

			@SerializedName("long")
			public double lon;
		}

	}
}
