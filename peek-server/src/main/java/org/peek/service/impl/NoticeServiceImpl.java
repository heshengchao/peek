package org.peek.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gome.cmp.constant.EmailConstant;
import com.gome.cmp.constant.ManageModuleEnum;
import com.gome.cmp.constant.NoticeLevelEnum;
import com.gome.cmp.constant.SendTypeEnum;
import com.gome.cmp.entity.po.Notice;
import com.gome.cmp.entity.repository.NoticeRepository;
import com.gome.cmp.mail.MailSenderInfo;
import com.gome.cmp.service.NoticeService;
import com.gome.cmp.service.utils.MailSenderUtils;
import com.gome.cmp.service.utils.SendSmsComponent;
import com.gome.cmp.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {
	// 短信服务地址
	public static final String smsTemplate = "http://10.0.4.30:8082/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?userId=test02&password=123456&pszMobis=%s&pszMsg=%s&iMobiCount=1&pszSubPort=*";

	@Autowired
	private NoticeRepository noticeRepository;

	@Override
	public void noticeBySms(ManageModuleEnum projectName, List<String> phoneList, String sendContent,NoticeLevelEnum level) {
		log.info("NoticeServiceImpl.noticeBySms.projectName={},phoneList={},sendContent={},level={}", projectName,
				JSON.toJSON(phoneList), sendContent,level);
		// 发送+存储
		if (phoneList != null && phoneList.size() != 0) {
			for (String phone : phoneList) {
				boolean b = new SendSmsComponent().sendMsg(phone, sendContent, smsTemplate);
				log.info("sendSms.phone={},success={}", phone, b);
//				if (b) {
//					// 发送短信成功，存库
//					Notice noti = new Notice();
//					noti.setProjectName(projectName);
//					noti.setSendType(SendTypeEnum.SMS);
//					noti.setPhone(phone);
//					noti.setSendContent(sendContent);
//					noticeRepository.save(noti);
//				}
			}
			// 发送短信成功，存库
			String phoneLis="";
			Notice noti = new Notice();
			noti.setProjectName(projectName.name());
			noti.setSendType(SendTypeEnum.SMS.name());
			for (String phone : phoneList) {
				phoneLis=phone+";";
			}
			noti.setPhone(phoneLis);
			noti.setSendContent(sendContent);
			noti.setLevel(level.name());
			noticeRepository.save(noti);
			
		}
	}

	@Override
	public void noticeByEmail(ManageModuleEnum projectName, List<String> emailAddressList, String sendTitle,
			String sendContent,NoticeLevelEnum level) {

		log.info("NoticeServiceImpl.noticeByEmail.projectName={},emailAddressList={},sendTitle={},sendContent={},level={}",
				projectName, JSON.toJSON(emailAddressList), sendTitle, sendContent,level);
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
//				if (b) {
//					Notice noti = new Notice();
//					noti.setProjectName(projectName);
//					noti.setSendType(SendTypeEnum.email);
//					noti.setEmailAddress(address);
//					noti.setSendTitle(sendTitle);
//					noti.setSendContent(sendContent);
//					noticeRepository.save(noti);
//				}
			}
			// 发送邮件成功，存库
			String addressLis=""; 
			Notice noti = new Notice();
			noti.setProjectName(projectName.name());
			noti.setSendType(SendTypeEnum.email.name());
			for (String address : emailAddressList) {
				addressLis=address+";";
			}
			noti.setEmailAddress(addressLis);
			noti.setSendTitle(sendTitle);
			noti.setSendContent(sendContent);
			noti.setLevel(level.name());
			noticeRepository.save(noti);
		}

	}

	@Override
	public void chooseNotice(ManageModuleEnum projectName, SendTypeEnum sendType, List<String> emailAddressList,
			List<String> phoneList, String sendTitle, String sendContent,NoticeLevelEnum level) {
		log.info("NoticeServiceImpl.chooseNotice:{},{},{},{},{},{},{}", projectName, sendType, emailAddressList, phoneList,
				sendTitle, sendContent,level);
		
		if (sendType == SendTypeEnum.email) {
			//发送邮件
			this.noticeByEmail(projectName, emailAddressList, sendTitle, sendContent,level);
		} else if (sendType == SendTypeEnum.SMS) {
			//发送短信
			this.noticeBySms(projectName, phoneList, sendContent,level);
		} else if (sendType == SendTypeEnum.SMSAndEmail) {
			//发送邮件+短信
			for (String phone : phoneList) {
				boolean b = new SendSmsComponent().sendMsg(phone, sendContent, smsTemplate);
				log.info("sendSms.phone={},success={}", phone, b);
			}
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
			//存库
			String addressLis=""; 
			String phoneLis="";
			Notice noti = new Notice();
			noti.setProjectName(projectName.name());
			noti.setSendType(SendTypeEnum.SMSAndEmail.name());
			for (String address : emailAddressList) {
				addressLis=address+";";
			}
			noti.setPhone(phoneLis);
			for (String phone : phoneList) {
				phoneLis=phone+";";
			}
			noti.setEmailAddress(addressLis);
			noti.setSendTitle(sendTitle);
			noti.setSendContent(sendContent);
			noti.setLevel(level.name());
			noticeRepository.save(noti);

		}

	}

	@Override
	public List<Notice> query(ManageModuleEnum projectName, SendTypeEnum sendType,
			String emailAddress, String phone, String sendTitle, NoticeLevelEnum level){
		
		List<Notice> noticeList ;
		
		Specification<Notice> querySpecifi = new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != projectName){
                    predicates.add(criteriaBuilder.greaterThan(root.get("projectName"), projectName.name()));
                }
                if(null != level){
                    predicates.add(criteriaBuilder.greaterThan(root.get("level"), level.name()));
                }
                if(null != sendType){
                    predicates.add(criteriaBuilder.lessThan(root.get("sendType"), sendType.name()));
                }
                if(!StringUtils.isEmpty(sendTitle)){
                    predicates.add(criteriaBuilder.like(root.get("sendTitle"), "%"+sendTitle+"%"));
                }
                if(!StringUtils.isEmpty(emailAddress)){
                    predicates.add(criteriaBuilder.like(root.get("emailAddress"), "%"+emailAddress+"%"));
                }
                if(!StringUtils.isEmpty(phone)){
                    predicates.add(criteriaBuilder.like(root.get("phone"), "%"+phone+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        
        noticeList = noticeRepository.findAll(querySpecifi);
        
        return noticeList;
	}
}
