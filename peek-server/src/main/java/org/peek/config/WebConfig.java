package org.peek.config;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig  implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebInterceptor());
    }
	
	@Bean  
    public FilterRegistrationBean<FreemarkerFilter>  filterFreemarker() {  
        FilterRegistrationBean<FreemarkerFilter> registrationBean = new FilterRegistrationBean<>();  
        registrationBean.setFilter(new FreemarkerFilter()); 
        registrationBean.addUrlPatterns("*.html");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD);
        return registrationBean;  
    }  
    
	@Bean  
    public FilterRegistrationBean<WebSiteMeshFilter>  filterSitemesh() {  
        FilterRegistrationBean<WebSiteMeshFilter> registrationBean = new FilterRegistrationBean<>();  
        registrationBean.setFilter(new WebSiteMeshFilter());  
        return registrationBean;  
    }  
}
