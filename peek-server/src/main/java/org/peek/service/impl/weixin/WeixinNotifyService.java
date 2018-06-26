package org.peek.service.impl.weixin;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.peek.domain.User;
import org.peek.service.impl.UserService;
import org.peek.uitls.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeixinNotifyService {
	@Autowired UserService userService;
	public void notifyUser( Date datetime,String content,String stack) {
		for(User user:userService.findAll()) {
			this.submitWeixin(user.getWeixinOpenId(), datetime, content, stack);
		}
	}
	
	public String submitWeixin(String toUser,Date datetime,String content,String stack) {

		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+AccessTokenUtil.getAccessToken();
//		 log.info("weixin submitUrl:{}",url);
		 
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        
//        json方式
        AlarmNotifyWrappr alarm=new AlarmNotifyWrappr();
        alarm.setTouser(toUser);
        alarm.setTopcolor("#FF0000");
        alarm.setTemplate_id("jhEtKJsd2xZ9OC5rNt-JFDO8PfNh8M_7iOlmHMpHNAg");
        Map<String,AlarmNotifyParam> parames=new HashMap<>();
        alarm.setData(parames);
        parames.put("first",new AlarmNotifyParam( "异常通知","#173177"));
        parames.put("keyword1",new AlarmNotifyParam( DateUtils.format(datetime),"#173177"));
        parames.put("keyword2",new AlarmNotifyParam( content,"#173177"));
        parames.put("remark",new AlarmNotifyParam(stack,"#173177"));
        
        String submitStr=JSON.toJSONString(alarm);
//        log.info("weixin submit:{}",alarm);
        StringEntity entity = new StringEntity(submitStr,"utf-8");//解决中文乱码问题    
        entity.setContentEncoding("UTF-8");    
        entity.setContentType("application/json");    
        httpPost.setEntity(entity);
        
        String respContent = null;
        HttpResponse resp;
		try {
			resp = client.execute(httpPost);
			if(resp.getStatusLine().getStatusCode() == 200) {
	            HttpEntity he = resp.getEntity();
	            respContent = EntityUtils.toString(he,"UTF-8");
	        }
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}finally {
			try {
				client.close();
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
       
        log.info("weixin rsp:{}",respContent);
        return respContent;
    }
}
