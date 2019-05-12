package org.peek.service.impl;

import org.peek.domain.Config;
import org.peek.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

	@Autowired ConfigRepository configRepository;
	
	public Config getByKey(String key) {
		return configRepository.findByKey(key);
	}
	
	public String getValue(String key) {
		Config conf= configRepository.findByKey(key);
		if(conf!=null) {
			return conf.getValue();
		}else {
			return "";
		}
	}

	public void save(Config conf) {
		configRepository.save(conf);
	}
}
