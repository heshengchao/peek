package org.peek.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/** 字典,配置项
 * @author heshengchao
 */
@Data
@Table(name="config")
public class Config {

	public static final String key_weixinMsgTmpCode="weixinMsgTmpCode";
	public static final String key_weixinMsgTmpCode_serverAlive="weixinMsgTmpCode_serverAlive";
	public static final String key_weixinAppID="appId";
	public static final String key_weixinAppSecrt="appSecrt";
	public static final String key_peekServerUrl="peekServerUrl";
	/**组名*/
	private String group;
	@Id
	private String key;
	private String value;
	/**失效期*/
	private Date expireTime;
}
