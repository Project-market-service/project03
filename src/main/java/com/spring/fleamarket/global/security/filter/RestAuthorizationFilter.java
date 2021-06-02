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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.fleamarket.domain.account.service.AccountFindService;
import com.spring.fleamarket.domain.account.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.RefreshToken;
import com.spring.fleamarket.global.security.model.LoginSuccessResponse;
import com.spring.fleamarket.global.security.model.LoginDetails;
import com.spring.fleamarket.global.security.service.JwtTokenService;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestAuthorizationFilter extends BasicAuthenticationFilter {

	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private AccountFindService accountFindService;
	
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
				log.warn("expired access token!");
				
				String token = getRefreshToken(request);
				if (token != null) {
					int accountId = jwtTokenService.getIdFromJwtToken(jwtToken);
					RefreshToken refreshToken = refreshTokenService.selectRefreshTokenByAccountId(accountId);
					if (refreshToken == null) {
						log.warn("not find token");
					} else if (!refreshToken.getToken().equals(token)) {
						log.warn("not same token");
					} else if (refreshToken.getExpiredDate().before(new Date())) {
						log.warn("expired refresh token");
					} else {
						log.info("renew access token!");
						
						String username = jwtTokenService.getUsernameFromJwtToken(jwtToken);
						String renewedToken = jwtTokenService.generateAccessToken(accountId, username);		
						LoginSuccessResponse authResponse = LoginSuccessResponse.builder()
																		.token(renewedToken)
																		.build();

						response.getWriter().println(objectMapper.writeValueAsString(authResponse));
					
						try {
							LoginDetails loginDetails = new LoginDetails(accountFindService.selectAccountByName(username));
							Authentication auth = new UsernamePasswordAuthenticationToken(loginDetails, null, loginDetails.getAuthorities());
							SecurityContextHolder.getContext().setAuthentication(auth);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
					
				} else {
					log.info("no token!");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		chain.doFilter(request, response);
	}
	
	private String getRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies) {
			if (c.getName().equals("refreshToken")) {
				return c.getValue();
			}
		}
		
		return null;
	}
	
}
