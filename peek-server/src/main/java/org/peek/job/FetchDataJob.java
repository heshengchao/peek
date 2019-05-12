package org.peek.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.peek.domain.AppInstance;
import org.peek.domain.LoggerInfo;
import org.peek.enums.InstanceState;
import org.peek.job.ActionThreadPool.Call;
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
					if(StringUtils.isEmpty(msg.getXmlMsg()) || msg.getVersion()>1)
						return;
					log.info("receve data:{},{},{}",msg.getVersion(),msg.getCmd(),msg.getSeq());
					List<LoggerCount> list=null;
					try {
						list=JSON.parseArray(msg.getXmlMsg(),LoggerCount.class);
					} catch (Exception e) {
						 log.error("parse jsonFail,the str:{}",msg.getXmlMsg());
						 return ;
					}
					if(list == null) {
						return;
					}
					List<LoggerInfo> plist=Lists.transform(list, new Function<LoggerCount,LoggerInfo>(){
						@Override
						public LoggerInfo apply(LoggerCount input) {
							LoggerInfo li=new LoggerInfo();
							BeanUtils.copyProperties(input, li);
							
							li.setId(idGenerate.getId());
							li.setInsGroupId(app.getGroupId());
							li.setInsId(app.getInsId());
							
							if(!StringUtils.isEmpty(li.getStack())) {
								weixinNotifyService.notifyUser(app,li);
							}
							
							return li;
						}
					});
					
					logRepository.saveAll(plist);
				}

				@Override
				public void close() {
					clientMap.remove(key);
				}

				@Override
				public void connectFail() {
					clientMap.remove(key);
				}
			});
			boolean connectioned=client.connection();
			if(connectioned) {
				wapper=new AliveClientWapper();
				wapper.setClient(client);
				wapper.setInstance(app);
				clientMap.put(key, wapper);
			}else {
				log.warn("connect[{}] fail!",app);
			}
			
		}
		return null;
	}

	@Scheduled(cron="0/5 * * * * ?")
	public void fetchLogger() {
		Map<String, AliveClientWapper> map=Maps.newHashMap(clientMap);
		for(Map.Entry<String, AliveClientWapper> en:map.entrySet()) {
			ActionThreadPool.execute(new Call() {
				@Override public void run() {
					boolean success=en.getValue().getClient().sendMsg("fetchLogger");
					if(!success) {
						log.warn("sendMsg fail!");
					}
				}
			});
				
		}
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void connect() {
		connect(false);
	}
	
	private synchronized void  connect(boolean first) {
		List<AppInstance> list=appRepository.findAll();
		if(list!=null && list.size()>0) {
			for(AppInstance app:list) {
				String key=app.getInsIp()+app.getInsPort();
				if(clientMap.containsKey(key))
					continue;
				AliveClient client=null;
				try {
					client=getClient(key,app);
				}catch (Exception e) {
					appInsStateService.add(app, InstanceState.connectFail);
					continue;
				}
				if(client==null) {
					appInsStateService.add(app, InstanceState.connectFail);
					continue;
				}
				try {
					client.sendMsg("fetchLogger");
					if(!first)
						appInsStateService.add(app, InstanceState.online);
				}catch (Exception e) {
					appInsStateService.add(app, InstanceState.connectFail);
					clientMap.remove(key); 
					try {
						client.close();
					}catch (Exception e1) {
						log.error(e1.getMessage(),e1);
					}
				}
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			connect(true);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
