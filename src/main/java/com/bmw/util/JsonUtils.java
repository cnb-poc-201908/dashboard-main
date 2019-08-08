package com.bmw.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.StringUtils;

public class JsonUtils {

	private final static ObjectMapper mapper = new ObjectMapper();

	public static Boolean hasNode(String jsonStr, String key) throws IOException {
		JsonNode node = mapper.readTree(jsonStr);
		String str = node.path(key).asText();
		if (StringUtils.isNotBlank(str)) {
			return true;
		}
		return false;
	}

	private static final List<String> amountLabor = Collections.unmodifiableList(Arrays.asList("6", "7", "8.5"));
	private static final int amountLaborSIZE = amountLabor.size();
	private static final Random RANDOM = new Random();

	public static String randomAmountLabor() {
		return amountLabor.get(RANDOM.nextInt(amountLaborSIZE));
	}
	
	public static boolean randomActivity() {
		return RANDOM.nextBoolean();
	}

}
