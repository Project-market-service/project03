package com.spring.fleamarket.global.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.fleamarket.domain.model.Account;
import com.spring.fleamarket.global.security.model.LoginDetails;
import com.spring.fleamarket.global.security.model.LoginRequest;
import com.spring.fleamarket.global.security.service.JwtTokenService;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	JwtTokenService jwtTokenService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(request.getMethod() + " is not supported");
		}
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
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
		LoginDetails user = (LoginDetails) authResult.getPrincipal();
		Account account = user.getAccount();
		
		log.info(jwtTokenService);
		
		String jwtToken = jwtTokenService.createToken(account.getId(), account.getName());
		jwtTokenService.setJwtToken(response, jwtToken);
		
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
}
