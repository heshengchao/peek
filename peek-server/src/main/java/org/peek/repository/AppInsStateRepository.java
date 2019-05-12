package org.peek.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.ibatis.annotations.Mapper;
import org.peek.domain.AppGroup;
import org.peek.domain.AppInsState;
import org.peek.service.query.AppStateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.Modifying;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

public interface AppInsStateRepository  extends MybatisRepository<AppInsState, String> {

	@Query(statement="queryList")
	List<AppInsState> queryList(AppStateQuery query, int topn);
	
	@Modifying
	@Query("insert into app_ins_state (id,ins_id,ins_name,state) values (#{id},#{insId},#{insName},#{state})")
	AppInsState save(AppInsState entity);
//	@Override
//	@Modifying
//	@Query("update app_ins_state SET id='RB5DM201344001',ins_id='test',ins_name='test',sys_time='05/12/2019 \r\n" + 
//			"21:20:13.486',state='connectFail' where id = 'RB5DM201344001' ")
//	AppInsState save(AppInsState insState);
//	@Autowired
//    private EntityManager entityManager;
//
//	String collectionName="appInsState";
//	
//	public List<AppInsState> find(final AppStateQuery query, Integer topn){
//		
////		List<Predicate> predicatesList = new ArrayList<>();
////		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
////		CriteriaQuery<Object> criteria = entityManager.getCriteriaBuilder().createQuery();
////		 Root<AppStateQuery>  root=criteria.from(AppStateQuery.class);
////		
////		 
////		 new Specification<AppInsState>() {
////		      public Predicate toPredicate(Root<AppInsState> root, CriteriaQuery<?> cquery,
////		            CriteriaBuilder builder) {
////		    	  if(!StringUtils.isEmpty(query.getAppInsId())){
////		    		  builder.and(criteriaBuilder.equal(root.get("insId"),query.getAppInsId())) ;
////		    	}
////		    	  if(query.getStartTime()!=null){
////		  			criteriaBuilder.and(builder.ge(root.get("sysTime"),query.getStartTime()));
////		  		}
////		  		if(query.getEndTime()!=null){
////		  			criteria.where(builder.and(root.le(root.get("sysTime"),query.getEndTime())));
////		  		}
////		         return criteria.where(cquery);
////		      }
////		    };
////		    
////		
////		
////		querys.with(Sort.by(Sort.Direction.DESC, "sysTime"));  
////		if(topn!=null) {
////			querys.skip(0).limit(topn);
////		}
////		return mongoTemplate.find(querys,AppInsState.class);
//		return null;
//	}
//
//	public void save(AppInsState appState) {
//		entityManager.persist(appState);
//	}

}
