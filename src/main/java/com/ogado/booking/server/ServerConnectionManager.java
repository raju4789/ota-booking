package com.ogado.booking.server;

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
	private static HttpServer httpServer;
	
	private static HttpServer getHttpServer() throws ConfigurationException, IOException {
		if(httpServer == null) {
			ServerConfiguration serverConfiguration = ConfigLoader
					.loadConfiguration(ApplicationConstants.SERVER_CONFIG_FILE, ServerConfiguration.class);
			httpServer = HttpServer.create(new InetSocketAddress(serverConfiguration.getPort()), 0);

		}
		
		return httpServer;
	}

	public static void startServer(){
		try {
			
			getHttpServer().createContext(ApplicationConstants.BOOKINGS_URI, new FilterBookingsHandler());
			getHttpServer().createContext(ApplicationConstants.AMEND_URI, new UpdateBookingsHandler());
			getHttpServer().createContext(ApplicationConstants.CREATE_URI, new CreateBookingsHandler());


			getHttpServer().start();
			
			String[] temp = httpServer.getAddress().toString().split(":");
			String portNumber = temp[temp.length-1];
			log.info("supplier service started running at port : " + portNumber);

		} catch (ConfigurationException | IOException e) {
			log.error("failed to start server : " + e.getMessage());
		} 

	}

}

