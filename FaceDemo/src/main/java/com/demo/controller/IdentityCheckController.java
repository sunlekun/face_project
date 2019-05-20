package com.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.demo.model.Manager;
import com.demo.model.RandomCheck;
import com.demo.model.TempUser;
import com.demo.model.VideoIdent;
import com.demo.model.Xzb;
import com.demo.realm.PermissionName;
import com.demo.service.VideoIdentService; 
import com.demo.service.XzbService;
import com.demo.util.ExcelUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author lekun.sun
 * @version 创建时间：2019年5月16日 下午2:30:26
 * @ClassName 类名称：身份认证审核
 * @Description 类描述
 */
@Controller
@RequestMapping(value = "/identityCheck")
public class IdentityCheckController {
	public static Logger log= Logger.getLogger(IdentityCheckController.class);
	@Autowired
	private VideoIdentService videoIdentService;
	
	@Autowired
	private XzbService xzbService;
	
	@RequestMapping(value = "/identityCheckList")	
	public void identityCheckList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,Object > map=new HashMap<String ,Object >();
		
		 
		if(manager.getRole_type()==1)//超级用户显示所有的采集信息
		     map.put("data_type",null);
		else  //其他用户只显示各自的类别的采集信息
			map.put("data_type", manager.getUser_type());
		
		
		if(manager.getRole_type()==1)//超级用户，类别为城乡居民的，显示所有
			map.put("xzb", null);
		else //非系统用户只能按照自己的管理权限筛选
			map.put("xzb", manager.getXzb());
		
		//筛选条件
		map.put("user_township", request.getParameter("user_township")); 
		map.put("year", request.getParameter("year")); 
		map.put("video_status", request.getParameter("video_status"));
		
		List<VideoIdent> identityChecks = videoIdentService.findVideoListByMultiCondition(map);
		String jsons = JSON.toJSONString(identityChecks);

		JSONObject object = new JSONObject();

		object.put("status", "true");
		object.put("jsons", jsons);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
		 
		
	}
	
	
	@RequestMapping(value = "/toIdentityCheckList")	
	@RequiresPermissions("identityCheck:Show")
	@PermissionName("身份认证审核")
	public ModelAndView toIdentityCheckList(Integer pageSize,Integer pageNumber,String key, HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView modelAndView = new ModelAndView();   
		
		List<Xzb> xzbs= xzbService.findAllXzb();
		modelAndView.addObject("xzbs", xzbs);  
		
		List<String> years  = videoIdentService.findVidoIdentYears();	 
		modelAndView.addObject("years", years);   
		
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
			
			if(manager.getRole_type()==1)//超级用户，类别为城乡居民的，显示所有
				map.put("xzb", null);
			else //非系统用户只能按照自己的管理权限筛选
				map.put("xzb", manager.getXzb());
			
			//筛选条件
			map.put("user_township", request.getParameter("user_township")); 
			map.put("year", request.getParameter("year")); 
			map.put("video_status", request.getParameter("video_status"));
			
			List<VideoIdent> identityChecks = videoIdentService.findVideoListByMultiCondition(map);
			PageInfo<VideoIdent> page = new PageInfo<>(identityChecks);
			modelAndView.addObject("page", page); 
			modelAndView.addObject("key", key); 
            modelAndView.setViewName("admin/identityCheckListImg"); 
            
		}
		else
		{
			type="Word";
			modelAndView.setViewName("admin/identityCheckList"); 
		}
		
		
	 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("year", request.getParameter("year"));  
		modelAndView.addObject("type", type);   
 
	    return modelAndView;
	}
	
	
