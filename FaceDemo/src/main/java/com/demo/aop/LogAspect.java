package com.demo.aop;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
 
 
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger; 
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect; 
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; 
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.model.Log;
import com.demo.model.Manager; 
import com.demo.realm.PermissionName;
import com.demo.service.LogService;

@Aspect 
@Component 
public class LogAspect { 
	public static Logger log= Logger.getLogger(LogAspect.class);
	 
	@Autowired
	private LogService logService;	  //自己创建，用来保存日志信息
	
	/**
     * 管理员登录方法的切入点
     */
    @Pointcut("execution(* com.demo.service.impl.ManagerServiceImpl.findUserByUsername(..))")
    public void login(){}
	
	/**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.demo.controller..*.*Add(..))") //切点
	public void AddObject() {}
    
    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* com.demo.controller..*.*Delete(..))") //切点
	public void DeleteObject() {}
     
    
    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* com.demo.controller..*.*Edit(..))") //切点
	public void EditObject() {}
    
    /**
     * 下载文件方法切入点
     */
    @Pointcut("execution(* com.demo.controller..*.*down*(..))") //切点
	public void downObject() {}
    
    

  /*  @After(value = "login()")*/
    @AfterReturning(value = "login()", argNames = "joinPoint,object", returning = "object")
	public void doAfter(JoinPoint joinPoint, Object object){
    	
    	/*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request= attributes.getRequest();
    	 
    	Manager manager=(Manager)object;
         if (manager==null) {
             return;
         }
         if (joinPoint.getArgs() == null) {// 没有参数
             return;
         }

      
       
        String Ip = getIpAddress(request); //ip地址
        
        Log log = new Log();
     
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		
	    log.setUser_ip(Ip);
	    log.setAction_type("Login");
	    log.setRemark("用户登录");
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);*/
	    
	    
    	/*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request= attributes.getRequest();
    	 
        if (SecurityUtils.getSubject().getPrincipals() == null) {// 没有登录
            return;
        }
        Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class); 
       
        String Ip = getIpAddress(request); //ip地址
        
        Log log = new Log();
     
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		
	    log.setUser_ip(Ip);
	    log.setAction_type("Login");
	    log.setRemark("用户登录");
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);*/
	}
    
    
    /**
     * 添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     */
    @After(value = "AddObject()")
    public void addLog(JoinPoint joinPoint) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request= attributes.getRequest();
      
    	// 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
       
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 判断参数
        if (methodName.startsWith("to")) {// 跳转页面
        	
            return;
        }
      
       

        Log log = new Log();
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		String Ip = getIpAddress(request); //ip地址
	    log.setUser_ip(Ip);
	    log.setAction_type("Add");
	    
	   
	    
