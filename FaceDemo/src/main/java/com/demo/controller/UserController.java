package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
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
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.UserService;
import com.demo.service.XzbService;
import com.demo.util.LoadProperties;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/user")
public class UserController { 
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private XzbService xzbService;
	
	@RequestMapping(value = "/toUserList")
	@RequiresPermissions("user:Show")
	@PermissionName("居民信息采集管理")
	public ModelAndView toUserList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		 
		modelAndView.setViewName("admin/userList"); 
		 
	 
		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		return modelAndView;
	}
	
	@RequestMapping(value = "/userList")	
	public void userList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		map.put("data_type", manager.getUser_type());
		
		String user_township=manager.getXzb();
		if(manager.getXzb()==null||"".equals(manager.getXzb()))
				user_township=request.getParameter("user_township");
		map.put("user_township", user_township);
		
		map.put("isHasVideo", request.getParameter("isHasVideo"));
		map.put("startTime", request.getParameter("startTime"));  
		map.put("endTime", request.getParameter("endTime"));  
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
	@PermissionName("居民信息采集详情")
	public ModelAndView userDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		User user= userService.findUserById(id) ;
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("user", user); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));   
		modelAndView.setViewName("admin/userDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toUserEdit")
	@RequiresPermissions("user:Edit")
	@PermissionName("居民信息采集修改")
	public ModelAndView toUserEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		User user= userService.findUserById(id) ;  
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("user", user); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));   
		modelAndView.setViewName("admin/userEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/userEdit")
	public ModelAndView userEdit(User user ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
			
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		    
		if (hid_photo_names != null && hid_photo_names.length == 3)
		{
			String path = "";
			for (int i = 0; i < hid_photo_names.length; i++) {
				String[] name = hid_photo_names[i].split("\\|");
				
				/* if(name!=null) 
				 { 
					 String[]	 name1=name[1].split("\\/"); 
					 System.out.println(name1[2]);
				     path+=name[1]+";";
				 }*/
				 
				path += name[1] + ";";
			}

			if (path.length() > 0)
			{
				path = path.substring(0, path.length() - 1);
				/*user.setOriginal_path(path);
				user.setOriginal_path(path);*/
			}
		}
	 		
		 
		userService.updateUser(user);

	
		modelAndView.setViewName("redirect:/user/toUserList?type="+request.getParameter("type")+"&status="+request.getParameter("status1"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/toUserAdd")
	@RequiresPermissions("user:Add")
	@PermissionName("居民信息采集新增")
	public ModelAndView toUserAdd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(); 
		
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));
	 
		modelAndView.setViewName("admin/userAdd");
		
		return modelAndView;
	}
	@RequestMapping(value = "/userAdd")
	public ModelAndView  userAdd(User user ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		  
		
			JSONObject object = new JSONObject();

			object.put("status", "true");
			
		    ModelAndView modelAndView = new ModelAndView();
	       
		    user.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 		 
	 		if(hid_photo_names!=null&&hid_photo_names.length==3)
	 		{
	 		String path="";
	        for(int i=0;i<hid_photo_names.length;i++)
	        {    
	        	String[] name=hid_photo_names[i].split("\\|"); 
	        /*	if(name!=null&&name.length>=2) 
	        	{ 
	        		String[] name1=name[1].split("\\/"); 
	        		System.out.println(name1[3]);
	        		path+=name[1]+";";
	        	}*/
	        	path+=name[1]+";";
	        }
	         
	        if(path.length()>0)
	       {
	        	path=path.substring(0, path.length()-1);
	        	/*user.setOriginal_path(path);
	        	user.setOriginal_path(path);*/
	       }
	 		}
	        
	 
    	/*object.put("status", false); 
	    object.put("msg", "用户身份信息采集,必须上传三张照片!\n1、请上传被采集人正面照片要求白色背景。2、上传被采集人身份证照片。3、上传采集人和被采集人合照。"); 
     
	          */
	 
		userService.insertUser(user);

	 
		modelAndView.setViewName("redirect:/user/toUserList?type="+request.getParameter("type"));

		return modelAndView;
	}
	
	
	
	
	@RequestMapping(value = "/upload")
	public void upload( /*@RequestParam("file")  MultipartFile file,*/HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JSONObject object=new JSONObject(); 
		String collectInfoUploadSavePath = LoadProperties.loadProperties("common.properties", "collectInfoUploadSavePath");
		
		MultipartHttpServletRequest mul=(MultipartHttpServletRequest)request;  
	    Map<String,MultipartFile> files=mul.getFileMap();
	    
	    for(MultipartFile file:files.values())
	    {
	
		
		// 得到上传的文件名称，
		String filename = file.getOriginalFilename();
		String fileExtName = filename.substring(filename.lastIndexOf("."));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		//重命名文件
		filename = df.format(System.currentTimeMillis()) + fileExtName;
 
		file.transferTo(new File(collectInfoUploadSavePath + filename));
  
	 /*    String thumbnail =  request.getParameter("IsThumbnail");         
                //生成缩略图
                if ("1".equals(thumbnail))
                {
                    String sFileName = "thumb_" + filename;//缩略图文件名
                     
                    Thumbnails.of(infoUpdateSavePath + filename) 
                    .size(112, 112)  
                    .toFile(infoUpdateSavePath +"\\thumb\\"+ sFileName );
                    object.put("thumb","/info_uploadfiles/retire/thumb/"+ sFileName);
                }*/
               
                
		object.put("name", "/collect_info/upload/" + filename);
		object.put("path", "/collect_info/upload/" + filename );
		object.put("thumb","/collect_info/upload/" + filename);
	    }
		object.put("status", true);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	   
		
          
	}
	@RequestMapping(value = "/userDelete")
	@RequiresPermissions("user:Delete")
	@PermissionName("居民信息采集删除")
	public ModelAndView userDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				userService.deleteUserBatch(ids);
		}
		
		modelAndView.setViewName("redirect:/user/toUserList?type="+request.getParameter("type")+"&status="+request.getParameter("status"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/isExistUserIdcard")
	public  void isExistUserIdcard(String user_idcard,HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		List<User> users = userService.findUserByUserIdcard(user_idcard);	 
		JSONObject object=new JSONObject(); 
		if(users.size()!=0){		 
			object.put("status", "false"); 
		    object.put("msg", "用户身份信息已采集,请勿重复采集!"); 
		}
		else { 
			object.put("status", "true");   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	  
		} 
	
	
		

		} 
