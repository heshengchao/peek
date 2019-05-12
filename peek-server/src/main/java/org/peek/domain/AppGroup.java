package org.peek.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**监控服务分组
 * @author heshengchao
 */
@Data
@Entity
@Table(name="app_group")
public class AppGroup {

	@Id
	private String groupId;
	/**分组名称*/
	@NotNull
	private String groupName;
	@NotNull
	private String createUser;
	@NotNull
	private Date createTime;
}
