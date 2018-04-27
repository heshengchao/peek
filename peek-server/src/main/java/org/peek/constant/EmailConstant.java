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
    public static final String MAIL_SERVER_HOST = "10.0.10.30";
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
//    /** 邮箱修改密码内容 **/
//    public static final String MAIL_CONTENT_UPDATE_PWD = "<div>尊敬的用户，您好! <br>我收到一个重置您微店商家平台账户密码的请求，请点击以下链接重置密码：<br> %s <br> 如果您忽略此信息，您的密码将不会改变。<br><br><br>国美来购<hr>此邮件由国美来购发出，系统不接收回信，请勿直接回复。</div>";
    /** 修改手机账号，邮件内容 **/
    public static final String MAIL_CONTENT_CHANGE_PHONE = "<div>尊敬的用户，您好! <br>我收到一个更改您微店商家平台账户的请求，请点击以下链接修改账号：<br> %s <br> 如果您忽略此信息，您的账号将不会改变。<br><br><br>国美来购<hr>此邮件由国美来购发出，系统不接收回信，请勿直接回复。</div>";
    /** 修改邮件信息，邮件内容 **/
    public static final String MAIL_CONTENT_UPDATE_EMAIL = "<div>尊敬的用户，您好! <br>我收到一个重置您微店商家平台账户邮箱的请求，请点击以下链接重置邮箱：<br> %s <br> 如果您忽略此信息，您的邮箱将不会改变。<br><br><br>国美来购<hr>此邮件由国美来购发出，系统不接收回信，请勿直接回复。</div>";
    /** 邮箱验证内容 **/
    public static final String MAIL_CONTENT_VALIDATE = "<div>尊敬的用户，您好! <br>我收到一个验证您微店商家平台邮箱的请求，请点击以下链接验证邮箱：<br> %s <br> 为保证您的账户安全，请在24小时内点击链接。<br><br><br>国美来购<hr>此邮件由国美来购发出，系统不接收回信，请勿直接回复。</div>";
//    /** 邮箱验证内容 **/
//    public static final String MAIL_CONTENT_UPDATE = "<div>尊敬的用户，您好! <br>我收到一个更改您微店商家平台邮箱的请求，请点击以下链接修改邮箱：<br> %s <br> 如果您忽略此信息，您的邮箱将不会得到修改。<br><br><br>国美来购<hr>此邮件由国美来购发出，系统不接收回信，请勿直接回复。</div>";
    /** 邮箱rediskey前缀 **/
    public static final String MAIL_VALIDATE_REDIS_PREFIX = "MAIL_VALIDATE_REDIS_PREFIX";
    /** 邮箱redis失效时间 **/
    public static final long MAIL_VALIDATE_REDIS_EXPIRY = 24l;
    /** 发送邮件失败提示 */
    public static final String SEND_MAIL_FAIL = "send email fail";
   
}
