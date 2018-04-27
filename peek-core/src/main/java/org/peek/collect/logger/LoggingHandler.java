package org.peek.collect.logger;

import java.util.ArrayList;
import java.util.List;

public class LoggingHandler {
	
	List<LoggerCount> logList=new ArrayList<>(1000);
	
	private static final LoggingHandler instance=new LoggingHandler();

	
	private void add(LoggerCount lc) {
		synchronized (logList) {
			logList.add	(lc);
		}
	}
	public List<LoggerCount> rebuild() {
		synchronized (logList) {
			List<LoggerCount>	old=logList;
			logList=new ArrayList<>(1000);
			return old;
		}
	}
	
	
	public static void addErrorLogToCounter(LoggerCount lc) {
		instance.add(lc);
	}

	public static List<LoggerCount> collect() {
		return instance.rebuild();
	}
}
