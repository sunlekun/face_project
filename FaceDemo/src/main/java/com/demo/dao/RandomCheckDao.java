package com.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.RandomCheck; 
import com.demo.model.Role;
import com.demo.model.TempUser;
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface RandomCheckDao {
	public List<RandomCheck>  findRandomCheckByStatus( @Param("status") String status);
	public List<RandomCheck>  findRandomCheckByVideoStatusAndStatus(@Param("video_status")  String video_status,@Param("status") String status);
	public List<RandomCheck> findRandomCheckByMultiCondition(HashMap<String ,Object > map);
	public int  creatRandomCheckByMultiCondition(HashMap<String ,Object > map);
	public int  insertRandomCheckBatch(List<RandomCheck> randomChecks);  
	public RandomCheck findRandomCheckById( @Param("id") Integer id);
	public void updateRandomCheckStatus( @Param("status") Integer status,@Param("id") Integer id);	  
}

 