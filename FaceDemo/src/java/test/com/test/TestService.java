package com.test;

import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.core.TxFaceService;
import com.demo.model.DetectAuth;
import com.demo.model.TempUser;
import com.demo.model.VideoIdent;
import com.demo.service.DetectAuthService;
import com.demo.service.TempUserService;
import com.demo.service.VideoIdentService;
import com.demo.util.DateFormatUtil;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月10日 下午1:57:04
 * @ClassName 类名称
 * @Description 类描述:测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/spring-mybatis.xml","classpath:conf/application-context.xml"})
public class TestService {
	@Autowired
	private TempUserService tempUserService;
	@Autowired
	private VideoIdentService videoIdentService;
	@Autowired
	private DetectAuthService detectAuthService;
	
	@Autowired
	private  TxFaceService txFaceService;
	@Test
	public void test() throws Exception{
		List<TempUser> users = tempUserService.findTempUserByUserIdcard("411324198910014526");
		System.out.println(users.size());
		LocalDateTime time = LocalDateTime.now();
//		LocalDateTime date= time.withDayOfMonth(1);
//		LocalDate today = LocalDate.now();//取当前时间
//		LocalDate firstDate = today.withDayOfMonth(1);//当月第一天
		
		
		Date date = new Date();
		System.out.println(date);
		long l = 7200*1000;
		Date beforeDate = new Date(date .getTime() + l);
		System.out.println(beforeDate);
		System.out.println(DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
		
		
		List<VideoIdent> list = videoIdentService.findVideoListByIdAndTime(1,DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
		System.out.println(list.size());
	}
	
	@Test
	public void test1() {
		//验证在7200秒内是或否调用核身前置接口
		TempUser tempUser = new TempUser();
		tempUser.setUser_idcard("340826198806280833");
//		txFaceService.faceProcess(tempUser);
				Date date = new Date();
				long l = 7200*1000;
				Date beforeDate = new Date(date .getTime() - l);
				System.out.println(beforeDate);
				System.out.println(DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
				List<DetectAuth> listDA = detectAuthService.findDetectAuthByIdAndTime(tempUser.getUser_idcard(),DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
				if(listDA!=null&&listDA.size()>0){
					System.out.println("7200秒内调用过");
//					modelAndView.setViewName("redirect:"+listDA.get(0).getUrl());
				}else{
					txFaceService.faceProcess(tempUser);
				}
		
//		DetectAuth da = new DetectAuth();
//        da.setBizToken("1123");
//        da.setUrl("http://test");
//        da.setCreate_time(new Date());
//        da.setRequest_id("12");
//        da.setUser_idcard("杨伟");
//        detectAuthService.insertDA(da);
	}
}
