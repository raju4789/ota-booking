package com.ogado.booking.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ClientConnectionManager {
	private static HttpClient httpClient;

	private static HttpClient getHTTPClient() {
		if (httpClient == null) {
			httpClient = HttpClient.newHttpClient();
		}

		return httpClient;

	}

	public static HttpResponse<String> post(String uri, String requestBody) throws Exception {
		HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(requestBody)).build();

		return getHTTPClient().send(request, BodyHandlers.ofString());

	}
}
