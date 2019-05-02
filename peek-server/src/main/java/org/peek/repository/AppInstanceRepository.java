package org.peek.repository;

import org.peek.domain.AppInstance;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInstanceRepository  extends MybatisRepository<AppInstance, String> {

}
