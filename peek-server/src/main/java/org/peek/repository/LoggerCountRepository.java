package org.peek.repository;

import java.util.List;


import org.peek.domain.LoggerInfo;
import org.peek.service.query.LoggerInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
public interface LoggerCountRepository  extends MybatisRepository<LoggerInfo, String> {

	@Query(statement="findLogger")
	public List<LoggerInfo> findLogger(LoggerInfoQuery query, Integer topn) ;

	public LoggerInfo getById(long logId);
	String collectionName="loggerInfo";
	
//	
//	public void saveAll(List<LoggerInfo> list) {
//		for(LoggerInfo li:list) {
////			entityManager.persist(li);
//		}
//	}
//	public void save(List<LoggerInfo> list) {
//		for(LoggerInfo li:list) {
////			entityManager.persist(li);
//		}
//	}

//	public List<LoggerInfo> find(LoggerInfoQuery query, Integer topn) {
//		Query querys=new Query();
//		Criteria criteria = new Criteria();
//		querys.addCriteria(criteria);
//		
//		if(!StringUtils.isEmpty(query.getAppGroupId())){
//			criteria.and("appGroupId").is(query.getAppGroupId());
//		}
//		if(!StringUtils.isEmpty(query.getAppInsId())){
//			criteria.and("appInsId").is(query.getAppInsId());
//		}
//		if(query.getStartTime()!=null){
//			criteria.and("time").gte(query.getStartTime());
//		}
//		if(query.getEndTime()!=null){
//			criteria.and("time").lte(query.getEndTime());
//		}
//		querys.with(Sort.by(Sort.Direction.DESC, "time"));  
//		if(topn!=null) {
//			querys.skip(0).limit(topn);
//		}
//		return entityManager.find(querys,LoggerInfo.class);
//		return null;
//	}
//	public LoggerInfo getById(long logId) {
//		//return entityManager.find(LoggerInfo.class, logId);
//		return null;
//	}
}
