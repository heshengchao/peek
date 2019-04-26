package org.peek;


import org.peek.protocol.server.MinaServer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

public class PeekConfiguration {
	
	@Bean(name = "peekFilter")
	public FilterRegistrationBean filterMonitoringFilter(MinaServer minaService) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new MonitorFilter(minaService));
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	
	@Bean(initMethod="start",destroyMethod="stop")
	public MinaServer tcpServer(PeekConfigSetting monitorSetting) {
		MinaServer server= new MinaServer(monitorSetting.getPort());
		if(!StringUtils.isEmpty(monitorSetting.getIp())) {
			server.setIp(monitorSetting.getIp());
		}
		
		return server;
	}


}