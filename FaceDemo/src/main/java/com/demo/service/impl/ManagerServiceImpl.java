package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
 


import org.springframework.stereotype.Service;

import com.demo.dao.ManagerDao;
import com.demo.model.Manager;
import com.demo.service.ManagerService;

@Service 
public class ManagerServiceImpl implements ManagerService {
	@Autowired
    @Qualifier("managerDao")
    private ManagerDao managerDao;

	 
	public Manager findUserByUsername(String username) { 
		return managerDao.findUserByUsername(username);
	}
	@Override
	public List<Manager> findAllManager(){
		return managerDao.findAllManager();
	}
	
	@Override
	public Manager findManagerById(int id){
		return managerDao.findManagerById(id);
	}
	@Override
	public Manager findManagerByUserName(String user_name){
		return managerDao.findManagerByUserName(user_name);
	}
	
	
	public List<Manager> findManagerByKeyword(String keyword)
	{
		return managerDao.findManagerByKeyword(keyword);
	}
	@Override
	public void insertManager(Manager manager)
	{
		managerDao.insertManager(manager);
	}
	@Override
	public void updateManager(Manager manager){
		managerDao.updateManager(manager);
	}
	public void deleteManagerBatch(String[] ids){
		managerDao.deleteManagerBatch(ids);
	}
}
