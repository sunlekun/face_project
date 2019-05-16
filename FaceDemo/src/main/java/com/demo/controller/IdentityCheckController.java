package com.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.demo.model.Manager;
import com.demo.model.VideoIdent;
import com.demo.realm.PermissionName;
import com.demo.service.VideoIdentService; 

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月16日 下午2:30:26
 * @ClassName 类名称：身份认证审核
 * @Description 类描述
 */
@Controller
@RequestMapping(value = "/identityCheck")
public class IdentityCheckController {
	public static Logger log= Logger.getLogger(IdentityCheckController.class);
	@Autowired
	private VideoIdentService videoIdentService;
	@RequestMapping(value = "/identityCheckList")	
	public void identityCheckList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,Object > map=new HashMap<String ,Object >();
		map.put("video_status", request.getParameter("video_status"));
		map.put("status", request.getParameter("status"));
		map.put("data_type", manager.getUser_type());
		List<VideoIdent> identityChecks = videoIdentService.findVideoListByMultiCondition(map);
		String jsons = JSON.toJSONString(identityChecks);

		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
		
	}
	
	
	@RequestMapping(value = "/toIdentityCheckList")	
	@RequiresPermissions("identityCheck:Show")
	@PermissionName("身份认证审核")
	public ModelAndView toIdentityCheckList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = new ModelAndView();  
		modelAndView.addObject("status", request.getParameter("status")); 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
	    modelAndView.setViewName("admin/identityCheckList"); 
	    return modelAndView;
	}
	
}
