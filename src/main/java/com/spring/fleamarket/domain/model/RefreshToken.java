package com.spring.fleamarket.domain.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Alias("RefreshToken")
@Getter
@ToString
public class RefreshToken {

	public static final long VALIDATION_TIME = 60 * 60 * 1000L; 
	
	private int accountId;
	@NotBlank
	private String token;
	@NotNull
	private Timestamp expiredDate;
	
	@Builder
	public RefreshToken(int accountId, String token) {
		this.accountId = accountId;
		this.token = token;
		this.expiredDate = new Timestamp(System.currentTimeMillis() + VALIDATION_TIME);
	}
	
}
