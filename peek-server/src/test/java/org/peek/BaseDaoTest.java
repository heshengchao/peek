package org.peek;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;



/** 
* @ClassName: BaseDaoTest 
* @Description: TODO
* @author heshengchao
* @date 2016年9月5日 下午4:20:33 
*  
*/
@SpringBootTest(classes = { DaoConfiguration.class })
@RunWith(SpringRunner.class)
@ActiveProfiles("test,300")
public abstract class BaseDaoTest {
	
}