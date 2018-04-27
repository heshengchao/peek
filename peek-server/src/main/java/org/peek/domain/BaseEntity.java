package org.peek.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "deleted", columnDefinition = "Bit default 0")
	protected boolean deleted = false;

	@Column(name = "createdTime", nullable = false)
	private Date createdTime;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updateTime")
	private Date updateTime;
	
	@Column(name = "update_by")
	private String updateBy;


	public abstract long id();

	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdateBy() {
		return updateBy;
	}


	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@PrePersist
	protected void prePersist() {
		if (this.createdTime == null)
			createdTime = new Date();
		if (this.updateTime == null)
			updateTime = new Date();
	}

	@PreUpdate
	protected void preUpdate() {
		this.updateTime = new Date();
	}

	@PreRemove
	protected void preRemove() {
		this.updateTime = new Date();
	}

	protected ToStringHelper toStringHelper() {
		return MoreObjects.toStringHelper(this).add("createdTime", createdTime).add("createdBy",createdBy)
				.add("updateTime", updateTime).add("updateBy",updateBy);
	}

	public String toString() {
		return toStringHelper().toString();
	}
}
