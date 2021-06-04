package com.spring.fleamarket.domain.auth.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.spring.fleamarket.domain.auth.exception.InvalidHeaderFormatException;
import com.spring.fleamarket.domain.auth.exception.NotFoundAuthenticationHeaderException;

public interface JwtTokenService {
	
	public Authentication getAuthentication(String token);
	
	public String getJwtToken(HttpServletRequest request) throws NotFoundAuthenticationHeaderException, InvalidHeaderFormatException;

	public String generateAccessToken(int id, String username);

	public int getIdFromJwtToken(String token);

	public String getUsernameFromJwtToken(String token);

	public void verifyJwtToken(String token) throws TokenExpiredException, JWTVerificationException, Exception;
	
}
