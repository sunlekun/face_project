package com.demo.service;

import java.util.List; 
 

import com.demo.model.Log; 
 
public interface LogService{
	public List<Log>  findAllLog(); 
	public List<Log>  findLogByTime(String startTime, String endTime); 
	public int  insertLog(Log log); 
}
