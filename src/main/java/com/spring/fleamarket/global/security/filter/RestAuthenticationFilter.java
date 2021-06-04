package com.spring.fleamarket.global.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.fleamarket.domain.auth.service.JwtTokenService;
import com.spring.fleamarket.domain.auth.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.Account;
import com.spring.fleamarket.domain.model.RefreshToken;
import com.spring.fleamarket.global.security.model.LoginSuccessResponse;
import com.spring.fleamarket.global.security.model.LoginDetails;
import com.spring.fleamarket.global.security.model.LoginRequest;

import lombok.extern.log4j.Log4j;

// 'login' 요청시 동작하는 필터
@Log4j
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(request.getMethod() + " is not supported");
		}
		
		try {
			LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());			
			
			return getAuthenticationManager().authenticate(authToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("LOGIN SUCCESS");
		
		LoginDetails user = (LoginDetails) authResult.getPrincipal();
		
		String accessToken = jwtTokenService.generateAccessToken(user.getId(), user.getUsername());		
		RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user.getId());
		
		LoginSuccessResponse authResponse = LoginSuccessResponse.builder()
												.token(accessToken)
												.build();
		
		response.addCookie(refreshTokenService.createRefreshTokenCookie(refreshToken));
		response.getWriter().println(objectMapper.writeValueAsString(authResponse));
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.warn("LOGIN FAIL");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().print("login fail");
	}
	
	
}
