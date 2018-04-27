package org.peek.job;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.peek.logger.LoggerCount;
import org.peek.protocol.WriteBean;
import org.peek.protocol.client.MinaClient;
import org.peek.service.NoticeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FetchData implements InitializingBean {
	
	@Autowired NoticeService noticeService;
	
	public void fetch(String host,int port,List<String> emailList) {
		 Timer timer = new Timer();
		 //每10秒查询一次
		 timer.schedule(new TimerTask() {
			@Override public void run() {
				try {
					WriteBean  wb=MinaClient.sendMsg(host, port, "fetchLogger");
					if(wb!=null) {
						List<LoggerCount> list=JSON.parseArray(wb.getXmlMsg(),LoggerCount.class);
						for(LoggerCount lc:list) {
							if(!StringUtils.isEmpty(lc.getStack())) {
								noticeService.noticeByEmail(emailList, "系统发生异常", lc.getStack(), lc.getLevel());
							}
						}
					}
				}catch (Throwable e) {
					log.error(e.getMessage(),e);
				}
			}
		}, new Date(), 10000);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		fetch("127.0.0.1",1314,Lists.newArrayList("heshengchao@gome.com.cn"));
	}
}
