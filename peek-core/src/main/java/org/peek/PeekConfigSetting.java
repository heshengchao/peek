package org.peek;

import java.util.Map;

import org.peek.logger.LOG;
import org.peek.protocol.server.MinaServer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

public class PeekConfigSetting  implements EnvironmentAware , ImportBeanDefinitionRegistrar {
	
	private int port=MinaServer.defaltPort;
	private String ip;
	
	@Override
	public void setEnvironment(Environment env) {
		String ipStr=env.getProperty("peek.ip");
		if(!StringUtils.isEmpty(ipStr)) {
			ip=ipStr;
		}
		
		String portStr=env.getProperty("peek.port");
		if(!StringUtils.isEmpty(portStr)) {
			port= Integer.parseInt(portStr);
		}
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		Map<String, Object> defaultAttrs = metadata
				.getAnnotationAttributes(EnablePeekCollector.class.getName(), true);
		this.port=(int) defaultAttrs.get("monitorPort");
		
		
		BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(PeekConfigSetting.class);
		definition.addPropertyValue("ip", ip);
		definition.addPropertyValue("port", port);
		definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		
		
		BeanDefinitionReaderUtils.registerWithGeneratedName(definition.getBeanDefinition(), registry);
		
		LOG.info("setting finish!");
	}


	public void setPort(int port) {
		this.port = port;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}
	
}