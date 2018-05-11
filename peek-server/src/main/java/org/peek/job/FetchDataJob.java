package org.peek.job;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.peek.domain.AppInstance;
import org.peek.domain.LoggerInfo;
import org.peek.logger.LoggerCount;
import org.peek.protocol.WriteBean;
import org.peek.protocol.client.MinaClient;
import org.peek.repository.AppInstanceRepository;
import org.peek.repository.LoggerCountRepository;
import org.peek.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchDataJob implements InitializingBean {
	
	@Autowired AppInstanceRepository appRepository;
	@Autowired NoticeService noticeService;
	@Autowired LoggerCountRepository logRepository;
	
	
	public void fetch(AppInstance ins) {
		WriteBean  wb=MinaClient.sendMsg(ins.getInsIp(), ins.getInsPort(), "fetchLogger");
		if(wb!=null) {
			List<LoggerCount> list=JSON.parseArray(wb.getXmlMsg(),LoggerCount.class);
			List<LoggerInfo> plist=Lists.transform(list, new Function<LoggerCount,LoggerInfo>(){
				@Override
				public LoggerInfo apply(LoggerCount input) {
					LoggerInfo li=new LoggerInfo();
					BeanUtils.copyProperties(input, li);
					
					li.setGroupId(ins.getGroupId());
					li.setInsId(ins.getInsId());
					return li;
				}
			});
			
			logRepository.save(plist);
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
							fetch(app);
						}
					}
				}catch (Throwable e) {
					log.error(e.getMessage(),e);
				}
			}
		}, new Date(), 10000);
		
	}
}
