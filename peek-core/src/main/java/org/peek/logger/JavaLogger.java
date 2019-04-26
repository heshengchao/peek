package org.peek.logger;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;


public class JavaLogger  implements PeekLogger {
	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger(INTERNAL_LOGGER_NAME);

	/** {@inheritDoc} */
	@Override
	public void debug(String msg) {
		LOGGER.log(Level.FINE, msg);
	}

	/** {@inheritDoc} */
	@Override
	public void debug(String msg, Throwable throwable) {
		LOGGER.log(Level.FINE, msg, throwable);
	}

	/** {@inheritDoc} */
	@Override
	public void info(String msg) {
		LOGGER.log(Level.INFO, msg);
	}

	/** {@inheritDoc} */
	@Override
	public void info(String msg, Throwable throwable) {
		LOGGER.log(Level.INFO, msg, throwable);
	}

	/** {@inheritDoc} */
	@Override
	public void warn(String msg, Throwable throwable) {
		LOGGER.log(Level.WARNING, msg, throwable);
	}

	/** {@inheritDoc} */
	@Override
	public void logHttpRequest(HttpServletRequest httpRequest, String requestName, long duration,
			boolean systemError, int responseSize, String loggerName) {
		final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(loggerName);
		if (logger.isLoggable(Level.INFO)) {
			logger.info(LOG.buildLogMessage(httpRequest, duration, systemError, responseSize));
		}
	}

	@Override
	public boolean isDebugAble() {
		return LOGGER.isLoggable(Level.FINE);
	}

	@Override
	public void warn(String msg) {
		LOGGER.warning(msg);
	}
}
