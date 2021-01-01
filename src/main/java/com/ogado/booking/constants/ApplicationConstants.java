package com.ogado.booking.constants;

public class ApplicationConstants {
	
	public static final String SERVER_CONFIG_FILE = "src/main/resources/ServerConfig.json";
	public static final String DB_CONFIG_FILE = "src/main/resources/DBConfig.json";
	public static final String DB_QUERIES_CONFIG_FILE = "src/main/resources/BookingQueries.json";


	
	public static final String CREATE_URI = "/booking/v1/create";

	public static final String FILTER_BOOKINGS_BY_CRITERIA_DATE_URI = "/booking/v1/bookings";

	public static final String AMEND_URI = "/booking/v1/amend";
	
	public static final String FILTER_BOOKINGS_BY_CHECKIN_DATE_URI = "/booking/v1/filter";

	
	public static final String SUPPLIER_BOOKING_URI = "http://localhost:8082/supplier/v1/book";	
	
	public static final String STATUS_CONFIRMED  = "CONFIRMED";
	public static final String STATUS_CANCELED  = "CANCELED";

}
