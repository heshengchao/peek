package org.peek.service;

import java.util.List;

import org.peek.constant.NoticeLevelEnum;

public interface NoticeService {

	/**
	 * @Title:noticeBySms
	 * @Description:发送短信
	 * @author qiaoguobing 
	 * @Date:2017年11月9日上午10:03:57
	 * @param projectName  模块名字
	 * @param phoneList	手机号
	 * @param sendContent  内容
	 * @return void
	 */
	void noticeBySms( List<String> phoneList, String sendContent,NoticeLevelEnum level);

	
	/**
	 * @Title:noticeByEmail
	 * @Description:发送邮件
	 * @author qiaoguobing 
	 * @Date:2017年11月9日上午10:04:01
	 * @param projectName  模块名字
	 * @param emailAddressList 发送地址
	 * @param sendTitle	发送标题	
	 * @param sendContent  	发送内容
	 * @return void
	 */
	void noticeByEmail(List<String> emailAddressList, String sendTitle,
			 String sendContent,NoticeLevelEnum level);


}
