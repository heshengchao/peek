package org.peek.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.peek.domain.AppInstance;
import org.peek.domain.LoggerInfo;
import org.peek.logger.LoggerCount;
import org.peek.protocol.WriteBean;
import org.peek.protocol.client.AliveClient;
import org.peek.repository.AppInstanceRepository;
import org.peek.repository.LoggerCountRepository;
import org.peek.service.impl.IdGenerate;
import org.peek.service.impl.weixin.WeixinNotifyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchDataJob implements InitializingBean {
	
	@Autowired AppInstanceRepository appRepository;
	@Autowired IdGenerate idGenerate;
	@Autowired LoggerCountRepository logRepository;

	@Autowired WeixinNotifyService weixinNotifyService;
	
	
	static final Map<String,AliveClient> clientMap=new HashMap<>();
	
	private AliveClient getClient(String key,AppInstance app) {
		
		AliveClient client=clientMap.get(key);
		if(client==null) {
			client=new AliveClient(app.getInsIp(), app.getInsPort(),new AliveClient.Callback() {
				@Override public void action(WriteBean msg) {
					if(StringUtils.isEmpty(msg.getXmlMsg()))
						return;
					
					List<LoggerCount> list=null;
					try {
						list=JSON.parseArray(msg.getXmlMsg(),LoggerCount.class);
					} catch (Exception e) {
						 log.error("parse jsonFail,the str:{}",msg.getXmlMsg());
						 return ;
					}
					List<LoggerInfo> plist=Lists.transform(list, new Function<LoggerCount,LoggerInfo>(){
						@Override
						public LoggerInfo apply(LoggerCount input) {
							LoggerInfo li=new LoggerInfo();
							BeanUtils.copyProperties(input, li);
							
							li.setId(idGenerate.getId());
							li.setAppGroupId(app.getGroupId());
							li.setAppInsId(app.getInsId());
							
							if(!StringUtils.isEmpty(li.getStack())) {
								weixinNotifyService.notifyUser(app,li);
							}
							
							return li;
						}
					});
					
					logRepository.saveAll(plist);
				}
			});
			clientMap.put(key, client);
		}
		return client;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		 Timer timer = new Timer();
		 //每10秒查询一次
		 timer.schedule(new TimerTask() {
			@Override public void run() {
				try {
					List<AppInstance> list=appRepository.findAll();
					if(list!=null && list.size()>0) {
						for(AppInstance app:list) {
							String key=app.getInsIp()+app.getInsPort();
							try {
								getClient(key,app).sendMsg("fetchLogger");
							}catch (Exception e) {
								clientMap.remove(key); 
							}
						}
					}
				}catch (Throwable e) {
					log.error(e.getMessage(),e);
				}
			}
		}, new Date(), 10000);
		
	}
}
