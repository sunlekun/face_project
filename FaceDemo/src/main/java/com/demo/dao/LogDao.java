package com.demo.dao;

import java.util.List; 

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
import com.demo.model.Log; 
 

@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface LogDao {
	public List<Log>  findAllLog(); 
	public List<Log>  findLogByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);  
	public int  insertLog(Log log); 
	 
}
