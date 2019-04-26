package com.demo.service.impl; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.demo.dao.RoleNavigationDao;
import com.demo.model.RoleNavigation; 
import com.demo.service.RoleNavigationService;
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public class RoleNavigationServiceImpl implements RoleNavigationService{
	@Autowired
    @Qualifier("roleNavigationDao")
    private RoleNavigationDao roleNavigationDao;
	public List<String>  findRoleNavigationByRoleId( int role_id){
		return roleNavigationDao.findRoleNavigationByRoleId(role_id);
	}
	public void  insertRoleNavigationBatch(List<RoleNavigation> rns){
		roleNavigationDao.insertRoleNavigationBatch(rns);
	}
	public void  deleteRoleNavigationByRoleId( int role_id){
		roleNavigationDao.deleteRoleNavigationByRoleId(role_id);
	}
}
