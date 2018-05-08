package org.peek.repository;

import org.peek.logger.LoggerCount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerCountRepository extends MongoRepository<LoggerCount, String> {

}
