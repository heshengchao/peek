package org.peek.repository;

import org.peek.domain.AppGroup;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupRepository  extends MybatisRepository<AppGroup, String> {

}
