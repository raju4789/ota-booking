package com.ogado.booking.services;

import java.sql.SQLException;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.ogado.booking.constants.HTTPStatus;
import com.ogado.booking.dao.BookingDAO;
import com.ogado.booking.dao.IBookingDAO;
import com.ogado.booking.exceptions.ConfigurationException;
import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.models.BookingResponse;
import com.ogado.booking.utils.APIValidationUtil;

public class BookingService implements IBookingService {

	private static Logger log = Logger.getLogger(BookingService.class);

	private IBookingDAO bookingDAO;

	public BookingService() throws ConfigurationException, SQLException {

		bookingDAO = new BookingDAO();

	}

	@Override
	public BookingResponse createBooking(BookingInfo bookingInfo) throws SQLException {
		
		BookingResponse bookingResponse = new BookingResponse();
		
		List<String> errors = APIValidationUtil.validateRequest(bookingInfo);
		
		if(errors.size() > 0) {
			bookingResponse.setHttpStatus(HTTPStatus.BAD_REQUEST);
			bookingResponse.setErrors(errors);
			log.error("invalid booking request");
			
			return bookingResponse;
		}
		
		String bookingId = UUID.randomUUID().toString();

		while (bookingDAO.isBookingExist(bookingId)) {
			bookingId = UUID.randomUUID().toString();
		}
		
		bookingInfo.setBookingId(bookingId);
		
		BookingInfo dbBooking = bookingDAO.saveBooking(bookingInfo);
		
		if(dbBooking == null) {
			bookingResponse.setHttpStatus(HTTPStatus.INTERNAL_SERVER_ERROR);
			errors.add("");
			bookingResponse.setErrors(errors);
			return bookingResponse;

		}
		
		bookingResponse.setHttpStatus(HTTPStatus.CREATED);
		bookingResponse.setBookingId(bookingId);
		return bookingResponse;
	}

	@Override
	public List<BookingInfo> filterBookings(String checkInDate, String checkOutDate, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void amendBooking(List<BookingInfo> bookingInfo) {
		// TODO Auto-generated method stub

	}

}
