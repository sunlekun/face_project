package com.demo.service;

import java.util.List;

import com.demo.model.Navigation;
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public interface NavigationService {
 
	public List<Navigation>  findAllNavigation(); 	 
	public List<Navigation> findNavigationByRoleId(int role_id);
	public List<Navigation>  findNavigationByParentId(int parent_id); 	 
	public List<Navigation>  findNavigationByRoleIdOrParentId(int role_id,int parent_id); 
	
	public List<String>  findParentIDNavigationByIds(String[] ids); 	 
}
