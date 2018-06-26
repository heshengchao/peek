package org.peek.service.impl;

import java.util.List;

import org.peek.domain.User;
import org.peek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired UserRepository userRepository;
	
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
}
