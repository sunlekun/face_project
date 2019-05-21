package com.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.demo.bean.DetectAuthRespBean;
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
 * @version 创建时间：2019年5月9日 下午6:01:01
 * @ClassName 类名称
 * @Description 类描述:用户输入身份证号查询信息采集库并请求实名核身鉴权
 */
@RestController
@RequestMapping(value = "/faceDetectAuth")
public class FaceDetectAuthController {
	public static Logger log= Logger.getLogger(FaceDetectAuthController.class);
	@Autowired
	private TempUserService tempUserService;
	
	@Autowired
	private VideoIdentService videoIdentService;
	
	@Autowired
	private DetectAuthService detectAuthService;
	
	@Autowired
	private TxFaceService txFaceService;
	
	@SuppressWarnings({ "unchecked", "null", "rawtypes" })
	@RequestMapping(value = "/reqFaceDetectAuth") 
	public Map idToDAuthUrl(String user_idcard,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map map = new HashMap<String, Object>();
		try {
			if(!cardCodeVerifySimple(user_idcard)){
				map.put("status", "error");
				map.put("msg", "请输入合法的身份证号");
				return map; 
			}
			//先去信息表里查询用户是否采集信息
			List<TempUser> users = tempUserService.findTempUserByUserIdcard(user_idcard);
			if(users==null&&users.size()==0){	
				map.put("status", "error");
				map.put("msg", "用户身份信息未采集,请先去所属乡镇部门采集身份信息!");
				return map;
			}
			//验证是否人脸核身过
			List<VideoIdent> list = videoIdentService.findVideoListByIdAndTime(users.get(0).getId(),DateFormatUtil.getCurrentDT().substring(0,4));
			if(list!=null&&list.size()>0){
//				modelAndView.addObject("error", DateFormatUtil.getCurrentDT().substring(0,4)+"年身份信息已经审核过，详情请咨询当地乡镇有关部门");
//				return modelAndView;
				map.put("status", "error");
				map.put("msg", DateFormatUtil.getCurrentDT().substring(0,4)+"年身份信息已经审核过，详情请咨询当地乡镇有关部门");
				return map;
			}
			//验证在7200秒内是或否调用核身前置接口
			Date date = new Date();
			long l = 7200*1000;
			Date beforeDate = new Date(date .getTime() - l);
			List<DetectAuth> listDA = detectAuthService.findDetectAuthByIdAndTime(users.get(0).getUser_idcard(),DateFormatUtil.getDTFormat(beforeDate, "yyyyMMddHHmmss"));
			if(listDA!=null&&listDA.size()>0){
				map.put("status", "sucess");
				map.put("msg", listDA.get(0).getUrl());
				return map;
			}else{
				
				DetectAuthRespBean respBean = txFaceService.faceProcess(users.get(0));
				map.put("status", "sucess");
				map.put("msg", respBean.getUrl());
				return map;
			}
		} catch (Exception e) {
			map.put("status", "error");
			map.put("msg", "系统错误");
			return map; 

		}
	}
	
	/**
	 * 人脸核身结果接收地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notify.do", method = RequestMethod.POST)
	@ResponseBody
	public String notify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String BizToken = request.getParameter("BizToken");
		if(!StringUtils.isEmpty(BizToken)){
			txFaceService.notifyProcess(BizToken);
		}
		return null;
		
	}
	
	
	private boolean cardCodeVerifySimple(String cardcode) {
	    //第一代身份证正则表达式(15位)
	    String isIDCard1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	    //第二代身份证正则表达式(18位)
	    String isIDCard2 ="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";

	    //验证身份证
	    if (cardcode.matches(isIDCard1) || cardcode.matches(isIDCard2)) {
	        return true;
	    }
	    return false;
	}
}
