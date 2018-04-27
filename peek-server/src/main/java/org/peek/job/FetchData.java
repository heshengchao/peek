package org.peek.job;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.peek.logger.LoggerCount;
import org.peek.protocol.WriteBean;
import org.peek.protocol.client.MinaClient;
import org.peek.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

@Component
public class FetchData {
	@Autowired NoticeService noticeService;
	
	public void fetch(String host,int port,List<String> emailList) {
		 Timer timer = new Timer();
		 //每10秒查询一次
		 timer.schedule(new TimerTask() {
			@Override public void run() {
				WriteBean  wb=MinaClient.sendMsg(host, port, "fetchLogger");
				
				List<LoggerCount> list=JSON.parseArray(wb.getXmlMsg(),LoggerCount.class);
				for(LoggerCount lc:list) {
					if(!StringUtils.isEmpty(lc.getStack())) {
						noticeService.noticeByEmail(emailList, "系统发生异常", lc.getStack(), lc.getLevel());
					}
				}
			}
		}, new Date(), 10000);
	}
}
