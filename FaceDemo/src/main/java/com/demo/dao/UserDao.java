package com.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.User;
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface UserDao { 
	public List<User> findAllUser();
	public List<User> findAllUserByKey(@Param("key") String key);
	public List<User> findAllUserByMultiCondition(HashMap<String ,String > map);
	
	public User findUserById(int id);
	public List<User> findUserByUserIdcard(String user_idcard);	 
	
	
	public void insertUser(User user);
	public void updateUser(User user);
	public void updateUserName(User user);
	public void deleteUserBatch(String[] ids);
	public long findAllUserCount();
}
