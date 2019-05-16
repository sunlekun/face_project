package com.demo.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.model.User;

public interface UserService {
	 
	public List<User> findAllUser();
	public List<User> findAllUserByKey(@Param("key") String key);
	public List<User> findAllUserByMultiCondition( HashMap<String ,String > map);
	public User findUserById(int id);
	public List<User> findUserByUserIdcard(String user_idcard);
	
	 
	public void insertUser(User user);
	public void updateUser(User user);
	public void updateUserName(User user);
	public void deleteUserBatch(String[] ids);
}
