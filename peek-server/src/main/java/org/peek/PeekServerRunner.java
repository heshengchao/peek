package org.peek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
@SpringBootApplication
public class PeekServerRunner {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PeekServerRunner.class);
		application.run(args);
	}
	
	
	   @Bean  
	    public FilterRegistrationBean  filterSitemesh() {  
	        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
	        registrationBean.setFilter(new WebSiteMeshFilter());  
	        return registrationBean;  
	    }  
	
}