        String opContent = optionContent(joinPoint.getArgs(),"新增");
	    log.setRemark(opContent);
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);
    }
 

    /**
     * 删除操作
     *
     * @param joinPoint
     * @param object
     */
    @After(value = "DeleteObject()")
    public void deleteLog(JoinPoint joinPoint) throws Throwable {  
    	
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request= attributes.getRequest();
    	
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
       
        
        // 创建日志对象
        Log log = new Log();
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		String Ip = getIpAddress(request); //ip地址
	    log.setUser_ip(Ip);
	    log.setAction_type("Delete");
	  
        String opContent = optionContentDetele(joinPoint,request);
	    log.setRemark(opContent);
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);
    }
 

    /**
     * 管理员修改操作日志(后置通知)
     *
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @After(value = "EditObject()")
    public void editLog(JoinPoint joinPoint) throws Throwable {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request= attributes.getRequest();
    	// 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
       
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 判断参数
        if (methodName.startsWith("to")) {// 跳转页面 
            return;
        }
        
        Log log = new Log();
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		String Ip = getIpAddress(request); //ip地址
	    log.setUser_ip(Ip);
	    log.setAction_type("Edit"); 
        String opContent = optionContent(joinPoint.getArgs(),"修改");
	    log.setRemark(opContent);
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);
        
    }
 
    /**
     * 下载图片操作
     *
     * @param joinPoint
     * @param object
     */
    @After(value = "downObject()")
    public void downLog(JoinPoint joinPoint) throws Throwable {  
    	
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	HttpServletRequest request= attributes.getRequest();
    	
        
        
        // 创建日志对象
        Log log = new Log();
    	Manager	manager = SecurityUtils.getSubject().getPrincipals().oneByType(Manager.class);
		log.setUser_name(manager.getUser_name());
		log.setUser_id(manager.getId());
		String Ip = getIpAddress(request); //ip地址
	    log.setUser_ip(Ip);
	    log.setAction_type("Down");
	    String flag="图片";
	    // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        if(methodName.contains("Excel"))
        	flag="Excel";
	    String opContent = optionContentDown(joinPoint,flag);
	    log.setRemark(opContent);
	    log.setAdd_time(new Timestamp(new Date(System.currentTimeMillis()).getTime())); 
	 
	    logService.insertLog(log);
    }
    
     
    

	/**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent1(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型:" + className + "，值:");
            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                // 判断是不是get方法
                if (methodName.indexOf("get") == -1) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                } catch (Exception e) {
                    continue;
                }
                // 将值加入内容中
                rs.append("(" + methodName + ":" + rsValue + ")");
            }
            rs.append("]");
            index++;
        }
        return rs.toString();
    }
    /**
     * 使用Java反射来获取被拦截方法(insert,edit)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     * @throws IOException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public String optionContent(Object[] args, String flag) throws Exception {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(flag+" ");
        List<String>  allClass= getAllEntity();
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
           String className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
           
           if(allClass.contains(className)) 
           {   
        	    
        	    
        	   rs.append(getInfo(className)+"【");
        	   String[]  ms=getMethodInfo(className);
        	   for(String s:ms)
        	   {  
	        	  String[] ff=s.split(":");
	        	  if("新增".equals(flag)&&"getId".equals(ff[1])){
	        		  ;
	        	  }
	        	  else{
		        	  Method method=  info.getClass().getMethod(ff[1]); 
		        	  rs.append(ff[0]+"="+ method.invoke(info)+" ；");
		        	  }
        	   }
        	   if (ms.length>0) {
        		   rs.deleteCharAt(rs.length()-1);
				
			}
        	   rs.append("】");
           }
           
        }
        return rs.toString();
    }
    /**
     * 使用Java反射来获取被拦截方法(delete)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     * @throws IOException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public String optionContentDetele(JoinPoint joinPoint ,HttpServletRequest request) throws Exception {
    	 
        StringBuffer rs = new StringBuffer();  
        
        rs.append("删除"+" ");
      /*  if("".equals(getControllerMethodDescription(joinPoint)))
           rs.append(joinPoint.getSignature().getName());//获取方法名 
        else
    	   rs.append(getControllerMethodDescription(joinPoint));*/
        
        String targetName = joinPoint.getTarget().getClass().getName();  
        targetName = targetName.substring(targetName.lastIndexOf(".") + 1);
        
        rs.append(getInfo(targetName));
        
		String ids=request.getParameter("ids");
	    rs.append("【id:" + ids + "】"); 
        
        return rs.toString();
    }
    
    public String optionContentDown(JoinPoint joinPoint,String flag) throws Exception {
   	 
        StringBuffer rs = new StringBuffer();  
        
        rs.append("下载"+" ");
      /*  if("".equals(getControllerMethodDescription(joinPoint)))
           rs.append(joinPoint.getSignature().getName());//获取方法名 
        else
    	   rs.append(getControllerMethodDescription(joinPoint));*/
        
        String targetName = joinPoint.getTarget().getClass().getName();  
        targetName = targetName.substring(targetName.lastIndexOf(".") + 1);
        
        rs.append(getInfo(targetName));
        
        rs.append("【"+flag+"】");
        
        return rs.toString();
    }
 
	  /** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
     *  
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
     *  
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
     * 192.168.1.100 
     *  
     * 用户真实IP为： 192.168.1.110 
     *  
     * @param request 
     * @return 
     */  
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
      
    /**
	 * 获取方法的描述
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		 //获取目标类名  
        String targetName = joinPoint.getTarget().getClass().getName();  
        //获取方法名  
        String methodName = joinPoint.getSignature().getName();  
         
        //生成类对象  
        Class targetClass = Class.forName(targetName);  
        //获取该类中的方法  
        Method[] methods = targetClass.getMethods();   
       
        String description = methodName;  
          
        for(Method method : methods) {  
            if(method.getName().equals(methodName)) { 
            	if(method.getAnnotation(PermissionName.class)!=null)
            	       description = method.getAnnotation(PermissionName.class).value();  
            	 
            }
        }  
        return description;  
	}
  public List<String> getAllEntity() throws IOException{ 
	  List<String>  all=new ArrayList<String>(); 
      // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
	  String path=this.getClass().getResource("/").getPath();
      File f2 = new File(path+"//com//demo//model//");
     
      String[] filelist = f2.list();
      for(String s:filelist)
      {  /*
    	  System.out.println(s+"  ;  "+s.indexOf(".")+"   ;  ");
    	  String[] ss=s.split("\\.");*/
    	  if(s.indexOf(".")!=-1)
    		  all.add(s.split("\\.")[0]) ;
      }
    	  
	 return all;
	 
  }
	public static String[] getMethodInfo(String key){
		Map<String ,String[]> map= new HashMap<String ,String[]>();
		map.put("Role",  new String[]{"编号:getId","角色名:getRole_name"});
		map.put("Manager",  new String[]{"编号:getId","用户名:getUser_name"});
		map.put("Xzb", new String[]{"编号:getId","名称:getTitle"});	
		map.put("User",  new String[]{"编号:getIdd","用户名:getUser_name","身份证号:getUser_idcard"});
		map.put("TempUser",  new String[]{"编号:getId","用户名:getUser_name","身份证号:getUser_idcard","状态:getStatuss"});
		map.put("VideoIdent",  new String[]{"编号:getId","用户名:getUser_name","身份证号:getUser_idcard","状态:getVideo_statuss"}); 
		
		
		return map.get(key);
	} 
	 
	public static  String getInfo(String key){
		Map<String ,String> map= new HashMap<String ,String>();
		map.put("Role", "管理角色");
		map.put("Manager", "管理员");
		map.put("Xzb", "乡镇办");	
		map.put("User",  "用户信息");
		map.put("TempUser", "采集信息");
		map.put("VideoIdent","身份认证"); 
		map.put("RoleController", "管理角色");
		map.put("ManagerController", "管理员");
		map.put("XzbController", "乡镇办");	
		map.put("UserController",  "用户信息");
		map.put("TempUserController", "采集信息");
		map.put("TempUserAuditController", "采集信息审核");
		map.put("IdentityCheckController","身份认证"); 
		
		return map.get(key);
	}
	 
	 
}
