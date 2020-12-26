package com.ogado.booking.handlers;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CreateBookingsHandler implements HttpHandler {
	
	private static Logger log = Logger.getLogger(CreateBookingsHandler.class);

	public void handle(HttpExchange httpExchange) throws IOException {
		log.info("CreateBookingsHandler called");
	}

}
