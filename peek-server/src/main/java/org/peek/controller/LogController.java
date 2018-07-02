package org.peek.controller;

import java.io.IOException;
import java.util.List;

import org.peek.domain.LoggerInfo;
import org.peek.service.impl.AppService;
import org.peek.service.impl.LoggerCountService;
import org.peek.service.query.LoggerInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/log")
public class LogController {

	@Autowired LoggerCountService loggerCountService;
	@Autowired AppService appService;
	
	@RequestMapping(value = "")
	public ModelAndView  index(@RequestParam(name="appGroup",required=false) String groupId,
			@RequestParam(name="appInsId",required=false)String appInsId) throws IOException{
		ModelAndView mv=new ModelAndView("/logger");
		LoggerInfoQuery query=new LoggerInfoQuery();
		if(!StringUtils.isEmpty(groupId))
			query.setAppGroupId(groupId);
		if(!StringUtils.isEmpty(appInsId))
			query.setAppInsId(appInsId);
		
		List<LoggerInfo> list=loggerCountService.findByApp(query);
		mv.addObject("loggerList", list);
		return mv;
	}
	
	@RequestMapping(value = "/detail/{logId}")
	public ModelAndView  detail(@PathVariable("logId") long logId ) throws IOException{
		ModelAndView mv=new ModelAndView("/loggerDetail");
		LoggerInfo logInfo=loggerCountService.getById(logId);	
		if(!StringUtils.isEmpty(logInfo.getStack())) {
			logInfo.setStack(logInfo.getStack().replaceAll("\n", "<w:br />"));
		}
		mv.addObject("logInfo",logInfo );
		return mv;
	}
}
