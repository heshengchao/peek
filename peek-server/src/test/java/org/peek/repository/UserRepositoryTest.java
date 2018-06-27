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
		
		ins.setEmail("wangjie24@gome.com.cn");
		ins.setUserCode("wangjie24");
		ins.setUserName("王洁");
		ins.setMobile("13699449404");
		ins.setWeixinOpenId("oVW-ywSnUM1EMEQM6Sdd7WMDIRrA");
		ins=repository.save(ins);
		
		
		ins.setEmail("tongpeiling@gome.com.cn");
		ins.setUserCode("tongpeiling");
		ins.setUserName("童培林");
		ins.setMobile("13699449404");
		ins.setWeixinOpenId("oVW-ywZ4HYwJj72dOcznBSU-0hxE");
		ins=repository.save(ins);
		
		
		ins.setEmail("luojiejun@gome.com.cn");
		ins.setUserCode("luojiejun");
		ins.setUserName("罗杰俊");
		ins.setMobile("13699449404");
		ins.setWeixinOpenId("oVW-ywcsY7jkrWtnfRNztnqITCsM");
		ins=repository.save(ins);
		
		
		ins.setEmail("dengbing@gome.com.cn");
		ins.setUserCode("dengbing");
		ins.setUserName("邓斌");
		ins.setMobile("13699449404");
		ins.setWeixinOpenId("oVW-ywehJ7wRwxEFtcDdkaYB6XP4");
		ins=repository.save(ins);
		
		
		log.warn("ins:{}",JSON.toJSONString(ins));
	}
}
