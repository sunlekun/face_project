<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
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
<title>管理员详情</title>
  
<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />


<script type="text/javascript" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/admin/scripts/jquery/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script> 
 

<script type="text/javascript">
    $(function () {
        //初始化表单验证
         
        var lock='${managerDetial.is_lock}'; 
        if(lock!=0)   
           $("#cbIsLock").click(); 
           
         
    });
  </script>
</head>
<body class="mainbody">
<form method="post" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKMTgzMzgzMDA0NQ9kFgICAQ9kFgoCAQ8QZBAVDRLor7fpgInmi6nop5LoibIuLi4P6LaF57qn566h55CG5ZGYD+WIneWuoeeuoeeQhuWRmBLogYzku4vmiYDnrqHnkIblkZgV5L+h5oGv5Lit5b+D566h55CG5ZGYEuS6uuaJjeS6pOa1geS4reW/gxvmnLrlhbPkuovkuJrlhbvogIHkv53pmanlip4M5YaN5bCx5Lia5YqeCeWIm+S4muWKnhjkvIHkuJrlhbvogIHkv53pmankuK3lv4MM5Z+O5Lmh5bGF5L+dD+WMu+eWl+W3peS8pOiCoQznpL7kv53pl67nrZQVDQABMQEyATMBNAE1ATYBNwE4ATkCMTACMTECMTIUKwMNZ2dnZ2dnZ2dnZ2dnZxYBAgFkAgMPZBYCAgEPEGRkFgFmZAIHDw8WBB4EVGV4dAUGc3lzdGVtHghSZWFkT25seWdkZAIJDw9kFgIeBXZhbHVlBQcwfDB8MHwwZAILDw9kFgIfAgUHMHwwfDB8MGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFCGNiSXNMb2NrsrXUSnr4oOIPhLQuh+zivZGCbQtPpgdK4NAAG+0IdCc=" />
</div>

<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="32BA39F4" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdABbou4Uc11MfudSEIzC5sHwVJNc9N2sZRgHQr0c8oBmMvyWH/otKA12bY8M1IRpJFzJqLJF8libJlanDSMTb1QBbmeRr6sdoPfvU07H8WyTbFjME6pIn9WnRtxjmGK/dhhdHmpWCqiQHaqV6CesTm5TAUulrLCak5Mj7YLs4ipiyPUwUHLl9lNLtQFZJhMddCpnuvU/8TBMiEsmd9e00Xfatul9Ba2McEHeXc6ov9gFbtit3a54MB1KTd4//tzrZlu9MbKZjVrt5aqfu8ktKqyrsJ6z/cGp3iusvddiFtEsgt/odpoPrhsAQoKrju4CoqnBjemWCTRgEB59HPczIGVNwdjb4xzpKucCqKjegIpvFhmqo7aew1hS1bVYfh36ZR/LtlnMNEb9c2oNomluWo8jrIHrO+TsSdJeSSHvKzw59W0MHZh9ZuPBRie2vA/HfoRI85pbWlDO2hADfoPXD/5tdz+V9u0yQKDu+pu8777W4TJyTTILuCZ2OBljNWWpv7qM=" />
</div>
<!--导航栏-->
<div class="location">
  <a href="manager/toManagerList" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="manager/toManagerList"><span>管理员</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>管理员详情</span>
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
 
<c:choose>
<c:when test="${!empty managerDetial }">
  <dl>
    <dt>管理角色</dt>
    <dd>
    <input name="role_name" type="text" value="${managerDetial.role_name }" readonly="readonly" id="role_name" class="input normal" datatype="*1-18" sucmsg=" " />
    </dd>
  </dl>
   
  
  <dl>
    <dt>管理类型</dt>
    <dd>
      <div class="rule-multi-radio">
          <span id="rblType">
        
          <input id="rblType_0" type="radio" name="rblType" value="机关事业养老保险" ${managerDetial.user_type=='机关事业养老保险'?'checked=\'checked\'':'' } ><label for="rblType_0">机关事业养老保险</label>
          <input id="rblType_1" type="radio" name="rblType" value="城乡居民养老保险" ${managerDetial.user_type=='城乡居民养老保险'?'checked=\'checked\'':'' } ><label for="rblType_1">城乡居民养老保险</label>
          <input id="rblType_2" type="radio" name="rblType" value="企业职工养老保险" ${managerDetial.user_type=='企业职工养老保险'?'checked=\'checked\'':'' } ><label for="rblType_2">企业职工养老保险</label>
          </span>
      </div>
    </dd>
  </dl>
  
  <%-- <dl>
    <dt>所属乡镇办</dt>
    <dd>
    <input name="role_name" type="text" value="${managerDetial.xzb }" readonly="readonly" id="role_name" class="input normal" datatype="*1-18" sucmsg=" " />
    </dd>
  </dl> --%>
   <c:if test="${!empty managerDetial.xzb }"> 
  <dl>
    <dt>乡镇办</dt>
    <dd><input name="xzb" type="text" value="${managerDetial.xzb }"  readonly="readonly" id="xzb" class="input normal" /></dd>
  </dl>
  </c:if>
  
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
    <dd><input name="txtUserName" type="text" value="${managerDetial.user_name }" readonly="readonly" id="txtUserName" class="input normal" datatype="*1-18" sucmsg=" " /></dd>
  </dl> 
  <dl>
    <dt>登录密码</dt>
    <dd><input name="txtPassword" type="password" id="txtPassword"  readonly="readonly" class="input normal" datatype="*6-20" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value="0|0|0|0" /></dd>
  </dl>
   
  <dl>
    <dt>姓名</dt>
    <dd><input name="txtRealName" type="text" value="${managerDetial.real_name }" readonly="readonly" id="txtRealName" class="input normal" /></dd>
  </dl>
  
  <dl>
    <dt>电话</dt>
    <dd><input name="txtTelephone" type="text"  value="${managerDetial.mobile }" readonly="readonly"  value="13213375923"  readonly="readonly" id="txtTelephone" class="input normal" /></dd>
  </dl>
  
  <dl>
  <dt>创建时间</dt>
    <dd><input name="add_time" type="text" value="${managerDetial.add_time }" readonly="readonly" id="add_time" class="input normal"/></dd>
  </dl>
</c:when>
<c:otherwise >
  <dd>未查询到相关信息！</dd>
</c:otherwise>
</c:choose>

 
</div>
<!--/内容-->
 <!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
    <!-- <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" /> -->
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
  </div>
</div>
<!--/工具栏-->
</form>
</body>
</html>