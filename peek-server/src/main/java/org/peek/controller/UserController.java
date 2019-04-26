package org.peek.controller;

import java.util.List;

import org.peek.domain.User;
import org.peek.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired UserService userService;
	
	@RequestMapping(value = "")
	public ModelAndView  index( ) {
		ModelAndView mv=new ModelAndView("/user");
		
		List<User> list=userService.findAll();
		mv.addObject("userList", list);
		return mv;
	}
}
