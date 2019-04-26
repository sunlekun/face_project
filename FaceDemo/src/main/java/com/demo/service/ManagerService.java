package com.demo.service;

import java.util.List;

import com.demo.model.Manager;

public interface ManagerService {
	public  Manager findUserByUsername(String username) ;
	public List<Manager> findAllManager();
	public Manager findManagerById(int id);
	public Manager findManagerByUserName(String user_name); 
	
	public List<Manager> findManagerByKeyword(String keyword);  
	public void insertManager(Manager manager);
	public void updateManager(Manager manager);
	public void deleteManagerBatch(String[] ids);
}
