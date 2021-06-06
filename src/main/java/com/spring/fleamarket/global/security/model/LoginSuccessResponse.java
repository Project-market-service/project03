package com.spring.fleamarket.global.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginSuccessResponse {

	private String token;
	
	@Builder
	public LoginSuccessResponse(String token) {
		this.token = token;
	}
	
}
