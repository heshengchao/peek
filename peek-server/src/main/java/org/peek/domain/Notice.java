package org.peek.domain;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.gome.cmp.common.entity.BaseEntity;


@Entity
@Table(name = "Notice")
@Where(clause = "deleted = 0")
public class Notice  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "project_name", nullable = false)
	private String projectName;
	
	@Column(name = "phone", nullable = true)
	private String phone;
	
	@Column(name = "email_address", nullable = true)
	private String emailAddress;
	
	@Column(name = "send_type", nullable = false)
	private String sendType;
	
	@Column(name = "send_title", nullable = true)
	private String sendTitle;
	
	@Column(name = "send_content", nullable = false)
	private String sendContent;
	
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Column(name = "success", nullable = false)
	private boolean success;
	
	@Column(name = "notice_level", nullable = false)
	private String level;

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}



	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getSendType() {
		return sendType;
	}


	public void setSendType(String sendType) {
		this.sendType = sendType;
	}


	public String getSendTitle() {
		return sendTitle;
	}


	public void setSendTitle(String sendTitle) {
		this.sendTitle = sendTitle;
	}


	public String getSendContent() {
		return sendContent;
	}


	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	@Override
	public long id() {
		return id;
	}

}
