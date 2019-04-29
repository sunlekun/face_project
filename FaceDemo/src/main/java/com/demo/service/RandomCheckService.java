package com.demo.service;

import java.util.List;
 


import org.apache.ibatis.annotations.Param;

import com.demo.model.RandomCheck; 
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public interface RandomCheckService {
	public List<RandomCheck>  findRandomCheckByStatus(String status);
	public List<RandomCheck>  findRandomCheckByVideoStatus(String video_status);
	 
}
