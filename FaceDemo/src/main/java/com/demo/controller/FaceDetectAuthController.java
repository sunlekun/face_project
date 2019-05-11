package com.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.demo.model.TempUser;
import com.demo.model.VideoIdent;
import com.demo.service.TempUserService;
import com.demo.service.VideoIdentService;
import com.demo.util.DateFormatUtil;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月9日 下午6:01:01
 * @ClassName 类名称
 * @Description 类描述:用户输入身份证号查询信息采集库并请求实名核身鉴权
 */
@RestController
public class FaceDetectAuthController {
	public static Logger log= Logger.getLogger(FaceDetectAuthController.class);
	@Autowired
	private TempUserService tempUserService;
	
	@Autowired
	private VideoIdentService videoIdentService;
	
	
	@RequestMapping(value = "/reqFaceDetectAuth")
	public ModelAndView idToDAuthUrl(String user_idcard,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		if(StringUtils.isEmpty(user_idcard)){
			modelAndView.addObject("error", "请输入合法的身份证号");
			return modelAndView; 
		}
		//先去信息表里查询用户是否采集信息
		List<TempUser> users = tempUserService.findTempUserByUserIdcard(user_idcard);
		JSONObject object=new JSONObject(); 
		if(users==null&&users.size()==0){	
			modelAndView.addObject("error", "用户身份信息未采集,请先去所属乡镇部门采集身份信息!");
			return modelAndView;
		}
		//验证是否人脸核身过
		List<VideoIdent> list = videoIdentService.findVideoListByIdAndTime(1,DateFormatUtil.dayOfMonth());
		if(list!=null&&list.size()>0){
			modelAndView.addObject("error", "当月身份信息已经审核过，详情请咨询当地乡镇有关部门");
			return modelAndView;
		}
		//验证在7200秒内是或否调用核身前置接口
//		modelAndView.setViewName("redirect:/manager/toManagerList");
		return modelAndView;
	}
}
