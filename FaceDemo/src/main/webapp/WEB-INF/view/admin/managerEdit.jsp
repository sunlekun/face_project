<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>编辑管理员</title>

<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />

<script type="text/javascript" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/admin/scripts/jquery/Validform_v5.3.2_min.js"></script>  
<script type="text/javascript" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" src="js/admin/scripts/webuploader/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
       
        var lock='${managerDetial.is_lock}';
        if(lock!=0)  
         { 
           $("#cbIsLock").click();;
           $("#is_lock").val("1");
         } 
    });
 
      $(function () {
       $(".mainbody").Validform({
		  	beforeSubmit : function(curform) {  
               
				return true;
				//这里明确return false的话表单将不会提交;
			}   
			
		});
	}); 
 
    
    function changes(obj){ 
      if('1'==obj)   
       {  
           $("#div_xzb").show();  
           $("#xzb").attr("datatype","*");
       }
      else 
       {  
          $("#div_xzb").hide();
          $("#xzb").removeAttr("datatype");  
        }
      
       
     }
</script>


</head>

<body class="mainbody" >
<form method="post" action="<%=path%>/manager/managerEdit" id="form1"> 
<input type="hidden" name="id" id="id" value="${managerDetial.id }" />

 

<!--导航栏-->
<div class="location">
  <a href="manager/toManagerList" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="manager/toManagerList"><span>管理员</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>管理员编辑</span>
</div>
<div class="line10"></div>
<!--/导航栏-->
<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">管理员信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
   <dl>
    <dt>管理角色</dt>
    <dd>
      <div class="rule-single-select">
        <select name="role_id"  id="role_id" onchange="changes(this)" datatype="*" errormsg="请选择管理员角色" sucmsg=" ">
        	
            <c:if test="${!empty roles }"> 
            <c:if test="${!empty managerDetial}"> 
            <option value=""  selected="selected">请选择角色...</option>
				<c:forEach items="${roles }" var="role">
				    <c:if test="${role.role_name!=managerDetial.role_name}">
					    <option value="${role.id} ${role.role_type}">${role.role_name }</option> 
				    </c:if>
			    	<c:if test="${role.role_name==managerDetial.role_name}">
				      <option value="${role.id} ${role.role_type}"  selected="selected" >${role.role_name }</option>
				   </c:if>
				</c:forEach>
			</c:if>
				 
		  <c:if test="${empty managerDetial}">
			   <option value=""  selected="selected">请选择角色...</option>
			   <c:forEach items="${roles }" var="role"> 
				    <option value="${role.id} ${role.role_type}">${role.role_name }</option>
				</c:forEach>
		  </c:if>
		  </c:if>
      </select>
      </div>
    </dd>
  </dl>
  
  <dl>
    <dt>管理类型</dt>
    <dd>
      <div class="rule-multi-radio">
          <span id="rblType">
        
          <input id="rblType_0" type="radio" name="user_type" value="机关事业养老保险" onclick="changes('0')" ${managerDetial.user_type=='机关事业养老保险'?'checked=\'checked\'':'' } ><label for="rblType_0">机关事业养老保险</label>
          <input id="rblType_1" type="radio" name="user_type" value="城乡居民养老保险" onclick="changes('1')" ${managerDetial.user_type=='城乡居民养老保险'?'checked=\'checked\'':'' } ><label for="rblType_1">城乡居民养老保险</label>
          <input id="rblType_2" type="radio" name="user_type" value="企业职工养老保险" onclick="changes('2')" ${managerDetial.user_type=='企业职工养老保险'?'checked=\'checked\'':'' } ><label for="rblType_2">企业职工养老保险</label>
          </span>
      </div>
    </dd>
  </dl>
  
  
    <dl id="div_xzb" style="display:${managerDetial.user_type=='城乡居民养老保险'?'display':'none'}">
    <dt>所属乡镇办</dt>
    <dd>
      <div class="rule-single-select">
        <select name="xzb" id="xzb"  errormsg="请选择所属乡镇办" sucmsg=" ">
	    <option selected="selected" value="">请选择乡镇办...</option>	   
	    <c:forEach items="${xzbs }" var="xzb">
			<c:if test="${xzb.title!=managerDetial.xzb}">
					    <option value="${xzb.title }">${xzb.title }</option>
			</c:if>
			<c:if test="${xzb.title==managerDetial.xzb}">
				    <option value="${xzb.title }"  selected="selected" >${xzb.title }</option>
			</c:if>
		</c:forEach>

      </select>
      </div>
    </dd>
  </dl>
  
  
  
 <dl>
    <dt>是否启用</dt>
    <dd>
      <div class="rule-single-checkbox">
          <input id="cbIsLock" type="checkbox" name="cbIsLock" checked="checked" />
      </div>
      <span class="Validform_checktip">*不启用则无法使用该账户登录</span>
    </dd>
  </dl>
    
 
  <dl>
    <dt>用户名</dt>
    <dd><input name="user_name" type="text" value="${managerDetial.user_name }" readonly="readonly" id="user_name" class="input normal" datatype="/^[a-zA-Z0-9\-\_]{2,50}$/" sucmsg=" " /> <span class="Validform_checktip">*字母、下划线，不可修改，注册后不可修改</span></dd>
  </dl> 
  
  <dl>
    <dt>登录密码</dt>
    <dd><input name="password" type="password" id="password" class="input normal" datatype="*6-20" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value="0|0|0|0" /> <span class="Validform_checktip">*</span></dd>
  </dl>
  <dl>
    <dt>确认密码</dt>
    <dd><input name="password1" type="password" id="password1" class="input normal" datatype="*" recheck="password" nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致" sucmsg=" " value="0|0|0|0" /> <span class="Validform_checktip">*</span></dd>
  </dl>
  <dl>
    <dt>姓名</dt>
    <dd><input name="real_name" type="text" value="${managerDetial.real_name }" id="real_name" class="input normal" /></dd>
  </dl>
  
  <dl>
    <dt>电话</dt>
    <dd><input name="mobile" type="text" value="${managerDetial.mobile }"id="mobile" class="input normal" maxlength="11" /></dd>
  </dl>
  
</div>
<!--/内容-->
<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
    <input type="submit"  name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" />
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
  </div>
</div>
<!--/工具栏-->
</form>
</body>
</html>