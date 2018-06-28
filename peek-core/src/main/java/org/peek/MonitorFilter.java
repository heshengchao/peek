package org.peek;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.peek.logger.LOG;
import org.peek.logger.LogbackAppender;
import org.peek.protocol.server.MinaServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorFilter  implements Filter {
	private MinaServer minaService;
	
	public MonitorFilter(MinaServer minaService) {
		this.minaService=minaService;
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("peek monitor start");
		initLogs();
	}
	private static void initLogs() {

		if (LOG.LOG4J_ENABLED) {
			// si log4j est disponible on branche aussi l'appender pour le counter de logs
//			Log4JAppender.getSingleton().register();
		}

		if (LOG.LOG4J2_ENABLED) {
			// si log4j2 est disponible on branche aussi l'appender pour le counter de logs
			//			Log4J2Appender.getSingleton().register();
		}

		if (LOG.LOGBACK_ENABLED) {
			// si logback est disponible on branche aussi l'appender pour le counter de logs
			LogbackAppender.getSingleton().register();
		}
		LOG.debug("log listeners initialized");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest req=(HttpServletRequest)request;


//		String m=req.getHeader("peek.monitor");
//		if(!StringUtils.isEmpty(m)) {
//			response.setContentType("text/json; charset=UTF-8");  
//			String time=req.getParameter("time");
//			PrintWriter out = response.getWriter();  
////	        out.print(Counter.popByTime(time));  
//	        out.flush();  
//	        return;
//		}
		
//		long currentTime=System.currentTimeMillis();
		
		chain.doFilter(request, response);
//		Date now= new Date();
//		long finishTime=now.getTime();
		
//		Counter.addCount(now, MetricConstant.HttpCount, finishTime-currentTime, CountType.AVG);
	}

	@Override
	public void destroy() {
		minaService.stop();
		log.info("peek stop");
	}

}
