package org.peek.constant;

/**
 * 邮箱常量
 * s
 * @ClassName: EmailConstant
 * @author 
 * @date 
 */
public class EmailConstant {
    /**
     * @Fields MAIL_LINK_URL_BASE : 邮箱基础地址
     */
//    public static final String MAIL_LINK_URL_BASE = "http://10.112.68.180/business_platform/index.html";
    /**
     * @Fields MAIL_LINK_URL_CHANGE_PHONE : 验证身份后修改手机页面
     */
    public static final String MAIL_LINK_URL_CHANGE_PHONE = "#/change-phone-link";
    /**
     * @Fields MAIL_LINK_URL_CHANGE_EMAIL : 验证身份后修改邮箱页面
     */
    public static final String MAIL_LINK_URL_CHANGE_EMAIL = "#/change-email-link";
    /**
     * @Fields MAIL_LINK_URL_BIND_EMAIL : 绑定邮箱成功后的绑定成功页面
     */
    public static final String MAIL_LINK_URL_BIND_EMAIL = "#/verify-email/3";
    /**
     * @Fields MAIL_LINK_URL_VERIFY_EMAIL : 修改邮箱成功 后的修改成功页面
     */
    public static final String MAIL_LINK_URL_VERIFY_EMAIL = "#/change-email/3";
    
    /** 邮箱服务器 **/
    public static final String MAIL_SERVER_HOST = "115.182.95.80";
    /** 邮箱服务端口 **/
    public static final String MAIL_SERVER_PORT = "25";
    /** 邮箱是否登录 **/
    public static final boolean MAIL_VALIDATE = true;
    /** 邮箱帐号 **/
    public static final String MAIL_USERNAME = "noreply-laigome";
    /** 邮箱密码 **/
    public static final String MAIL_PASSWORD = "Cdcp12345";
    /** 邮箱发送者email **/
    public static final String SENDER_MAIL_ADDRESS = "noreply-laigome@gome.com.cn";
    /** 邮箱标题 **/
    public static final String MAIL_TITLE = "验证你的国美来购邮箱";
    /** 邮箱rediskey前缀 **/
    public static final String MAIL_VALIDATE_REDIS_PREFIX = "MAIL_VALIDATE_REDIS_PREFIX";
    /** 邮箱redis失效时间 **/
    public static final long MAIL_VALIDATE_REDIS_EXPIRY = 24l;
    /** 发送邮件失败提示 */
    public static final String SEND_MAIL_FAIL = "send email fail";
   
}
