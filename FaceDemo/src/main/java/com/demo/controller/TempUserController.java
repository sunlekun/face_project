package com.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.demo.aop.LogAspect;
import com.demo.model.Log;
import com.demo.model.Manager; 
import com.demo.model.TempUser;
import com.demo.model.User;
import com.demo.model.VideoIdent;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.TempUserService;
import com.demo.service.UserService;
import com.demo.service.VideoIdentService;
import com.demo.service.XzbService;
import com.demo.util.DateFormatUtil;
import com.demo.util.ExcelUtils;
import com.demo.util.ExportExcelUtils;
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
	private UserService userService;
	
	@Autowired
	private VideoIdentService videoIdentService;
	
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
			
			if(manager.getRole_type()==1)//超级用户，显示所有
				map.put("user_township", null);
			else //非系统用户只能按照自己的管理权限筛选
				map.put("user_township", manager.getXzb());
			
			map.put("status", request.getParameter("status"));
			map.put("dataType", request.getParameter("dataType"));  
			Page<TempUser> page=  tempUserService.findAllTempUserByMultiCondition(map,pageNumber,pageSize);
			PageInfo<TempUser> pageInfo= page.toPageInfo();
			modelAndView.addObject("page", pageInfo); 
			
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
		modelAndView.addObject("key", key); 
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

		if(manager.getRole_type()==1)//超级用户，显示所有
			map.put("user_township", null);
		else //非系统用户只能按照自己的管理权限筛选
			map.put("user_township", manager.getXzb());
		
		map.put("status", request.getParameter("status"));
		map.put("dataType", request.getParameter("dataType")); 
		
		/*System.out.println("user_township = "+map.get("user_township") );
		System.out.println("status = "+map.get("status") );
		System.out.println("dataType = "+map.get("dataType") );
		System.out.println("key = "+map.get("key") );
		System.out.println("用户权限data_type = "+map.get("data_type") );*/
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
		modelAndView.addObject("key", request.getParameter("key"));
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
		modelAndView.addObject("key", request.getParameter("key"));
		modelAndView.setViewName("admin/tempUserEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserEdit")
	@PermissionName("采集信息")
	public ModelAndView tempUserEdit(TempUser tempUser ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
			
		String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		String img_url="";
		if (hid_photo_names != null && hid_photo_names.length == 3)
		{
			String path = "";
			for (int i = 0; i < hid_photo_names.length; i++) {
				String[] name = hid_photo_names[i].split("\\|");
				 
				path += name[1] + ";";
				 
				if(i==tempUser.getImg_urlIndex() )
				{
					 int beginIndex="/collect_info/upload/".length(); 
					 String name1=name[1].substring(beginIndex);  
				     img_url=name1;
				}
			}

			if (path.length() > 0)
			{
				path = path.substring(0, path.length() - 1);
				tempUser.setOriginal_path(path);
				tempUser.setThumb_path(path);
			}
		}
	 		
		if(!"城乡居民养老保险".equals(tempUser.getData_type()))   
	     {  
	          String user_company=request.getParameter("user_company");
	          tempUser.setUser_township(user_company==null?"":user_company);
	     }
		
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		tempUser.setOpt_name(manager.getUser_name());
		
		
		TempUser old=tempUserService.findTempUserById(tempUser.getId()); 
		
		//更新用户表中对应的用户信息 
		//图片更换  ：将新的图片替换原来的图片
		if(!old.getOriginal_path().equals(tempUser.getOriginal_path())||tempUser.getImg_urlIndex()!=old.getImg_urlIndex())
		{   
			String idCardImgUploadSavePath=LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
			String collectInfoUploadSavePath=LoadProperties.loadProperties("common.properties", "collectInfoUploadSavePath");
			File file = new File(idCardImgUploadSavePath +tempUser.getUser_idcard()+".jpg");
			if(file.exists())
	        	 file.delete();
			
            Path source = Paths.get (collectInfoUploadSavePath+img_url);//源文件
	        Files.copy(source, new FileOutputStream(idCardImgUploadSavePath+tempUser.getUser_idcard()+".jpg"));
	          
	        String basePath = DateFormatUtil.getCurrentDT().substring(0, 4)
					+ "//" + DateFormatUtil.getCurrentDT().substring(4, 6);
		
	        String filePath=LoadProperties.loadProperties("sysConfig.properties", "filePuth");
			File videntImg = new File(filePath + basePath+"//"+tempUser.getUser_idcard()+".jpg");
		    if(videntImg.exists())
		        videntImg.delete();
		    Files.copy(source, new FileOutputStream(filePath + basePath+"//"+tempUser.getUser_idcard()+".jpg"));
		}
		userService.updateUserByTempUser(tempUser);
		
		tempUserService.updateTempUser(tempUser);
		
		modelAndView.addObject("status", request.getParameter("status1"));   
		modelAndView.addObject("dataType", request.getParameter("dataType"));  
		modelAndView.addObject("type", request.getParameter("type"));   
		modelAndView.addObject("key", request.getParameter("key"));
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
		modelAndView.addObject("key", request.getParameter("key"));
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
		    String img_url="";
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
		        	if(i==tempUser.getImg_urlIndex() )
					{
						 int beginIndex="/collect_info/upload/".length(); 
						 String name1=name[1].substring(beginIndex);  
					     img_url=name1;
					}
		        }
	         
	        if(path.length()>0)
	       {
	        	path=path.substring(0, path.length()-1);
	        	tempUser.setOriginal_path(path);
	        	tempUser.setThumb_path(path);
	       }
	 		}
	        
	 
	 		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
			tempUser.setOpt_name(manager.getUser_name());
	 	
	     if(!"城乡居民养老保险".equals(tempUser.getData_type()))   
	        {  
	          String user_company=request.getParameter("user_company");
	          tempUser.setUser_township(user_company==null?"":user_company);
	        }
	        
	  
		tempUserService.insertTempUser(tempUser);
		
		//1.身份信息不存在:将采集人员的信息导入users表  ，并生成一条认证信息
		// User user= toUser( tempUser);
		
		String fileExtName=".jpg";
		//复制图片
		String idCardImgUploadSavePath=LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
		String collectInfoUploadSavePath=LoadProperties.loadProperties("common.properties", "collectInfoUploadSavePath");
		File file = new File(idCardImgUploadSavePath +tempUser.getUser_idcard()+".jpg");
		if(file.exists())
        	 file.delete();
        Path source = Paths.get (collectInfoUploadSavePath+img_url);//源文件
        Files.copy(source, new FileOutputStream(idCardImgUploadSavePath+tempUser.getUser_idcard()+fileExtName));
      
        userService.insertUserByTempUser(tempUser);
        
	    
        //2 将信息插入到认证表中
	       String basePath = DateFormatUtil.getCurrentDT().substring(0, 4)
					+ "//" + DateFormatUtil.getCurrentDT().substring(4, 6);

	       String filePath=LoadProperties.loadProperties("sysConfig.properties", "filePuth");
		   File dir = new File(filePath + basePath );
	        if (!dir.exists()) {
	             dir.mkdirs();
	         }
	         
	       
	         File videntImg = new File(filePath + basePath+"//"+tempUser.getUser_idcard()+".jpg");
	         if(videntImg.exists())
	        	 videntImg.delete();
	         Files.copy(source, new FileOutputStream(filePath + basePath+"//"+tempUser.getUser_idcard()+".jpg"));
	         
	    	VideoIdent videoIdent = new VideoIdent();
	    	List<User> users =userService.findUserByUserIdcard(tempUser.getUser_idcard());
	    	videoIdent.setUser_id(users.get(0).getId());
	    	 
			videoIdent.setImg_url("/upload/" + basePath + "/" + tempUser.getUser_idcard()+".jpg"); 
			videoIdent.setTxt_remarks("人工采集信息"); 
			videoIdent.setAdd_time(new Date());
			videoIdent.setVideo_status(2);
			videoIdent.setYear(DateFormatUtil.getCurrentDT().substring(0, 4));
						 
			videoIdentService.insertVL(videoIdent);
		
		
		modelAndView.addObject("status", request.getParameter("status1"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));
		modelAndView.addObject("key", request.getParameter("key"));
		modelAndView.setViewName("redirect:/tempUser/toTempUserList?type="+request.getParameter("type"));

		return modelAndView;
	}
	
	public User toUser(TempUser tempUser) {
		User user=new User();
		user.setUser_name(tempUser.getUser_name());
		user.setUser_idcard(tempUser.getUser_idcard());
		user.setUser_township(tempUser.getUser_township());
		user.setUser_village(tempUser.getUser_village());
		user.setData_type(tempUser.getData_type());
		user.setMobile(tempUser.getMobile());
		user.setImg_url("/upload/" +user.getUser_idcard()+".jpg");
		user.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
		user.setIs_delete(0); 

		return user;
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
		modelAndView.addObject("key", request.getParameter("key"));
		modelAndView.setViewName("redirect:/tempUser/toTempUserList");
		return modelAndView;
	}
	
	@RequestMapping(value = "/isExistUserIdcard")
	public  void isExistUserIdcard(String user_idcard,HttpServletRequest request,HttpServletResponse response) throws IOException {
		  
		List<TempUser> tempUsers = tempUserService.findTempUserByUserIdcard(user_idcard);	
		List<User> users = userService.findUserByUserIdcard(user_idcard);
		JSONObject object=new JSONObject(); 
		if(tempUsers.size()>0){		 
			object.put("status", "false"); 
		    object.put("msg", "用户身份信息已采集,请勿重复采集!"); 
		}
		else if(users.size()>0){		 
			object.put("status", "false"); 
		    object.put("msg", "用户身份信息已存在,请勿重复录入!"); 
		}
		else { 
			object.put("status", "true");   
		}
	 	response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString()); 
	  
		} 
	
	
	
	@RequestMapping(value = "/downExcel")
	 @RequiresPermissions("tempUser:Show")	
	public  void downExcel(HttpServletRequest request, HttpServletResponse response){ 
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		map.put("data_type", manager.getUser_type());
		map.put("status", request.getParameter("status"));
		map.put("dataType", request.getParameter("dataType")); 
		map.put("key", request.getParameter("key"));
		if(manager.getRole_type()==1)//超级用户显示所有的采集信息
		     map.put("data_type",null);
		else  //其他用户只显示各自的类别的采集信息
			map.put("data_type", manager.getUser_type());
		 
		if(manager.getRole_type()==1)//超级用户，显示所有
			map.put("user_township", null);
		else //非系统用户只能按照自己的管理权限筛选
			map.put("user_township", manager.getXzb());
		
		List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		String fileName="居民信息采集人员"+df.format(System.currentTimeMillis());
	     
		try {
			 // 设置请求
			response.reset();
			response.setContentType("application/csv;charset=GBK");
			response.setHeader("Content-Disposition","attachment;filename=" +URLEncoder.encode(fileName) + ".csv");
			response.setCharacterEncoding("GBK");
			PrintWriter out= response.getWriter();// 打开流
		
			out.println("姓名,身份证号码,类型,乡镇办,村名,手机号");
			for (int i = 0, length = tempUsers.size(); i < length; i++) {
			TempUser tempUser = tempUsers.get(i);
			//System.out.println(tempUser.toString());
			String str = tempUser.toString().replace("null", "");			
			out.println(str);
			}
		  } 
		catch (Exception e) {
			   e.printStackTrace();
			} 
		
		 //return response ;
	}
	
	@RequestMapping(value = "/downImgs")
	 @RequiresPermissions("tempUser:Show")	
	public  void downImgs(HttpServletRequest request, HttpServletResponse response)	   throws Exception {
	        try {
	            /**这个集合就是你想要打包的所有文件，
	             * 这里假设已经准备好了所要打包的文件
	             */
	             
	           List<File> files = new ArrayList<File>();
	        	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
	    		HashMap<String ,String > map=new HashMap<String ,String >(); 
	    		map.put("data_type", manager.getUser_type());
	    		map.put("status", request.getParameter("status"));
	    		map.put("dataType", request.getParameter("dataType")); 
	    		map.put("key", request.getParameter("key"));
	    		List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
	     
	    		
	    		String collectInfoUploadSavePath = LoadProperties.loadProperties("common.properties", "collectInfoUploadSavePath");
	    		for(int i=0;i<tempUsers.size();i++)
	    		{  
	    			if(tempUsers.get(i).getOriginal_path()!=null)
		    		{
		    			String[] filenames=tempUsers.get(i).getOriginal_path().split(";");
		    			
		    			for(int j=0;j<filenames.length;j++)
		    			{   
		    				String[] ss=filenames[j].split("\\/");
		    				if(ss.length>=5)
		    					files.add(new File(collectInfoUploadSavePath +ss[3]+"/"+ss[4] ));
		    			}
		    		}
	    		}
	            /**创建一个临时压缩文件，
	             * 我们会把文件流全部注入到这个文件中
	             * 这里的文件你可以自定义是.rar还是.zip
	　　                                    * 这里的file路径发布到生产环境时可以改为
	             */
	    		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
	            String zipFilePath=LoadProperties.loadProperties("common.properties", "tempFilePath");
	            
	            
	            
	            
			        File filePath = new File(zipFilePath);
	                if(!filePath.isDirectory()&&!filePath.exists()){
	                	filePath.mkdirs();
		            }
	               
	                File file = new File(zipFilePath+df.format(System.currentTimeMillis())+".zip");
	                if (!file.exists()){   
			             file.createNewFile();   
			         }
			         
			        
	                response.reset();
	                //response.getWriter()
	                //创建文件输出流
	                FileOutputStream fous = new FileOutputStream(file);   
	                /**打包的方法我们会用到ZipOutputStream这样一个输出流,
	                 * 所以这里我们把输出流转换一下*/
	    //            org.apache.tools.zip.ZipOutputStream zipOut 
	    //                = new org.apache.tools.zip.ZipOutputStream(fous);
	               ZipOutputStream zipOut    = new ZipOutputStream(fous);
	                /**这个方法接受的就是一个所要打包文件的集合，
	                 * 还有一个ZipOutputStream
	                 */
	               zipFile(files, zipOut);
	                zipOut.close();
	                fous.close();
	              
	                downloadZip(file,response);
	            }catch (Exception e) {
	                    e.printStackTrace();
	                }
	                /**直到文件的打包已经成功了，
	                 * 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
	                 * 稍后会呈现出来，接下来的就是往客户端写数据了
	                 */
	               // OutputStream out = response.getOutputStream();
	               
	         
	            
	        }
	    
	      /**
	         * 把接受的全部文件打成压缩包 
	         * @param List<File>;  
	         * @param org.apache.tools.zip.ZipOutputStream  
	         */
	        public static void zipFile  (List files,ZipOutputStream outputStream) {
	            int size = files.size();
	            for(int i = 0; i < size; i++) {
	                File file = (File) files.get(i);
	                zipFile(file, outputStream);
	            }
	        }
	    public static void downloadZip(File file,HttpServletResponse response) {
	        try {
	        // 以流的形式下载文件。
	        InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	        // 清空response
	        response.reset();

	        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");// response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
	        toClient.write(buffer);
	        toClient.flush();
	        toClient.close();
	        } catch (IOException ex) {
	        ex.printStackTrace();
	        }finally{
	             try {
	                    File f = new File(file.getPath());
	                    f.delete();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	             
	             file.delete();
	        }
	      
	    }

	    /**  
	         * 根据输入的文件与输出流对文件进行打包
	         * @param File
	         * @param org.apache.tools.zip.ZipOutputStream
	         */
	        public static void zipFile(File inputFile,	 ZipOutputStream ouputStream) {
	            try {
	                if(inputFile.exists()) {
	                    /**如果是目录的话这里是不采取操作的，
	                     * 至于目录的打包正在研究中
	                     */
	                    if (inputFile.isFile()) {
	                        FileInputStream IN = new FileInputStream(inputFile);
	                        BufferedInputStream bins = new BufferedInputStream(IN, 512);
	                        //org.apache.tools.zip.ZipEntry
	                        ZipEntry entry = new ZipEntry(inputFile.getName());
	                       // ZipEntry entry =new ZipEntry(System.currentTimeMillis() + "");
	                        ouputStream.putNextEntry(entry);
	                        // 向压缩文件中输出数据   
	                        int nNumber;
	                        byte[] buffer = new byte[512];
	                        while ((nNumber = bins.read(buffer)) != -1) {
	                            ouputStream.write(buffer, 0, nNumber);
	                        }
	                        // 关闭创建的流对象   
	                        bins.close();
	                        IN.close();
	                    } else {
	                        try {
	                            File[] files = inputFile.listFiles();
	                            for (int i = 0; i < files.length; i++) {
	                                zipFile(files[i], ouputStream);
	                            }
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	 
	        
	
		

		} 


