package com.spring.fleamarket.domain.upload.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Alias("FileDTO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AttachFileDTO {
	
	private int fileNo;
	private String fileCategory;
	private int userId;
	private String uuid;
	private String originalFileName;
	private String savedFileName;
	private String uploadPath;
	private long fileSize;
	//private boolean image;
	private Date uplaodDate;
}
