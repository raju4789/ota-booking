package com.ogado.booking.utils;

import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ogado.booking.exceptions.ConfigurationException;

public class ConfigLoader {
	
	private static Logger log = Logger.getLogger(ConfigLoader.class);
	
	public static <A> A loadConfiguration(String fileName, Class<A> cls) throws ConfigurationException {
		try (FileReader fileReader = new FileReader(fileName)) {
			StringBuffer sb = new StringBuffer();
			int i;
			while ((i = fileReader.read()) != -1) {
				sb.append((char) i);
			}

			JsonNode conf = JsonMapper.parse(sb.toString());

			return JsonMapper.fromJson(conf, cls);

		} catch (JsonProcessingException e) {
			log.error("Failed to parse configuration file: " + e.getMessage());
			throw new ConfigurationException(e.getMessage());
		} catch (IOException e) {
			log.error("Failed to read configuration file: " + e.getMessage());
			throw new ConfigurationException(e.getMessage());
		}
	}

}
