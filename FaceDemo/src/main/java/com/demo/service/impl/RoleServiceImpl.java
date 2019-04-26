package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
 
 




import com.demo.dao.RoleDao;
import com.demo.model.Role;
import com.demo.service.RoleService;

import java.sql.Timestamp;
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
public class RoleServiceImpl implements RoleService{
 
	@Autowired
    @Qualifier("roleDao")
    private RoleDao roleDao;
 
	
	public Role findRoleById(int id){
		return roleDao.findRoleById(id);
	}
	public Role findRoleByRoleName(String role_name){
		return roleDao.findRoleByRoleName(role_name);
	}
	public List<Role> findAllRole() { 
		return roleDao.findAllRole();
	}
	
	public List<Role>  findRoleByKeyword(String  keyword){
		return roleDao.findRoleByKeyword(keyword);
	}
	 
 	public void insertRole(Role role){
 		roleDao.insertRole(role);
 	}
	public void updateRole(Role role)
	{
		roleDao.updateRole(role);	
	}
	public void deleteRoleBatch(String[] ids){
		roleDao.deleteRoleBatch(ids);
	}
	public void  insertNavigationRoleBatch(int role_id,List<String> nav_ids, Timestamp add_time){
		roleDao.insertNavigationRoleBatch(role_id,nav_ids,add_time);
	}
	public void  deleteNavigationByRoleId( int role_id){
		roleDao.deleteNavigationByRoleId(role_id);
	}
}