package com.demo.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.demo.model.TempUser;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.TempUserService;
import com.demo.service.XzbService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/tempUserAudit")
public class TempUserAuditController { 
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(TempUserAuditController.class);
	
	@Autowired
	private TempUserService tempUserService;
	
	@Autowired
	private XzbService xzbService;
	
	@RequestMapping(value = "/toTempUserAuditList")
	@RequiresPermissions("tempUserAudit:Show")
	@PermissionName("居民信息采集审核")
	public ModelAndView toTempUserAuditList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		String type=request.getParameter("type");
		
		if("Img".equals(type))
		{
			if(pageSize==null)
				pageSize=10;
			if(pageNumber==null)
				pageNumber =1;
			PageHelper.startPage(pageNumber, pageSize);
			Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
			HashMap<String ,String > map=new HashMap<String ,String >();
			map.put("key", key);
			map.put("data_type", manager.getUser_type());
			map.put("user_township", manager.getXzb());
			List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
			PageInfo<TempUser> page = new PageInfo<>(tempUsers);
			modelAndView.addObject("page", page); 
			modelAndView.addObject("key", key); 
            modelAndView.setViewName("admin/tempUserAuditListImg"); 
            
		}
		else
		{
			type="Word";
			modelAndView.setViewName("admin/tempUserAuditList"); 
		    }
		
		modelAndView.addObject("type", type);   
		return modelAndView;
	}
	
	@RequestMapping(value = "/tempUserAuditList")	
	public void tempUserAuditList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		map.put("data_type", manager.getUser_type());
		map.put("user_township", manager.getXzb());
		List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
 		 
		String jsons = JSON.toJSONString(tempUsers); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	

	@RequestMapping(value = "/tempUserAuditDetial")
	@RequiresPermissions("tempUserAudit:View")
	@PermissionName("居民信息采集详情")
	public ModelAndView tempUserAuditDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.setViewName("admin/tempUserAuditDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toTempUserAuditEdit")
	@RequiresPermissions("tempUserAudit:Edit")
	@PermissionName("居民信息审核修改")
	public ModelAndView toTempUserAuditEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;  
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		
		modelAndView.setViewName("admin/tempUserAuditEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserAuditEdit")
	public ModelAndView tempUserAuditEdit(TempUser tempUser ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
			
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		    
		if (hid_photo_names != null && hid_photo_names.length == 3)
		{
			String path = "";
			for (int i = 0; i < hid_photo_names.length; i++) {
				String[] name = hid_photo_names[i].split("\\|");
				/*
				 * if(name!=null&&name.length>=2) { String[]
				 * name1=name[1].split("\\/"); System.out.println(name1[3]);
				 * path+=name[1]+";"; }
				 */
				path += name[1] + ";";
			}

			if (path.length() > 0)
			{
				path = path.substring(0, path.length() - 1);
				tempUser.setOriginal_path(path);
				tempUser.setOriginal_path(path);
			}
		}
	 		
		TempUser old= tempUserService.findTempUserById(tempUser.getId()) ;  
		if(old.getStatus()!=tempUser.getStatus())
			tempUser.setAudit_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
		tempUserService.updateTempUser(tempUser);

	
		modelAndView.setViewName("redirect:/tempUserAudit/toTempUserAuditList?type="+request.getParameter("type"));
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserAuditDelete")
	@RequiresPermissions("tempUserAudit:Delete")
	@PermissionName("居民信息采集审核删除")
	public ModelAndView tempUserAuditDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				tempUserService.deleteTempUserBatch(ids);
		}
		
		modelAndView.setViewName("redirect:/tempUserAudit/toTempUserAuditList?type="+request.getParameter("type"));
		return modelAndView;
	}


		} 
