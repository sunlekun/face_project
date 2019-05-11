package com.demo.controller;
 
 
import java.io.IOException; 
import java.sql.Timestamp; 
import java.util.Calendar;
import java.util.Date; 
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
import com.demo.model.RandomCheck;
import com.demo.model.VideoIdent;
import com.demo.realm.PermissionName;
import com.demo.service.RandomCheckService;
import com.demo.service.VideoIdentService;
import com.demo.service.XzbService;
 
 

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
@RequestMapping(value = "/randomCheck")
public class RandomCheckController { 
	public static Logger log= Logger.getLogger(RandomCheckController.class);
	
	@Autowired
	private RandomCheckService randomCheckService;
	 
	@Autowired
	private VideoIdentService videoIdentService;
	
	@RequestMapping(value = "/randomCheckList")	
	public void randomCheckList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,Object > map=new HashMap<String ,Object >();
		map.put("video_status", request.getParameter("video_status"));
		map.put("status", request.getParameter("status"));
		map.put("data_type", manager.getUser_type());
		List<RandomCheck> randomChecks = randomCheckService.findRandomCheckByMultiCondition(map); 
		 
		String jsons = JSON.toJSONString(randomChecks);

		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	@RequestMapping(value = "/toRandomCheckList")
	@RequiresPermissions("randomCheck:Show")
	@PermissionName("认证抽查管理")
	public ModelAndView toRandomCheckList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();  
		modelAndView.addObject("status", request.getParameter("status")); 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
	    modelAndView.setViewName("admin/randomCheckList"); 
		return modelAndView;
	}
	@RequestMapping(value = "/toRandomCheckSet")
	@RequiresPermissions("random:Show")
	@PermissionName("随机抽查")
	public ModelAndView toRandomCheckSet(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();   
		List<String> years  = videoIdentService.findVidoIdentYears();	 
		modelAndView.addObject("years", years); 
		modelAndView.addObject("current", Calendar.getInstance().get(Calendar.YEAR)); 
	    modelAndView.setViewName("admin/randomCheckSet"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/randomCheckSet")	
    public void randomCheckSet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,Object > map=new HashMap<String ,Object >();
		map.put("video_status", null);
		map.put("status", "1");
		map.put("data_type", manager.getUser_type());
		List<RandomCheck> randomChecks = randomCheckService.findRandomCheckByMultiCondition(map); 
		 
		/*List<RandomCheck> randomChecks  = randomCheckService.findRandomCheckByVideoStatusAndStatus(null,"1");*/	 
		JSONObject object=new JSONObject();
		  
		if(randomChecks!=null&&randomChecks.size()>0){	
			
			object.put("status", "false");
		    object.put("url", "user/tologin");
		    object.put("msg", "抱歉：分配认证信息还未审核完毕，请审核完毕后再次进行分配！");
			
		        
		}
		else{  
			    map=new HashMap<String ,Object >();
				map.put("number", 300);
				map.put("year", request.getParameter("year"));
				map.put("data_type", manager.getUser_type());
				int count= randomCheckService.creatRandomCheckByMultiCondition(map);
				object.put("status", "true");
			    object.put("url","randomCheck/toRandomCheckList");
		    
		   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
		
		 

		} 
	@RequestMapping(value = "/toRandomCheckConfirm")
	@RequiresPermissions("randomCheck:Confirm")
	@PermissionName("抽查审核")
	public ModelAndView toRandomCheckConfirm(int id,int video_id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		RandomCheck randomCheck  = randomCheckService.findRandomCheckById(id);	
		VideoIdent videoIdent=videoIdentService.findVideoIdentById(video_id);
		
		modelAndView.addObject("videoIdent", videoIdent); 
		modelAndView.addObject("randomCheck", randomCheck); 
		modelAndView.addObject("status", request.getParameter("status")); 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		
		modelAndView.addObject("videoIdent", videoIdent); 
		modelAndView.setViewName("admin/randomCheckConfirm"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/randomCheckConfirm")
	@PermissionName("视频详情")
	public ModelAndView randomCheckConfirm(int status,int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		String s=request.getParameter("status");
		
	
		randomCheckService.updateRandomCheckStatus(status, id);
		 
		modelAndView.addObject("status1", request.getParameter("status")); 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		
		modelAndView.setViewName("redirect:/randomCheck/toRandomCheckList");
		return modelAndView;
	}
	 
	@RequestMapping(value = "/randomCheckDetial")
	@PermissionName("视频详情")
	public ModelAndView randomCheckDetial(int id,int video_id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		RandomCheck randomCheck  = randomCheckService.findRandomCheckById(id);	 
		VideoIdent videoIdent=videoIdentService.findVideoIdentById(video_id); 
		modelAndView.addObject("videoIdent", videoIdent); 
		modelAndView.addObject("randomCheck", randomCheck); 
		modelAndView.addObject("status", request.getParameter("status")); 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		modelAndView.setViewName("admin/randomCheckDetial"); 
		return modelAndView;
	}
	
}