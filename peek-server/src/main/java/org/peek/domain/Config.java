package org.peek.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

/** 字典,配置项
 * @author heshengchao
 */
@Data
public class Config {

	/**组名*/
	private String group;
	@Id
	private String key;
	private String value;
	/**失效期*/
	private Date expireTime;
}
