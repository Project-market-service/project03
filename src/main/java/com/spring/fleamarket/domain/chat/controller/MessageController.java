package com.spring.fleamarket.domain.chat.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.spring.fleamarket.domain.account.service.AccountFindService;
import com.spring.fleamarket.domain.chat.model.MessageModel;
import com.spring.fleamarket.domain.chat.service.MessageManageService;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
public class MessageController {
	
	@Autowired
	MessageManageService service;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
		
	@MessageMapping("/chat/{senderId}/{receiverId}")
	public void sendMessage(@DestinationVariable int senderId, @DestinationVariable int receiverId, MessageModel message) {
		
		
			
		log.info("handling send message: " + message);
		
		service.saveMessage(message);
				
		Date messageTime = service.checkTime(message.getMessageNo());
		
		message.setCreateDate(messageTime);
		
		//유저 유효성 처리하기 (백엔드)
		if(senderId != 0 && receiverId != 0) {
			simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), message);
			
			simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getSenderId(), message);
			
		}
	}
	
	
}
