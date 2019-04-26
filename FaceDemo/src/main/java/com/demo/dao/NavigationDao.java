package com.demo.dao;

import java.util.List; 

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
import com.demo.model.Navigation; 
 

@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface NavigationDao {
   
	public List<Navigation>  findAllNavigation(); 	 
	public List<Navigation> findNavigationByRoleId(@Param("role_id") int role_id);
	public List<Navigation>  findNavigationByParentId(int parent_id); 
	public List<Navigation>  findNavigationByRoleIdOrParentId(@Param("role_id") int role_id,@Param("parent_id")  int parent_id); 
	public List<String>  findParentIDNavigationByIds(String[] ids);  

}
