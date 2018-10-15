package org.peek.service.impl.weixin;


import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;

import org.peek.domain.Config;
import org.peek.service.impl.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;  
  
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
  
/**
 * 获取微信APPID和secret工具类 
 */  
@Slf4j
@Service
public  class AccessTokenUtil {  
  
	@Autowired ConfigService configService;
    public synchronized  String getAccessToken() {  
        try {  
            //获取Appid，APPsecret  
            String APPID = configService.getValue("appId");  
            String APPSECRET = configService.getValue("appSecrt");  
            // 获取accesstoken，初始值为空，第一次调用之后会把值写入文件  
            Config access_token = configService.getByKey("accessToken");  
  
           if(access_token!=null && access_token.getExpireTime().after(new Date())) {
        	   return access_token.getValue();
           }
           
           
           Calendar cal=Calendar.getInstance();
            //获取token url  
            String url = "http://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ APPID + "&secret=" + APPSECRET;  
            //发送http请求得到json流  
            JSONObject jobject = httpRequest(url);  
            //从json流中获取access_token  
            String  j_access_token = jobject.getString("access_token");  
            String  j_expires_in = jobject.getString("expires_in");  

            if(!StringUtils.isEmpty(j_access_token)) {
            	cal.set(Calendar.SECOND, cal.get(Calendar.SECOND)+7200);
            	
            	Config conf=new Config();
            	conf.setExpireTime(cal.getTime());
            	conf.setKey("accessToken");
            	conf.setValue(j_access_token);
            	configService.save(conf);
            }
            //如果已经过期返回获取到的access_token  
            return j_access_token;  
        } catch (Exception e) {  
        	log.error(e.getMessage(),e);
            return null;  
        }  
  
  
    }  
  
    // 获取accesstoken  
    public synchronized static JSONObject httpRequest(String requestUrl) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod("GET");  
  
            httpUrlConn.connect();  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            
            String rsp=buffer.toString();
            log.info("response str:{}",rsp);
            jsonObject = JSONObject.parseObject(rsp);  
  
        } catch (Exception e) {  
        	log.error(e.getMessage(),e);
        }  
  
        return jsonObject;  
    }  
}  
