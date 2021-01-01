package com.ogado.booking.services;

import java.sql.SQLException;
import java.util.List;

import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.models.BookingResponse;
import com.ogado.booking.models.FilteredBookings;

public interface IBookingService {
	
	public BookingResponse createBooking(BookingInfo bookingInfo) throws SQLException, Exception;
	
	public FilteredBookings filterBookingsByCheckInDate(String checkInDate) throws SQLException;
	
	public void amendBooking(List<BookingInfo> bookingInfo);

	public FilteredBookings filterBookingsByCriteria(String checkInDate, String checkOutDate, String status) throws SQLException;

}
