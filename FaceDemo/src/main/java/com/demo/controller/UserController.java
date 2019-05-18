package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.demo.model.Manager; 
import com.demo.model.User;
import com.demo.model.VideoIdent;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.UserService;
import com.demo.service.VideoIdentService;
import com.demo.service.XzbService;
import com.demo.util.LoadProperties; 

@Controller
@RequestMapping(value = "/user")
public class UserController { 
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private XzbService xzbService;
	
	@Autowired
	private VideoIdentService videoIdentService;
	
	@RequestMapping(value = "/toUserList")
	@RequiresPermissions("user:Show")
	@PermissionName("用户信息列表")
	public ModelAndView toUserList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		 
		modelAndView.setViewName("admin/userList"); 
		 
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs);  
		
		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		return modelAndView;
	}
	
	@RequestMapping(value = "/userList")	
	public void userList(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		 
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		
		if(manager.getRole_type()==1)//超级用户，显示所有的。 
			map.put("data_type", null);
		else  //其他用户只显示各自的类别的用户信息
			map.put("data_type", manager.getUser_type());
		
	 
		
		if(manager.getRole_type()==1)//超级用户，类别为城乡居民的，显示所有
			map.put("xzb", null);
		else //非系统用户只能按照自己的管理权限筛选
			map.put("xzb", manager.getXzb());
		
		 
		map.put("user_township", request.getParameter("user_township"));
		 
		
		
		map.put("isHasVideo", request.getParameter("isHasVideo"));
		
		
		
		String startTime1=request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime1=request.getParameter("endTime")==null?"":request.getParameter("endTime");

		String startTime=startTime1;
		String endTime=endTime1;
		
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 
		if(!("").equals(startTime1)&&!("").equals(endTime1))
			 if(sdf.parse(startTime1).getTime()>sdf.parse(endTime1).getTime()){//转成long类型比较
				 
	        	startTime=endTime1;
	        	endTime=startTime1;
	         }
            
		 
		
		map.put("startTime", startTime);  
		map.put("endTime", endTime);  
		List<User> users=  userService.findAllUserByMultiCondition(map); 
 		 
		String jsons = JSON.toJSONString(users); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
			
	}
	
	@RequestMapping(value = "/userDetial")
	@RequiresPermissions("user:View")
	@PermissionName("用户信息详情")
	public ModelAndView userDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		User   user= userService.findUserById(id) ; 
		modelAndView.addObject("user", user); 
		
		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		
		modelAndView.setViewName("admin/userDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/userVideoIdentList") 
	@PermissionName("用户认证信息列表")
	public void userVideoIdentList(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException {
	 
		List<VideoIdent> videoIdents=videoIdentService.findVideoListByUserId(id);
		String jsons = JSON.toJSONString(videoIdents); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
		
	  
	}
	
	@RequestMapping(value = "/toUserEdit")
	@RequiresPermissions("user:Edit")
	@PermissionName("用户信息编辑")
	public ModelAndView toUserEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		User user= userService.findUserById(id) ;   
		modelAndView.addObject("user", user); 
		
		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		
		modelAndView.setViewName("admin/userEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/userEdit")
	@PermissionName("用户信息")
	public ModelAndView userEdit(User user ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
			 
		 
		userService.updateUserName(user);

		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township1"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		modelAndView.setViewName("redirect:/user/toUserList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/toUserAdd")
	@RequiresPermissions("user:Add")
	@PermissionName("用户信息增加")
	public ModelAndView toUserAdd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(); 
		
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		
		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		
		modelAndView.setViewName("admin/userAdd");
		
		return modelAndView;
	}
	@RequestMapping(value = "/userAdd")
	@PermissionName("用户信息")
	public ModelAndView  userAdd(User user ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		 String idCardImgUploadSavePath = LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
		
			JSONObject object = new JSONObject();

			object.put("status", "true");
			
		    ModelAndView modelAndView = new ModelAndView();
	       
		    user.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 		 String oldFileName="";
	 		if(hid_photo_names!=null&&hid_photo_names.length==1)
	 		{   
	 			 
	        	String[] name=hid_photo_names[0].split("\\|"); 
	        	if(name!=null&&name.length>=2) 
	        	{ 
	        		String[] name1=name[1].split("\\/"); 
	        		//System.out.println(name1[3]);
	        		oldFileName=name1[3];
	        	}
	        	 
	 		}
	 		String fileExtName = oldFileName.substring(oldFileName.lastIndexOf("."));
	        new File(idCardImgUploadSavePath+oldFileName).renameTo(new File(idCardImgUploadSavePath+user.getUser_idcard()+fileExtName));
	        user.setImg_url("/upload/" +user.getUser_idcard()+fileExtName);
     
	 
		userService.insertUser(user);

		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township1"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		modelAndView.setViewName("redirect:/user/toUserList");

		return modelAndView;
	}
	
	
	
	
	@RequestMapping(value = "/upload")
	public void upload( /*@RequestParam("file")  MultipartFile file,*/HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JSONObject object=new JSONObject(); 
		String idCardImgUploadSavePath = LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
		
		MultipartHttpServletRequest mul=(MultipartHttpServletRequest)request;  
	    Map<String,MultipartFile> files=mul.getFileMap();
	    
	    for(MultipartFile file:files.values())
	    {
	
		
		 
		String filename = file.getOriginalFilename();
		String fileExtName = filename.substring(filename.lastIndexOf("."));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		 
		filename = df.format(System.currentTimeMillis()) + fileExtName;
 
		file.transferTo(new File(idCardImgUploadSavePath + filename));
  
	 /*    String thumbnail =  request.getParameter("IsThumbnail");         
                 
                if ("1".equals(thumbnail))
                {
                    String sFileName = "thumb_" + filename;//缂╃暐鍥炬枃浠跺悕
                     
                    Thumbnails.of(infoUpdateSavePath + filename) 
                    .size(112, 112)  
                    .toFile(infoUpdateSavePath +"\\thumb\\"+ sFileName );
                    object.put("thumb","/info_uploadfiles/retire/thumb/"+ sFileName);
                }*/
               
                
		object.put("name", "/img_identity/upload/" + filename);
		object.put("path", "/img_identity/upload/" + filename );
		object.put("thumb","/img_identity/upload/" + filename);
	    }
		object.put("status", true);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	   
		
          
	}
	@RequestMapping(value = "/userDelete")
	@RequiresPermissions("user:Delete")
	@PermissionName("用户信息")
	public ModelAndView userDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				userService.deleteUserBatch(ids);
		}
		 
		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		modelAndView.setViewName("redirect:/user/toUserList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/isExistUserIdcard")
	public  void isExistUserIdcard(String user_idcard,HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		List<User> users = userService.findUserByUserIdcard(user_idcard);	 
		JSONObject object=new JSONObject(); 
		if(users.size()!=0){		 
			object.put("status", "false"); 
		    object.put("msg", "用户身份信息已存在,请勿重复增加!"); 
		}
		else { 
			object.put("status", "true");   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	  
		} 
	 

		} 
