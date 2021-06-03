package com.spring.fleamarket.domain.auth.mapper;

import com.spring.fleamarket.domain.model.RefreshToken;

public interface RefreshTokenMapper {
	
	public void save(RefreshToken token);
	
	public RefreshToken selectRefreshTokenByAccountId(int accountId);
	
}
