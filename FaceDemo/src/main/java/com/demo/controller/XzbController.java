package com.demo.controller;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
 



import org.apache.log4j.Logger; 
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

  




import com.alibaba.fastjson.JSON; 
import com.demo.model.Manager;
import com.demo.model.Navigation;
import com.demo.model.Role; 
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.XzbService;
import com.demo.util.LoadProperties; 
 

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
@RequestMapping(value = "/xzb")
public class XzbController { 
	public static Logger log= Logger.getLogger(XzbController.class);
	
	@Autowired
	private XzbService xzbService;
	 
 
 
	@RequestMapping(value = "/xzbList")	
	public void xzbList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		 
		List<Xzb> xzbs = xzbService.findAllXzb();
		 
		String jsons = JSON.toJSONString(xzbs);

		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	@RequestMapping(value = "/toXzbList")
	@RequiresPermissions("xzb:toXzbList")
	@PermissionName("乡镇办管理")
	public ModelAndView toXzbList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(); 
	    modelAndView.setViewName("admin/xzbList"); 
		return modelAndView;
	}
	 
	@RequestMapping(value = "/xzbDetial")
	@RequiresPermissions("xzb:xzbDetial")
	@PermissionName("乡镇办详情")
	public ModelAndView xzbDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		Xzb xzb=xzbService.findXzbById(id); 
		modelAndView.addObject("xzb", xzb); 
		modelAndView.setViewName("admin/xzbDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toXzbEdit")
	@RequiresPermissions("xzb:toXzbEdit")
	@PermissionName("乡镇办修改")
	public ModelAndView toXzbEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		Xzb xzb=xzbService.findXzbById(id); 
		modelAndView.addObject("xzb", xzb); 
		modelAndView.setViewName("admin/xzbEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/xzbEdit")	
	public ModelAndView xzbEdit(Xzb xzb ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
		
		xzbService.updateXzb(xzb);
		 
		modelAndView.setViewName("redirect:/xzb/toXzbList");
		return modelAndView;
	}
	@RequestMapping(value = "/toXzbAdd")
	@RequiresPermissions("xzb:toXzbAdd")
	@PermissionName("乡镇办增加")
	public ModelAndView toXzbAdd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.setViewName("admin/xzbAdd"); 
		return modelAndView;
	}
	@RequestMapping(value = "/xzbAdd")
	public ModelAndView xzbAdd(Xzb xzb ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView(); 
		xzb.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
		xzbService.insertXzb(xzb);
		
		modelAndView.setViewName("redirect:/xzb/toXzbList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/xzbDelete")
	@RequiresPermissions("xzb:xzbDelete")
	@PermissionName("乡镇办删除")
	public ModelAndView xzbDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new  ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				xzbService.deleteXzbBatch(ids);
		}
		
		modelAndView.setViewName("redirect:/xzb/toXzbList");
		return modelAndView;
	}
	
}