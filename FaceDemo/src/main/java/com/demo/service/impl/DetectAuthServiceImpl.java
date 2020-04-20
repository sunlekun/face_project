package com.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.demo.dao.DetectAuthDao;
import com.demo.model.DetectAuth;
import com.demo.service.DetectAuthService;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月13日 下午1:35:00
 * @ClassName 类名称
 * @Description 类描述：接口实现类
 */
@Service 
public class DetectAuthServiceImpl implements DetectAuthService {
	@Autowired
    @Qualifier("detectAuthDao")
    private DetectAuthDao detectAuthDao;
	
	@Override
	public List<DetectAuth> findDetectAuthByIdAndTime(String userIdcard,String time){
		return detectAuthDao.findDetectAuthByIdAndTime(userIdcard,time);
	}

	@Override
	public void insertDA(DetectAuth detectAuth) {
		detectAuthDao.insertDA(detectAuth);
		
	}

	@Override
	public String findUserId(String bizToken) {
		// TODO Auto-generated method stub
		return detectAuthDao.findUserId(bizToken);
	}

	@Override
	public List<DetectAuth> findDetectAuthByTime(Date beforeTime, Date endTime){
		// TODO Auto-generated method stub
		return detectAuthDao.findDetectAuthByTime(beforeTime,endTime);
	}

}
