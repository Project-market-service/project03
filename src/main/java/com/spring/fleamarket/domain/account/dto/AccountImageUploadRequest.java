package com.spring.fleamarket.domain.account.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountImageUploadRequest {

	@Min(value = 0)
	private int left;
	@Min(value = 0)
	private int top;
	private int width;
	private int height;
	
	private final int resize = 300;

	@Builder
	public AccountImageUploadRequest(int left, int top, int width, int height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}
	
	
	
}
