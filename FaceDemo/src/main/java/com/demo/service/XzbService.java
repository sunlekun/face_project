package com.demo.service;

import java.util.List;

import com.demo.model.Xzb;
/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service
 * @Function_Description 业务层接口，处理具体的业务方面的逻辑
 *
 */
public interface XzbService {
 
	public List<Xzb> findAllXzb(); 
	public Xzb  findXzbById(int id);
	public void updateXzb(Xzb xzb);
	public void insertXzb(Xzb xzb);
	public void deleteXzbBatch(String[] ids);
}
