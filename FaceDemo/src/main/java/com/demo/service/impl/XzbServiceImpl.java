package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
 

import com.demo.dao.XzbDao;
import com.demo.model.Xzb;  
import com.demo.service.XzbService;

import java.util.List;

/**
 * @Author Yang
 * @Date 创建时间：2017-12-01
 * @Version 1.0
 *
 * @Project_Package_Description springmvc || com.demo.service.impl
 * @Function_Description 业务层接口实现类，处理具体的业务方面的逻辑
 *
 */
@Service
public class XzbServiceImpl implements XzbService{
 
	@Autowired
    @Qualifier("xzbDao")
    private XzbDao xzbDao;

 
	@Override
	public List<Xzb> findAllXzb() {
		return xzbDao.findAllXzb();
	} 
	 
	public Xzb  findXzbById(int id){
		return xzbDao.findXzbById(id);
	}
	public void updateXzb(Xzb xzb){
		xzbDao.updateXzb(xzb);
	}
	public void insertXzb(Xzb xzb){
		 xzbDao.insertXzb(xzb);
	}
	public void deleteXzbBatch(String[] ids){
		xzbDao.deleteXzbBatch(ids);
	}
}