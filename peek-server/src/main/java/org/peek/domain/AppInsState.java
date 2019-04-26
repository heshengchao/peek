package org.peek.domain;

import java.util.Date;

import javax.persistence.Table;

import org.peek.enums.InstanceState;
import org.springframework.data.annotation.Id;

import lombok.Data;

/**APP状态（生命周期）管理
 * @author heshengchao
 */
@Data
@Table(name="appInsState")
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
