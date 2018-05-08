package org.peek.repository;

import org.peek.domain.AppInstance;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppInstanceRepository  extends MongoRepository<AppInstance, String> {

}
