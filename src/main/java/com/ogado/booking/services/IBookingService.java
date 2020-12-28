package com.ogado.booking.services;

import java.util.List;

import com.ogado.booking.models.BookingInfo;

public interface IBookingService {
	
	public String createBooking(BookingInfo bookingInfo);
	
	public List<BookingInfo> filterBookings(String checkInDate, String checkOutDate, String status);
	
	public void amendBooking(BookingInfo bookingInfo);

}
