package org.peek.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="user")
public class User {
	@Id
	private String userCode;
	private String userName;
	private String mobile;
	private String email;
	private String weixinOpenId;
}
