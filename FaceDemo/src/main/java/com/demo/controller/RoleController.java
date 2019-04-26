package com.demo.controller;
 
 
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.demo.model.Navigation; 
import com.demo.model.RoleNavigation;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.NavigationService;
import com.demo.service.RoleNavigationService;
import com.demo.service.RoleService;  
import com.demo.service.XzbService;
import com.demo.util.PasswordEncoder;


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
@RequestMapping(value = "/role")
public class RoleController {
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(RoleController.class);
	 
	 
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private NavigationService navigationService;
	
	@Autowired
	private RoleNavigationService roleNavigationService;
	
	@RequestMapping(value = "/toRoleList")
	@RequiresPermissions("role:toRoleList")
	@PermissionName("角色管理")
	public ModelAndView toRoleList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.setViewName("admin/roleList"); 
		return modelAndView;
	}
	@RequestMapping(value = "/roleList")	
	public void roleList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		 
		List<Role> roles=  roleService.findAllRole(); 
 		 
		String jsons = JSON.toJSONString(roles); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	
	@RequestMapping(value = "/roleDetial")
	@RequiresPermissions("role:roleDetial")
	@PermissionName("角色详情") 
	public ModelAndView roleDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		Role role=roleService.findRoleById(id) ; 
	 	 
		List<Navigation> allNav=printTree(); 
 		modelAndView.addObject("allNav", allNav);  
 		List<String> roleNavs=roleNavigationService.findRoleNavigationByRoleId(id); 
		//Map<Navigation ,List<Navigation>> maps=getNavigationMap(id);
		  
		modelAndView.addObject("role", role); 
		modelAndView.addObject("roleNavs", roleNavs);   
		
		
		modelAndView.setViewName("admin/roleDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toRoleEdit")
	@RequiresPermissions("role:toRoleEdit")
	@PermissionName("角色修改")
	public ModelAndView toRoleEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		 
		Role role=roleService.findRoleById(id) ; 
	 	 
		List<Navigation> allNav=printTree(); 
 		modelAndView.addObject("allNav", allNav);  
 		List<String> roleNavs=roleNavigationService.findRoleNavigationByRoleId(id);  
		  
		modelAndView.addObject("role", role); 
		modelAndView.addObject("roleNavs", roleNavs); 
		
		modelAndView.setViewName("admin/roleEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/roleEdit")
	public ModelAndView roleEdit(Role newRole ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
		
		if(newRole.getRole_type()==1)
			newRole.setIs_sys(1);
		else
			newRole.setIs_sys(0);
		 
		
		roleService.updateRole(newRole);
		
		String[] actList=request.getParameterValues("actList");  
		List<RoleNavigation> rns=new ArrayList<RoleNavigation>();
		for(int i=0;i<actList.length;i++)
		{   
			RoleNavigation rn=new RoleNavigation();
			rn.setRole_id(newRole.getId());
			rn.setNavigation_id(Integer.parseInt(actList[i].split(",")[0]));
			rn.setNav_name(actList[i].split(",")[1]);
			rn.setAction_type(actList[i].split(",")[2]); 
			rn.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
			rns.add(rn) ;
		}
		roleNavigationService.deleteRoleNavigationByRoleId(newRole.getId());
		roleNavigationService.insertRoleNavigationBatch(rns);
		 
		
		modelAndView.setViewName("redirect:/role/toRoleList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/toRoleAdd")
	@RequiresPermissions("role:toRoleAdd")
	@PermissionName("角色新增")
	public ModelAndView toRoleAdd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(); 
		List<Navigation> allNav=printTree(); 
 		modelAndView.addObject("allNav", allNav);  
		modelAndView.setViewName("admin/roleAdd");
		
		return modelAndView;
	}
	 
	
	@RequestMapping(value = "/roleAdd")
	public ModelAndView  roleAdd(Role role ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		
		String[] actList=request.getParameterValues("actList"); 
		
		ModelAndView modelAndView = new ModelAndView();
		 
		if(role.getRole_type()==1)
			role.setIs_sys(1);
		else
			role.setIs_sys(0);
		role.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));

		roleService.insertRole(role);
		
		
		//${nav.id },${nav.name },${act }
		List<RoleNavigation> rns=new ArrayList<RoleNavigation>();
		for(int i=0;i<actList.length;i++)
		{   
			RoleNavigation rn=new RoleNavigation();
			rn.setRole_id(role.getId());
			rn.setNavigation_id(Integer.parseInt(actList[i].split(",")[0]));
			rn.setNav_name(actList[i].split(",")[1]);
			rn.setAction_type(actList[i].split(",")[2]); 
			rn.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
			rns.add(rn) ;
		}
		
		roleNavigationService.insertRoleNavigationBatch(rns);

		modelAndView.setViewName("redirect:/role/toRoleList");

		return modelAndView;
	}
	 
	 
	@RequiresPermissions("role:isExistRoleName")
	@PermissionName("角色是否存在")
	@RequestMapping(value = "/isExistRoleName")
	public  void isExistRoleName(String role_name,HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		Role role = roleService.findRoleByRoleName(role_name);	 
		JSONObject object=new JSONObject(); 
		if(role!=null){		 
			object.put("status", "false"); 
		    object.put("msg", "角色名已存在！"); 
		}
		else { 
			object.put("status", "true");   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	  
		} 
	
	@RequestMapping(value = "/roleDelete")
	@RequiresPermissions("role:roleDelete")
	@PermissionName("角色删除")
	public ModelAndView roleDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				roleService.deleteRoleBatch(ids);
		}
		
		modelAndView.setViewName("redirect:/role/toRoleList");
		return modelAndView;
	}
	
	public List<Navigation>  printTree( ){
		int level=1;
		// 查询所有菜单
		List<Navigation> resultNav = new ArrayList<Navigation>() ;
		List<Navigation> allNavigation =navigationService.findAllNavigation();
		//根节点
		List<Navigation> rootNav = new ArrayList<Navigation>();
		for (Navigation nav : allNavigation) {
			if (nav.getParent_id() == 0) {// 父节点是0的，为根节点。
				rootNav.add(nav);
			}
		}
		Collections.sort(rootNav);
		for (Navigation nav : rootNav) {
			/* 获取根节点下的所有子节点 使用getChild方法 */  
			//System.out.println(nav.getTitle());
			nav.setLevel(level); 
			resultNav.add(nav);
			print(nav.getId(), allNavigation,level,resultNav); 
		}
		return resultNav;
	}
	 public void print(int id,List<Navigation> allNavigation,int level,List<Navigation> resultNav){
		 
		    level++;
			/*List<Navigation> childList = navigationService.findNavigationByParentId(id);*/
		    //子菜单
		    List<Navigation> childList = new ArrayList<Navigation>();
		   for (Navigation nav : allNavigation) {
		      // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
		      //相等说明：为该根节点的子节点。
		      if(nav.getParent_id()==id){
		        childList.add(nav);
		      }
		    }
		   Collections.sort(childList);
		    //递归
		    for (Navigation nav : childList) { 
		    	//System.out.println(nav.getTitle());
		    	nav.setLevel(level); 
		    	resultNav.add(nav);
		        print( nav.getId(),allNavigation, level,resultNav);
		    }
		    
		    //如果节点下没有子节点，返回一个空List（递归退出）
		    if(childList.size() == 0){
		      return ;
		    }
		     
		  }
	 
	 
	@RequestMapping(value = "/findNavigationTreeByRoleId")
	 public void findNavigationTreeByRoleId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 
		  /* Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		
		    // 查询所有菜单 
			List<Navigation> allNavigation =navigationService.findNavigationByRoleId(manager.getRole_id());
			//根节点
			List<Navigation> rootNav = new ArrayList<Navigation>();
			for (Navigation nav : allNavigation) {
				if (nav.getParent_id() == 0) {// 父节点是0的，为根节点。
					rootNav.add(nav);
				}
			}
			Collections.sort(rootNav); 根据Menu类的order排序 
			
		 
		 
		
		// 为根菜单设置子菜单，getClild是递归调用的
		for (Navigation nav : rootNav) {
			 获取根节点下的所有子节点 使用getChild方法  
			//System.out.println(nav.getTitle());
			List<Navigation> childList = getChild(nav.getId(),allNavigation);
			nav.setChildren(childList);// 给根节点设置子节点 
		}*/
		 
		String s=
   " <div class='list-group'>"+
       " <h1 title='内容管理'><i class='iconfont icon-home-full'></i></h1>"+
        "<div class='list-wrap'>"+
            "<h2>内容管理<i class='iconfont icon-arrow-down'></i></h2>"+
            "<ul style='display: block;'>"+
                "<li>"+
                    "<a navid='channel_main' target='mainframe'>"+
                     "   <i></i><span>用户管理</span>"+
                       " <b class='expandable iconfont icon-open'></b></a>"+
                   " <ul style='display: block;'>"+
                       " <li><a href='users/user_list.aspx' navid='user_list' target='mainframe'><span>用户信息管理</span></a></li>"+
                       " <li><a href='users/video_list.aspx' navid='video_list' target='mainframe'><span>采集信息审核</span></a></li>"+
                  "  </ul>"+
               " </li>"+
              "  <li>"+
                   " <a navid='channel_main' target='mainframe'>"+
                       " <i></i><span>认证抽查</span>"+
                       " <b class='expandable iconfont icon-open'></b></a>"+
                   " <ul style='display: block;'>"+
                        "<li><a href='#' target='mainframe'><span>今日抽查</span></a></li>"+
                       " <li><a href='#' target='mainframe'><span>随机分配</span></a></li>"+
                  "  </ul>"+
              "  </li>"+
           " </ul>"+
      "  </div>"+
    "</div>"+
    "<div class='list-group'>"+
       " <h1 title='控制面板'><i class='iconfont icon-home-full'></i></h1>"+
       " <div class='list-wrap'>"+
            "<h2>控制面板<i class='iconfont icon-arrow-down'></i></h2>"+
           " <ul style='display: block;'>"+
               " <li>"+
                  "  <a navid='channel_main' target='mainframe'>"+
                       " <i></i><span>系统用户</span>"+
                      "  <b class='expandable iconfont icon-open'></b></a>"+
                   " <ul style='display: block;'>"+
                        "<li><a href='manager/manager_list.aspx' target='mainframe'><span>管理员管理</span></a></li>"+
                       " <li><a href='manager/township_list.aspx' target='mainframe'><span>乡镇办管理</span></a></li>"+
                      "  <li><a href='manager/manager_log.aspx' target='mainframe'><span>管理日志</span></a></li>"+
                    "</ul>"+
                "</li>"+
           " </ul>"+
      "  </div>"+
    "</div>";
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(s);  
		    
		  }
	 
	/**
	   * 获取子节点
	   * @param id 父节点id
	   * @param allNav 所有菜单列表
	   * @return 每个根节点下，所有子菜单列表
	   */
	  public List<Navigation> getChild(int id,List<Navigation> allNavigation){
	    
		  
		//子菜单
		    List<Navigation> childList = new ArrayList<Navigation>();
		   for (Navigation nav : allNavigation) {
		      // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
		      //相等说明：为该根节点的子节点。
		      if(nav.getParent_id()==id){
		        childList.add(nav);
		      }
		    }
		   Collections.sort(childList);
		   
	    //递归
	    for (Navigation nav : childList) {
	      nav.setChildren(getChild(nav.getId(),allNavigation ));
	    }
	    
	    //如果节点下没有子节点，返回一个空List（递归退出）
	    if(childList.size() == 0){
	      return new ArrayList<Navigation>();
	    }
	    return childList;
	  }
	//顺序查询所有菜单
	/*public List<Navigation>  printTree( ){
		int level=1;
		// 查询所有菜单
		List<Navigation> allNavigation = new ArrayList<Navigation>();
				
		List<Navigation> rootNav= navigationService.findNavigationByParentId(0);// 父节点是0的，为根节点。
		for (Navigation nav : rootNav) {
			 获取根节点下的所有子节点 使用getChild方法   
			nav.setLevel(level); 
			allNavigation.add(nav);
			print(nav.getId(), allNavigation,level); 
		}
		return allNavigation;
	}
	 public void print(int id,List<Navigation> allNav,int level){
		 
		    level++;
			List<Navigation> childList = navigationService.findNavigationByParentId(id);
		    //递归
		    for (Navigation nav : childList) { 
		    	nav.setLevel(level); 
		        allNav.add(nav);
		        print( nav.getId(),allNav, level);
		    }
		    
		    //如果节点下没有子节点，返回一个空List（递归退出）
		    if(childList.size() == 0){
		      return ;
		    }
		     
		  }*/
	
	 
	/*public List<Navigation> findNavigationTree(int role_id){
		// 查询所有菜单
		List<Navigation> allNavigation = navigationService.findAllNavigation();
		// 根节点
		List<Navigation> rootNav = new ArrayList<Navigation>();
		for (Navigation nav : allNavigation) {
			if (nav.getParent_id() == 0) {// 父节点是0的，为根节点。
				rootNav.add(nav);
			}
		}
		
		
		 List<Navigation> rootNav= navigationService.findNavigationByParentId(0);// 父节点是0的，为根节点。
		 根据Menu类的order排序 
		// 为根菜单设置子菜单，getClild是递归调用的
		for (Navigation nav : rootNav) {
			 获取根节点下的所有子节点 使用getChild方法  
			List<Navigation> childList = getChild(nav.getId(), allNavigation);
			nav.setChildren(childList);// 给根节点设置子节点 
		}
		*//**
		 * 输出构建好的菜单数据。
		 * 
		 *//*

		
		
		return rootNav;
		    
		  }
	 
	*//**
	   * 获取子节点
	   * @param id 父节点id
	   * @param allNav 所有菜单列表
	   * @return 每个根节点下，所有子菜单列表
	   *//*
	  public List<Navigation> getChild(int id,List<Navigation> allNav){
	    //子菜单
	     List<Navigation> childList = new ArrayList<Navigation>();
	   for (Navigation nav : allNav) {
	      // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
	      //相等说明：为该根节点的子节点。
	      if(nav.getParent_id()==id){
	        childList.add(nav);
	      }
	    }
		  
		List<Navigation> childList = navigationService.findNavigationByParentId(id);
	    //递归
	    for (Navigation nav : childList) {
	      nav.setChildren(getChild(nav.getId(), allNav));
	    }
	    
	    //如果节点下没有子节点，返回一个空List（递归退出）
	    if(childList.size() == 0){
	      return new ArrayList<Navigation>();
	    }
	    return childList;
	  }
*/
	 
	 
}