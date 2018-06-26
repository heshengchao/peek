package org.peek.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.peek.controller.vo.WinxinReturnVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.kevinsawicki.http.HttpRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/peekWeixin")
public class WeixinController {
	
	
	@RequestMapping(value = "/callback")
	public WinxinReturnVo  index(HttpRequest req) throws IOException{
		WinxinReturnVo vo=new WinxinReturnVo();
		vo.setErrmsg("ok");
		return vo;
	}
	
	 /**
     * 重定向微信认证页面
     */
	@RequestMapping(value = "/toAuthorize")
    public void toAuthorize( HttpServletResponse response) throws IOException {
        String encodedUrl = URLEncoder.encode("http://test.laigome.com/peekWeixin/getOpenId", "utf-8");
        StringBuilder redirect = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize");
        redirect.append("?appid=").append("wx12dad99918ae1d41")
                .append("&redirect_uri=").append(encodedUrl)
                .append("&response_type=code&scope=snsapi_base")
                .append("&state=").append("peek")
                .append("#wechat_redirect");
        log.info("[weixin]: go to authorize page : {}.", redirect);
        response.sendRedirect(redirect.toString());
    }
	
	@RequestMapping(value = "/getOpenId", method = {RequestMethod.GET})
    public  Map<String, String> getOpenId(@RequestParam(required=false,name="code")final String code,final HttpServletRequest request,HttpServletResponse response) {
    	String tokenBody= HttpRequest.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx12dad99918ae1d41&secret=91c34685d2afb5312b406c6b344ee76a&code="+code+"&grant_type=authorization_code").body();
    	log.info("tokenBody rsp:{}",tokenBody);
    	Map<String, String> tokenMap = JSON.parseObject(tokenBody,new TypeReference<Map<String, String>>(){} );
    	final String openId=tokenMap.get("openid");
    	log.info("loginFrom weixin openid:{}",openId);

    	 return tokenMap;
    }
}
