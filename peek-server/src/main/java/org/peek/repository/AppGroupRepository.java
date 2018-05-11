package org.peek.repository;

import org.peek.domain.AppGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupRepository  extends MongoRepository<AppGroup, String> {

}
