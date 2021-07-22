package com.spring.fleamarket.domain.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.fleamarket.domain.chat.mapper.ReportSaveMapper;
import com.spring.fleamarket.domain.chat.service.ReportService;
import com.spring.fleamarket.domain.model.Report;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	ReportSaveMapper mapper;

	@Override
	public void saveReport(Report rp) {
		mapper.saveReport(rp);
		
	}
	
	

}
