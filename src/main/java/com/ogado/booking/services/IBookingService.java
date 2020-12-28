package com.ogado.booking.services;

import java.sql.SQLException;
import java.util.List;

import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.models.BookingResponse;

public interface IBookingService {
	
	public BookingResponse createBooking(BookingInfo bookingInfo) throws SQLException;
	
	public List<BookingInfo> filterBookings(String checkInDate, String checkOutDate, String status);
	
	public void amendBooking(List<BookingInfo> bookingInfo);

}
