package com.demo.dao;
 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.DetectAuth;
 
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,isolation=Isolation.DEFAULT)
public interface DetectAuthDao {

	List<DetectAuth> findDetectAuthByIdAndTime(@Param("userIdcard")String userIdcard,@Param("time") String time);

	void insertDA(DetectAuth detectAuth);


	String findUserId(@Param("bizToken")String bizToken);
	 
}
