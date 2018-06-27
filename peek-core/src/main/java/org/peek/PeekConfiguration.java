package org.peek;


import org.peek.protocol.server.MinaServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

public class PeekConfiguration {
	@Autowired   Environment env;  
	
	@Bean(name = "peekFilter")
	public FilterRegistrationBean filterMonitoringFilter(MinaServer minaService) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new MonitorFilter(minaService));
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	
	@Bean(initMethod="start",destroyMethod="stop")
	public MinaServer tcpServer() {
		String ipStr=env.getProperty("peek.ip");
		String portStr=env.getProperty("peek.port");
		MinaServer server= new MinaServer();
		if(!StringUtils.isEmpty(ipStr)) {
			server.setIp(ipStr);
		}
		if(!StringUtils.isEmpty(portStr)) {
			server.setPort(Integer.parseInt(portStr));
		}
		return server;
	}
}