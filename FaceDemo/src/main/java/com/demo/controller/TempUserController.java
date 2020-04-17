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
import com.demo.model.TempUser;
import com.demo.model.User;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.TempUserService;
import com.demo.service.XzbService;
import com.demo.util.LoadProperties;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/tempUser")
public class TempUserController { 
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(TempUserController.class);
	
	@Autowired
	private TempUserService tempUserService;
	
	@Autowired
	private XzbService xzbService;
	
	@RequestMapping(value = "/toTempUserList")
	@RequiresPermissions("tempUser:Show")
	@PermissionName("居民信息采集列表")
	public ModelAndView toTempUserList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		String type=request.getParameter("type");
		
		if("Img".equals(type))
		{
			if(pageSize==null)
				pageSize=10;
			if(pageNumber==null)
				pageNumber =1;
			Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
			HashMap<String ,String > map=new HashMap<String ,String >();
			map.put("key", key);
			if(manager.getRole_type()==1)//超级用户显示所有的采集信息
			     map.put("data_type",null);
			else  //其他用户只显示各自的类别的采集信息
				map.put("data_type", manager.getUser_type());
			map.put("user_township", manager.getXzb());
			map.put("status", request.getParameter("status"));
			map.put("dataType", request.getParameter("dataType")); 
			
			Page<TempUser> page=  tempUserService.findAllTempUserByMultiCondition(map,pageNumber,pageSize); 
			modelAndView.addObject("page", page); 
			modelAndView.addObject("key", key); 
            modelAndView.setViewName("admin/tempUserListImg"); 
            
		}
		else
		{
			type="Word";
			modelAndView.setViewName("admin/tempUserList"); 
		 }
	 
