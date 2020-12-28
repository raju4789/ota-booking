package com.ogado.booking;

import com.ogado.booking.config.server.ServerConnectionManager;

public class BookingApplication {

	public static void main(String[] args) {
		ServerConnectionManager.startServer();
		
	}
	
}
