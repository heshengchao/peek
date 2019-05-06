package org.peek.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

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
	@NonNull
	private String groupName;
	@NonNull
	private String createUser;
	@NonNull
	private Date createTime;
}
