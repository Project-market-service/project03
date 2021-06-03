package com.spring.fleamarket.domain.auth.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.spring.fleamarket.domain.auth.exception.InvalidRefreshTokenException;
import com.spring.fleamarket.domain.auth.exception.NotFoundRefreshTokenException;
import com.spring.fleamarket.domain.model.RefreshToken;

public interface RefreshTokenService {
	
	public RefreshToken generateRefreshToken(int accountId);
	
	public void checkValidation(String refreshToken, int accountId) throws InvalidRefreshTokenException;
	
	public Cookie createRefreshTokenCookie(RefreshToken refreshToken);
	
	public String getRefreshToken(HttpServletRequest request) throws NotFoundRefreshTokenException;
	
}
