package org.peek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class PeekServerRunner {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PeekServerRunner.class);
		application.run(args);
	}
	
	
}
