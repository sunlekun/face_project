package com.demo.controller;

import java.io.IOException; 
import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException; 
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

 





import com.demo.aop.LogAspect;
import com.demo.model.Log;
import com.demo.model.Manager;
import com.demo.realm.PermissionName;
import com.demo.service.LogService;
/**
 * 
 * @author yangwei
 *
 */
@Controller 
@RequestMapping(value = "/login")
public class LoginController { 
	public static Logger log= Logger.getLogger(LoginController.class);
	
	@Autowired
	private LogService logService;	  //自己创建，用来保存日志信息
	@RequestMapping(value = "/valid")
	@PermissionName("用户登录")	
	public void login(ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response) throws IOException{ 

		JSONObject object = new JSONObject(); 
		String username=request.getParameter("username");
		String password=request.getParameter("password"); 
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		
		Subject subject=SecurityUtils.getSubject();
		try {
		subject.login(token);   
		} 
		catch (UnknownAccountException e) {
			object.put("msg", "账号不存在"); 
			object.put("status", "false"); 
		}
		catch (IncorrectCredentialsException e) {
			object.put("msg", "用户名/密码错误"); 
			object.put("status", "false"); ;
		}
		catch (LockedAccountException e) {
			object.put("msg", "该账号被锁定");
			object.put("status", "false"); 
		}
		catch (Exception e) {
			object.put("msg", "其他异常信息"); 
			object.put("status", "false"); 
			object.put("url","forward:index.jsp");
		}
		
     if(subject.isAuthenticated())//是否认证通过
		{   object.put("status", "true"); 
			object.put("msg", "登录成功");
			object.put("url","main");
		    
			
			Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		    String Ip = LogAspect.getIpAddress(request); //ip地址
	        
	        Log log = new Log();
	     
			log.setUser_name(manager.getUser_name());
			log.setUser_id(manager.getId());
			
		    log.setUser_ip(Ip);
		    log.setAction_type("Login");
		    log.setRemark("用户登录");
		    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
		 
		    logService.insertLog(log);
		}
  
	    response.setCharacterEncoding("utf-8"); 
		response.getWriter().write(object.toString());

	}

}
