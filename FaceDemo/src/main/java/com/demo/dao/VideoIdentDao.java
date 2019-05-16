package com.demo.dao;

import java.util.List;
 



import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.VideoIdent;

 
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface VideoIdentDao {
	public List<String>  findVidoIdentYears( );
	public VideoIdent findVideoIdentById( @Param("id") Integer id);
	public List<VideoIdent> findVideoListByIdAndTime(@Param("id") Integer id, @Param("date") String date);
	void insertVideoIdent(VideoIdent videoIdent);
	public List<VideoIdent> findVideoListByMultiCondition(Map map);
}

 