package com.demo.service;

import java.util.List; 
 


import com.demo.model.Log; 
import com.github.pagehelper.Page;
 
public interface LogService{
	public List<Log>  findAllLog(); 
	public Page<Log>  findLogByTime(String startTime, String endTime,Integer pageSize,Integer pageNumber); 
	public int  insertLog(Log log); 
}
