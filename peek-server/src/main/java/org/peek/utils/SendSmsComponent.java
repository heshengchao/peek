package org.peek.utils;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import com.github.kevinsawicki.http.HttpRequest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class SendSmsComponent {

	/**
	 * <发送消息>
	 * 
	 * @param toPhone 接收短信手机号码
	 * @param message 消息内容
	 * @param smsTemplate 短信服务地址
	 * @return
	 */
	public boolean sendMsg(String toPhone, String message, String smsTemplate) {
		try {
			HttpRequest.get(String.format(smsTemplate, new Object[] { toPhone, URLEncoder.encode(message, "UTF-8") })).body();
			return true;
		} catch (Exception e) {
			log.error("failed to send message to:[{}]", toPhone);
			return false;
		}
	}
}
