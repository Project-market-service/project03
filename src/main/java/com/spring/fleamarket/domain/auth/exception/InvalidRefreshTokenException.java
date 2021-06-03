package com.spring.fleamarket.domain.auth.exception;

public class InvalidRefreshTokenException extends Exception {

	public InvalidRefreshTokenException(String message) {
		super(message);
	}
	
}
