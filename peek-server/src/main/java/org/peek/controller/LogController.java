package org.peek.controller;

import java.io.IOException;

import org.peek.domain.AppGroup;
import org.peek.service.impl.AppService;
import org.peek.utils.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/app")
public class LogController {

	@Autowired AppService appService;
	
	@RequestMapping(value = "/")
	public ModelAndView  index() throws IOException{
		ModelAndView mv=new ModelAndView("logger");
		
		appService.addGroup(group);
		
		return WebResult.ok();
	}
}
