package com.demo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
 






import com.demo.dao.VideoIdentDao; 
import com.demo.model.VideoIdent;
import com.demo.service.VideoIdentService;
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
public class VideoIdentServiceImpl implements VideoIdentService{
	 
		@Autowired
	    @Qualifier("videoIdentDao")
	    private VideoIdentDao videoIdentDao;

		@Override
		public List<String> findVidoIdentYears() { 
			return videoIdentDao.findVidoIdentYears();
		}

		@Override
		public VideoIdent findVideoIdentById(Integer id) { 
			return videoIdentDao.findVideoIdentById(id);
		}

		@Override
		public List<VideoIdent> findVideoListByIdAndTime(int id, String date) {
			return videoIdentDao.findVideoListByIdAndTime(id,date);
			
		}

		@Override
		public void insertVL(VideoIdent videoIdent) {
			videoIdentDao.insertVideoIdent(videoIdent);
		}

		@Override
		public List<VideoIdent> findVideoListByMultiCondition(Map map) {
			// TODO Auto-generated method stub
			return videoIdentDao.findVideoListByMultiCondition(map);
		}
		
	}