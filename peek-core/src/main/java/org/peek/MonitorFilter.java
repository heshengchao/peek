package org.peek;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.peek.logger.LOG;
import org.peek.logger.LogbackAppender;
import org.peek.operator.OperatorInfoProvider;
import org.peek.protocol.server.MinaServer;
import org.springframework.util.StringUtils;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorFilter  implements Filter {
	private MinaServer minaService;
	
	public MonitorFilter(MinaServer minaService) {
		this.minaService=minaService;
	}
	
	OperatorInfoProvider operatorProvider;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("peek monitor start");
		initLogs();
	}
	private static void initLogs() {

		if (LOG.LOG4J_ENABLED) {
//			Log4JAppender.getSingleton().register();
		}

		if (LOG.LOG4J2_ENABLED) {
			//Log4J2Appender.getSingleton().register();
		}

		if (LOG.LOGBACK_ENABLED) {
			LogbackAppender.getSingleton().register();
		}
		LOG.debug("log listeners initialized");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;

		Transaction transaction = ElasticApm.startTransaction();
		try {
		    transaction.setName(req.getRequestURI());
		    transaction.setType(Transaction.TYPE_REQUEST);
		    if(operatorProvider!=null && !StringUtils.isEmpty(operatorProvider.getCode())) {
		    	transaction.setUser(operatorProvider.getCode(), "", operatorProvider.getName());
		    }
			chain.doFilter(request, response);
		} catch (Exception e) {
		    transaction.captureException(e);
		    throw e;
		} finally {
		    transaction.end();
		}
		
	}

	@Override
	public void destroy() {
		minaService.stop();
		log.info("peek stop");
	}

}
