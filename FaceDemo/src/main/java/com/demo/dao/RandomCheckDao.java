package com.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.RandomCheck; 
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface RandomCheckDao {
	public List<RandomCheck>  findRandomCheckByStatus( @Param("status") String status);
	public List<RandomCheck>  findRandomCheckByVideoStatusAndStatus(@Param("video_status")  String video_status,@Param("status") String status);
	public int  creatRandomCheckByNumberAndYear( @Param("number") int number,@Param("year") String year);
	public int  insertRandomCheckBatch(List<RandomCheck> randomChecks);  

}

 