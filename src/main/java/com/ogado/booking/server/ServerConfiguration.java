package com.ogado.booking.server;

public class ServerConfiguration {
	private int port;
	private String webroot;
	
	public ServerConfiguration() {}
	
	public ServerConfiguration(int port, String webroot) {
		super();
		this.port = port;
		this.webroot = webroot;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getWebroot() {
		return webroot;
	}
	public void setWebroot(String webroot) {
		this.webroot = webroot;
	}
	
	
}
