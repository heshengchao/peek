package org.peek.domain;

import javax.persistence.Table;

import javax.persistence.Id;

import lombok.Data;

@Data
@Table(name="user")
public class User {
	@Id
	private String userCode;
	private String userName;
	private String mobile;
	private String email;
	private String weixinOpenId;
}
