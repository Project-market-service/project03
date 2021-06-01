package com.spring.fleamarket.domain.account.service;

import com.spring.fleamarket.domain.model.RefreshToken;

public interface RefreshTokenService {
	
	public RefreshToken generateRefreshToken(int accountId);
	
}
