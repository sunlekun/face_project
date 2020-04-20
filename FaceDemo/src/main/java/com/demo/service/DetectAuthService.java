package com.demo.service;

import java.util.Date;
import java.util.List;

import com.demo.model.DetectAuth;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月13日 下午1:34:33
 * @ClassName 类名称
 * @Description 类描述:接口
 */
public interface DetectAuthService {
	List<DetectAuth> findDetectAuthByIdAndTime(String userIdcard,String time);

	void insertDA(DetectAuth detectAuth);

	String findUserId(String bizToken);

	List<DetectAuth> findDetectAuthByTime(Date beforeTime, Date endTime);
}
