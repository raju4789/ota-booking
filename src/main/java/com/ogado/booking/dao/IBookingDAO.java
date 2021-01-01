package com.ogado.booking.dao;

import java.sql.SQLException;
import java.util.List;

import com.ogado.booking.models.BookingInfo;

public interface IBookingDAO {
	
	public BookingInfo saveBooking(BookingInfo bookingInfo) throws SQLException;
	
	public List<BookingInfo> filterBookingsByCriteria(String checkInDate, String checkOutDate, String status) throws SQLException;
	
	public List<BookingInfo> filterBookingsByCheckInDate(String checkInDate) throws SQLException;
	
	public void updateBooking(BookingInfo bookingInfo) throws SQLException;

	public BookingInfo getBookingById(String bookingId) throws SQLException;

}
