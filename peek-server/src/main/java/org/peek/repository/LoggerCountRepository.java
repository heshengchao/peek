package org.peek.repository;

import java.util.List;

import org.peek.domain.LoggerInfo;
import org.peek.service.query.LoggerInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
public class LoggerCountRepository  {
	@Autowired
    private MongoTemplate mongoTemplate;

	String collectionName="loggerInfo";
	
	public void saveAll(List<LoggerInfo> list) {
		mongoTemplate.insertAll(list);
	}
	public void save(List<LoggerInfo> list) {
		mongoTemplate.insertAll(list);
	}

	public List<LoggerInfo> find(LoggerInfoQuery query, Integer topn) {
		Query querys=new Query();
		Criteria criteria = new Criteria();
		querys.addCriteria(criteria);
		
		if(!StringUtils.isEmpty(query.getAppGroupId())){
			criteria.and("appGroupId").is(query.getAppGroupId());
		}
		if(!StringUtils.isEmpty(query.getAppInsId())){
			criteria.and("appInsId").is(query.getAppInsId());
		}
		if(query.getStartTime()!=null){
			criteria.and("time").gte(query.getStartTime());
		}
		if(query.getEndTime()!=null){
			criteria.and("time").lte(query.getEndTime());
		}
		querys.with(Sort.by(Sort.Direction.ASC, "time"));  
		querys.limit(topn);
		return mongoTemplate.find(Query.query(criteria),LoggerInfo.class);
	}
	public LoggerInfo getById(long logId) {
		return mongoTemplate.findById(logId, LoggerInfo.class);
	}
}
