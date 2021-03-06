package com.demo.service.impl;

import java.util.HashMap;
import java.util.List;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; 
 

import org.springframework.stereotype.Service;

import com.demo.dao.TempUserDao;
import com.demo.model.TempUser;
import com.demo.service.TempUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service 
public class TempUserServiceImpl implements TempUserService {
	@Autowired
    @Qualifier("tempUserDao")
    private TempUserDao tempUserDao;

	 
	@Override
	public List<TempUser> findAllTempUser(){
		return tempUserDao.findAllTempUser();
	}
	public List<TempUser> findAllTempUserByKey(String key){
		return tempUserDao.findAllTempUserByKey(key);
	}
	public Page<TempUser> findAllTempUserByMultiCondition(HashMap<String ,String > map, Integer pageNumber, Integer pageSize){
		PageHelper.startPage(pageNumber, pageSize);
		return (Page<TempUser>)tempUserDao.findAllTempUserByMultiCondition(map);
	}
	
	public List<TempUser> findAllTempUserByMultiCondition(HashMap<String ,String > map){
		return tempUserDao.findAllTempUserByMultiCondition(map);
	}
	@Override
	public TempUser findTempUserById(int id){
		return tempUserDao.findTempUserById(id);
	}
	@Override
	public List<TempUser> findTempUserByUserIdcard(String user_idcard){
		return tempUserDao.findTempUserByUserIdcard(user_idcard);
	}
	
	 
	@Override
	public void insertTempUser(TempUser tempUser)
	{
		tempUserDao.insertTempUser(tempUser);
	}
	@Override
	public void updateTempUser(TempUser tempUser){
		tempUserDao.updateTempUser(tempUser);
	}
	public void deleteTempUserBatch(String[] ids){
		tempUserDao.deleteTempUserBatch(ids);
	}
}
