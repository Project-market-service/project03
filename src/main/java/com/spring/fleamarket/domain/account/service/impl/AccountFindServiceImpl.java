package com.spring.fleamarket.domain.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.account.mapper.AccountFindMapper;
import com.spring.fleamarket.domain.account.service.AccountFindService;

@Service
public class AccountFindServiceImpl implements AccountFindService {

	@Autowired
	AccountFindMapper mapper;
	
	@Override
	public String selectAccountImageByAccountId(int accountId) {
		return mapper.selectAccountImageByAccountId(accountId);
	}

}