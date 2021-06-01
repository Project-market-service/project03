package com.spring.fleamarket.domain.account.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.account.mapper.RefreshTokenMapper;
import com.spring.fleamarket.domain.account.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.RefreshToken;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	private RefreshTokenMapper mapper;
	
	@Override
	public RefreshToken generateRefreshToken(int accountId) {
		String token = UUID.randomUUID().toString();
		
		RefreshToken refreshToken = RefreshToken.builder()
										.accountId(accountId)
										.token(token)
										.build();
		mapper.save(refreshToken);
		
		return refreshToken;
	}

	@Override
	public RefreshToken selectRefreshTokenByAccountId(int accountId) {
		return mapper.selectRefreshTokenByAccountId(accountId);
	}
	
}
