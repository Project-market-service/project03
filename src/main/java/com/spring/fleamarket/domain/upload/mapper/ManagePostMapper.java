package com.spring.fleamarket.domain.upload.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.fleamarket.domain.model.Post;
import com.spring.fleamarket.domain.upload.model.AttachFileDTO;

public interface ManagePostMapper {

	public void uploadFiles(AttachFileDTO attachDTO);
	
	public void savePosts(Post post);
	
	public List<Post> bringPostList(int userId);
	
	
	// mapper에는 하나의 객체만 받을수있음 (수정완료)
	public int searchFileNo(Post post);
	
	public String searchFilePath(int fileNo);
	
	
}
