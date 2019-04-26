package org.peek.repository;

import java.util.List;

import org.peek.domain.AppInsState;
import org.peek.service.query.AppStateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class AppInsStateRepository  {
	@Autowired
    private MongoTemplate mongoTemplate;

	String collectionName="appInsState";
	
	public List<AppInsState> find(AppStateQuery query, Integer topn){
		Query querys=new Query();
		Criteria criteria = new Criteria();
		querys.addCriteria(criteria);
		
		if(!StringUtils.isEmpty(query.getAppInsId())){
			criteria.and("insId").is(query.getAppInsId());
		}
		if(query.getStartTime()!=null){
			criteria.and("sysTime").gte(query.getStartTime());
		}
		if(query.getEndTime()!=null){
			criteria.and("sysTime").lte(query.getEndTime());
		}
		querys.with(Sort.by(Sort.Direction.DESC, "sysTime"));  
		if(topn!=null) {
			querys.skip(0).limit(topn);
		}
		return mongoTemplate.find(querys,AppInsState.class);
	}

	public void save(AppInsState appState) {
		mongoTemplate.save(appState);
	}

}
