package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service; 

import com.demo.dao.NavigationDao; 
import com.demo.model.Navigation;  
import com.demo.service.NavigationService;

import java.util.List;

/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service.impl
 * @Function_Description 业务层接口实现类，处理具体的业务方面的逻辑
 *
 */
@Service
public class NavigationServiceImpl implements NavigationService{
 
	@Autowired
    @Qualifier("navigationDao")
    private NavigationDao navigationDao;
	public List<Navigation>  findAllNavigation(){
	   return navigationDao.findAllNavigation();
	}
	public List<Navigation> findNavigationByRoleId(int role_id){
	  return navigationDao.findNavigationByRoleId(role_id);
	}
	public List<Navigation>  findNavigationByParentId(int parent_id){
		return navigationDao.findNavigationByParentId(parent_id);
	}
	public List<Navigation>  findNavigationByRoleIdOrParentId(int role_id,int parent_id){
		return navigationDao.findNavigationByRoleIdOrParentId(role_id,parent_id);
	}
	
	
	public List<String>  findParentIDNavigationByIds(String[] ids){
		return navigationDao.findParentIDNavigationByIds(ids);
	}
}