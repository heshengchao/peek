package org.peek.logger;

import java.util.Queue;

public class LoggingHandler {
	
	Queue<LoggerCount> logList=new LimitQueue<>(1000);
	
	private static final LoggingHandler instance=new LoggingHandler();

	
	private void add(LoggerCount lc) {
		synchronized (logList) {
			logList.offer(lc);
		}
	}
	public Queue<LoggerCount> rebuild() {
		synchronized (logList) {
			Queue<LoggerCount>	old=logList;
			logList=new LimitQueue<>(1000);
			return old;
		}
	}
	
	
	public static void addErrorLogToCounter(LoggerCount lc) {
		instance.add(lc);
	}

	public static Queue<LoggerCount> collect() {
		return instance.rebuild();
	}
}
