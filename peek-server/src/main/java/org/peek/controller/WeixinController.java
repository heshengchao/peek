package org.peek.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.peek.controller.vo.WinxinReturnVo;
import org.peek.domain.Config;
import org.peek.service.impl.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.kevinsawicki.http.HttpRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/peekWeixin")
public class WeixinController {
	
	@Autowired ConfigService configService;
	
	@ResponseBody
	@RequestMapping(value = "/token")
	public String  token() throws IOException{
		return "peekToken";
	}
	
	@ResponseBody
	@RequestMapping(value = "/echostr")
	public String  echostr(@RequestParam("echostr") String echostr) throws IOException{
		return echostr;
	}
	@ResponseBody
	@RequestMapping(value = "/aesKey")
	public String  aesKey() throws IOException{
		return "Ud619UzuHNRQ660E8TDxURVapm2urGgH77b7PtKNjvu";
	}
	@RequestMapping(value = "/callback")
	public WinxinReturnVo  index() throws IOException{
		WinxinReturnVo vo=new WinxinReturnVo();
		vo.setErrmsg("ok");
		return vo;
	}
	
	 /**
     * 重定向微信认证页面
     */
	@RequestMapping(value = "/toAuthorize")
    public void toAuthorize( HttpServletResponse response) throws IOException {
		
		String appId=configService.getValue(Config.key_weixinAppID);
		
        String encodedUrl = URLEncoder.encode("http://test.laigome.com/peek/peekWeixin/getOpenId", "utf-8");
        StringBuilder redirect = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize");
        redirect.append("?appid=").append(appId)
                .append("&redirect_uri=").append(encodedUrl)
                .append("&response_type=code&scope=snsapi_base")
                .append("&state=").append("peek")
                .append("#wechat_redirect");
        log.info("[weixin]: go to authorize page : {}.", redirect);
        response.sendRedirect(redirect.toString());
    }
	
	@RequestMapping(value = "/getOpenId", method = {RequestMethod.GET})
    public  Map<String, String> getOpenId(@RequestParam(required=false,name="code")final String code,final HttpServletRequest request,HttpServletResponse response) {
		log.info("getOpenId for:{}",code);
		String appId=configService.getValue(Config.key_weixinAppID);
		String secret=configService.getValue(Config.key_weixinAppSecrt);
		String tokenBody= HttpRequest.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code").body();
    	log.info("tokenBody rsp:{}",tokenBody);
    	Map<String, String> tokenMap = JSON.parseObject(tokenBody,new TypeReference<Map<String, String>>(){} );
    	final String openId=tokenMap.get("openid");
    	log.info("loginFrom weixin openid:{}",openId);

    	 return tokenMap;
    }
}
