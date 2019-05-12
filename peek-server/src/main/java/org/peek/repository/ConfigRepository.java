package org.peek.repository;


import org.apache.ibatis.annotations.Param;
import org.peek.domain.Config;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.data.mybatis.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository  extends MybatisRepository<Config, String> {

	@Query("select  *  from config cc where cc.key=#{key}")
	Config findByKey(@Param("key")String key);
}
