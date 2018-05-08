package  org.peek.repository;


import java.util.Date;

import org.junit.Test;
import org.peek.BaseDaoTest;
import org.peek.domain.AppInstance;
import org.peek.repository.AppInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppInstanceRepositoryTest extends BaseDaoTest{

	@Autowired AppInstanceRepository repository;
	
	@Test
	public void save() {
		AppInstance ins=new AppInstance();
		ins.setInsId("gsh_carp_300_94");
		ins.setInsName("carp_300_94");
		ins.setInsIp("10.115.88.20");
		ins.setInsPort(1314);
		ins.setCreateTime(new Date());
		ins=repository.save(ins);
		
		log.warn("ins:{}",JSON.toJSONString(ins));
	}
}
