package com.spring.fleamarket.domain.auth.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.auth.exception.InvalidRefreshTokenException;
import com.spring.fleamarket.domain.auth.exception.NotFoundRefreshTokenException;
import com.spring.fleamarket.domain.auth.mapper.RefreshTokenMapper;
import com.spring.fleamarket.domain.auth.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.RefreshToken;

import jdk.internal.jline.internal.Log;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
	private static final String REFRESH_TOKEN_PATH = "/fleamarket/auth/refresh";
	
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
	public void checkValidation(String refreshToken, int accountId) throws InvalidRefreshTokenException {
		RefreshToken savedRefreshToken = mapper.selectRefreshTokenByAccountId(accountId);

		if (savedRefreshToken == null
				|| !savedRefreshToken.getToken().equals(refreshToken)
				|| savedRefreshToken.getExpiredDate().before(new Date())) {
			throw new InvalidRefreshTokenException("Invalid Token");
		}
	}

	@Override
	public Cookie createRefreshTokenCookie(RefreshToken refreshToken) {
		Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken.getToken());
		cookie.setHttpOnly(true);
		cookie.setMaxAge((int) (validationTime / 1000));
		cookie.setPath(REFRESH_TOKEN_PATH);
		return cookie;
	}

	@Override
	public String getRefreshToken(HttpServletRequest request) throws NotFoundRefreshTokenException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
					return c.getValue();
				}
			}
		}
		
		throw new NotFoundRefreshTokenException("Not Found Refresh Token");
	}
	
}
