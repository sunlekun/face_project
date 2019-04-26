package com.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
 
import com.demo.model.Xzb; 
 

@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface XzbDao {
   
	public List<Xzb>  findAllXzb(); 
	public Xzb  findXzbById(int id);
	public void updateXzb(Xzb xzb);
	public void insertXzb(Xzb xzb);
	public void deleteXzbBatch(String[] ids);
}
