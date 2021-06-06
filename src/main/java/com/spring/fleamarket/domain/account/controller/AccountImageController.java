package com.spring.fleamarket.domain.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.fleamarket.domain.account.dto.AccountImageUploadRequest;
import com.spring.fleamarket.domain.account.service.AccountFindService;
import com.spring.fleamarket.domain.account.service.AccountImageService;
import com.spring.fleamarket.domain.model.Account;
import com.spring.fleamarket.domain.model.AccountImage;
import com.spring.fleamarket.global.security.annotation.LoginedAccount;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class AccountImageController {

	@Autowired
	private AccountImageService accountImageService;
	
	@Autowired
	private AccountFindService accountFindService;

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/account/image/{accountId}")
	public ResponseEntity<byte[]> getAccoutImage(@PathVariable int accountId) {
		AccountImage accountImage = accountFindService.selectAccountImageByAccountId(accountId);
		if (accountImage == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity<byte[]> result = null;
		HttpHeaders headers = null;
		byte[] image = null;
		try {
			headers = accountImageService.getHttpHeaders(accountImage.getPath());
			image = accountImageService.getImageBytes(accountImage.getPath());
			result = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
		} catch (Exception e) {
			log.warn(e.getMessage());
			
			try {
				headers = accountImageService.getHttpHeaders(AccountImage.DEFAULT_PATH);
				image = accountImageService.getImageBytes(AccountImage.DEFAULT_PATH);
				result = new ResponseEntity<byte[]>(image, headers, HttpStatus.NOT_FOUND);
			} catch (Exception e1) {
				log.warn(e1.getMessage());
				result = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			}
		}

		return result;
	}
	
	@PostMapping("/account/image")
	public ResponseEntity<String> uploadAccountImage(MultipartFile file, 
												     AccountImageUploadRequest request,
												     @LoginedAccount Account account) {
		String msg = null;
		if (account == null) {
			msg = "로그인하지 않았습니다.";
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		} else if (file == null) {
			msg = "파일이 존재하지 않습니다.";
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		
		try {
			AccountImage accountImage = accountFindService.selectAccountImageByAccountId(account.getId());
			if (accountImage.getPath().equals(AccountImage.DEFAULT_PATH)) {
				accountImageService.insertAccountImageByAccountId(file, request, accountImage);
			} else {
				accountImageService.updateAccountImageByAccountId(file, request, accountImage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "파일업로드 실패";
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/account/image")
	public ResponseEntity<String> deleteAccountImage(@LoginedAccount Account account) {
		String msg = null;
		if (account == null) {
			msg = "로그인하지 않았습니다.";
			return new ResponseEntity<String>(msg, HttpStatus.UNAUTHORIZED);
		}
		
		try {
			AccountImage accountImage = accountFindService.selectAccountImageByAccountId(account.getId());
			if (accountImage.getPath().equals(AccountImage.DEFAULT_PATH)) {
				msg = "삭제할 이미지가 없습니다.";		
			} else {
				msg = "삭제 성공";
				accountImageService.deleteAccountImageByAccountId(accountImage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "파일 삭제 실패";
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
}
