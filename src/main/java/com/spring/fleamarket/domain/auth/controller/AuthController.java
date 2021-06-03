package com.spring.fleamarket.domain.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.spring.fleamarket.domain.account.service.AccountFindService;
import com.spring.fleamarket.domain.auth.exception.InvalidHeaderFormatException;
import com.spring.fleamarket.domain.auth.exception.InvalidRefreshTokenException;
import com.spring.fleamarket.domain.auth.exception.NotFoundAuthenticationHeaderException;
import com.spring.fleamarket.domain.auth.exception.NotFoundRefreshTokenException;
import com.spring.fleamarket.domain.auth.service.JwtTokenService;
import com.spring.fleamarket.domain.auth.service.RefreshTokenService;
import com.spring.fleamarket.domain.model.Account;
import com.spring.fleamarket.domain.model.RefreshToken;
import com.spring.fleamarket.global.error.model.ErrorResponse;
import com.spring.fleamarket.global.security.model.LoginSuccessResponse;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private AccountFindService accountFindService;
	
	@GetMapping("/refresh")
	public ResponseEntity<LoginSuccessResponse> getRefreshToken(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		String accessToken = jwtTokenService.getJwtToken(request);
		String refreshToken = refreshTokenService.getRefreshToken(request);
		
		// access token 유효성 검사
		try {
			jwtTokenService.verifyJwtToken(accessToken);	
		} catch (TokenExpiredException e) {
			log.info(e.getMessage());
		}
		
		int id = jwtTokenService.getIdFromJwtToken(accessToken);
		String username = jwtTokenService.getUsernameFromJwtToken(accessToken);
		Account account = accountFindService.selectAccountByName(username);
		if (id != account.getId()) {
			throw new JWTVerificationException("Wrong Token Payload");
		}
		
		// refresh token 유효성 검사
		refreshTokenService.checkValidation(refreshToken, id);
		
		// access token 생성
		String renewedAccessToken = jwtTokenService.generateAccessToken(id, username);
		LoginSuccessResponse authResponse = LoginSuccessResponse.builder()
												.token(renewedAccessToken)
												.build();
		
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidRefreshTokenException.class)
	protected ResponseEntity<ErrorResponse> handleUnauthorizedException(InvalidRefreshTokenException e) {
		ErrorResponse response = ErrorResponse.builder()
									.httpStatus(HttpStatus.UNAUTHORIZED)
									.message(e.getMessage())
									.build();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
	
	@ExceptionHandler({NotFoundAuthenticationHeaderException.class, 
 					   InvalidHeaderFormatException.class, 
					   NotFoundRefreshTokenException.class,
					   Exception.class})
	protected ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
		ErrorResponse response = ErrorResponse.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(e.getMessage())
				.build();
		return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
	}
	
}
