package com.demo.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.TempUser;
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface TempUserDao { 
	public List<TempUser> findAllTempUser();
	public TempUser findTempUserById(int id);
	public List<TempUser> findTempUserByUserIdcard(String user_idcard);	 
	
	
	public void insertTempUser(TempUser tempUser);
	public void updateTempUser(TempUser tempUser);
	public void deleteTempUserBatch(String[] ids);
}
