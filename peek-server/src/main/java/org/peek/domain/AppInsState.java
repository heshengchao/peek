package org.peek.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.peek.enums.InstanceState;

import lombok.Data;

/**APP状态（生命周期）管理
 * @author heshengchao
 */
@Data
@Entity
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
