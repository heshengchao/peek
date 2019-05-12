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
		return repository.findLogger(query,null);
	}

	public LoggerInfo getById(long logId) {
 		return repository.getById(logId);
	}

	public List<LoggerInfo> findTopN(LoggerInfoQuery query, int topn) {
		return repository.findLogger(query,topn);
	}
 }
