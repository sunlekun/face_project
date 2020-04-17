package com.demo.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.model.TempUser;
import com.demo.model.VideoIdent;
import com.github.pagehelper.PageInfo;

public interface TempUserService {
	 
	public List<TempUser> findAllTempUser();
	public List<TempUser> findAllTempUserByKey(@Param("key") String key);
	public PageInfo<TempUser> findAllTempUserByMultiCondition( HashMap<String ,String > map, Integer pageNumber, Integer pageSize);
	public List<TempUser> findAllTempUserByMultiCondition( HashMap<String ,String > map);
	public TempUser findTempUserById(int id);
	public List<TempUser> findTempUserByUserIdcard(String user_idcard);
	
	 
	public void insertTempUser(TempUser tempUser);
	public void updateTempUser(TempUser tempUser);
	public void deleteTempUserBatch(String[] ids);
}
