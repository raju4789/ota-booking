package com.ogado.booking.services;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.ToStringConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogado.booking.models.BookingInfo;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings("resource")
public class BookingServiceIT {
	static GenericContainer mysql;
	static GenericContainer app;
	static final OkHttpClient client = new OkHttpClient();
	static final Network network = Network.newNetwork();
	static final Logger logger = Logger.getLogger(BookingService.class);
	static final ObjectMapper mapper = new ObjectMapper();
	static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	static final String bookingsSqlLocation = "/usr/local/data/bookings.sql";
	static String baseURL;

	
	@BeforeClass
	public static void setUp() throws Exception {
		mysql = new GenericContainer("mysql:8.0.19").withExposedPorts(3306).withNetwork(network)
				.withNetworkAliases("mysql").withEnv("MYSQL_ROOT_PASSWORD", "root")
				.withClasspathResourceMapping("/sql/bootstrap.sql", "/docker-entrypoint-initdb.d/bootstrap.sql",
						BindMode.READ_ONLY)
				.withClasspathResourceMapping("/sql/bookings.sql", bookingsSqlLocation, BindMode.READ_WRITE);
		mysql.start();
		app = new GenericContainer("testcontainers:latest").withExposedPorts(8081)
				.withNetworkAliases("app").withEnv("spring_datasource_url", "jdbc:mysql://mysql:3306/userdata")
				.withNetwork(network);
		app.start();
		enableContainerLogger(app);
		baseURL = "http://" + app.getContainerIpAddress() + ":" + app.getFirstMappedPort();
		logger.info("URL for external app is " + baseURL);
	}
	
	/**
     * Enables container logging.
     * @param container
     */
    public static void enableContainerLogger(GenericContainer container) {
        ToStringConsumer toStringConsumer =  new ToStringConsumer();
        container.followOutput(toStringConsumer);
    }
    
    @After
    public void tearDown() throws Exception {
        mysql.execInContainer("/bin/bash",  "-c" , "mysql -uroot -proot userdata -e \"truncate table ogado-bookings;\"");
    }
    
    @Test
    public void saveBooking() throws IOException {
        BookingInfo booking = new BookingInfo();
        booking.setCheckInDate("");
        booking.setCheckOutDate("");
        booking.setHotelName("Paasha");
        booking.setNoOfGuests(4);

        String payload = mapper.writeValueAsString(booking);
        RequestBody body = RequestBody.create(JSON, payload);
        Request request = new Request.Builder()
                .url(baseURL + "/create")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        assertTrue(response.code() == 200);
    }

}
