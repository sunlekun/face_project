package com.demo.dao;
 
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
 
import com.demo.model.SysConfig; 
 

@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface SysConfigDao {
   
	public SysConfig  findSysConfig();  
}
