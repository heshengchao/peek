package org.peek.service.impl;

import java.util.List;

import org.peek.domain.LoggerInfo;
import org.peek.logger.LoggerCount;
import org.peek.repository.LoggerCountRepository;
import org.peek.service.query.LoggerInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoggerCountService {

	@Autowired LoggerCountRepository repository;
	
	public List<LoggerInfo> findByApp(LoggerInfoQuery query){
		return repository.find(query);
	}
 }
