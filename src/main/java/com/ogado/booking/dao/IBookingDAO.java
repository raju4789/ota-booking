package com.ogado.booking.dao;

import java.sql.SQLException;
import java.util.List;

import com.ogado.booking.models.BookingInfo;

public interface IBookingDAO {
	
	public BookingInfo saveBooking(BookingInfo bookingInfo) throws SQLException;
	
	public List<BookingInfo> filterBookings(String checkInDate, String checkOutDate, String status);
	
	public void updateBooking(BookingInfo bookingInfo);

	boolean isBookingExist(String bookingId) throws SQLException;

}