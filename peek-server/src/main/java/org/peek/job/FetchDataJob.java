package org.peek.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.peek.domain.AppInstance;
import org.peek.domain.LoggerInfo;
import org.peek.enums.InstanceState;
import org.peek.logger.LoggerCount;
import org.peek.protocol.WriteBean;
import org.peek.protocol.client.AliveClient;
import org.peek.repository.AppInstanceRepository;
import org.peek.repository.LoggerCountRepository;
import org.peek.service.impl.AppInsStateService;
import org.peek.service.impl.IdGenerate;
import org.peek.service.impl.weixin.WeixinNotifyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchDataJob implements InitializingBean {
	
	@Autowired AppInstanceRepository appRepository;
	@Autowired IdGenerate idGenerate;
	@Autowired LoggerCountRepository logRepository;

	@Autowired AppInsStateService appInsStateService;
	@Autowired WeixinNotifyService weixinNotifyService;
	
	static final Map<String,AliveClientWapper> clientMap=new HashMap<>();
	
	private AliveClient getClient(String key,AppInstance app) {
		
		AliveClientWapper wapper=clientMap.get(key);
		AliveClient client=(wapper==null)?null:wapper.getClient();
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
			wapper=new AliveClientWapper();
			wapper.setClient(client);
			wapper.setInstance(app);
			clientMap.put(key, wapper);
		}
		return client;
	}

	@Scheduled(cron="0/10 * * * * ?")
	public void fetchLogger() {
		Map<String, AliveClientWapper> map=Maps.newHashMap(clientMap);
		for(Map.Entry<String, AliveClientWapper> en:map.entrySet()) {
			AliveClientWapper wapper=en.getValue();
			String key=wapper.getInstance().getInsIp()+wapper.getInstance().getInsPort();
			try {
				boolean success=wapper.getClient().sendMsg("fetchLogger");
				if(!success) {
					log.warn("sendMsg fail!");
					clientMap.remove(en.getKey());
				}
			}catch (Exception e) {
				appInsStateService.add(wapper.getInstance(), InstanceState.connectFail);
				clientMap.remove(key); 
			}
		}
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void connect() {
		List<AppInstance> list=appRepository.findAll();
		if(list!=null && list.size()>0) {
			for(AppInstance app:list) {
				String key=app.getInsIp()+app.getInsPort();
				if(clientMap.containsKey(key))
					continue;
				
				try {
					getClient(key,app).sendMsg("fetchLogger");
				}catch (Exception e) {
					appInsStateService.add(app, InstanceState.connectFail);
					clientMap.remove(key); 
				}
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
		connect();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
