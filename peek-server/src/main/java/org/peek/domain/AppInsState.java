package org.peek.domain;

import java.util.Date;

import org.peek.enums.InstanceState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**APP状态（生命周期）管理
 * @author heshengchao
 */
@Data
@Document(collection="appInsState")
public class AppInsState {

	@Id
	private String id;
	/**资源Id*/
	private String insId;
	/**资源名称*/
	private String insName;
	private Date sysTime;
	private InstanceState state;
	
}
