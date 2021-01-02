package com.ogado.booking.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ogado.booking.models.BookingInfo;

public class APIValidationUtil {

	public static List<String> validateRequest(BookingInfo bookingInfo) {
		
		List<String> errors = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 

		if (bookingInfo.getCheckInDate() == null) {
			errors.add("checkInDate is mandatory");
		}else {
			if(LocalDateTime.parse(bookingInfo.getCheckInDate(), formatter).isBefore(LocalDateTime.now())) {
				errors.add("checkInDate can't be less than current date");
			}
		}
		
		if(LocalDateTime.parse(bookingInfo.getCheckOutDate(), formatter).isBefore(LocalDateTime.parse(bookingInfo.getCheckInDate(), formatter))) {
			errors.add("checkOutDate can't be less than checkInDate");
		}

		if (bookingInfo.getHotelName() == null) {
			errors.add("hotelName is mandatory");
		}

		if (bookingInfo.getNoOfGuests() <= 0) {
			errors.add("noOfGuests can't be less than 0");
		}

		return errors;
	}

}
