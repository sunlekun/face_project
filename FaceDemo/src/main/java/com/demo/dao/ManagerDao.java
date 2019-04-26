package com.demo.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.Manager;
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface ManagerDao {
	public Manager findUserByUsername(String username);
	public List<Manager> findAllManager();
	public Manager findManagerById(int id);
	public Manager findManagerByUserName(String user_name);	
	public List<Manager> findManagerByKeyword(String keyword);  
	public void insertManager(Manager manager);
	public void updateManager(Manager manager);
	public void deleteManagerBatch(String[] ids);
}
