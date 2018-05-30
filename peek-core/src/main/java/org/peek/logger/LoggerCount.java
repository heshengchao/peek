package org.peek.logger;

import java.util.Date;

import org.peek.common.LoggerLevelEnum;

import lombok.Data;

@Data
public class LoggerCount {

	private String msg;
	private String clazz;
	private String clazzMethod;
	private int clazzLine;
	private String thread;
	private Date time;
	private LoggerLevelEnum level;
	private String stack;
}
