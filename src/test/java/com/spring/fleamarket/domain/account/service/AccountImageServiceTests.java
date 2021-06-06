package com.spring.fleamarket.domain.account.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.spring.fleamarket.domain.account.dto.AccountImageUploadRequest;
import com.spring.fleamarket.domain.model.AccountImage;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",	
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@Log4j
public class AccountImageServiceTests {
	
	@Autowired
	AccountImageService service;
	
	@Autowired
	AccountFindService accountFindService;
	
	private MultipartFile file;
	private AccountImageUploadRequest accountImageRequest;
	private AccountImage accountImage;
	
	@Before
	public void initialize() throws IOException {
		String fileDir = "src/test/resources/file";
		String fileName = "image1.png";
		file = new MockMultipartFile("file", 
									 fileName, 
									 null, 
									 new FileInputStream(fileDir + File.separator + fileName));
		
		accountImageRequest = AccountImageUploadRequest.builder()
												 .left(100).top(200)
												 .width(200)
												 .height(200)
												 .build();
		accountImage = AccountImage.builder()
								   .accountId(1)
								   .build();
	}
		
	@Test
	public void insertAccountImageByAccountIdTest() throws Exception {
		accountImage = accountFindService.selectAccountImageByAccountId(accountImage.getAccountId());
		if (accountImage == null) {
			service.insertAccountImageByAccountId(file, accountImageRequest, accountImage);
		} else {
			log.info("이미 등록된 프로필 이미지가 있습니다.");
		}
	}
	
	@Test
	public void updateAccountImageByAccountIdTest() throws Exception {
		accountImage = accountFindService.selectAccountImageByAccountId(accountImage.getAccountId());
		if (accountImage != null) {			
			accountImage.setPath(accountImage.getPath());
			service.updateAccountImageByAccountId(file, accountImageRequest, accountImage);			
		} else {
			log.info("등록된 프로필 이미지가 없습니다.");
		}
	}

	@Test
	public void deleteAccountImageByAccountIdTest() throws Exception {
		accountImage = accountFindService.selectAccountImageByAccountId(accountImage.getAccountId());
		if (accountImage != null) {			
			accountImage.setPath(accountImage.getPath());
			service.deleteAccountImageByAccountId(accountImage);	
		} 
	}
	
}
