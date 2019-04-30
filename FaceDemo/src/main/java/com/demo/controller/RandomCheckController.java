package com.demo.controller;
 
 
import java.io.IOException; 
import java.sql.Timestamp; 
import java.util.Calendar;
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
import com.demo.model.RandomCheck;
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
		  
		List<RandomCheck> randomChecks = randomCheckService.findRandomCheckByVideoStatusAndStatus(request.getParameter("video_status"), request.getParameter("status")); 
		 
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
		
		 
		List<RandomCheck> randomChecks  = randomCheckService.findRandomCheckByVideoStatusAndStatus(null,"1");	 
		JSONObject object=new JSONObject();
		  
		if(randomChecks!=null&&randomChecks.size()>0){	
			
			object.put("status", "false");
		    object.put("url", "user/tologin");
		    object.put("msg", "抱歉：分配认证信息还未审核完毕，请审核完毕后再次进行分配！");
			
		        
		}
		else{  
			int count= randomCheckService.creatRandomCheckByNumberAndYear(300 ,request.getParameter("year"));
			object.put("status", "true");
		    object.put("url","randomCheck/toRandomCheckList");
		    
		   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
		
		 

		} 
	/*@RequestMapping(value = "/xzbDetial")
	@RequiresPermissions("xzb:View")
	@PermissionName("乡镇办详情")
	public ModelAndView xzbDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		Xzb xzb=xzbService.findXzbById(id); 
		modelAndView.addObject("xzb", xzb); 
		modelAndView.setViewName("admin/xzbDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toXzbEdit")
	@RequiresPermissions("xzb:Edit")
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
	@RequiresPermissions("xzb:Add")
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
	@RequiresPermissions("xzb:Delete")
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
	}*/
	
}