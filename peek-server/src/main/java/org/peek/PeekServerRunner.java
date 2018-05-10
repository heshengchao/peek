package org.peek;

import javax.servlet.DispatcherType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@SpringBootApplication
public class PeekServerRunner {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PeekServerRunner.class);
		ConfigurableApplicationContext context = application.run(args);
		 context.addApplicationListener(new ApplicationPidFileWriter());
	}
	
	  @Bean  
	    public FilterRegistrationBean  filterFreemarker() {  
	        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
	        registrationBean.setFilter(new FreemarkerFilter()); 
	        registrationBean.addUrlPatterns("*.html");
	        registrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD);
	        return registrationBean;  
	    }  
	    
	   @Bean  
	    public FilterRegistrationBean  filterSitemesh() {  
	        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
	        registrationBean.setFilter(new WebSiteMeshFilter());  
	        return registrationBean;  
	    }  
	
}
