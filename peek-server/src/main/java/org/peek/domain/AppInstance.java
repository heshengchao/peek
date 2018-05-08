package org.peek.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**监控进程实例
 * @author heshengchao
 */
@Data
@Document(collection="appInstance")
public class AppInstance {
	@Id
	private String insId;
	/**实例名称*/
	private String insName;
	/**实例对应的IP*/
	private String insIp;
	/**实例对应的端口*/
	private int insPort;
	private String groupId;
	private String groupName;
	private String createUser;
	private Date createTime;
}
