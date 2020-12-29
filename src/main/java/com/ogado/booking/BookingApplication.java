package com.ogado.booking;

import com.ogado.booking.server.ServerConnectionManager;

public class BookingApplication {

	public static void main(String[] args) {
		ServerConnectionManager.startServer();
		
	}
	
}