//	@RequestMapping(value = "/toIdentityCheckConfirm")
//	@RequiresPermissions("identityCheck:Confirm")
//	@PermissionName("抽查认证")
//	public ModelAndView toRandomCheckConfirm(int id,int video_id,HttpServletRequest request,HttpServletResponse response) {
//		ModelAndView modelAndView = new ModelAndView();
//		
////		RandomCheck randomCheck  = randomCheckService.findRandomCheckById(id);	
//		VideoIdent videoIdent=videoIdentService.findVideoIdentById(video_id);
//		
//		modelAndView.addObject("videoIdent", videoIdent); 
////		modelAndView.addObject("randomCheck", randomCheck); 
//		modelAndView.addObject("status", request.getParameter("status")); 
//		modelAndView.addObject("video_status", request.getParameter("video_status")); 
//		
//		modelAndView.addObject("videoIdent", videoIdent); 
//		modelAndView.setViewName("admin/randomCheckConfirm"); 
//		return modelAndView;
//	}
	
	
	@RequestMapping(value = "/identityCheckDelete")
	@RequiresPermissions("identityCheck:Delete")
	@PermissionName("认证删除")
	public ModelAndView identityCheckDelete(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String  idss=request.getParameter("ids");
		if(idss!=null&&idss.length()>0){
			String[] ids=idss.split(",");
			if(ids.length>=1)
				videoIdentService.deleteVideoIdentBatch(ids);
		}
		
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("year", request.getParameter("year"));  
		modelAndView.addObject("type", request.getParameter("type"));  
		modelAndView.setViewName("redirect:/identityCheck/toIdentityCheckList");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/downExcel")
	public  void downExcel(HttpServletRequest request, HttpServletResponse response){ 
		Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		HashMap<String ,Object > map=new HashMap<String ,Object >();
		
		map.put("video_status", request.getParameter("video_status"));
//		map.put("status", request.getParameter("status"));
	//	map.put("data_type", manager.getUser_type());
		
		
		if(manager.getRole_type()==1)//超级用户显示所有的采集信息
		     map.put("data_type",null);
		else  //其他用户只显示各自的类别的采集信息
			map.put("data_type", manager.getUser_type());
		
		
		if(manager.getRole_type()==1)//超级用户，类别为城乡居民的，显示所有
			map.put("xzb", null);
		else //非系统用户只能按照自己的管理权限筛选
			map.put("xzb", manager.getXzb());
		
		//筛选条件
		map.put("user_township", request.getParameter("user_township")); 
		map.put("year", request.getParameter("year")); 
		map.put("video_status", request.getParameter("video_status"));
		
		
		
		List<VideoIdent> identityChecks = videoIdentService.findVideoListByMultiCondition(map);
//		String jsons = JSON.toJSONString(identityChecks);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		String fileName="视频认证审核人员"+df.format(System.currentTimeMillis());
	     
		try {
			 // 设置请求
			/*response.setContentType("application/application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName + ".xlsx", "UTF-8"));*/
			
			response.setContentType("application/octet-stream");// response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileName + ".xls", "UTF-8"));
			
			String[] excelHeader = {"姓名","身份证号码","类型","乡镇办","村名","手机号","是否审核通过"};//此处为标题，excel首行的title，按照此格式即可，格式无需改动，但是可以增加或者减少项目。
			HSSFWorkbook wb=ExcelUtils.export2( fileName, excelHeader, identityChecks);//调用封装好的导出方法，具体方法在下面
			 
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
	
	
	
	@RequestMapping(value = "/identityCheckDetial")
	public ModelAndView toIdentityCheckDetial(int id,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		VideoIdent videoIdent=videoIdentService.findVideoListByVideoIdentId(id);
		
		modelAndView.addObject("videoIdent", videoIdent); 
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("year", request.getParameter("year"));  
		modelAndView.addObject("type", request.getParameter("type"));  
		
		modelAndView.setViewName("admin/identityCheckDetial"); 
		return modelAndView;
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/identityCheckConfirm")
	@RequiresPermissions("identityCheck:Audit")
	@PermissionName("认证审核")
	public ModelAndView toIdentityCheckConfirm(int id, HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		
		VideoIdent videoIdent=videoIdentService.findVideoListByVideoIdentId(id);
		
		modelAndView.addObject("videoIdent", videoIdent); 
//		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("year", request.getParameter("year"));  
		modelAndView.addObject("type", request.getParameter("type"));
		modelAndView.addObject("userid", videoIdent.getId()); 
		
		modelAndView.setViewName("admin/identityCheckConfirm");  
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/identityCheckEdit")
	public ModelAndView toIdentityCheckEdit(Integer id,String auditors_reason,String txt_remarks,Integer  video_status,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		videoIdentService.updateById(id,auditors_reason,txt_remarks,video_status);
		
		modelAndView.addObject("video_status", request.getParameter("video_status")); 
		modelAndView.addObject("user_township", request.getParameter("user_township"));  
		modelAndView.addObject("year", request.getParameter("year"));  
		modelAndView.addObject("type", request.getParameter("type"));  
		modelAndView.setViewName("admin/identityCheckList"); 
		
		return modelAndView;
	}
	
}
