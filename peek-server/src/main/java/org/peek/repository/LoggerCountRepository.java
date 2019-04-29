package org.peek.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.peek.domain.Config;
import org.peek.domain.LoggerInfo;
import org.peek.service.query.LoggerInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
public class LoggerCountRepository extends SimpleJpaRepository<Config, String> {
	
	
	@Autowired
    private EntityManager entityManager;

	String collectionName="loggerInfo";
	
	public LoggerCountRepository(Class<Config> domainClass, EntityManager em) {
		super(domainClass, em);
	}
	
	public void saveAll(List<LoggerInfo> list) {
		for(LoggerInfo li:list) {
			entityManager.persist(li);
		}
	}
	public void save(List<LoggerInfo> list) {
		for(LoggerInfo li:list) {
			entityManager.persist(li);
		}
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
		querys.with(Sort.by(Sort.Direction.DESC, "time"));  
		if(topn!=null) {
			querys.skip(0).limit(topn);
		}
		return entityManager.find(querys,LoggerInfo.class);
	}
	public LoggerInfo getById(long logId) {
		return entityManager.find(LoggerInfo.class, logId);
	}
}
