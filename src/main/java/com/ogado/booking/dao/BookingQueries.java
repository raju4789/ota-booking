package com.ogado.booking.dao;

public class BookingQueries {

	private String createBookingTable;
	private String insertBooking;
	private String updateBooking;
	private String selectBooking;
	private String filterBookingsByCriteria;
	private String filterBookingsByCheckInDate;


	public BookingQueries() {
		super();
	}


	public BookingQueries(String createBookingTable, String insertBooking, String updateBooking, String selectBooking,
			String filterBookingsByCriteria, String filterBookingsByCheckInDate) {
		super();
		this.createBookingTable = createBookingTable;
		this.insertBooking = insertBooking;
		this.updateBooking = updateBooking;
		this.selectBooking = selectBooking;
		this.filterBookingsByCriteria = filterBookingsByCriteria;
		this.filterBookingsByCheckInDate = filterBookingsByCheckInDate;
	}


	public String getCreateBookingTable() {
		return createBookingTable;
	}


	public void setCreateBookingTable(String createBookingTable) {
		this.createBookingTable = createBookingTable;
	}


	public String getInsertBooking() {
		return insertBooking;
	}


	public void setInsertBooking(String insertBooking) {
		this.insertBooking = insertBooking;
	}


	public String getUpdateBooking() {
		return updateBooking;
	}


	public void setUpdateBooking(String updateBooking) {
		this.updateBooking = updateBooking;
	}


	public String getSelectBooking() {
		return selectBooking;
	}


	public void setSelectBooking(String selectBooking) {
		this.selectBooking = selectBooking;
	}


	public String getFilterBookingsByCriteria() {
		return filterBookingsByCriteria;
	}


	public void setFilterBookingsByCriteria(String filterBookingsByCriteria) {
		this.filterBookingsByCriteria = filterBookingsByCriteria;
	}


	public String getFilterBookingsByCheckInDate() {
		return filterBookingsByCheckInDate;
	}


	public void setFilterBookingsByCheckInDate(String filterBookingsByCheckInDate) {
		this.filterBookingsByCheckInDate = filterBookingsByCheckInDate;
	}

}
