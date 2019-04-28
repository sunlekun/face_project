package com.demo.service;

import java.util.List;

import com.demo.model.RoleNavigation;
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public interface RoleNavigationService {
	public List<String>  findRoleNavigationByRoleId( int role_id);
	public List<String>  findRoleNavigationNameByRoleId( int role_id);
	public List<String>  findRolePermissionByRoleId( int role_id);
	public void  insertRoleNavigationBatch(List<RoleNavigation> rns);
	public void  deleteRoleNavigationByRoleId( int role_id);
}