		modelAndView.addObject("status", request.getParameter("status"));   
		modelAndView.addObject("dataType", request.getParameter("dataType"));  
		modelAndView.addObject("type", type);   
		return modelAndView;
	}
	
	@RequestMapping(value = "/tempUserList")	
	public void tempUserList(Integer pageSize,Integer pageNumber,String key, HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		if(pageSize==null)
			pageSize=10;
		if(pageNumber==null)
			pageNumber =1;
		
		
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		map.put("key", key);
		
	 
		
		if(manager.getRole_type()==1)//超级用户显示所有的采集信息
		     map.put("data_type",null);
		else  //其他用户只显示各自的类别的采集信息
			map.put("data_type", manager.getUser_type());
		map.put("user_township", manager.getXzb());
		map.put("status", request.getParameter("status"));
		map.put("dataType", request.getParameter("dataType")); 
		Page<TempUser> pageInfo=  tempUserService.findAllTempUserByMultiCondition(map,pageNumber,pageSize);
 		 
		/*String jsons = JSON.toJSONString(tempUsers); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());*/
		
		String jsons = JSON.toJSONString(pageInfo.getResult());
		 
		JSONObject object = new JSONObject();
		object.put("total", pageInfo.getTotal()); 
		object.put("rows",jsons );   
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
		
			
	}
	
	@RequestMapping(value = "/tempUserDetial")
	@RequiresPermissions("tempUser:View")
	@PermissionName("居民采集信息详情")
	public ModelAndView tempUserDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));  
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
		modelAndView.setViewName("admin/tempUserDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toTempUserEdit")
	@RequiresPermissions("tempUser:Edit")
	@PermissionName("居民采集信息删除")
	public ModelAndView toTempUserEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;  
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
		modelAndView.setViewName("admin/tempUserEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserEdit")
	@PermissionName("采集信息")
	public ModelAndView tempUserEdit(TempUser tempUser ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
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
				tempUser.setOriginal_path(path);
				tempUser.setOriginal_path(path);
			}
		}
	 		
		tempUser.setStatus(1);
		if(!"城乡居民养老保险".equals(tempUser.getData_type()))   
	        {  
	          String user_company=request.getParameter("user_company");
	          tempUser.setUser_township(user_company==null?"":user_company);
	        }
		tempUser.setStatus_reason(null);
		tempUser.setAudit_time(null);
		tempUserService.updateTempUser(tempUser);

		modelAndView.addObject("status", request.getParameter("status1"));   
		modelAndView.addObject("dataType", request.getParameter("dataType"));  
		modelAndView.addObject("type", request.getParameter("type"));   
		modelAndView.setViewName("redirect:/tempUser/toTempUserList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/toTempUserAdd")
	@RequiresPermissions("tempUser:Add")
	@PermissionName("居民信息采集增加")
	public ModelAndView toTempUserAdd(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(); 
		
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));
		modelAndView.setViewName("admin/tempUserAdd");
		
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserAdd")
	@PermissionName("采集信息")
	public ModelAndView  tempUserAdd(TempUser tempUser ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		  
		
			JSONObject object = new JSONObject();

			object.put("status", "true");
			
		    ModelAndView modelAndView = new ModelAndView();
	       
		    tempUser.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 		 
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
	        	tempUser.setOriginal_path(path);
	        	tempUser.setOriginal_path(path);
	       }
	 		}
	        
	 
    	/*object.put("status", false); 
	    object.put("msg", "*友情提醒：1、请上传被采集人正面照片要求白色背景。2、上传被采集人身份证照片。3、上传采集人和被采集人合照。"); 
     
	          */
	 	tempUser.setStatus(1);
	 	
	     if(!"城乡居民养老保险".equals(tempUser.getData_type()))   
	        {  
	          String user_company=request.getParameter("user_company");
	          tempUser.setUser_township(user_company==null?"":user_company);
	        }
	     
		tempUserService.insertTempUser(tempUser);
		String s=request.getParameter("dataType");
		System.out.println(request.getParameter("dataType"));
		modelAndView.addObject("status", request.getParameter("status1"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));
	 
		modelAndView.setViewName("redirect:/tempUser/toTempUserList?type="+request.getParameter("type"));

		return modelAndView;
	}
	
	
	
	
	@RequestMapping(value = "/upload")
	public void upload( /*@RequestParam("file")  MultipartFile file,*/HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JSONObject object=new JSONObject(); 
		String collectInfoUploadSavePath = LoadProperties.loadProperties("common.properties", "collectInfoUploadSavePath");
		
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMM");
		  
		File dirFile = new File(collectInfoUploadSavePath+dfs.format(System.currentTimeMillis()) +"/");
        if (!dirFile.exists()) {
        	dirFile.mkdirs();
        }
        
		MultipartHttpServletRequest mul=(MultipartHttpServletRequest)request;  
	    Map<String,MultipartFile> files=mul.getFileMap();
	    
	    for(MultipartFile file:files.values())
	    {
	
		
		 
		String filename = file.getOriginalFilename();
		String fileExtName = filename.substring(filename.lastIndexOf("."));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		 
		filename = df.format(System.currentTimeMillis()) + fileExtName;
 
		file.transferTo(new File(collectInfoUploadSavePath+dfs.format(System.currentTimeMillis()) +"/" + filename));
  
	 /*    String thumbnail =  request.getParameter("IsThumbnail");         
                
                if ("1".equals(thumbnail))
                {
                    String sFileName = "thumb_" + filename;//缂╃暐鍥炬枃浠跺悕
                     
                    Thumbnails.of(infoUpdateSavePath + filename) 
                    .size(112, 112)  
                    .toFile(infoUpdateSavePath +"\\thumb\\"+ sFileName );
                    object.put("thumb","/info_uploadfiles/retire/thumb/"+ sFileName);
                }*/
               
            
		
        
		object.put("name", "/collect_info/upload/" +dfs.format(System.currentTimeMillis()) +"/"+ filename);
		object.put("path", "/collect_info/upload/" +dfs.format(System.currentTimeMillis()) +"/"+ filename );
		object.put("thumb","/collect_info/upload/" +dfs.format(System.currentTimeMillis()) +"/"+ filename);
	    }
		object.put("status", true);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	   
		
          
	}
	@RequestMapping(value = "/tempUserDelete")
	@RequiresPermissions("tempUser:Delete")
	@PermissionName("采集信息")
	public ModelAndView tempUserDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				tempUserService.deleteTempUserBatch(ids);
		}
		modelAndView.addObject("type", request.getParameter("type"));
		modelAndView.addObject("status", request.getParameter("status"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));
		modelAndView.setViewName("redirect:/tempUser/toTempUserList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/isExistUserIdcard")
	public  void isExistUserIdcard(String user_idcard,HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		List<TempUser> users = tempUserService.findTempUserByUserIdcard(user_idcard);	 
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
