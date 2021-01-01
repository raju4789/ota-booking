package com.ogado.booking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		String insertQuery = bookingQueries.getInsertBooking();

		PreparedStatement stmt = dbConnection.prepareStatement(insertQuery);
		stmt.setString(1, bookingInfo.getBookingId());
		stmt.setString(2, bookingInfo.getCheckInDate());
		stmt.setString(3, bookingInfo.getCheckOutDate());
		stmt.setString(4, bookingInfo.getHotelName());
		stmt.setInt(5, bookingInfo.getNoOfGuests());
		stmt.setString(6, bookingInfo.getStatus());
		stmt.setString(7, bookingInfo.getBookingReference());
		stmt.setString(8, bookingInfo.getCreatedOn());
		stmt.setString(9, bookingInfo.getUpdatedOn());

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
	public List<BookingInfo> filterBookingsByCriteria(String checkInDate, String checkOutDate, String status)
			throws SQLException {
		if (dbConnection == null) {
			log.error("Failed to acquire db connection");
		}

		String filterQuery = formSearchQueryByCriteria(status, checkInDate, checkOutDate);

		Statement stmt = dbConnection.createStatement();

		List<BookingInfo> filteredQueries = new ArrayList<>();
		ResultSet rs = stmt.executeQuery(filterQuery);

		while (rs.next()) {

			BookingInfo curBooking = getBookingFromResultSet(rs);

			filteredQueries.add(curBooking);
		}

		return filteredQueries;
	}

	@Override
	public void updateBooking(BookingInfo bookingInfo) throws SQLException {

		if (dbConnection == null) {
			log.error("Failed to acquire db connection");

		}
		PreparedStatement stmt = dbConnection.prepareStatement(bookingQueries.getSelectBooking());
		stmt.setString(1, bookingInfo.getBookingId());

		ResultSet rs = stmt.executeQuery();

		if (rs.next()) {
			stmt = dbConnection.prepareStatement(bookingQueries.getUpdateBooking());
			stmt.setString(1, bookingInfo.getCheckInDate());
			stmt.setString(2, bookingInfo.getCheckOutDate());
			stmt.setString(3, bookingInfo.getStatus());
			stmt.setString(4, bookingInfo.getUpdatedOn());
			stmt.setString(5, bookingInfo.getBookingId());
			stmt.executeUpdate();
		}else {
			log.error("No booking found with given booking id");
		}

		stmt.close();
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
			return getBookingFromResultSet(rs);
		}

		return null;
	}

	@Override
	public List<BookingInfo> filterBookingsByCheckInDate(String checkInDate) throws SQLException {
		if (dbConnection == null) {
			log.error("Failed to acquire db connection");
		}

		String filterQuery = formSearchQueryByCheckInDate(checkInDate);

		Statement stmt = dbConnection.createStatement();

		List<BookingInfo> filteredQueries = new ArrayList<>();
		ResultSet rs = stmt.executeQuery(filterQuery);

		while (rs.next()) {

			BookingInfo curBooking = getBookingFromResultSet(rs);

			filteredQueries.add(curBooking);
		}

		return filteredQueries;
	}

	private void createBookingsTable() throws SQLException {
		String sql = bookingQueries.getCreateBookingTable();

		Statement stmt = dbConnection.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}

	private static String formSearchQueryByCriteria(String status, String checkInDate, String checkOutDate) {
		String searchQuery = bookingQueries.getFilterBookingsByCriteria();

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

	private String formSearchQueryByCheckInDate(String checkInDate) {
		String searchQuery = bookingQueries.getFilterBookingsByCheckInDate();

		searchQuery += "DATE('" + checkInDate + "')";

		return searchQuery;
	}
	
	private static BookingInfo getBookingFromResultSet(ResultSet rs) throws SQLException {
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setBookingId(rs.getString("booking_id"));
		bookingInfo.setCheckInDate(rs.getString("check_in_date"));
		bookingInfo.setCheckOutDate(rs.getString("check_out_date"));
		bookingInfo.setHotelName(rs.getString("hotel_name"));
		bookingInfo.setNoOfGuests(rs.getInt("no_of_guests"));
		bookingInfo.setStatus(rs.getString("status"));
		bookingInfo.setBookingReference(rs.getString("booking_reference"));
		bookingInfo.setCreatedOn(rs.getString("created_on"));
		bookingInfo.setUpdatedOn(rs.getString("updated_on"));
		return bookingInfo;
	}

}
