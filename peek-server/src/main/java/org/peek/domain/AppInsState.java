package org.peek.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.peek.enums.InstanceState;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import lombok.Data;

/**APP状态（生命周期）管理
 * @author heshengchao
 */
@Data
@Entity
@Table(name="app_ins_state")
public class AppInsState  implements Persistable<String>, Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	/**资源Id*/
	@NotNull
	private String insId;
	/**资源名称*/
	private String insName;
	@CreatedDate
	private Date sysTime;
	@Enumerated(EnumType.STRING)
	@Column(updatable = false)
	private InstanceState state;
	
	@Override
	public boolean isNew() {
		return true;
	}
	
}
