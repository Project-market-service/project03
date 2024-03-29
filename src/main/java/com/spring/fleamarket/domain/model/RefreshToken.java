package com.spring.fleamarket.domain.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Value;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Alias("RefreshToken")
@Getter
@ToString
public class RefreshToken {

	private int accountId;
	@NotBlank
	private String token;
	@NotNull
	private Timestamp expiredDate;
	
	@Builder
	public RefreshToken(int accountId, String token, Timestamp expiredDate) {
		this.accountId = accountId;
		this.token = token;
		this.expiredDate = expiredDate;
	}
	
}
