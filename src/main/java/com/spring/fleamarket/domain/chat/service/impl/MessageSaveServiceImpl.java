package com.spring.fleamarket.domain.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.chat.mapper.MessageSaveMapper;
import com.spring.fleamarket.domain.chat.model.MessageModel;
import com.spring.fleamarket.domain.chat.service.MessageSaveService;

@Service
public class MessageSaveServiceImpl implements MessageSaveService{

	@Autowired
	MessageSaveMapper mapper;
	
	@Override
	public void saveMessage(MessageModel msgModel) {
		
		mapper.saveMessage(msgModel);
	}

	@Override
	public List<MessageModel> recallChat(MessageModel msgModel) {
			
		return mapper.recallChat(msgModel);
	}

}
