package com.ogado.booking.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApplicationConstants {

	public static final String SERVER_CONFIG_FILE = "src/main/resources/server-config.json";
	public static final String DB_QUERIES_CONFIG_FILE = "src/main/resources/booking-queries.json";

	public static final String CREATE_URI = "/booking/v1/create";
	public static final String AMEND_URI = "/booking/v1/amend";
	public static final String FILTER_BOOKINGS_BY_CHECKIN_DATE_URI = "/booking/v1/filter";
	public static final String FILTER_BOOKINGS_BY_CRITERIA_DATE_URI = "/booking/v1/bookings";


	public static final String DEV_ENV = "dev";

	private static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("dev", "src/main/resources/dev-api-urls.json");
		map.put("prod", "src/main/resources/prod-api-urls.json");
		map.put(null, "src/main/resources/dev-api-urls.json");

	}

	public static final Map<String, String> ENV_API_URLS = Collections.unmodifiableMap(map);

	private static Map<String, String> dbMap = new HashMap<String, String>();

	static {
		dbMap.put("dev", "src/main/resources/dev-db-config.json");
		dbMap.put("prod", "src/main/resources/prod-db-config.json");
		dbMap.put(null, "src/main/resources/dev-db-config.json");

	}

	public static final Map<String, String> DB_CONFIG = Collections.unmodifiableMap(dbMap);

	public static final String STATUS_CONFIRMED = "CONFIRMED";
	public static final String STATUS_CANCELED = "CANCELED";

}
