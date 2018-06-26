package org.peek.repository;

import org.peek.domain.Config;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository  extends MongoRepository<Config, String> {

}
