package com.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
 * @author yangwei
 *
 */
@Controller
public class LoginController { 
	public static Logger log= Logger.getLogger(LoginController.class);
	@RequestMapping(value = "/login")
	public String login(ModelAndView modelAndView,HttpServletRequest request, HttpServletResponse response) { 
		//此方法不处理登录成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        
		//如果登录失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName=(String) request.getAttribute("shiroLoginFailure");
		//根据shiro返回的异常类路径判断，抛出指定异常信息
		if(exceptionClassName!=null)
		{
			if(UnknownAccountException.class.getName().equals(exceptionClassName))
			{
				modelAndView.addObject("error", "账号不存在");
				request.setAttribute("error", "账号不存在");
			}
			else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName))
			{
				modelAndView.addObject("error", "用户名/密码错误");
				request.setAttribute("error", "用户名/密码错误");
			}
			else if(LockedAccountException.class.getName().equals(exceptionClassName))
			{
				modelAndView.addObject("error", "该账号被锁定");
				request.setAttribute("error", "账号被锁定");
			}
			else
			{
				modelAndView.addObject("error", "其他异常信息");
				request.setAttribute("error", "其他异常信息");
			}
		}
		
		
		//此方法不处理登录成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
		//登录失败到login页面
		return "forward:index.jsp";

	}

	}
