package com.demo.service;

import java.util.List;

import com.demo.model.TempUser;

public interface TempUserService {
	 
	public List<TempUser> findAllTempUser();
	public List<TempUser> findAllTempUserByKey(String key);
	public TempUser findTempUserById(int id);
	public List<TempUser> findTempUserByUserIdcard(String user_idcard);
	
	 
	public void insertTempUser(TempUser tempUser);
	public void updateTempUser(TempUser tempUser);
	public void deleteTempUserBatch(String[] ids);
}
