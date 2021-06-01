package com.spring.fleamarket.global.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.spring.fleamarket.global.security.service.JwtTokenService;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private JwtTokenService jwtTokenService;
	
	public RestAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String jwtToken = jwtTokenService.getJwtToken(request);
		if (jwtToken != null) {
			try {
				Authentication auth = jwtTokenService.getAuthentication(jwtToken);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (TokenExpiredException e) {
				log.warn("expired token!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		chain.doFilter(request, response);
	}
	
}
