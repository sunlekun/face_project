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
 * @version ����ʱ�䣺2019��5��10�� ����1:57:04
 * @ClassName ������
 * @Description ������:������
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
		
		txFaceService.notifyProcess("DDAFE2CA-11B0-4B73-BA3B-4408DA2B8CB2");
		
		/**
		 * 
		
		List<TempUser> users = tempUserService.findTempUserByUserIdcard("411324198910014526");
		System.out.println(users.size());
		LocalDateTime time = LocalDateTime.now();
//		LocalDateTime date= time.withDayOfMonth(1);
//		LocalDate today = LocalDate.now();//ȡ��ǰʱ��
//		LocalDate firstDate = today.withDayOfMonth(1);//���µ�һ��
		
		
		Date date = new Date();
		System.out.println(date);
		long l = 7200*1000;
		Date beforeDate = new Date(date .getTime() + l);
		System.out.println(beforeDate);
		System.out.println(DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
		
		
		List<VideoIdent> list = videoIdentService.findVideoListByIdAndTime(1,DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
		System.out.println(list.size());
		 */
	}
	
	@Test
	public void test1() throws Exception {
		String  userId = detectAuthService.findUserId("81E3A2A6-B223-414A-B5EA-7A0814BD601C");
		System.out.println(userId);
		//��֤��7200�����ǻ����ú���ǰ�ýӿ�
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
					System.out.println("7200���ڵ��ù�");
//					modelAndView.setViewName("redirect:"+listDA.get(0).getUrl());
				}else{
					txFaceService.faceProcess(tempUser);
				}
		
//		DetectAuth da = new DetectAuth();
//        da.setBizToken("1123");
//        da.setUrl("http://test");
//        da.setCreate_time(new Date());
//        da.setRequest_id("12");
//        da.setUser_idcard("��ΰ");
//        detectAuthService.insertDA(da);
	}
	
	@Test
	public void test2() {
		txFaceService.notifyProcess("DDAFE2CA-11B0-4B73-BA3B-4408DA2B8CB2");
//		System.out.println(userId);
		List<TempUser> users = tempUserService.findTempUserByUserIdcard("411324198910014526");
		System.out.println(users.size());
		LocalDateTime time = LocalDateTime.now();
//		LocalDateTime date= time.withDayOfMonth(1);
//		LocalDate today = LocalDate.now();//ȡ��ǰʱ��
//		LocalDate firstDate = today.withDayOfMonth(1);//���µ�һ��
		
		
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
	public void test3() {
		txFaceService.notifyProcess("DDAFE2CA-11B0-4B73-BA3B-4408DA2B8CB2");
	}
}
