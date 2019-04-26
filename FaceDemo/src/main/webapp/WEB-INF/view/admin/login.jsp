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
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>管理员登录</title>
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />
<script type="text/javascript" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
    $(function () {
        //检测IE
        if ('undefined' == typeof (document.body.style.maxHeight)) {
            window.location.href = 'ie6update.html';
        }
    });
</script>
</head>
<body class="loginbody">
<form method="post" action="login" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUJMTAyMjM3NzI1ZGSQt1W1QMo4jLxzcLcHibJoJ6QoAp/YeO780ci2DjHr5Q==" />
</div>

<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="28096282" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAATXOk5CIB03726NrSdNrY9sY3plgk0YBAefRz3MyBlTcHY2+Mc6SrnAqio3oCKbxYY85pbWlDO2hADfoPXD/5tdYrRaAzJD/17qtVoeDHccMBJbJrHtAIomSssTv2lRtyw=" />
</div>
<div style="width:100%; height:100%; min-width:300px; min-height:260px;"></div>
<div class="login-wrap">
  <div class="login-logo">LOGO</div>
  <div class="login-form">
    <div class="col">
      <input name="username" type="text"  id="username" class="login-input" placeholder="管理员账号" title="管理员账号" />
      <label class="icon" for="txtUserName"><i class="iconfont icon-user"></i></label>
    </div>
    <div class="col">
      <input name="password" type="password" id="password" class="login-input" placeholder="管理员密码" title="管理员密码" />
      <label class="icon" for="txtPassword"><i class="iconfont icon-key"></i></label>
    </div>
    <div class="col">
      <input type="submit" name="btnSubmit" value="登 录" id="btnSubmit" class="login-btn" />
    </div>
  </div>
  
  <c:if test="${!empty error }">
   <div class="login-tips"><i class="iconfont icon-info"></i><p id="msgtip">${error}</p></div>
  </c:if>
  <c:if test="${empty error }">
   <div class="login-tips"><i class="iconfont icon-info"></i><p id="msgtip">请输入用户名和密码</p></div>
  </c:if>
  
</div>
<div class="copy-right">
  <p>版权所有 禹州智慧人社身份核实系统 Copyright © 2017 - 2019 yzrszp.com Inc. All Rights Reserved.</p>
</div>
</form>
</body>
</html>

