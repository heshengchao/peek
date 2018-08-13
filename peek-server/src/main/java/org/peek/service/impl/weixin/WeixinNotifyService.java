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
import org.peek.domain.AppInstance;
import org.peek.domain.Config;
import org.peek.domain.LoggerInfo;
import org.peek.domain.User;
import org.peek.enums.InstanceState;
import org.peek.service.impl.ConfigService;
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
	@Autowired AccessTokenUtil accessTokenUtil;
	@Autowired ConfigService configService;
	
	private Map<InstanceState,String> stateMap=new HashMap<>();
	{
		stateMap.put(InstanceState.connectFail, "服务链接异常");
		stateMap.put(InstanceState.offline, "服务离线");
		stateMap.put(InstanceState.online, "服务上线");
	}
	
	public void notifyUser(AppInstance app,LoggerInfo loginfo) {
		for(User user:userService.findAll()) {
			this.submitWeixin(user.getWeixinOpenId(),app,loginfo);
		}
	}
	
	public String submitWeixin(String toUserOpenID,AppInstance app,LoggerInfo loginfo) {

		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessTokenUtil.getAccessToken();
		 
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        
//        json方式
        AlarmNotifyWrappr alarm=new AlarmNotifyWrappr();
        alarm.setTouser(toUserOpenID);
        alarm.setTopcolor("#FF0000");
        alarm.setUrl(configService.getValue(Config.key_peekServerUrl)+"/log/detail/"+loginfo.getId());
        alarm.setTemplate_id(configService.getValue(Config.key_weixinMsgTmpCode));
        Map<String,AlarmNotifyParam> parames=new HashMap<>();
        alarm.setData(parames);
//        parames.put("first",new AlarmNotifyParam( "异常通知","#173177"));
        parames.put("keyword1",new AlarmNotifyParam(app.getInsIp()));
        parames.put("keyword2",new AlarmNotifyParam( DateUtils.format(loginfo.getTime()),"#173177"));
        parames.put("keyword3",new AlarmNotifyParam(app.getInsName()));
        parames.put("keyword4",new AlarmNotifyParam( loginfo.getMsg(),"#173177"));
        parames.put("keyword5",new AlarmNotifyParam( "无"));
        parames.put("remark",new AlarmNotifyParam(loginfo.getStack(),"#173177"));
        
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
	
	public void serverAliveAlert(AppInstance app,InstanceState state) {
		for(User user:userService.findAll()) {
			this.serverAliveAlert(user.getWeixinOpenId(),app,state);
		}
	}
	/**服务可用性告警
	 * @param toUserOpenID
	 * @param app
	 * @param loginfo
	 * @return
	 */
	public String serverAliveAlert(String toUserOpenID,AppInstance app,InstanceState state) {

		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessTokenUtil.getAccessToken();
		 
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        
//        json方式
        AlarmNotifyWrappr alarm=new AlarmNotifyWrappr();
        alarm.setTouser(toUserOpenID);
        alarm.setTopcolor("#FF0000");
        alarm.setUrl(configService.getValue(Config.key_peekServerUrl)+"/log/app/"+app.getInsId());
        alarm.setTemplate_id(configService.getValue(Config.key_weixinMsgTmpCode_serverAlive));
        Map<String,AlarmNotifyParam> parames=new HashMap<>();
        alarm.setData(parames);
//        parames.put("first",new AlarmNotifyParam( "异常通知","#173177"));
        parames.put("keyword1",new AlarmNotifyParam( DateUtils.format(new Date()),"#173177"));
        parames.put("keyword2",new AlarmNotifyParam(stateMap.get(state)));//告警类型
        parames.put("keyword3",new AlarmNotifyParam(app.getInsName()+"("+app.getInsIp()+app.getInsPort()+")"));//告警内容
        
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
