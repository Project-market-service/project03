package com.spring.fleamarket.global.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class JwtConfig {
	
	@Value("${jwt.header}")
	private String headerName;
	
	@Value("${jwt.tokenPrefix}")
	private String tokenPrefix;
	
	@Value("${jwt.validationTime}")
	private long validationTime;
	
	@Value("${jwt.secretKey}")
	private String secretKey;
	
}
