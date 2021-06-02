package com.spring.fleamarket.domain.account.service.impl;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.account.mapper.RefreshTokenMapper;
import com.spring.fleamarket.domain.account.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.RefreshToken;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	private RefreshTokenMapper mapper;

	@Value("${refreshToken.validationTime}")
	private long validationTime; 
	
	@Override
	public RefreshToken generateRefreshToken(int accountId) {
		String token = UUID.randomUUID().toString();
		
		RefreshToken refreshToken = RefreshToken.builder()
										.accountId(accountId)
										.token(token)
										.expiredDate(new Timestamp(System.currentTimeMillis() + validationTime))
										.build();
		mapper.save(refreshToken);
		
		return refreshToken;
	}

	@Override
	public RefreshToken selectRefreshTokenByAccountId(int accountId) {
		return mapper.selectRefreshTokenByAccountId(accountId);
	}

	@Override
	public Cookie createRefreshTokenCookie(RefreshToken refreshToken) {
		Cookie cookie = new Cookie("refreshToken", refreshToken.getToken());
		cookie.setHttpOnly(true);
		cookie.setMaxAge((int) (validationTime / 1000));
		cookie.setPath("/fleamarket/auth/refresh");
		return cookie;
	}
	
}
