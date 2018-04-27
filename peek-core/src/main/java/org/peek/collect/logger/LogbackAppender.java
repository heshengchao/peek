package org.peek.collect.logger;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class LogbackAppender  extends UnsynchronizedAppenderBase<ILoggingEvent> {
	private static final String MESSAGE_PATTERN = "%-5level %logger{36} - %msg%nopex%n";
	private static final String EXCEPTION_PATTERN = "%ex";
	private static final Level THRESHOLD = Level.WARN;

	private static final LogbackAppender SINGLETON = new LogbackAppender();

	private final PatternLayout exceptionLayout = new PatternLayout();

	private final PatternLayout messageLayout = new PatternLayout();

	/**
	 * Constructeur.
	 */
	public LogbackAppender() {
		super();
		final LoggerContext lc = getDefaultContext();
		messageLayout.setContext(lc);
		messageLayout.setPattern(MESSAGE_PATTERN);
		messageLayout.start();

		exceptionLayout.setContext(lc);
		exceptionLayout.setPattern(EXCEPTION_PATTERN);
		exceptionLayout.start();

		setContext(lc);
		start();
	}

	private static LoggerContext getDefaultContext() {
		return (LoggerContext) LoggerFactory.getILoggerFactory();
	}

	static LogbackAppender getSingleton() {
		return SINGLETON;
	}

	void register() {
		getDefaultContext().getLogger(Logger.ROOT_LOGGER_NAME).addAppender(this);
	}

	void deregister() {
		getDefaultContext().getLogger(Logger.ROOT_LOGGER_NAME).detachAppender(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void append(ILoggingEvent event) {
		// inutile de vérifier que l'appender est bien "started",
		// car il est démarré dans le constructeur et si cela ne fonctionne pas il n'y a pas d'instance
		if (event.getLevel().isGreaterOrEqual(THRESHOLD)) {
			
			LoggerCount lc=new LoggerCount();
			lc.setLevel(event.getLevel().levelStr);
			lc.setMsg(event.getMessage());
			lc.setThread(event.getThreadName());
			lc.setTime(new Date(event.getTimeStamp()));
			String stackTrace = exceptionLayout.doLayout(event);
			if (stackTrace.isEmpty()) {
				lc.setStack(stackTrace);
			}
			
			LoggingHandler.addErrorLogToCounter(lc);
		}
	}
}
