package org.peek.logger;

import java.util.Date;

import lombok.Data;

@Data
public class LoggerCount {

	private String msg;
	private String thread;
	private Date time;
	private String level;
	private String stack;
}
