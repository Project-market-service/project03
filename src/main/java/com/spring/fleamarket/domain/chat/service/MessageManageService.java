package com.spring.fleamarket.domain.chat.service;

import java.util.Date;
import java.util.List;

import com.spring.fleamarket.domain.chat.model.MessageModel;
import com.spring.fleamarket.domain.chat.model.UserListDTO;

public interface MessageManageService {

	public void saveMessage(MessageModel msgModel);
	
	public List<MessageModel> recallChat(MessageModel msgModel);
	
	public Date checkTime(int messageNo);
	
	public List<UserListDTO> bringUserList(int userId);
		
}
