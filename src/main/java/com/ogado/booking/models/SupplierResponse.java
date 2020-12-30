package com.ogado.booking.models;

import java.util.List;

public class SupplierResponse {
	
	private int httpStatus;
	private BookingInfo bookingInfo;
	private List<String> errors;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public BookingInfo getBookingInfo() {
		return bookingInfo;
	}
	public void setBookingInfo(BookingInfo bookingInfo) {
		this.bookingInfo = bookingInfo;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
