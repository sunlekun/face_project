package com.demo.dao;

import java.util.List;
 

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.VideoIdent;

 
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface VideoIdentDao {
	public List<String>  findVidoIdentYears( );
	public VideoIdent findVideoIdentById( @Param("id") Integer id);
	
}

 