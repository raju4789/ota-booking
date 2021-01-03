package com.ogado.booking.client;

public class APIURL {
	private String supplierBookingURL;

	public APIURL() {
	}

	public APIURL(String supplierBookingURL) {
		super();
		this.supplierBookingURL = supplierBookingURL;
	}

	public String getSupplierBookingURL() {
		return supplierBookingURL;
	}

	public void setSupplierBookingURL(String supplierBookingURL) {
		this.supplierBookingURL = supplierBookingURL;
	}

}
