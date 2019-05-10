package com.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.model.TempUser;
import com.demo.model.VideoIdent;
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
@ContextConfiguration(locations = {"classpath:conf/spring-mybatis.xml"})
public class TestService {
	@Autowired
	private TempUserService tempUserService;
	@Autowired
	private VideoIdentService videoIdentService;
	@Test
	public void test() throws Exception{
		List<TempUser> users = tempUserService.findTempUserByUserIdcard("411324198910014526");
		System.out.println(users.size());
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime date= time.withDayOfMonth(1);
		LocalDate today = LocalDate.now();//取当前时间
		LocalDate firstDate = today.withDayOfMonth(1);//当月第一天
		List<VideoIdent> list = videoIdentService.findVideoListByIdAndTime(1,DateFormatUtil.dayOfMonth());
		System.out.println(list.size());
	}
}
