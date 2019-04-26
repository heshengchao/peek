package org.peek.domain;

import java.util.Date;

import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

import lombok.Data;

/**监控服务分组
 * @author heshengchao
 */
@Data
@Table(name="appGroup")
public class AppGroup {

	@Id
	private String groupId;
	/**分组名称*/
	@NonNull
	private String groupName;
	@NonNull
	private String createUser;
	@NonNull
	private Date createTime;
}
