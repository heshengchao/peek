package org.peek;


import org.peek.protocol.server.MinaServer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class PeekConfiguration {

	@Bean(name = "peekFilter")
	public FilterRegistrationBean filterMonitoringFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new MonitorFilter());
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	
	@Bean(initMethod="start",destroyMethod="stop")
	public MinaServer tcpServer() {
		return new MinaServer();
	}
}