package org.peek.service.query;

import java.util.Date;

import lombok.Data;

@Data
public class LoggerInfoQuery {

	private String appGroupId;
	private String appInsId;
	
	private Date startTime;
	private Date endTime;
}
