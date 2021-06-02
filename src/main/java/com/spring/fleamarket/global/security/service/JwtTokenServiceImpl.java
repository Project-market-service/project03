package com.spring.fleamarket.global.security.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.spring.fleamarket.domain.account.mapper.AccountFindMapper;
import com.spring.fleamarket.domain.model.Account;
import com.spring.fleamarket.global.security.config.JwtConfig;
import com.spring.fleamarket.global.security.model.LoginDetails;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	AccountFindMapper mapper;

	@Override
	public Authentication getAuthentication(String token) throws TokenExpiredException, Exception {
		String username = JWT.require(Algorithm.HMAC256(jwtConfig.getSecretKey())).build().verify(token).getClaim("username").asString();
		if (username == null) {
			return null;
		}
		
		Account account = mapper.selectAccountByName(username);
		LoginDetails loginDetails = new LoginDetails(account);
		return new UsernamePasswordAuthenticationToken(loginDetails, null, loginDetails.getAuthorities());
	}

	@Override
	public String getJwtToken(HttpServletRequest request) {
		String headerContent = request.getHeader(jwtConfig.getHeaderName());
		if (headerContent == null
				|| !headerContent.startsWith(jwtConfig.getTokenPrefix())) {
			return null;
		}
		
		return headerContent.replace(jwtConfig.getTokenPrefix(), "");
	}
	

	@Override
	public String generateAccessToken(int id, String username) {
		String token = JWT.create()
				.withClaim("id", id)
				.withClaim("username", username)
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getValidationTime()))
				.sign(Algorithm.HMAC256(jwtConfig.getSecretKey()));
		return jwtConfig.getTokenPrefix() + token;
	}

	@Override
	public int getIdFromJwtToken(String token) {
		return JWT.decode(token).getClaim("id").asInt();
	}

	@Override
	public String getUsernameFromJwtToken(String token) {
		return JWT.decode(token).getClaim("username").asString();
	}

}
