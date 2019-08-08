package com.bmw.feign;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bmw.main.service.CDKService;
import com.bmw.redis.BaseRedisService;
import com.bmw.util.TokenUtils;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Import(FeignClientsConfiguration.class)
@Configuration
public class FeignClientConfig {

	@Value(value = "${api.serviceUri}")
	String serviceUri;
	
	@Autowired
	private BaseRedisService baseRedisService;

	@Bean
    Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
    } 
	
	@Bean
    FeignLoggerFactory infoFeignLoggerFactory() {
        return new InfoFeignLoggerFactory();
    }
	
	public static class InfoFeignLoggerFactory implements FeignLoggerFactory {

        @Override
        public Logger create(Class<?> type) {
            return new InfoFeignLogger(LoggerFactory.getLogger(type));
        }
    }
	
	@Bean
	public CDKService cDKClient(Contract contract, Decoder decoder, Encoder encoder) {
		return Feign.builder()
//				.contract(new SpringMvcContract())
//				.logger(new Slf4jLogger())
//				.logLevel(Logger.Level.FULL)
				.requestInterceptor(requestTemplate -> {
					String token = (String) baseRedisService.get("token");
					if (StringUtils.isEmpty(token)) {
						token = TokenUtils.getToken();
						baseRedisService.set("token", token, 60);
					}
					log.info("RequestInterceptor:{}", token);
					requestTemplate.header("Authorization", "Bearer " + token);
					requestTemplate.header("Accept-Language", "zh-CN");
				}).target(CDKService.class, serviceUri);
	}
}
