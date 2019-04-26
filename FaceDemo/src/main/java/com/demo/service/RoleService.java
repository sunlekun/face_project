package com.demo.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.model.Role;
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public interface RoleService {
 
	public Role findRoleById(int id);
	public List<Role>  findRoleByKeyword(String  keyword); 	 
	public Role findRoleByRoleName(String role_name); 
	public List<Role>  findAllRole(); 	 
	public void insertRole(Role role);
	public void updateRole(Role role);	  
	public void deleteRoleBatch(String[] ids);
	public void  insertNavigationRoleBatch(int role_id,List<String> nav_ids, Timestamp add_time);
	public void  deleteNavigationByRoleId( int role_id);

}
