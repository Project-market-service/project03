package com.spring.fleamarket.domain.account.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.account.mapper.RefreshTokenMapper;
import com.spring.fleamarket.domain.account.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.RefreshToken;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private long refreshTokenValidationTime = 60 * 60 * 1000L; // 1시간
	
	@Autowired
	private RefreshTokenMapper mapper;
	
	@Override
	public RefreshToken generateRefreshToken(int accountId) {
		String token = UUID.randomUUID().toString();
		
		RefreshToken refreshToken = RefreshToken.builder()
										.accountId(accountId)
										.token(token)
										.expiredDate(new Timestamp(System.currentTimeMillis() + refreshTokenValidationTime))
										.build();
		mapper.save(refreshToken);
		
		return refreshToken;
	}

}
