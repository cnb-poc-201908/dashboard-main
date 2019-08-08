package com.bmw.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.core.instrument.util.StringUtils;

public class JsonUtils {

	private final static ObjectMapper mapper = new ObjectMapper();
	
	public static Boolean hasNode(String jsonStr,String key) throws IOException {
		JsonNode node = mapper.readTree(jsonStr);
		String str = node.path(key).asText();
		if(StringUtils.isNotBlank(str)){
			return true;
		}
		return false;
    }
	
}
