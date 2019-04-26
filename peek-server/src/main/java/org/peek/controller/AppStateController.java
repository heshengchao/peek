package org.peek.controller;

import java.io.IOException;
import java.util.List;

import org.peek.domain.AppInsState;
import org.peek.service.impl.AppInsStateService;
import org.peek.service.query.AppStateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/appState")
public class AppStateController {
	@Autowired AppInsStateService appStateService;
	
	@RequestMapping(value = "")
	public ModelAndView  index(@RequestParam(name="appInsId",required=false)String appInsId,
			@RequestParam(name="topn",required=false,defaultValue="50")int topn) throws IOException{
		ModelAndView mv=new ModelAndView("/appState");
		AppStateQuery query=new AppStateQuery();
		if(!StringUtils.isEmpty(appInsId))
			query.setAppInsId(appInsId);
		
		List<AppInsState> list=appStateService.findTopN(query,topn);
		mv.addObject("appStateList", list);
		return mv;
	}
}
