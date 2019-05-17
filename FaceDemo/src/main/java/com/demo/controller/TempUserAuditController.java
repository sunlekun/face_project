package com.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
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

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.demo.model.Manager; 
import com.demo.model.TempUser;
import com.demo.model.User;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.TempUserService;
import com.demo.service.UserService;
import com.demo.service.XzbService;
import com.demo.util.ExcelUtils;
import com.demo.util.ExportExcelUtils;
import com.demo.util.LoadProperties;
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
			List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
			PageInfo<TempUser> page = new PageInfo<>(tempUsers);
			modelAndView.addObject("page", page); 
			modelAndView.addObject("key", key); 
            modelAndView.setViewName("admin/tempUserAuditListImg"); 
            
		}
		else
		{
			type="Word";
			modelAndView.setViewName("admin/tempUserAuditList"); 
		    }
	 
		modelAndView.addObject("status", request.getParameter("status"));   
		modelAndView.addObject("type", type);   
		return modelAndView;
	}
	
	@RequestMapping(value = "/tempUserAuditList")	
	@RequiresPermissions("tempUserAudit:Show")
	public void tempUserAuditList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,String > map=new HashMap<String ,String >(); 
		if(manager.getRole_type()==1)//超级用户显示所有的采集信息
		     map.put("data_type",null);
		else  //其他用户只显示各自的类别的采集信息
			map.put("data_type", manager.getUser_type());
		map.put("user_township", manager.getXzb());
		map.put("status", request.getParameter("status"));
		List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
 		 
		String jsons = JSON.toJSONString(tempUsers); 
		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
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
		tempUserService.updateTempUser(tempUser);

	
		modelAndView.setViewName("redirect:/tempUserAudit/toTempUserAuditList?type="+request.getParameter("type")+"&status="+request.getParameter("status1"));
		return modelAndView;
	}
	@RequestMapping(value = "/tempUserAuditDelete")
	@RequiresPermissions("tempUserAudit:Delete")
	@PermissionName("居民信息采集审核删除")
	public ModelAndView tempUserAuditDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				tempUserService.deleteTempUserBatch(ids);
		}
		
		modelAndView.setViewName("redirect:/tempUserAudit/toTempUserAuditList?type="+request.getParameter("type")+"&status="+request.getParameter("status"));
		return modelAndView;
	}
	@RequestMapping(value = "/uploadImgs")
	@PermissionName("居民信息身份证图片上传")
	public void uploadImgs(HttpServletRequest request, HttpServletResponse response) {
		JSONObject object=new JSONObject(); 
		String idCardImgUploadSavePath = LoadProperties.loadProperties("common.properties", "idCardImgUploadSavePath");
		try {
		MultipartHttpServletRequest mul=(MultipartHttpServletRequest)request;  
	    Map<String,MultipartFile> files=mul.getFileMap();
	    
	    List<List<String>> re=new ArrayList<List<String>>();
	    for(MultipartFile file:files.values())
	    {
	 
	    List<String> r=new ArrayList<String>();
		// 得到上传的文件名称，
		String filename = file.getOriginalFilename(); 
		String idcard=filename.substring(0, filename.indexOf(".")) ;
		
		 
		List<TempUser> tempUsers =tempUserService.findTempUserByUserIdcard(idcard);
        if(tempUsers.size()>=1)
		{
        	List<User> olds=userService.findUserByUserIdcard(idcard);
        	if(olds.size()>=1)
        	{
            	 
        		 
        		//重命名文件
        		filename = idcard+ filename.substring(filename.lastIndexOf(".")); 
        		file.transferTo(new File(idCardImgUploadSavePath + filename)); 
        		
        		r.add(idcard);
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
	    		
	    		r.add(idcard);
	        	r.add("导入成功");
	        	r.add("");
        	}
    		
		}
        else{
        	r.add(idcard);
        	r.add("未导入成功");
        	r.add("没有该身份信息的信息");
        }
		
		re.add(r);
	    
	    }
	   
	    //生成导入结果EXCel文件
	    
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
 		String fileName="图片信息导入结果"+df.format(System.currentTimeMillis());
	    String[] excelHeader = {"身份证号码","导入结果","备注"};//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
		 
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
	@RequestMapping(value = "/downLoadFiles")
	 @RequiresPermissions("tempUserAudit:Show")	
	public  void downLoadFiles(HttpServletRequest request, HttpServletResponse response)	   throws Exception {
	        try {
	            /**这个集合就是你想要打包的所有文件，
	             * 这里假设已经准备好了所要打包的文件
	             */
	             
	           List<File> files = new ArrayList<File>();
	        	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
	    		HashMap<String ,String > map=new HashMap<String ,String >(); 
	    		map.put("data_type", manager.getUser_type());
	    		map.put("status", request.getParameter("status"));
	    		List<TempUser> tempUsers=  tempUserService.findAllTempUserByMultiCondition(map); 
	     
	    		
	    		String collectInfoUploadSavePath = LoadProperties.loadProperties("common.properties", "collectInfoUploadSavePath");
	    		for(int i=0;i<tempUsers.size();i++)
	    		{  
	    			if(tempUsers.get(i).getOriginal_path()!=null)
		    		{
		    			String[] filenames=tempUsers.get(i).getOriginal_path().split(";");
		    			for(int j=0;j<filenames.length;j++)
		    			    files.add(new File(collectInfoUploadSavePath +filenames[j].split("\\/")[3] ));
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