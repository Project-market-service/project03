package com.spring.fleamarket.domain.account.service;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import com.spring.fleamarket.domain.account.dto.AccountImageUploadRequest;
import com.spring.fleamarket.domain.model.AccountImage;

public interface AccountImageService {
	
	public void insertAccountImageByAccountId(MultipartFile file, @Valid AccountImageUploadRequest request, AccountImage accountImage) throws Exception;
	
	public void updateAccountImageByAccountId(MultipartFile file, @Valid AccountImageUploadRequest request, AccountImage accountImage) throws Exception;
	
	public void deleteAccountImageByAccountId(AccountImage accountImage) throws Exception;
	
	public byte[] getImageBytes(String path) throws Exception;
	
	public HttpHeaders getHttpHeaders(String path) throws Exception;
	
}
