package  org.peek.repository;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.peek.BaseDaoTest;
import org.peek.domain.AppInstance;
import org.peek.repository.AppInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppInstanceRepositoryTest extends BaseDaoTest{

	@Autowired AppInstanceRepository repository;
	
	@Test
	public void save() {
		InputStream ins=getClass().getClassLoader().getResourceAsStream("800AppIns.json");
		
		List<AppInstance> appes=JSON.parseArray(ConvertStream2Json(ins), AppInstance.class);
		for(AppInstance app:appes) {
			repository.save(app);
		}
		
	}
	
	
	private static String ConvertStream2Json(InputStream inputStream)
    {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }
	
	@Test
	public void findAll() {
		List<AppInstance> list=repository.findAll();
		
		log.warn("ins:{}",JSON.toJSONString(list));
	}
	
}
