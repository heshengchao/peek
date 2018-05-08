package org.peek.job;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.peek.domain.AppInstance;
import org.peek.logger.LoggerCount;
import org.peek.protocol.WriteBean;
import org.peek.protocol.client.MinaClient;
import org.peek.repository.AppInstanceRepository;
import org.peek.repository.LoggerCountRepository;
import org.peek.service.NoticeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchDataJob implements InitializingBean {
	
	@Autowired AppInstanceRepository appRepository;
	@Autowired NoticeService noticeService;
	@Autowired LoggerCountRepository logRepository;
	
	
	public void fetch(String host,int port) {
		WriteBean  wb=MinaClient.sendMsg(host, port, "fetchLogger");
		if(wb!=null) {
			List<LoggerCount> list=JSON.parseArray(wb.getXmlMsg(),LoggerCount.class);
			logRepository.saveAll(list);
		}
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
							fetch(app.getInsIp(),app.getInsPort());
						}
					}
				}catch (Throwable e) {
					log.error(e.getMessage(),e);
				}
			}
		}, new Date(), 10000);
		
	}
}
