package org.peek.repository;

import org.peek.domain.LoggerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoggerCountRepository  extends MongoRepository<LoggerInfo, String> {
//	@Autowired
//    private MongoTemplate mongoTemplate;
//
//	public void save(List<LoggerInfo> list) {
//		mongoTemplate.insert(list);
//	}
//
//	public void find(LoggerInfoQuery query) {
//		
//	}
}
