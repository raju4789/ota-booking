package com.ogado.booking.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.utils.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CreateBookingsHandler implements HttpHandler {

	private static Logger log = Logger.getLogger(CreateBookingsHandler.class);

	public void handle(HttpExchange httpExchange){
		log.info("Inside CreateBookingsHandler.");
		
		try {
			BookingInfo bookingInfo= getRequestObject(httpExchange.getRequestBody());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		

	}

	private BookingInfo getRequestObject(InputStream inputStream) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}
		
		JsonNode requestNode = JsonMapper.parse(sb.toString());
		return JsonMapper.fromJson(requestNode, BookingInfo.class);

	}

}
