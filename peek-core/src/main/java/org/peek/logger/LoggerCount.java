package org.peek.logger;

import java.util.Date;

import org.peek.common.LoggerLevelEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="loggerCount")
public class LoggerCount {

	private String msg;
	private String thread;
	private Date time;
	private LoggerLevelEnum level;
	private String stack;
}
