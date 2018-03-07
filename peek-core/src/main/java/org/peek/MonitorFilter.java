package org.peek;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.peek.metric.CountType;
import org.peek.metric.Counter;
import org.peek.metric.constant.MetricConstant;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorFilter  implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("monitor start");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;


		String m=req.getHeader("peek.monitor");
		if(!StringUtils.isEmpty(m)) {
			response.setContentType("text/json; charset=UTF-8");  
			String time=req.getParameter("time");
			PrintWriter out = response.getWriter();  
	        out.print(Counter.popByTime(time));  
	        out.flush();  
	        return;
		}
		
		long currentTime=System.currentTimeMillis();
		
		chain.doFilter(request, response);
		Date now= new Date();
		long finishTime=now.getTime();
		
		Counter.addCount(now, MetricConstant.HttpCount, finishTime-currentTime, CountType.AVG);
	}

	@Override
	public void destroy() {
		log.info("monitor stop");
	}

}
