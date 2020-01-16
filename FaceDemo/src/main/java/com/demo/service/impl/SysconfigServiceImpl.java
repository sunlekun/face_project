package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
 
 

import com.demo.dao.SysConfigDao;
import com.demo.model.SysConfig; 
import com.demo.service.SysConfigService; 
 
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
public class SysconfigServiceImpl implements SysConfigService{
 
	@Autowired
    @Qualifier("sysConfigDao")
    private SysConfigDao sysConfigDao;
	public SysConfig  findSysConfig( ){
		return sysConfigDao.findSysConfig( );
	}
}