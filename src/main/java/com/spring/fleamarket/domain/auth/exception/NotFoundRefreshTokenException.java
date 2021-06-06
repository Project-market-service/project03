package com.spring.fleamarket.domain.auth.exception;

public class NotFoundRefreshTokenException extends Exception {

	public NotFoundRefreshTokenException(String message) {
		super(message);
	}
}
