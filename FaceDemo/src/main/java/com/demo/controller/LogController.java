package com.demo.controller;
 
 
import java.io.IOException;  
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
  

import org.apache.log4j.Logger; 
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.servlet.ModelAndView;
 
 
import com.alibaba.fastjson.JSON;  
import com.demo.model.Log;
import com.demo.realm.PermissionName;
import com.demo.service.LogService; 
 

/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 * 
 * @Project_Package_Description springmvc || com.demo.controller
 * @Function_Description 核心控制类，处理页面的请求以及业务
 * 
 */
@Controller
@RequestMapping(value = "/log")
public class LogController { 
	public static Logger log= Logger.getLogger(LogController.class);
	
	@Autowired
	private LogService logService;
	 
 
 
	@RequestMapping(value = "/logList")	
	public void logList(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		
		
		String startTime1=request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime1=request.getParameter("endTime")==null?"":request.getParameter("endTime");

		String startTime=startTime1;
		String endTime=endTime1;
		
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 
		if(!("").equals(startTime1)&&!("").equals(endTime1))
			 if(sdf.parse(startTime1).getTime()>sdf.parse(endTime1).getTime()){//转成long类型比较
				 
	        	startTime=endTime1;
	        	endTime=startTime1;
	         }
            
		 
		 
		List<Log> logs = logService.findLogByTime(startTime, endTime);
		 
		String jsons = JSON.toJSONString(logs);

		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	@RequestMapping(value = "/toLogList")
	@RequiresPermissions("log:Show")
	@PermissionName("日志管理")
	public ModelAndView toLogList(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("startTime", request.getParameter("startTime")); 
		modelAndView.addObject("endTime", request.getParameter("endTime")); 
	    modelAndView.setViewName("admin/logList"); 
		return modelAndView;
	}
	 
   
	    /** 
	     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	     *  
	     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
	     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
	     *  
	     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
	     * 192.168.1.100 
	     *  
	     * 用户真实IP为： 192.168.1.110 
	     *  
	     * @param request 
	     * @return 
	     */  
	    public static String getIpAddress(HttpServletRequest request) {  
	        String ip = request.getHeader("x-forwarded-for");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }  
	        return ip;  
	    }  
	      
	
 
	
}