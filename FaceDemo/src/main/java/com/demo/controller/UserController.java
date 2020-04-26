package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
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
import com.demo.model.User;
import com.demo.model.VideoIdent;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.LogService;
import com.demo.service.UserService;
import com.demo.service.VideoIdentService;
import com.demo.service.XzbService;
import com.demo.util.ExportExcelUtils;
import com.demo.util.LoadProperties; 
import com.demo.util.POIExcelUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper; 

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
	
	@Autowired
	private LogService logService;
	
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
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/userList")	
	public void userList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		// request.setCharacterEncoding("utf-8"); 
		if(pageSize==null)
			pageSize=10;
		if(pageNumber==null)
			pageNumber =1;
		PageHelper.startPage(pageNumber, pageSize);
		
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		map.put("key", key);
		
		if(manager.getRole_type()==1)//超级用户，显示所有的。 
			map.put("data_type", null);
		else  //其他用户只显示各自的类别的用户信息
			map.put("data_type", manager.getUser_type());
	 
		
		if(manager.getRole_type()==1)//超级用户，类别为城乡居民的，显示所有
			map.put("xzb", null);
		else //非系统用户只能按照自己的管理权限筛选
			map.put("xzb", manager.getXzb());
		
		/* System.out.println("获取乡镇办："+ request.getParameter("user_township"));
		 System.out.println("获取乡镇办："+new String(request.getParameter("user_township").getBytes("ISO-8859-1"),"UTF-8"));
		 System.out.println("获取乡镇办："+new String(request.getParameter("user_township").getBytes("ISO-8859-1"),"ISO-8859-1"));
		 log.info("获取乡镇办："+new String(request.getParameter("user_township").getBytes("ISO-8859-1"),"gb2312"));*/
		map.put("user_township", request.getParameter("user_township"));
		  		
		map.put("isHasVideo", request.getParameter("isHasVideo"));
		
		map.put("dataType", request.getParameter("dataType"));
		
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
		Page<User> pageInfo=  userService.findAllUserByMultiCondition(map,pageNumber, pageSize); 
 		 
		
		String jsons = JSON.toJSONString(pageInfo.getResult());
		 
		JSONObject object = new JSONObject();
		object.put("total", pageInfo.getTotal()); 
		object.put("rows",jsons );   
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
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
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
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
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
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
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
		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		
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
	 	//	String fileExtName = oldFileName.substring(oldFileName.lastIndexOf("."));
	 		String fileExtName = ".jpg";
	        new File(idCardImgUploadSavePath+oldFileName).renameTo(new File(idCardImgUploadSavePath+user.getUser_idcard()+fileExtName));
	        user.setImg_url("/upload/" +user.getUser_idcard()+fileExtName);
     
	        if(!"城乡居民养老保险".equals(user.getData_type()))   
	        {  
	          String user_company=request.getParameter("user_company");
	       	  user.setUser_township(user_company==null?"":user_company);
	        }
	 
		userService.insertUser(user);

		modelAndView.addObject("isHasVideo", request.getParameter("isHasVideo"));   
		modelAndView.addObject("user_township", request.getParameter("user_township1"));  
		modelAndView.addObject("startTime", request.getParameter("startTime"));  
		modelAndView.addObject("endTime", request.getParameter("endTime"));  
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
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
	/*	String fileExtName = filename.substring(filename.lastIndexOf("."));*/
		String fileExtName=".jpg";
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
		modelAndView.addObject("dataType", request.getParameter("dataType")); 
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
	 
	@RequestMapping(value = "/importExcel")
	@PermissionName("居民信息Excel导入")
	public void importExcel(MultipartFile  file_Import,HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
        
       List<String> r=new ArrayList<String>();
       int success=0;
	   int error=0; 
	   if(file_Import.isEmpty()){   
	 		    r.add("文件读取失败,未导入成功,文件读取失败");
	   }
	   else{
			  List<User> users = new  ArrayList<User>();
			  List<String[]> list=  POIExcelUtil.readExcel(file_Import.getInputStream() ,file_Import.getOriginalFilename());
              String[] titles={"姓名","身份证号","乡镇办","村社区","数据类别","电话"};
			  List<String> title=Arrays.asList(titles);
			  boolean  flag=true;
			  if(list.size()>=1)
			 {
				  for(int i=0;i<list.get(0).length;i++)
				  {
					  if(!title.contains(list.get(0)[i]))
					  {
						  flag=false;
						  break;
					  }
					
				  }
			 }
			
			  if(!flag)
			  {    
				  r.add("文件读取失败,未导入成功,Excle文件表头不正确，表头只能是姓名、身份证号、乡镇办、村社区、数据类别、电话！");
		 	 }
			else 
	 		  {
				 for(int i=1;i<list.size();i++)
				  {
					  
					User user =toUser(list.get(0),list.get(i)); 
					List<User> olds=userService.findUserByUserIdcard(user.getUser_idcard());
		        	if(olds.size()>=1)
		        	{  
		        		r.add(user.getUser_idcard()+"\t,导入失败,已存在该身份信息已存在");
		            	error++;
		            }
		        	else{
		        		users.add(user);
			    		success++;
			    		r.add(user.getUser_idcard()+"\t,导入成功,");
		        	}
		        	 System.out.println(i);
				  }
	 				  
				   
				  userService.insertUserBatch(users); 
				 }
	 	 
	   }
		
	    // 创建日志对象
        Log log = new Log();
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		String Ip = LogAspect.getIpAddress(request); //ip地址
	    log.setUser_ip(Ip);
	    log.setAction_type("Import");
	     
	    String opContent = "导入"+" "+ LogAspect.getInfo("UserController")+"【成功="+success+" ；失败="+error+"】【用户信息】";
	   
	    log.setRemark(opContent);
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);
	     
	    //生成导入结果EXCel文件
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
	 	String fileName="用户信息导入结果"+df.format(System.currentTimeMillis());
        response.reset();
		response.setContentType("application/csv;charset=GBK"); 
		response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName) + ".csv");
		response.setCharacterEncoding("GBK");
		
		PrintWriter out = response.getWriter();
	    String  excelHeader = "文件名称,导入结果,备注";//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
		out.println(excelHeader);
		for (int i = 0; i < r.size(); i++)  
			out.println(r.get(i));
		 
		out.flush();
		out.close();
				
		}
	
   public	User  toUser(String[] titles ,String[] content){
	   User user =new User();
	   //姓名,身份证号,乡镇办,村社区,数据类别,电话
	   for (int j = 0; j < content.length; j++) 
		{    
   		    String title=titles[j];
   		    switch(title)
   		    {
   		       case "姓名":user.setUser_name(content[j]);break;
   		       case "身份证号":
   		    	   user.setUser_idcard(content[j]); 
   		    	   user.setImg_url("/upload/"+content[j]+".jpg");
   		           break;
   		       case "乡镇办":user.setUser_township(content[j]);break;
   		       case "村社区":user.setUser_village(content[j]);break;
   		       case "数据类别":user.setData_type(content[j]);break;
   		       case "电话":user.setMobile(content[j]);break;
   		    }
   		     
		}
	   user.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	   user.setIs_delete(0);
	   
	  return user;
	 }
} 
