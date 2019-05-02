package org.peek.repository;

import org.peek.domain.User;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends MybatisRepository<User, String> {

}
