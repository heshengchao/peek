package org.peek.repository;

import org.peek.domain.AppInstance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInstanceRepository  extends MongoRepository<AppInstance, String> {

}
