package br.com.weavenmc.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import br.com.weavenmc.commons.WeavenMC;
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

public class GeoIpUtils {

	private static final Map<String, IpInfo> CACHE = new HashMap<>();

	public static IpInfo getIpInfo(String ip) throws IOException {
		if (CACHE.containsKey(ip))
			return CACHE.get(ip);
		URLConnection con = new URL("https://geoip-db.com/json/" + ip).openConnection();
		IpInfo ipInfo = WeavenMC.getGson()
				.fromJson(new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")), IpInfo.class);
		return CACHE.computeIfAbsent(ip, v -> ipInfo);
	}

	@Getter
	public static class IpInfo {
		private String country_code;
		private String country_name;
		private String city;
		private String postal;
		private String latitude;
		private String longitude;
		private String IPv4;
		private String state;
	}
}
