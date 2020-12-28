package com.ogado.booking.models;

import java.util.List;

public class BookingResponse {
	
	private int httpStatus;
	private String bookingId;
	private List<String> errors;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	@Override
	public String toString() {
		return "BookingResponse [httpStatus=" + httpStatus + ", bookingId=" + bookingId + ", errors=" + errors + "]";
	}
	
	
}
