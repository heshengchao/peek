package  org.peek.repository;



import java.util.Date;

import org.junit.Test;
import org.peek.BaseDaoTest;
import org.peek.domain.Config;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigRepositoryTest extends BaseDaoTest{

	@Autowired ConfigRepository repository;
	
	@Test
	public void save() {
		Config conf=new Config();
    	conf.setKey("appId");
    	conf.setValue("wx12dad99918ae1d41");
		repository.save(conf);
		
		
		conf.setKey("appSecrt");
    	conf.setValue("91c34685d2afb5312b406c6b344ee76a");
		repository.save(conf);
		
		
		conf.setKey("accessToken");
    	conf.setValue("91c34685d2afb5312b406c6b344ee76a");
    	conf.setExpireTime(new Date());
		repository.save(conf);
		
		
		conf.setKey(Config.key_weixinMsgTmpCode);
    	conf.setValue("xRrRZie8fBiDSOc1o7R8BUbT9hBxsd-qsxUBPAeORsc");
    	conf.setExpireTime(new Date());
		repository.save(conf);
		
		log.warn("save finish!");
	}
}
