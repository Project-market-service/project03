package com.spring.fleamarket.global.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthenticationSuccessResponse {

	private String accessToken;
	
	@Builder
	public AuthenticationSuccessResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
