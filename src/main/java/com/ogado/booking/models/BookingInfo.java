package com.ogado.booking.models;

public class BookingInfo {

	private String bookingId;

	private String checkInDate;
	private String checkOutDate;

	private String hotelName;
	private int noOfGuests;

	private String status;
	private String bookingReference;

	private String createdOn;
	private String updatedOn;

	public BookingInfo() {
	}

	public BookingInfo(String bookingId, String checkInDate, String checkOutDate, String hotelName, int noOfGuests,
			String status, String bookingReference, String createdOn, String updatedOn) {
		super();
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.hotelName = hotelName;
		this.noOfGuests = noOfGuests;
		this.status = status;
		this.bookingReference = bookingReference;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getNoOfGuests() {
		return noOfGuests;
	}

	public void setNoOfGuests(int noOfGuests) {
		this.noOfGuests = noOfGuests;
	}

	public String getBookingReference() {
		return bookingReference;
	}

	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BookingInfo [bookingId=" + bookingId + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
				+ ", hotelName=" + hotelName + ", noOfGuests=" + noOfGuests + ", status=" + status
				+ ", bookingReference=" + bookingReference + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ "]";
	}

}
