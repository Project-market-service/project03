package com.spring.fleamarket.global.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthenticationSuccessResponse {

	private String accessToken;
	private String refreshToken;
	
	@Builder
	public AuthenticationSuccessResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
