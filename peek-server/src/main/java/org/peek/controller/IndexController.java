package org.peek.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	
	@RequestMapping(value = "/")
	public String  index() throws IOException{
		return "index";
	}
}
