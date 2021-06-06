package com.spring.fleamarket.domain.account.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.spring.fleamarket.domain.account.dto.AccountImageUploadRequest;
import com.spring.fleamarket.domain.account.mapper.AccountImageMapper;
import com.spring.fleamarket.domain.account.service.AccountImageService;
import com.spring.fleamarket.domain.model.AccountImage;
import com.spring.fleamarket.global.util.file.FileUtils;
import com.spring.fleamarket.global.util.file.exception.NotAcceptableImageFileFormatException;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@Service
public class AccountImageServiceImpl implements AccountImageService {

	@Resource(name = "accountFileUploadPath")
	String uploadPath;
	
	@Autowired
	private AccountImageMapper mapper;
	
	@Override
	public void insertAccountImageByAccountId(MultipartFile file, @Valid AccountImageUploadRequest request, AccountImage accountImage) throws Exception {		
		String path = null;
		try {
			path = uploadCroppedImageFile(file, request);
			accountImage.setPath(path);
			log.info(accountImage);
			mapper.insertAccountImageByAccountId(accountImage);
		} catch (SQLException e) {
			log.info(e.getMessage());
			if (path != null) {
				FileUtils.deleteFile(uploadPath + File.separator + path);				
			}
		}
		
	}

	@Override
	public void updateAccountImageByAccountId(MultipartFile file, @Valid AccountImageUploadRequest request, AccountImage accountImage) throws Exception {
		String newPath = null;
		try {
			String prevPath = accountImage.getPath();
			newPath = uploadCroppedImageFile(file, request);
			accountImage.setPath(newPath);
				
			mapper.updateAccountImageByAccountId(accountImage);
			FileUtils.deleteFile(uploadPath + File.separator + prevPath);
		} catch (SQLException e) {
			log.info(e.getMessage());
			if (newPath != null) {
				FileUtils.deleteFile(uploadPath + File.separator + newPath);	
			}
		}
		
	}
	
	@Override
	public void deleteAccountImageByAccountId(AccountImage accountImage) throws Exception {
		try {
			mapper.deleteAccountImageByAccountId(accountImage.getAccountId());
			FileUtils.deleteFile(uploadPath + File.separator + accountImage.getPath());
		} catch (SQLException e) {
			log.info(e.getMessage());
		}
	}
	
	private String uploadCroppedImageFile(MultipartFile file, AccountImageUploadRequest request) throws Exception {
		return FileUtils.uploadCroppedImageFile(file, 
											    uploadPath, 
											    request.getLeft(), 
											    request.getTop(), 
											    request.getWidth(), 
											    request.getHeight(),
											    request.getResize());
	}

	@Override
	public byte[] getImageBytes(String path) throws IOException {		
		return IOUtils.toByteArray(new FileInputStream(uploadPath + File.separator + path));
	}

	@Override
	public HttpHeaders getHttpHeaders(String path) throws NotAcceptableImageFileFormatException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(FileUtils.getMediaTypeFromFileName(path));
		return headers;
	}

}
