package com.demo.service.impl;

import java.util.List; 

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
 





import com.demo.dao.LogDao;
import com.demo.model.Log;
import com.demo.model.User;
import com.demo.service.LogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class LogServiceImpl implements LogService{
	@Autowired
    @Qualifier("logDao")
    private LogDao logDao;
	
	public List<Log>  findAllLog(){
		return logDao.findAllLog();
	}
	public Page<Log>  findLogByTime(String startTime, String endTime,Integer pageSize,Integer pageNumber){
		PageHelper.startPage(pageNumber, pageSize);
		return (Page<Log>)logDao.findLogByTime(startTime,  endTime,pageSize,pageNumber);
	}
	public int  insertLog(Log log){
		return logDao.insertLog(log);
	}
}
