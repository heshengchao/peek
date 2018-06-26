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
import org.peek.service.NoticeService;
import org.peek.service.impl.UserService;
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
//	@Autowired NoticeService noticeService;
	@Autowired LoggerCountRepository logRepository;

	@Autowired WeixinNotifyService weixinNotifyService;
	
	
	static final Map<String,AliveClient> clientMap=new HashMap<>();
	
	private AliveClient getClient(AppInstance app) {
		AliveClient client=clientMap.get(app.getInsIp()+app.getInsPort());
		if(client==null) {
			client=new AliveClient(app.getInsIp(), app.getInsPort(),new AliveClient.Callback() {
				@Override public void action(WriteBean msg) {
					List<LoggerCount> list=JSON.parseArray(msg.getXmlMsg(),LoggerCount.class);
					List<LoggerInfo> plist=Lists.transform(list, new Function<LoggerCount,LoggerInfo>(){
						@Override
						public LoggerInfo apply(LoggerCount input) {
							LoggerInfo li=new LoggerInfo();
							BeanUtils.copyProperties(input, li);
							
							li.setAppGroupId(app.getGroupId());
							li.setAppInsId(app.getInsId());
							
							if(!StringUtils.isEmpty(li.getStack())) {
								weixinNotifyService.notifyUser(li.getTime(), li.getMsg(), li.getStack());
							}
							
							return li;
						}
					});
					
					logRepository.saveAll(plist);
				}
			});
			clientMap.put(app.getInsIp()+app.getInsPort(), client);
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
							getClient(app).sendMsg("fetchLogger");
						}
					}
				}catch (Throwable e) {
					log.error(e.getMessage(),e);
				}
			}
		}, new Date(), 10000);
		
	}
}
