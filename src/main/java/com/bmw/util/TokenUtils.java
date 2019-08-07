package com.bmw.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class TokenUtils {
	
	@Autowired
	 private Environment env;
	
	private static String accessTokenUri; 
	
	private static String clientSecret;
	
	private static String clientId;
	
	private static String grantType;
	

	 
    @PostConstruct
    public void readConfig() {
    	accessTokenUri =  env.getProperty("security.oauth2.client.accessTokenUri");
    	clientSecret = env.getProperty("security.oauth2.client.clientSecret");
    	clientId = env.getProperty("security.oauth2.client.clientId");
    	grantType = env.getProperty("security.oauth2.client.grantType");
    }

    
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static String getToken(){
		String accessToken = null;
		RestTemplate restTemplate = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> oauth2Settings = new HashMap<>();
        oauth2Settings.put("client_id", clientId);
        oauth2Settings.put("client_secret", clientSecret);
        oauth2Settings.put("grant_type", grantType);
        try {
			String payload = objectMapper.writeValueAsString(oauth2Settings);
			HttpEntity<String> request = new HttpEntity<>(payload, headers);
			String result = restTemplate.postForObject(accessTokenUri, request, String.class);
			JsonNode root = objectMapper.readTree(result);
			accessToken = root.findValue("access_token").asText();
			log.info("token provider refreshed accessToken:{}", accessToken);
		} catch (IOException e) {
			log.error("Failed to retrieve token from server", e);
		}
        return accessToken;
    }
}
