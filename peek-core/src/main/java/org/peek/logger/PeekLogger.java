package org.peek.logger;

import javax.servlet.http.HttpServletRequest;

public interface PeekLogger {
	/**
	 * Nom du logger interne.
	 */
	String INTERNAL_LOGGER_NAME = "org.peek";

	/**
	 * Log interne en niveau debug.
	 * @param msg Message
	 */
	void debug(String msg);

	/**
	 * Log interne en niveau debug.
	 * @param msg Message
	 * @param throwable Throwable
	 */
	void debug(String msg, Throwable throwable);

	/**
	 * Log interne en niveau info.
	 * @param msg Message
	 */
	void info(String msg);

	/**
	 * Log interne en niveau info.
	 * @param msg Message
	 * @param throwable Throwable
	 */
	void info(String msg, Throwable throwable);

	/**
	 * Log interne en niveau warn.
	 * @param msg Message
	 * @param throwable Throwable
	 */
	void warn(String msg, Throwable throwable);

	/**
	 * Log les détails de l'exécution d'une requête http en niveau info.
	 * @param httpRequest Requête http
	 * @param requestName Nom de la requête
	 * @param duration Durée
	 * @param systemError Si erreur systême
	 * @param responseSize Taille de la réponse
	 * @param loggerName Nom du logger à utiliser
	 */
	void logHttpRequest(HttpServletRequest httpRequest, String requestName, long duration,
			boolean systemError, int responseSize, String loggerName);
}
