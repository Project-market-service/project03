package com.spring.fleamarket.domain.upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.fleamarket.domain.model.Post;
import com.spring.fleamarket.domain.upload.model.AttachFileDTO;
import com.spring.fleamarket.domain.upload.service.UploadService;
import com.spring.fleamarket.global.util.file.FileUtils;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@CrossOrigin
public class UploadFileController {
	
	@Autowired
	UploadService service;
	
	@RequestMapping(value="/uploadFile", method = RequestMethod.POST)
	//@PostMapping(value="/uploadFile", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> uploadFiles(@ModelAttribute Post post){
						
		MultipartFile file = post.getFile();
		
		int user = post.getUserId();
		
		log.info("안에 내용" + post.toString());
		
		AttachFileDTO attachDTO = new AttachFileDTO();
		
		//유저가 없을때
		if(user == 0) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
						
		// 파일이 안들어왔을때
		if(file.isEmpty() || file == null) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);		
		}
		
		// 파일의 MimeType를 체크했을때(Simple Magic)
		else if(service.checkExtension(file) != "pdf") {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
				
		if(file != null) {
			service.createPdf(post, attachDTO);		
		}
						
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
		
	@GetMapping("/postList/{userId}")
	public List<Post> bringPostList(@PathVariable int userId){
		
		List<Post> result = service.bringPostList(userId);
		
		return result;	
	}
		
	@GetMapping("/images/{userId}/{postNo}/{imageNo}")
	public ResponseEntity<byte[]> searchFileNo(@PathVariable int userId, @PathVariable int postNo, @PathVariable int imageNo) throws Exception{
		
		InputStream in = null;
		
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		Post temPost = new Post();
		
		temPost.setUserId(userId);
		
		temPost.setPostNo(postNo);
		
		//게시물에서 파일 넘버 불러오기
		int fileNo = service.searchFileNo(temPost);
		
		log.info("파일넘버: " + fileNo);
		
		//유저아이디와 파일 넘버로 파일 경로 불러오기 
		String filePath = "C:\\upload" + File.separator + service.searchFilePath(fileNo) + File.separator + imageNo + ".png";
		
		log.info("파일위치: " + filePath);
				
		//파일 경로에 있는 파일 리스트 불러오기
		//File path = new File(filePath);
				
		in = new FileInputStream(filePath);
		log.info("in에 들어옴" + in);
		
		try {
			
			headers.setContentType(MediaType.IMAGE_PNG);
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
				
		} catch (IOException e) {
			
			e.printStackTrace();
			
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			
		}finally {
			
			in.close();
		}
		return entity;
		
	}
		
}
