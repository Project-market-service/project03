package com.spring.fleamarket.domain;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.fleamarket.domain.model.Account;
import com.spring.fleamarket.global.security.annotation.LoginedAccount;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class HomeController {
	
	/*
	 * 페이지에 접속했을 때, CSRF 토큰을 발급한다.
	 */
//	@GetMapping("/")
//	public ResponseEntity<String> init(CsrfToken token, HttpServletRequest request) {
//		log.info("token=" + token.getToken());
//		
//		return new ResponseEntity<>(token.getToken(), HttpStatus.OK);
//	}
	
	/*
	 * 페이지에 접속했을 때, 로그인 했다면 유저정보를 반환해주고, 아니라면 401을 반환한다.
	 */
//	@GetMapping("/userinfo")
//	public ResponseEntity<LoginResponse> getUserInfo(@LoginedAccount Account account) {
//		if (account == null) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//		
//		return new ResponseEntity<>(LoginResponse.builder()
//										.id(account.getId())
//										.username(account.getName())
//										.build(), 
//									HttpStatus.OK);
//	}
	
	@PostMapping("/api/test")
	public String test(@LoginedAccount Account account) {
		log.info("login account=" + account);
		return "OK";
	}
	
	@PostMapping("/auth/refresh")
	public void test() {}
	
}
