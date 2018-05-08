package org.peek.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**监控服务分组
 * @author heshengchao
 */
@Data
@Document(collection="appGroup")
public class AppGroup {

	@Id
	private String groupId;
	/**分组名称*/
	private String groupName;
	private String createUser;
	private Date createTime;
}
