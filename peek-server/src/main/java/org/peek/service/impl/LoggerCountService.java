package org.peek.service.impl;

import java.util.List;

import org.peek.domain.LoggerInfo;
import org.peek.repository.LoggerCountRepository;
import org.peek.service.query.LoggerInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerCountService {

	@Autowired LoggerCountRepository repository;
	
	public List<LoggerInfo> findByApp(LoggerInfoQuery query){
		return repository.find(query);
	}

	public LoggerInfo getById(long logId) {
 		return repository.getById(logId);
	}
 }
