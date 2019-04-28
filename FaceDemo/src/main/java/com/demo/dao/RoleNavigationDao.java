package com.demo.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.RoleNavigation;
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface RoleNavigationDao {
	public List<String>  findRoleNavigationByRoleId( int role_id);
	public List<String>  findRoleNavigationNameByRoleId( int role_id);
	public List<String>  findRolePermissionByRoleId( int role_id);
	public void  insertRoleNavigationBatch(List<RoleNavigation> rns);
	public void  deleteRoleNavigationByRoleId( int role_id);
}
