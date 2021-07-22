package com.spring.fleamarket.domain.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Alias("Post")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Post {
	
	private int postNo;
	private String postTitle;
	private String postSubtitle;
	private String content;
	private String category;
	private int fileNo;
	private MultipartFile file;
	private int numOfPage;
	private int userId;
	private Date postDate;
	
}
