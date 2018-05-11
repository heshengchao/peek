package org.peek.controller;

import java.io.IOException;

import org.peek.domain.AppGroup;
import org.peek.domain.AppInstance;
import org.peek.service.impl.AppService;
import org.peek.utils.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/app")
public class AppController {
	@Autowired AppService appService;
	
	@RequestMapping(value = "/addGroup")
	public WebResult<Boolean>  addGroup( AppGroup group) throws IOException{
		
		appService.addGroup(group);
		
		return WebResult.ok();
	}
	
	@RequestMapping(value = "/addInstance")
	public WebResult<Boolean>  addInstance( AppInstance ins) throws IOException{
		
		appService.addIns(ins);
		
		return WebResult.ok();
	}
}
