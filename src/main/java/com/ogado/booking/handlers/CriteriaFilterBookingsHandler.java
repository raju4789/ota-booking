package com.ogado.booking.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ogado.booking.constants.HTTPStatus;
import com.ogado.booking.models.FilteredBookings;
import com.ogado.booking.services.BookingService;
import com.ogado.booking.services.IBookingService;
import com.ogado.booking.utils.CommonUtil;
import com.ogado.booking.utils.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CriteriaFilterBookingsHandler implements HttpHandler {
	
	private static Logger log = Logger.getLogger(CriteriaFilterBookingsHandler.class);


	public void handle(HttpExchange httpExchange) throws IOException {
		
		FilteredBookings filteredBookings = new FilteredBookings();
		try {
			IBookingService bookingService = new BookingService();
			Map<String, String> queryParams = CommonUtil.queryToMap(httpExchange.getRequestURI().getQuery());
			
			String checkInDate = queryParams.get("check_in_date");
			String checkOutDate = queryParams.get("check_out_date");
			String status = queryParams.get("status");
			
			filteredBookings = bookingService.filterBookingsByCriteria(checkInDate, checkOutDate, status);
			
			log.info("successfully fetched bookings by given criteria");

		} catch (Exception e) {
			log.error("failed to fetch bookings by given criteria : "+ e.getMessage());
			filteredBookings.setHttpStatus(HTTPStatus.INTERNAL_SERVER_ERROR);
			filteredBookings.setErrorMessage("failed to fetch bookings by given criteria");
		}
		
		try {
			String bookingsStr = JsonMapper.stringifyPretty(filteredBookings);
			OutputStream outputStream = httpExchange.getResponseBody();
			httpExchange.getResponseHeaders().set("Content-Type", "appication/json");
			httpExchange.sendResponseHeaders(HTTPStatus.OK, bookingsStr.length());
			outputStream.write(bookingsStr.getBytes());

			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			log.error("failed to fetch bookings by given criteria : " + e.getMessage());
		}	

	}

}

