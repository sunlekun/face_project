package com.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream; 
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
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
import com.demo.service.LogService;
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
@RequestMapping(value = "/tempUserAudit")
public class TempUserAuditController { 
//	private static Logger log =LoggerFactory.getLogger(UserController.class);
	public static Logger log= Logger.getLogger(TempUserAuditController.class);
	
	@Autowired
	private TempUserService tempUserService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private XzbService xzbService;
	@Autowired
	private LogService logService;
	
	@Autowired
	private VideoIdentService videoIdentService;
	
	@RequestMapping(value = "/toTempUserAuditList")
	@RequiresPermissions("tempUserAudit:Show")
	@PermissionName("居民信息采集审核")
	public ModelAndView toTempUserAuditList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request) {
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
			PageInfo<TempUser> pageInfo= page.toPageInfo();
			modelAndView.addObject("page", pageInfo); 
			modelAndView.addObject("key", key); 
            modelAndView.setViewName("admin/tempUserAuditListImg"); 
            
		}
		else
		{
			type="Word";
			modelAndView.setViewName("admin/tempUserAuditList"); 
		    }
		
		 
		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		modelAndView.addObject("status", request.getParameter("status"));   
		modelAndView.addObject("type", type);   
		return modelAndView;
	}
	
	@RequestMapping(value = "/tempUserAuditList")	
	@RequiresPermissions("tempUserAudit:Show")
	public void tempUserAuditList(Integer pageSize,Integer pageNumber,String key,HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		if(pageSize==null)
			pageSize=10;
		if(pageNumber==null)
			pageNumber =1;
		PageHelper.startPage(pageNumber, pageSize);
		
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
 		 
		String jsons = JSON.toJSONString(pageInfo.getResult());
		 
		JSONObject object = new JSONObject();
		object.put("total", pageInfo.getTotal()); 
		object.put("rows",jsons );   
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
	 
			
	}
	

	@RequestMapping(value = "/tempUserAuditDetial")
	@RequiresPermissions("tempUserAudit:View")
	@PermissionName("居民信息采集详情")
	public ModelAndView tempUserAuditDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		modelAndView.setViewName("admin/tempUserAuditDetial"); 
		return modelAndView;
	}
	
	@RequestMapping(value = "/toTempUserAuditEdit")
	@RequiresPermissions("tempUserAudit:Edit")
	@PermissionName("居民信息审核修改")
	public ModelAndView toTempUserAuditEdit(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;  
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		modelAndView.setViewName("admin/tempUserAuditEdit");
		
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserAuditEdit")
	public ModelAndView tempUserAuditEdit(TempUser tempUser ,HttpServletRequest request,HttpServletResponse response) throws IOException {
	
		ModelAndView modelAndView = new ModelAndView();
			
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		    
		if (hid_photo_names != null && hid_photo_names.length == 3)
		{
			String path = "";
			for (int i = 0; i < hid_photo_names.length; i++) {
				String[] name = hid_photo_names[i].split("\\|");
				/*
				 * if(name!=null&&name.length>=2) { String[]
				 * name1=name[1].split("\\/"); System.out.println(name1[3]);
				 * path+=name[1]+";"; }
				 */
				path += name[1] + ";";
			}

			if (path.length() > 0)
			{
				path = path.substring(0, path.length() - 1);
				tempUser.setOriginal_path(path);
				tempUser.setOriginal_path(path);
			}
		}
	 		
		TempUser old= tempUserService.findTempUserById(tempUser.getId()) ;  
		if(old.getStatus()!=tempUser.getStatus())
			tempUser.setAudit_time(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
		
		 if(!"城乡居民养老保险".equals(tempUser.getData_type()))   
	        {  
	          String user_company=request.getParameter("user_company");
	          tempUser.setUser_township(user_company==null?"":user_company);
	        }
		 
		tempUserService.updateTempUser(tempUser);

		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		modelAndView.setViewName("redirect:/tempUserAudit/toTempUserAuditList?type="+request.getParameter("type")+"&status="+request.getParameter("status1"));
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserAuditDelete")
	@RequiresPermissions("tempUserAudit:Delete")
	@PermissionName("采集信息审核")
	public ModelAndView tempUserAuditDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				tempUserService.deleteTempUserBatch(ids);
		}
		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		modelAndView.setViewName("redirect:/tempUserAudit/toTempUserAuditList?type="+request.getParameter("type")+"&status="+request.getParameter("status"));
		return modelAndView;
	}
	
	@RequestMapping(value = "/toTempUserAuditImportIdCardImg") 
	@PermissionName("居民信息图片导入")
	public ModelAndView toTempUserAuditImportIdCardImg(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		TempUser tempUser= tempUserService.findTempUserById(id) ;  
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs); 
		modelAndView.addObject("tempUser", tempUser); 
		
		String type=request.getParameter("type");
		modelAndView.addObject("type", type);  
		modelAndView.addObject("status", request.getParameter("status"));
		modelAndView.addObject("dataType", request.getParameter("dataType"));   
		modelAndView.setViewName("admin/tempUserAuditImportIdCardImg");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/tempUserAuditImportIdCardImg")
	public void  tempUserAuditImportIdCardImg(User user ,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		 String[]  hid_photo_names=request.getParameterValues("hid_photo_name");
		    
		 String idCardImgUploadSavePath = LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
		 String idcard=user.getUser_idcard();
		
		 JSONObject object = new JSONObject();
		 
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
	 	
	 	
		 
		 if(new File(idCardImgUploadSavePath+user.getUser_idcard()+fileExtName).exists()) 
			 new File(idCardImgUploadSavePath+user.getUser_idcard()+fileExtName).delete();
			 
		 new File(idCardImgUploadSavePath+oldFileName).renameTo(new File(idCardImgUploadSavePath+user.getUser_idcard()+fileExtName));
		 
	 
		 //将信息插入到users表中
		 
		 List<User> users =userService.findUserByUserIdcard(idcard);
	      if(users.size()>=1)
			{  
	    	  
	    	     
	            	object.put("msg", "已存在该身份证的照片，已替替换");
		        	object.put("status", "true");
	        	 
			}
	        else{
	        	user.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	        	user.setImg_url("/upload/" +user.getUser_idcard()+fileExtName);
	    		userService.insertUser(user);
            	object.put("msg", "导入成功");
	        	object.put("status", "true");
	        }
	        
	      
	      //2 将信息插入到认证表中
	       String basePath = DateFormatUtil.getCurrentDT().substring(0, 4)
					+ "//" + DateFormatUtil.getCurrentDT().substring(4, 6);

	        String filePath=LoadProperties.loadProperties("sysConfig.properties", "filePuth");
		 	 File dir = new File(filePath + basePath );
	         if (!dir.exists()) {
	             dir.mkdirs();
	         }
	         
	       // copyToOtherPath(idCardImgUploadSavePath,filePath + basePath,user.getUser_idcard(),fileExtName);
	         Path path = Paths.get (idCardImgUploadSavePath+user.getUser_idcard()+fileExtName);//源文件
	         Files.copy(path, new FileOutputStream(filePath + basePath+"//"+user.getUser_idcard()+".jpg"));
	         
	    	VideoIdent videoIdent = new VideoIdent();
			videoIdent.setUser_id(user.getId());
			videoIdent.setImg_url("/upload/" + basePath + "/" + user.getUser_idcard()+".jpg"); 
			videoIdent.setTxt_remarks("人工采集信息"); 
			videoIdent.setAdd_time(new Date());
			videoIdent.setVideo_status(2);
			videoIdent.setYear(DateFormatUtil.getCurrentDT().substring(0, 4));
						 
			videoIdentService.insertVL(videoIdent);
				 
	      
	      
	      object.put("url", "tempUserAudit/toTempUserAuditList");
	      object.put("status", "true"); 
		  response.setCharacterEncoding("utf-8");
		  response.getWriter().write(object.toString());
		  
		   
		  // 创建日志对象
	        Log log = new Log();
	    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
			log.setUser_name(manager.getUser_name());
			log.setUser_id(manager.getId());
			String Ip = LogAspect.getIpAddress(request); //ip地址
		    log.setUser_ip(Ip);
		    log.setAction_type("Import");
		     
		    String opContent = "导入"+" "+ LogAspect.getInfo("TempUserAuditController")+"【用户名="+user.getUser_name()+" ；身份证号="+user.getUser_idcard()+"】【图片】";
		   
		    log.setRemark(opContent);
		    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
		 
		     logService.insertLog(log);
	     
 
	}
	 
	@RequestMapping(value = "/tempUserAuditImportIdCardImgs")
	@PermissionName("居民信息身份证图片上传")
	public void tempUserAuditImportIdCardImgs(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object=new JSONObject(); 
		String idCardImgUploadSavePath = LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
		int success=0;
		int error=0;
		try {
		MultipartHttpServletRequest mul=(MultipartHttpServletRequest)request;  
	  
	    
	    List<MultipartFile> filelist = mul.getFiles("file_Import");//图片列表
       System.out.println(filelist.size());
	    List<List<String>> re=new ArrayList<List<String>>();
	    for(MultipartFile file:filelist)
	    {
	 
	    List<String> r=new ArrayList<String>();
		// 得到上传的文件名称，
		String filename = file.getOriginalFilename(); 
		String idcard=filename.substring(0, filename.indexOf(".")) ;
		
		
		String type=filename.indexOf(".")!=-1?filename.substring(filename.lastIndexOf(".")+1, filename.length()):null;
		if (type!=null&&("PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())))
		{  
			List<TempUser> tempUsers =tempUserService.findTempUserByUserIdcard(idcard);
	        if(tempUsers.size()>=1)
			{
	        	List<User> olds=userService.findUserByUserIdcard(idcard);
	        	if(olds.size()>=1)
	        	{
	            	 
	        		 
	        		//重命名文件
	        		filename = idcard+ filename.substring(filename.lastIndexOf(".")); 
	        		file.transferTo(new File(idCardImgUploadSavePath + filename)); 
	        		success++;
	        		r.add(filename);
	            	r.add("替换成功");
	            	r.add("已存在该身份证的照片，已替换该");
	            }
	        	else{
		        	User user =new User();
		        	user.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
		    		user.setData_type(tempUsers.get(0).getData_type());
		    		user.setMobile(tempUsers.get(0).getMobile());
		    		user.setUser_idcard(tempUsers.get(0).getUser_idcard());
		    		user.setUser_name(tempUsers.get(0).getUser_name());
		    		user.setUser_township(tempUsers.get(0).getUser_township());
		    		user.setUser_village(tempUsers.get(0).getUser_village());
		    		 
		    		//重命名文件
		    		filename = idcard+ filename.substring(filename.lastIndexOf(".")); 
		    		file.transferTo(new File(idCardImgUploadSavePath + filename));
		    		user.setImg_url("/upload/"+filename);
		    		userService.insertUser(user);
		    		success++;
		    		r.add(filename);
		        	r.add("导入成功");
		        	r.add("");
	        	}
	    		
			}
	        else{
	        	error++;
	        	r.add(filename);
	        	r.add("未导入成功");
	        	r.add("没有该身份信息的信息");
	        }
		 }
		else
		{   error++;
			r.add(filename);
			r.add("未导入成功");
        	r.add("非文件格式");  
		
		}
		
		re.add(r);
	    
	    }
	   
	    
	    // 创建日志对象
        Log log = new Log();
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		String Ip = LogAspect.getIpAddress(request); //ip地址
	    log.setUser_ip(Ip);
	    log.setAction_type("Import");
	     
	    String opContent = "导入"+" "+ LogAspect.getInfo("TempUserAuditController")+"【成功="+success+" ；失败="+error+"】【图片】";
	   
	    log.setRemark(opContent);
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	     logService.insertLog(log);
	     
	    //生成导入结果EXCel文件
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
 		String fileName="图片信息导入结果"+df.format(System.currentTimeMillis());
	    String[] excelHeader = {"文件名称","导入结果","备注"};//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
		 
		ExportExcelUtils ee = new ExportExcelUtils("表格标题", Arrays.asList(excelHeader));

		for (int i = 0; i < re.size(); i++) {
			Row row = ee.addRow();
			for (int j = 0; j < re.get(i).size(); j++) {
				ee.addCell(row, j, re.get(i).get(j));
			}
		}
		String zipFilePath=LoadProperties.loadProperties("common.properties", "tempFilePath");
		ee.writeFile(zipFilePath+fileName + ".xlsx");
		ee.dispose();
		
	
		object.put("fileName",fileName + ".xlsx"); 
		 
		
		response.setCharacterEncoding("utf-8"); 
		response.getWriter().write(object.toString());
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		
		
		}
	@RequestMapping(value = "/uploadImgsResultExcel")	 
	public  void uploadImgsResultExcel(HttpServletRequest request, HttpServletResponse response){ 
	
	   String fileName = request.getParameter("fileName");
	   String zipFilePath=LoadProperties.loadProperties("common.properties", "tempFilePath");
       
       try {
 	           response.setContentType("application/octet-stream");// response.setContentType("application/octet-stream");
 	           response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileName, "UTF-8"));
 	   			 
 	            // 1.弹出下载框，并处理中文
 	            /** 如果是从jsp页面传过来的话，就要进行中文处理，在这里action里面产生的直接可以用
 	             * String filename = request.getParameter("filename");
 	             */
 	            /**
 	             if (request.getMethod().equalsIgnoreCase("GET")) {
 	             filename = new String(filename.getBytes("iso8859-1"), "utf-8");
 	             }
 	             */ 
 	            response.addHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));

 	            // 2.下载
 	            OutputStream out = response.getOutputStream();
 	           
 	            // inputStream：读文件，前提是这个文件必须存在，要不就会报错
 	            InputStream is = new FileInputStream(zipFilePath+fileName );

 	            byte[] b = new byte[4096];
 	            int size = is.read(b);
 	            while (size > 0) {
 	                out.write(b, 0, size);
 	                size = is.read(b);
 	            }
 	            out.close();
 	            is.close();
 	        } catch (Exception e) {
 	            e.printStackTrace();
 	        }
       new File(zipFilePath+fileName ).delete();
        new File(zipFilePath+fileName ).deleteOnExit(); 
		 //return response ;
	}
	
	
	@RequestMapping(value = "/downExcel")
	 @RequiresPermissions("tempUserAudit:Show")	
	public  void downExcel(HttpServletRequest request, HttpServletResponse response){ 
     	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
 		HashMap<String ,String > map=new HashMap<String ,String >(); 
 		map.put("data_type", manager.getUser_type());
 		map.put("status", request.getParameter("status"));
 		map.put("dataType", request.getParameter("dataType")); 
 		if(manager.getRole_type()==1)//超级用户显示所有的采集信息
		     map.put("data_type",null);
		else  //其他用户只显示各自的类别的采集信息
			map.put("data_type", manager.getUser_type());
		 
		
 		List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
 		
 		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
 		String fileName="居民信息采集人员"+df.format(System.currentTimeMillis());
 	     
		try {
			 // 设置请求
 			/*response.setContentType("application/application/vnd.ms-excel");
 			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName + ".xlsx", "UTF-8"));*/
 			
 			response.setContentType("application/octet-stream");// response.setContentType("application/octet-stream");
 	        response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileName + ".xls", "UTF-8"));
			
			String[] excelHeader = {"姓名","身份证号码","类型","乡镇办","村名","手机号","是否审核通过"};//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
			HSSFWorkbook wb=ExcelUtils.export( fileName, excelHeader, tempUsers);//调用封装好的导出方法，具体方法在下面
			 
			OutputStream outputStream = response.getOutputStream();// 打开流
			wb.write(outputStream);// HSSFWorkbook写入流
			wb.close();// HSSFWorkbook关闭
			outputStream.flush();// 刷新流
			outputStream.close();// 关闭流	 
			 } catch (Exception e) {
			   e.printStackTrace();
			}
		
		 //return response ;
	}
	@RequestMapping(value = "/downImgs")
	 @RequiresPermissions("tempUserAudit:Show")	
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