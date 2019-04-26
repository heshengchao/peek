package org.peek.service.impl;

import java.util.List;

import org.peek.domain.AppGroup;
import org.peek.domain.AppInstance;
import org.peek.repository.AppGroupRepository;
import org.peek.repository.AppInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppService {
	@Autowired AppGroupRepository groupRepository;
	@Autowired AppInstanceRepository insRepository;
	
	public void addGroup(AppGroup group) {
		groupRepository.save(group);
	}
	
	public void addIns(AppInstance ins) {
		insRepository.save(ins);
	}

	public List<AppInstance> listAppIns() {
		return insRepository.findAll();
	}
}
