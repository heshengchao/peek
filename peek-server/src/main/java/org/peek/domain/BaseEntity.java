package org.peek.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	protected boolean deleted = false;

	private Date createdTime;
	
	private String createdBy;

	private Date updateTime;
	
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

	protected ToStringHelper toStringHelper() {
		return MoreObjects.toStringHelper(this).add("createdTime", createdTime).add("createdBy",createdBy)
				.add("updateTime", updateTime).add("updateBy",updateBy);
	}

	public String toString() {
		return toStringHelper().toString();
	}
}
