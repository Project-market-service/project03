package com.spring.fleamarket.domain.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.fleamarket.domain.account.service.AccountFindService;
import com.spring.fleamarket.domain.auth.service.JwtTokenService;
import com.spring.fleamarket.domain.auth.service.RefreshTokenService;
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
		log.info(accessToken);
		
		return null;
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		return new ResponseEntity<ErrorResponse>(HttpStatus.BAD_REQUEST);
	}
	
}
