package com.ogado.booking.services;

import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.ogado.booking.client.ClientConnectionManager;
import com.ogado.booking.constants.ApplicationConstants;
import com.ogado.booking.constants.HTTPStatus;
import com.ogado.booking.dao.BookingDAO;
import com.ogado.booking.dao.IBookingDAO;
import com.ogado.booking.exceptions.ConfigurationException;
import com.ogado.booking.models.BookingInfo;
import com.ogado.booking.models.BookingResponse;
import com.ogado.booking.models.SupplierResponse;
import com.ogado.booking.utils.APIValidationUtil;
import com.ogado.booking.utils.JsonMapper;

public class BookingService implements IBookingService {

	private static Logger log = Logger.getLogger(BookingService.class);

	private IBookingDAO bookingDAO;

	public BookingService() throws ConfigurationException, SQLException {

		bookingDAO = new BookingDAO();

	}

	@Override
	public BookingResponse createBooking(BookingInfo bookingInfo) throws Exception {

		BookingResponse bookingResponse = new BookingResponse();

		List<String> errors = APIValidationUtil.validateRequest(bookingInfo);

		if (errors.size() > 0) {
			bookingResponse.setHttpStatus(HTTPStatus.BAD_REQUEST);
			bookingResponse.setErrors(errors);
			log.error("invalid booking request");

			return bookingResponse;
		}

		String bookingId = UUID.randomUUID().toString();

		while (bookingDAO.getBookingById(bookingId) != null) {
			bookingId = UUID.randomUUID().toString();
		}

		bookingInfo.setBookingId(bookingId);
		bookingInfo.setCreatedOn(LocalDateTime.now().toString());
		bookingInfo.setUpdatedOn(LocalDateTime.now().toString());

		BookingInfo supplierBooking = null;

		try {
			supplierBooking = storeInSupplier(bookingInfo);

		} catch (Exception e) {
			log.error("failed to save in supplier database : " + e.getMessage());
			bookingResponse.setHttpStatus(HTTPStatus.INTERNAL_SERVER_ERROR);
			errors.add("");
			bookingResponse.setErrors(errors);
			return bookingResponse;
		}

		if (supplierBooking == null) {
			log.error("failed to save in supplier database");
			bookingResponse.setHttpStatus(HTTPStatus.INTERNAL_SERVER_ERROR);
			errors.add("");
			bookingResponse.setErrors(errors);
			return bookingResponse;

		}

		BookingInfo dbBooking = bookingDAO.saveBooking(supplierBooking);

		if (dbBooking == null) {
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
	public List<BookingInfo> filterBookings(String checkInDate, String checkOutDate, String status)
			throws SQLException {
		LocalDate date = LocalDate.parse(checkInDate);

		if (date == null) {
			checkInDate = null;
		}

		date = LocalDate.parse(checkOutDate);

		if (date == null) {
			checkOutDate = null;
		}

		return bookingDAO.filterBookings(checkInDate, checkOutDate, status);
	}

	@Override
	public void amendBooking(List<BookingInfo> bookingInfo) {
		// TODO Auto-generated method stub

	}

	private BookingInfo storeInSupplier(BookingInfo bookingInfo) throws Exception {
		String requestBody = JsonMapper.stringifyPretty(bookingInfo);
		HttpResponse<String> res = ClientConnectionManager.post(ApplicationConstants.SUPPLIER_BOOKING_URI, requestBody);
		SupplierResponse supplierResponse = JsonMapper.parse(res.body(), SupplierResponse.class);

		if (supplierResponse != null) {
			return supplierResponse.getBookingInfo();
		}

		return null;

	}

}
