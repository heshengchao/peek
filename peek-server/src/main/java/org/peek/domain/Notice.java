package org.peek.domain;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Table(name="notice")
public class Notice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;

	private String projectName;
	
	private String phone;
	
	private String emailAddress;
	
	private String sendType;
	
	private String sendTitle;
	
	private String sendContent;
	
	private Date createTime;
	
	private boolean success;
	
	private String level;

}
