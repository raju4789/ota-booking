package com.ogado.booking.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonMapper {

	private static ObjectMapper jsonMapper = defaultObjectMapper();

	private static ObjectMapper defaultObjectMapper() {

		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return om;

	}

	public static <A> A parse(String jsonSrc, Class<A> cls) throws IOException {
		JsonNode jsonNode = jsonMapper.readTree(jsonSrc);
		return jsonMapper.treeToValue(jsonNode, cls);
	}

	public static <A> List<A> parseList(InputStream inputStream, Class<A> cls) throws IOException {

		CollectionType collectionType = jsonMapper.getTypeFactory().constructCollectionType(List.class, cls);

		return jsonMapper.readValue(inputStream, collectionType);

	}

	public static String stringifyPretty(Object obj) throws JsonProcessingException {
		JsonNode jsonNode = jsonMapper.valueToTree(obj);
		;
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
