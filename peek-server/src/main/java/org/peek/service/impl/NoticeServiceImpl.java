package org.peek.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.peek.common.LoggerLevelEnum;
import org.peek.constant.EmailConstant;
import org.peek.domain.Notice;
import org.peek.service.NoticeService;
import org.peek.service.impl.weixin.AccessTokenUtil;
import org.peek.service.impl.weixin.AlarmNotifyParam;
import org.peek.service.impl.weixin.AlarmNotifyWrappr;
import org.peek.uitls.DateUtils;
import org.peek.utils.MailSenderUtils;
import org.peek.utils.SendSmsComponent;
import org.peek.utils.email.MailSenderInfo;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {
	// 短信服务地址
	public static final String smsTemplate = "http://10.0.4.30:8082/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?userId=test02&password=123456&pszMobis=%s&pszMsg=%s&iMobiCount=1&pszSubPort=*";

//	@Autowired
//	private NoticeRepository noticeRepository;

	@Override
	public void noticeBySms(List<String> phoneList, String sendContent,LoggerLevelEnum level) {
		log.info("NoticeServiceImpl.noticeBySms.phoneList={},sendContent={},level={}",
				JSON.toJSON(phoneList), sendContent,level);
		// 发送+存储
		if (phoneList != null && phoneList.size() != 0) {
			for (String phone : phoneList) {
				boolean b = new SendSmsComponent().sendMsg(phone, sendContent, smsTemplate);
				log.info("sendSms.phone={},success={}", phone, b);
			}
		}
	}
	@Override
	public void noticeByWeixin(List<String> openIdList, String sendContent,LoggerLevelEnum level) {
		log.info("NoticeServiceImpl.noticeBySms.phoneList={},sendContent={},level={}",
				JSON.toJSON(openIdList), sendContent,level);
		// 发送+存储
		if (openIdList != null && openIdList.size() != 0) {
			for (String phone : openIdList) {
				boolean b = new SendSmsComponent().sendMsg(phone, sendContent, smsTemplate);
				log.info("sendSms.phone={},success={}", phone, b);
			}
		}
	}
	
	
	@Override
	public void noticeByEmail( List<String> emailAddressList, String sendTitle,
			String sendContent,LoggerLevelEnum level) {

//		log.info("NoticeServiceImpl.noticeByEmail.emailAddressList={},sendTitle={},sendContent={},level={}",
//				 JSON.toJSON(emailAddressList), sendTitle, sendContent,level);
		if (emailAddressList != null && emailAddressList.size() != 0) {
			for (String address : emailAddressList) {
				// 邮件参数
				MailSenderInfo mailInfo = new MailSenderInfo();
				mailInfo.setMailServerHost(EmailConstant.MAIL_SERVER_HOST);
				mailInfo.setMailServerPort(EmailConstant.MAIL_SERVER_PORT);
				mailInfo.setUserName(EmailConstant.MAIL_USERNAME);
				mailInfo.setFromAddress(EmailConstant.SENDER_MAIL_ADDRESS);
				mailInfo.setPassword(EmailConstant.MAIL_PASSWORD);
				mailInfo.setValidate(EmailConstant.MAIL_VALIDATE);
				mailInfo.setSubject(sendTitle);
				mailInfo.setContent(sendContent);
				mailInfo.setToAddress(address);
				boolean b = MailSenderUtils.sendTextMail(mailInfo);
				log.info("sendEmail.address={},success={}", address, b);
 
			}
		}

	}

}
