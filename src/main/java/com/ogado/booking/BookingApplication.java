package com.ogado.booking;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.ogado.booking.handlers.CreateBookingsHandler;
import com.ogado.booking.handlers.FilterBookingsHandler;
import com.ogado.booking.handlers.UpdateBookingsHandler;
import com.ogado.booking.utils.ApplicationConstants;

import com.sun.net.httpserver.HttpServer;

public class BookingApplication {
	
	private static Logger log = Logger.getLogger(BookingApplication.class);


	public static void main(String[] args) {
		try {
			createServer();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createServer() throws IOException {
		HttpServer httpServer = HttpServer.create(new InetSocketAddress(ApplicationConstants.SERVER_PORT), 0);

		httpServer.createContext(ApplicationConstants.CREATE_URI, new CreateBookingsHandler());
		httpServer.createContext(ApplicationConstants.BOOKINGS_URI, new FilterBookingsHandler());
		httpServer.createContext(ApplicationConstants.AMEND_URI, new UpdateBookingsHandler());

		httpServer.start();
		
		log.info("Booking service started running at port : "+ApplicationConstants.SERVER_PORT);
		

	}

}
