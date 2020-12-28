package com.ogado.booking.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {

	private static ObjectMapper jsonMapper = defaultObjectMapper();

	private static ObjectMapper defaultObjectMapper() {

		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;

	}

	public static JsonNode parse(String jsonSrc) throws IOException {
		return jsonMapper.readTree(jsonSrc);
	}

	public static <A> A fromJson(JsonNode jsonNode, Class<A> cls) throws JsonProcessingException {
		return jsonMapper.treeToValue(jsonNode, cls);
	}

	public static JsonNode toJson(Object obj) {
		return jsonMapper.valueToTree(obj);
	}

	public static String stringify(JsonNode jsonNode) throws JsonProcessingException {

		return generateJson(jsonNode, false);

	}

	public static String stringifyPretty(JsonNode jsonNode) throws JsonProcessingException {

		return generateJson(jsonNode, true);

	}

	private static String generateJson(Object obj, boolean pretty) throws JsonProcessingException {
		ObjectWriter ObjWriter = jsonMapper.writer();

		if (pretty) {
			ObjWriter = ObjWriter.with(SerializationFeature.INDENT_OUTPUT);
		}

		return ObjWriter.writeValueAsString(obj);
	}

}
