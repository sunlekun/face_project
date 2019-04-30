package com.demo.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.demo.dao.RandomCheckDao;   
import com.demo.model.RandomCheck; 
import com.demo.service.RandomCheckService; 
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */ 
 
@Service
public class RandomCheckServiceImpl implements RandomCheckService{
	 
		@Autowired
	    @Qualifier("randomCheckDao")
	    private RandomCheckDao randomCheckDao;

		@Override
		public List<RandomCheck> findRandomCheckByStatus(String status) { 
			return randomCheckDao.findRandomCheckByStatus( status);
		}
		public List<RandomCheck>  findRandomCheckByVideoStatusAndStatus(String video_status,String status){
			return randomCheckDao.findRandomCheckByVideoStatusAndStatus( video_status,status);
		}
		public int  creatRandomCheckByNumberAndYear( int number, String year){
			return randomCheckDao.creatRandomCheckByNumberAndYear( number,year);
		}
		public int insertRandomCheckBatch(List<RandomCheck> randomChecks){
			return randomCheckDao.insertRandomCheckBatch( randomChecks);
		}
	 
	}