package com.demo.controller;
 
 
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  

import net.sf.json.JSONObject; 

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
  



import com.alibaba.fastjson.JSON;
import com.demo.model.Manager;
import com.demo.model.Role;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.ManagerService; 
import com.demo.service.RoleService;
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
@RequestMapping(value = "/manager")
public class ManagerController {
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(ManagerController.class);
	
	@Autowired
	private ManagerService managerService;
	 
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private XzbService xzbService;
	
	@RequestMapping(value = "/toManagerList")
	@RequiresPermissions("manager:Show")
	@PermissionName("管理员管理")
	public ModelAndView toManagerList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.setViewName("admin/managerList"); 
		return modelAndView;
	}
	@RequestMapping(value = "/managerList")	
	public void managerList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		 
		List<Manager> managers=  managerService.findAllManager(); 
 		 
		String jsons = JSON.toJSONString(managers); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	
	@RequestMapping(value = "/managerDetial")
	@RequiresPermissions("manager:View")
	@PermissionName("管理员详情")
	public ModelAndView managerDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		Manager managerDetial= managerService.findManagerById(id) ;  
		modelAndView.addObject("managerDetial", managerDetial);  
		modelAndView.setViewName("admin/managerDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toManagerEdit")
	@RequiresPermissions("manager:Edit")
	@PermissionName("管理员修改")
	public ModelAndView toManagerEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		Manager managerDetial= managerService.findManagerById(id) ;  
		List<Xzb> xzbs= xzbService.findAllXzb();
		List<Role> roles= roleService.findAllRole();  
		modelAndView.addObject("managerDetial", managerDetial); 
		modelAndView.addObject("roles", roles); 
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.setViewName("admin/managerEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/managerEdit")
	public ModelAndView managerEdit(Manager newManager ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
		if(!"0|0|0|0".equals(newManager.getPassword()))
		{   
			newManager.setSalt(newManager.getUser_name()); 
			Md5Hash hash=new Md5Hash(newManager.getPassword(),newManager.getUser_name(),3);
			newManager.setPassword(hash.toString());
		} 
		else{
			newManager.setPassword(null);
		}
		 
		String cbIsLock=request.getParameter("cbIsLock");  
		String user_type = request.getParameter("user_type");
		 
		if("on".equals(cbIsLock))
			newManager.setIs_lock(0);
		else
			newManager.setIs_lock(1);
		
		
		String role_id=request.getParameter("role_id").split(" ")[0]; 
		String role_type=request.getParameter("role_id").split(" ")[1]; 
		newManager.setRole_id(Integer.parseInt(role_id));
		newManager.setRole_type(Integer.parseInt(role_type));
		
		
		if(!"城乡居民养老保险".equals(user_type))
			newManager.setXzb(null);
		
		managerService.updateManager(newManager);
		
		modelAndView.setViewName("redirect:/manager/toManagerList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/toManagerAdd")
	@RequiresPermissions("manager:Add")
	@PermissionName("管理员新增")
	public ModelAndView toManagerAdd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(); 
		List<Xzb> xzbs= xzbService.findAllXzb();
		List<Role> roles= roleService.findAllRole();  
 		modelAndView.addObject("roles", roles); 
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.setViewName("admin/managerAdd");
		
		return modelAndView;
	}
	@RequestMapping(value = "/managerAdd")
	public ModelAndView  managerAdd(Manager manager ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String cbIsLock=request.getParameter("cbIsLock");  
		String user_type = request.getParameter("user_type");
		ModelAndView modelAndView = new ModelAndView();
		if("on".equals(cbIsLock))
			manager.setIs_lock(0);
		else
			manager.setIs_lock(1);
		
		//${role.id },${role.role_type }
		String role_id=request.getParameter("role_id").split(" ")[0]; 
		String role_type=request.getParameter("role_id").split(" ")[1]; 
		manager.setRole_id(Integer.parseInt(role_id));
		manager.setRole_type(Integer.parseInt(role_type));
		 
		if(!"城乡居民养老保险".equals(user_type))
			manager.setXzb(null);
		
		manager.setSalt(manager.getUser_name()); 
		Md5Hash hash=new Md5Hash(manager.getPassword(),manager.getUser_name(),3);
		manager.setPassword(hash.toString());
		manager.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));

		managerService.insertManager(manager);

		modelAndView.setViewName("redirect:/manager/toManagerList");

		return modelAndView;
	}
	@RequestMapping(value = "/isExistUserName")
	public  void isExistUserName(String user_name,HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		Manager manager = managerService.findManagerByUserName(user_name);	 
		JSONObject object=new JSONObject(); 
		if(manager!=null){		 
			object.put("status", "false"); 
		    object.put("msg", "用户名已存在！"); 
		}
		else { 
			object.put("status", "true");   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	  
		} 
	
	@RequestMapping(value = "/managerDelete")
	@RequiresPermissions("manager:Delete")
	@PermissionName("管理员删除")
	public ModelAndView managerDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				managerService.deleteManagerBatch(ids);
		}
		
		modelAndView.setViewName("redirect:/manager/toManagerList");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/toSetPwd")
	//@RequiresPermissions("manager:toSetPwd")
	@PermissionName("管理员修改密码")
	public ModelAndView toSetPwd() {
		ModelAndView modelAndView = new ModelAndView();
		Manager	oldManager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		modelAndView.addObject("manager",oldManager);
		modelAndView.setViewName("admin/setPwd"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/setPwd")
    public void setPwd(Manager	newManager,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String newPassword=request.getParameter("newPassword");
		Manager	oldManager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		
		JSONObject object=new JSONObject();
		  
		String oldPassword=request.getParameter("oldPassword");
		 
        Md5Hash hash=new Md5Hash(oldPassword,oldManager.getUser_name(),3);
		
		if(!oldManager.getPassword().equals(oldPassword)&&!oldManager.getPassword().equals(hash.toString())){		
			object.put("status", "false");
		    object.put("url", "");
		    object.put("msg", "原密码错误，请重新输入！");
		}
		else
		{   
			
			oldManager.setUser_name(oldManager.getUser_name());
			oldManager.setSalt(oldManager.getUser_name());
			  
			hash=new Md5Hash(newPassword,oldManager.getUser_name(),3);
			oldManager.setPassword(hash.toString());
			managerService.updateManager(oldManager);

			object.put("status", "true");
			object.put("url", "manager/exit");
			object.put("msg", "密码修改成功！");

		
		}
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());

		}
	
	@RequestMapping(value = "/exit")
	@RequiresPermissions("manager:exit")
	@PermissionName("注销登录") 
	public ModelAndView  exit(HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		SecurityUtils.getSubject().logout(); 
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/main"); 
		return modelAndView;
		

		} 

	 
	 
}