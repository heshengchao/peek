package org.peek.service.impl;

import java.util.Date;

import org.junit.Test;
import org.peek.service.impl.weixin.WeixinNotifyService;


public class NoticeServiceTest {

	WeixinNotifyService notice=new WeixinNotifyService(); 
	
	@Test
	public void sendMsg() throws Exception {
		notice.submitWeixin("oVW-ywQ5XAqA9GtInRwheXN0KMko", new Date(), "test", "testContent");
	}
}
