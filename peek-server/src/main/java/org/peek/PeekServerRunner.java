package org.peek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnablePeekCollector(monitorPort=1231)
@EnableScheduling
@EnableMybatisRepositories("org.peek.repository")
@SpringBootApplication
public class PeekServerRunner {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PeekServerRunner.class);
		ConfigurableApplicationContext context = application.run(args);
		context.addApplicationListener(new ApplicationPidFileWriter());
	}
	
	
}
