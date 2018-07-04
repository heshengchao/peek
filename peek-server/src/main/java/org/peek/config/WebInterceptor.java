package org.peek.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse rsp, Object o, ModelAndView modelAndView) throws Exception {
    	 String contextPath=req.getContextPath();
    	 modelAndView.addObject("contextPath", contextPath);
    }

}
