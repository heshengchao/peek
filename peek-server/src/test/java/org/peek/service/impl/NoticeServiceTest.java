package org.peek.service.impl;

import java.util.Date;

import org.junit.Test;
import org.peek.service.impl.NoticeServiceImpl;


public class NoticeServiceTest {

	NoticeServiceImpl notice=new NoticeServiceImpl(); 
	
	@Test
	public void sendMsg() throws Exception {
		notice.httpPostWithJSON("289A71E7E88A042EAE16CD481A50F29B", new Date(), "test", "testContent");
	}
}
