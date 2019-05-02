package org.peek.repository;

import org.peek.domain.Config;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository  extends MybatisRepository<Config, String> {

}
