package com.demo.dao;

import java.sql.Timestamp;
import java.util.List; 

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
   



import com.demo.model.Role;
 

@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface RoleDao {
   
	public List<Role>  findAllRole(); 
	public List<Role>  findRoleByKeyword(String  keyword); 	 
	public Role findRoleById(int id);
	public Role findRoleByRoleName(String role_name);
	
	public void insertRole(Role role);
	public void updateRole(Role role);	  
	public void deleteRoleBatch(String[] ids);
	
	public void  deleteNavigationByRoleId( int role_id);
	public void  insertNavigationRoleBatch(@Param("role_id") int role_id, @Param("nav_ids") List<String> nav_ids, @Param("add_time") Timestamp add_time);
}
