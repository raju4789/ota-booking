package com.ogado.booking.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ogado.booking.constants.ApplicationConstants;
import com.ogado.booking.exceptions.ConfigurationException;
import com.ogado.booking.utils.ConfigLoader;

public class DBConnectionManager {

	private static Logger log = Logger.getLogger(DBConnectionManager.class);

	private static Connection dbConnection;

	private DBConnectionManager() {
	};

	public static Connection getDBConnection() {

		if (dbConnection != null) {
			return dbConnection;
		}
		
		try {
			DBConfig config = ConfigLoader.loadConfiguration(ApplicationConstants.DB_CONFIG.get(System.getProperty("env")), DBConfig.class);
			Class.forName(config.getDriverName());
			dbConnection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
			return dbConnection;
		} catch (SQLException | ClassNotFoundException | ConfigurationException e) {
			log.error("failed to connect to db : "+ e.getMessage());
			return null;
		} 

		
	}

}
