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
		ins.setInsId("gsh_chubWeb_300");
		ins.setInsName("chubWeb_300");
		ins.setInsIp("10.112.65.119");
		ins.setInsPort(17034);
		ins.setCreateTime(new Date());
		ins=repository.save(ins);
		
		log.warn("ins:{}",JSON.toJSONString(ins));
	}
}
