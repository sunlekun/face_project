package com.demo.controller;
 
 
import java.io.IOException; 
import java.sql.Timestamp; 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 
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
            
		
		
		 
		
		 System.out.println("startTime="+startTime+">>>>>endTime="+endTime);
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
	 

	
}