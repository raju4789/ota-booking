package com.ogado.booking.config.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.ogado.booking.constants.ApplicationConstants;
import com.ogado.booking.exceptions.ConfigurationException;
import com.ogado.booking.handlers.CreateBookingsHandler;
import com.ogado.booking.handlers.FilterBookingsHandler;
import com.ogado.booking.handlers.UpdateBookingsHandler;
import com.ogado.booking.utils.ConfigLoader;
import com.sun.net.httpserver.HttpServer;

public class ServerConnectionManager {

	private static Logger log = Logger.getLogger(ServerConnectionManager.class);

	public static void startServer(){
		HttpServer httpServer = null;
		try {
			ServerConfiguration serverConfiguration = ConfigLoader
					.loadConfiguration(ApplicationConstants.SERVER_CONFIG_FILE, ServerConfiguration.class);
			
			httpServer = HttpServer.create(new InetSocketAddress(serverConfiguration.getPort()), 0);

			httpServer.createContext(ApplicationConstants.CREATE_URI, new CreateBookingsHandler());
			httpServer.createContext(ApplicationConstants.BOOKINGS_URI, new FilterBookingsHandler());
			httpServer.createContext(ApplicationConstants.AMEND_URI, new UpdateBookingsHandler());

			httpServer.start();
			
			log.info("booking service started running at port : " + serverConfiguration.getPort());

		} catch (ConfigurationException | IOException e) {
			log.error("failed to start server : " + e.getMessage());
		} 

	}

}
