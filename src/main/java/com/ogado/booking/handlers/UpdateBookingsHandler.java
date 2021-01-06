package com.ogado.booking.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ogado.booking.constants.HTTPStatus;
import com.ogado.booking.exceptions.ConfigurationException;
import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.models.BookingResponse;
import com.ogado.booking.services.BookingService;
import com.ogado.booking.services.IBookingService;
import com.ogado.booking.utils.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UpdateBookingsHandler implements HttpHandler {
	
	private static Logger log = Logger.getLogger(UpdateBookingsHandler.class);

	public void handle(HttpExchange httpExchange) throws IOException {
		BookingResponse bookingResponse = null;
		try {
			IBookingService bookingService = new BookingService();
			List<BookingInfo> filteredBookings = JsonMapper.parseList(httpExchange.getRequestBody(), BookingInfo.class);
			
			log.info("UpdateBookingsHandler called");

			bookingService.amendBooking(filteredBookings);
		} catch (IOException | ConfigurationException | SQLException e) {
			log.error("failed to update bookings: "+ e.getMessage());

			bookingResponse = new BookingResponse();
			bookingResponse.setHttpStatus(HTTPStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			String bookingResponseStr = JsonMapper.stringifyPretty(bookingResponse);
			OutputStream outputStream = httpExchange.getResponseBody();
			httpExchange.getResponseHeaders().set("Content-Type", "appication/json");
			httpExchange.sendResponseHeaders(bookingResponse.getHttpStatus(), bookingResponseStr.length());
			outputStream.write(bookingResponseStr.getBytes());

			outputStream.flush();

			outputStream.close();
		} catch (IOException e) {
			log.error("failed to send response: "+ e.getMessage());
		}
	}	

}
