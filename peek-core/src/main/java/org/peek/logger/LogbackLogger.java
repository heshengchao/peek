package org.peek.logger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

public class LogbackLogger implements PeekLogger {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(INTERNAL_LOGGER_NAME);

	/** {@inheritDoc} */
	@Override
	public void debug(String msg) {
		LOGGER.debug(msg);
	}

	/** {@inheritDoc} */
	@Override
	public void debug(String msg, Throwable throwable) {
		LOGGER.debug(msg, throwable);
	}

	/** {@inheritDoc} */
	@Override
	public void info(String msg) {
		LOGGER.info(msg);
	}

	/** {@inheritDoc} */
	@Override
	public void info(String msg, Throwable throwable) {
		LOGGER.info(msg, throwable);
	}

	/** {@inheritDoc} */
	@Override
	public void warn(String msg, Throwable throwable) {
		LOGGER.warn(msg, throwable);
	}

	/** {@inheritDoc} */
	@Override
	public void logHttpRequest(HttpServletRequest httpRequest, String requestName, long duration,
			boolean systemError, int responseSize, String loggerName) {
		final Logger logger = org.slf4j.LoggerFactory.getLogger(loggerName);
		if (logger.isInfoEnabled()) {
			logger.info(LOG.buildLogMessage(httpRequest, duration, systemError, responseSize));
		}
	}
}

