package com.spring.fleamarket.global.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.fleamarket.domain.account.service.AccountFindService;
import com.spring.fleamarket.domain.auth.exception.InvalidHeaderFormatException;
import com.spring.fleamarket.domain.auth.exception.NotFoundAuthenticationHeaderException;
import com.spring.fleamarket.domain.auth.service.JwtTokenService;
import com.spring.fleamarket.domain.auth.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.RefreshToken;
import com.spring.fleamarket.global.security.model.LoginSuccessResponse;
import com.spring.fleamarket.global.security.model.LoginDetails;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestAuthorizationFilter extends BasicAuthenticationFilter {

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private AccountFindService accountFindService;
	
	public RestAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			String accessToken = jwtTokenService.getJwtToken(request);
			jwtTokenService.verifyJwtToken(accessToken);
			
			Authentication auth = jwtTokenService.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
			
		} catch (Exception e) {
			log.warn(e.getMessage());
		} 
		
		chain.doFilter(request, response);
	}
	
}
