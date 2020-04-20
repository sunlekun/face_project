package com.demo.job;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.core.TxFaceService;
import com.demo.model.DetectAuth;
import com.demo.model.User;
import com.demo.model.VideoIdent;
import com.demo.service.DetectAuthService;
import com.demo.service.UserService;
import com.demo.service.VideoIdentService;
import com.demo.util.DateFormatUtil;


/**
 * @author lekun.sun
 * @version 创建时间：2020年4月20日 下午1:40:54
 * @ClassName 类名称
 * @Description 类描述:主动查询认证结果
 */
public class NotifySynchroJob {
	public static Logger log= Logger.getLogger(NotifySynchroJob.class);
	
	@Autowired
	private VideoIdentService videoIdentService;
	@Autowired
	private UserService userService;
	@Autowired
	private DetectAuthService detectAuthService;
	@Autowired
	private TxFaceService txFaceService;
	 public void process() throws ParseException{
		 log.info("定时刷新任务开始"+new Date());
		 Date beforeTime = DateFormatUtil.parse(new Date(), 1);
		 Date endTime = DateFormatUtil.parse(new Date(), 0);
		 List<DetectAuth> list = detectAuthService.findDetectAuthByTime(beforeTime, endTime);
		 int i= 0;
		 if(list!=null&&list.size()>0){
			 for(DetectAuth detectAuth:list){
				 List<User>  users=userService.findUserByUserIdcard(detectAuth.getUser_idcard());
				 if(users.size()!=0){
					List<VideoIdent> listV = videoIdentService.findVideoByUserId(users.get(0).getId());
					if(listV.size()==0){
						log.info((i++)+"主动查询认证结果:"+detectAuth.getUser_idcard());
						txFaceService.notifyProcess(detectAuth.getBizToken());
					}
				 }
				
			 }
		 }
		 log.info("删除重复数据任务开始"+new Date());
		 videoIdentService.delrepeat();
	 }
}
