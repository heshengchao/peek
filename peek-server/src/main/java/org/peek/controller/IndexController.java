package org.peek.controller;

import java.io.IOException;
import org.peek.service.impl.AppService;
import org.peek.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@Autowired AppService appService;
	@Autowired UserService userService;
	
	@RequestMapping(value = "/")
	public ModelAndView  index() throws IOException{
		ModelAndView mv=new ModelAndView("index");
		mv.addObject("appInsList", appService.listAppIns()) ;
		mv.addObject("userList", userService.findAll()) ;
		return mv;
	}
}
