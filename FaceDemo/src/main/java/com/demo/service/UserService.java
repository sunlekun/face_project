package com.demo.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.model.TempUser;
import com.demo.model.User;
import com.github.pagehelper.Page;

public interface UserService {
	 
	public long findAllUserCount();
	public List<User> findAllUser();
	public List<User> findAllUserByKey(@Param("key") String key);
//	public List<User> findAllUserByMultiCondition( HashMap<String ,String > map);
	public Page<User> findAllUserByMultiCondition( HashMap<String ,String > map, Integer pageNumber, Integer pageSize);
	public User findUserById(int id);
	public List<User> findUserByUserIdcard(String user_idcard);
	
	 
	public void insertUser(User user);
	public void insertUserByTempUser(TempUser tempUser);
	public int insertUserBatch(List<User> users);
	public void updateUser(User user);
	public void updateUserByTempUser(TempUser tempUser);
	public void updateUserName(User user);
	public void deleteUserBatch(String[] ids);
}
