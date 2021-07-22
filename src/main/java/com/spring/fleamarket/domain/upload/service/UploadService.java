package com.spring.fleamarket.domain.upload.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.fleamarket.domain.model.Post;
import com.spring.fleamarket.domain.upload.model.AttachFileDTO;

public interface UploadService {

	public List<AttachFileDTO> createPdf(Post post, AttachFileDTO attachDTO);
	
	public String getFolder(Post post, AttachFileDTO attachDTO);
	
	public String checkExtension(MultipartFile fd);
	
	public List<Post> bringPostList(int userId);
	
	public int searchFileNo(Post post);
	
	public String searchFilePath(int fileNo);
		
	
}
