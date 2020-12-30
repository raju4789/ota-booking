package com.ogado.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ogado.booking.config.db.DBConnectionManager;
import com.ogado.booking.constants.ApplicationConstants;
import com.ogado.booking.exceptions.ConfigurationException;
import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.utils.ConfigLoader;

public class BookingDAO implements IBookingDAO {

	private Connection dbConnection;
	private static BookingQueries bookingQueries;

	private static Logger log = Logger.getLogger(BookingDAO.class);

	public BookingDAO() throws ConfigurationException, SQLException {
		dbConnection = DBConnectionManager.getDBConnection();
		bookingQueries = ConfigLoader.loadConfiguration(ApplicationConstants.DB_QUERIES_CONFIG_FILE,
				BookingQueries.class);
		createBookingsTable();
	}

	@Override
	public BookingInfo saveBooking(BookingInfo bookingInfo) throws SQLException {

		if (dbConnection == null) {
			log.error("Failed to acquire db connection");

		}

		LocalDateTime checkInDate = LocalDateTime.parse(bookingInfo.getCheckInDate());
		LocalDateTime checkOutDate = LocalDateTime.parse(bookingInfo.getCheckOutDate());

		LocalDateTime createdOn = LocalDateTime.parse(bookingInfo.getCreatedOn());
		LocalDateTime updatedOn = LocalDateTime.parse(bookingInfo.getUpdatedOn());

		String insertQuery = bookingQueries.getInsertBooking();

		PreparedStatement stmt = dbConnection.prepareStatement(insertQuery);
		stmt.setString(1, bookingInfo.getBookingId());
		stmt.setTimestamp(2, Timestamp.valueOf(checkInDate));
		stmt.setTimestamp(3, Timestamp.valueOf(checkOutDate));
		stmt.setString(4, bookingInfo.getHotelName());
		stmt.setInt(5, bookingInfo.getNoOfGuests());
		stmt.setString(6, bookingInfo.getStatus());
		stmt.setString(7, bookingInfo.getBookingReference());
		stmt.setTimestamp(8, Timestamp.valueOf(createdOn));
		stmt.setTimestamp(9, Timestamp.valueOf(updatedOn));

		int row = stmt.executeUpdate();

		if (row != 1) {
			log.error("failed to create booking");
			return null;
		}

		log.info("Succesfully created booking");

		stmt.close();

		return bookingInfo;

	}

	@Override
	public List<BookingInfo> filterBookings(String checkInDate, String checkOutDate, String status)
			throws SQLException {
		if (dbConnection == null) {
			log.error("Failed to acquire db connection");
		}

		String filterQuery = formSearchQuery(status, checkInDate, checkOutDate);

		Statement stmt = dbConnection.createStatement();

		List<BookingInfo> filteredQueries = new ArrayList<>();
		ResultSet rs = stmt.executeQuery(filterQuery);

		while (rs.next()) {

			BookingInfo curBooking = new BookingInfo();
			curBooking.setBookingId(rs.getString("booking_id"));
			curBooking.setCheckInDate(rs.getTimestamp("check_in_date").toString());
			curBooking.setCheckOutDate(rs.getTimestamp("check_out_date").toString());
			curBooking.setHotelName(rs.getString("hotel_name"));
			curBooking.setNoOfGuests(rs.getInt("no_of_guests"));
			curBooking.setStatus(rs.getString("status"));
			curBooking.setBookingReference(rs.getString("booking_reference"));
			curBooking.setCreatedOn(rs.getTimestamp("created_on").toString());
			curBooking.setUpdatedOn(rs.getTimestamp("updated_on").toString());

			filteredQueries.add(curBooking);
		}

		return filteredQueries;
	}

	@Override
	public void updateBooking(BookingInfo bookingInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public BookingInfo getBookingById(String bookingId) throws SQLException {

		if (dbConnection == null) {
			log.error("Failed to acquire db connection");

		}
		PreparedStatement stmt = dbConnection.prepareStatement(bookingQueries.getSelectBooking());
		stmt.setString(1, bookingId.toString());

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			BookingInfo curBooking = new BookingInfo();
			curBooking.setBookingId(rs.getString("booking_id"));
			curBooking.setCheckInDate(rs.getTimestamp("check_in_date").toString());
			curBooking.setCheckOutDate(rs.getTimestamp("check_out_date").toString());
			curBooking.setHotelName(rs.getString("hotel_name"));
			curBooking.setNoOfGuests(rs.getInt("no_of_guests"));
			curBooking.setStatus(rs.getString("status"));
			curBooking.setBookingReference(rs.getString("booking_reference"));
			curBooking.setCreatedOn(rs.getTimestamp("created_on").toString());
			curBooking.setUpdatedOn(rs.getTimestamp("updated_on").toString());

			return curBooking;
		}

		return null;
	}

	private void createBookingsTable() throws SQLException {
		String sql = bookingQueries.getCreateBookingTable();

		Statement stmt = dbConnection.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	private static String formSearchQuery(String status, String checkInDate, String checkOutDate) {
		String searchQuery = bookingQueries.getFilterBookings();

		if (status != null) {
			searchQuery += " AND status = '" + status.toUpperCase() + "'";
		}

		if (checkInDate != null) {
			searchQuery += " AND DATE(check_in_date) = DATE('" + checkInDate + "')";
		}

		if (checkOutDate != null) {
			searchQuery += " AND DATE(check_in_date) = DATE('" + checkOutDate + "')";
		}
		return searchQuery;
	}

}
