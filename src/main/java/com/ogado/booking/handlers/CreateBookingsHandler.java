package com.ogado.booking.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.ogado.booking.constants.HTTPStatus;
import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.models.BookingResponse;
import com.ogado.booking.services.BookingService;
import com.ogado.booking.services.IBookingService;
import com.ogado.booking.utils.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CreateBookingsHandler implements HttpHandler {

	private static Logger log = Logger.getLogger(CreateBookingsHandler.class);

	public void handle(HttpExchange httpExchange) {
		BookingResponse bookingResponse = null;

		try {
			IBookingService bookingService = new BookingService();
			BookingInfo bookingInfo = getRequestObject(httpExchange.getRequestBody());
			
			log.info("createBookingsHandler called with request: "+ bookingInfo);
			bookingResponse = bookingService.createBooking(bookingInfo);
		} catch (Exception e) {
			log.error("failed to create booking: "+ e.getMessage());
			e.printStackTrace();

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

	private BookingInfo getRequestObject(InputStream inputStream) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		return JsonMapper.parse(sb.toString(), BookingInfo.class);

	}

}
