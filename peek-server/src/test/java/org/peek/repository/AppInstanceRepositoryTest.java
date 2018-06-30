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
		ins.setInsId("gsh_sharkRpc_500_21");
		ins.setInsName("sharkRpc_500");
		ins.setInsIp("10.115.88.21");
		ins.setInsPort(16064);
		ins.setCreateTime(new Date());
		ins=repository.save(ins);
		
		ins.setInsId("gsh_sharkRpc_500_19");
		ins.setInsName("sharkRpc_500");
		ins.setInsIp("10.115.88.19");
		ins.setInsPort(16064);
		ins.setCreateTime(new Date());
		ins=repository.save(ins);
		
		log.warn("ins:{}",JSON.toJSONString(ins));
	}
}
