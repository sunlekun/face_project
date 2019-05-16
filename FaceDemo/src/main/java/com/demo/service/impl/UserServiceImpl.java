package com.demo.service.impl;

import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; 
 

import org.springframework.stereotype.Service;

import com.demo.dao.UserDao;
import com.demo.model.User;
import com.demo.service.UserService;

@Service 
public class UserServiceImpl implements UserService {
	@Autowired
    @Qualifier("userDao")
    private UserDao userDao;

	 
	@Override
	public List<User> findAllUser(){
		return userDao.findAllUser();
	}
	public List<User> findAllUserByKey(String key){
		return userDao.findAllUserByKey(key);
	}
	public List<User> findAllUserByMultiCondition(HashMap<String ,String > map){
		return userDao.findAllUserByMultiCondition(map);
	}
	@Override
	public User findUserById(int id){
		return userDao.findUserById(id);
	}
	@Override
	public List<User> findUserByUserIdcard(String user_idcard){
		return userDao.findUserByUserIdcard(user_idcard);
	}
	
	 
	@Override
	public void insertUser(User user)
	{
		userDao.insertUser(user);
	}
	@Override
	public void updateUser(User user){
		userDao.updateUser(user);
	}
	public void deleteUserBatch(String[] ids){
		userDao.deleteUserBatch(ids);
	}
}
