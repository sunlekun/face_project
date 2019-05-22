package com.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.demo.model.Manager;
import com.demo.model.Navigation;
import com.demo.service.NavigationService;
import com.demo.service.RoleNavigationService;

@Controller
public class MainController { 
	public static Logger log= Logger.getLogger(MainController.class);
	
	@Autowired
	private NavigationService navigationService;
	
	@Autowired
	private RoleNavigationService roleNavigationService;
	
	
	@RequestMapping(value = "/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) { 
	 
		ModelAndView modelAndView=new ModelAndView (); 
		if(SecurityUtils.getSubject().getPrincipals()!=null)
		{
			Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
			if(manager!=null)
			{
			List<Navigation> allNav=findNavigationTreeByRoleId(manager.getRole_id());
			
		 	modelAndView.addObject("allNav", allNav);  
			
			}
			modelAndView.setViewName("admin/main");
		}
		else
			modelAndView.setViewName("redirect:/login");
				
				
		return modelAndView;

	} 
	
	 public List<Navigation> findNavigationTreeByRoleId(int role_id){
		 
		    // 查询所有菜单 
			List<Navigation> allNavigation =navigationService.findNavigationByRoleId(role_id);
			//根节点
			List<Navigation> rootNav = new ArrayList<Navigation>();
			for (Navigation nav : allNavigation) {
				if (nav.getParent_id() == 0) {// 父节点是0的，为根节点。
					rootNav.add(nav);
				}
			}
			Collections.sort(rootNav);/* 根据Menu类的order排序 */
			
		 
		 
		
		// 为根菜单设置子菜单，getClild是递归调用的
		for (Navigation nav : rootNav) {
			/* 获取根节点下的所有子节点 使用getChild方法 */ 
			//System.out.println(nav.getTitle());
			List<Navigation> childList = getChild(nav.getId(),allNavigation);
			nav.setChildren(childList);// 给根节点设置子节点 
		}
		 
		return rootNav;
		    
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


}
