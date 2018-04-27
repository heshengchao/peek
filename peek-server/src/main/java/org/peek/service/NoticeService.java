package org.peek.service;

import java.util.List;

import com.gome.cmp.constant.ManageModuleEnum;
import com.gome.cmp.constant.NoticeLevelEnum;
import com.gome.cmp.constant.SendTypeEnum;
import com.gome.cmp.entity.po.Notice;

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
	void noticeBySms(ManageModuleEnum projectName, List<String> phoneList, String sendContent,NoticeLevelEnum level);

	
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
	void noticeByEmail(ManageModuleEnum projectName, List<String> emailAddressList, String sendTitle,
			 String sendContent,NoticeLevelEnum level);

	/**
	 * @Title:chooseNotice
	 * @Description:选择短信还是邮件，还是都要
	 * @author qiaoguobing 
	 * @Date:2017年11月10日下午3:45:14
	 * @param projectName 项目名
	 * @param sendType	发送类型
	 * @param emailAddressList	邮件地址
	 * @param phoneList	手机号码
	 * @param sendTitle	主题
	 * @param sendContent	内容
	 * @return  
	 */
	void chooseNotice(ManageModuleEnum projectName, SendTypeEnum sendType, List<String> emailAddressList,
			List<String> phoneList, String sendTitle, String sendContent,NoticeLevelEnum level);
	
	
	List<Notice> query(ManageModuleEnum projectName, SendTypeEnum sendType,
			String emailAddress, String phone, String sendTitle,NoticeLevelEnum level);

}
