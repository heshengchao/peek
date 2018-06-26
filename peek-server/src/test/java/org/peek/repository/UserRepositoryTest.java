package  org.peek.repository;



import org.junit.Test;
import org.peek.BaseDaoTest;
import org.peek.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepositoryTest extends BaseDaoTest{

	@Autowired UserRepository repository;
	
	@Test
	public void save() {
		User ins=new User();
		ins.setEmail("heshengchao@gome.com.cn");
		ins.setUserCode("heshengchao");
		ins.setUserName("何胜超");
		ins.setMobile("13699449404");
		ins.setWeixinOpenId("oVW-ywQ5XAqA9GtInRwheXN0KMko");
		ins=repository.save(ins);
		
		log.warn("ins:{}",JSON.toJSONString(ins));
	}
}
