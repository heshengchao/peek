package org.peek;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class PeekConfiguration {

	@Bean(name = "monitoringFilter")
	public FilterRegistrationBean filterMonitoringFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new MonitorFilter());
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}