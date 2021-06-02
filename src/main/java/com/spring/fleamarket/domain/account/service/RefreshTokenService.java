package com.spring.fleamarket.domain.account.service;

import javax.servlet.http.Cookie;

import com.spring.fleamarket.domain.model.RefreshToken;

public interface RefreshTokenService {
	
	public RefreshToken generateRefreshToken(int accountId);
	
	public RefreshToken selectRefreshTokenByAccountId(int accountId);
	
	public Cookie createRefreshTokenCookie(RefreshToken refreshToken);
	
}
