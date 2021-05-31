package com.spring.fleamarket.global.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

public interface JwtTokenService {
	
	public String createToken(int id, String username);
	
	public Authentication getAuthentication(String token);
	
	public String getJwtToken(HttpServletRequest request);

	public void setJwtToken(HttpServletResponse response, String token);
	
	public String getUsernameFromJwtToken(String token);
	
}
