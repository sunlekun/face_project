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
<title>后台管理中心</title>
<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery.nicescroll.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/layindex.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>

<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
    });
       $(function () { 

	$(".mainbody").Validform({
			beforeSubmit : function(curform) {
				setPwd();
				return false;
				//这里明确return false的话表单将不会提交;
			}
		});
	});
    function setPwd() {
    var user_name=encodeURI($("#user_name").val());
    var oldPassword=encodeURI($("#oldPassword").val());
    var newPassword=encodeURI($("#newPassword").val());
    var real_name=$("#real_name").val();
    var mobile = $("#mobile").val();
    var email = $("#email").val();
     
    $.ajax({
        type: "POST",
        url: "<%=path%>/manager/setPwd",
        data: { user_name: user_name, oldPassword: oldPassword, newPassword: newPassword,real_name:real_name, mobile:mobile,email:email},
        dataType: "json",
        success: function (data) {
            if (data.status == 'false') {
                alert(data.msg);
                return;
            }
            else {
                alert(data.msg);
                top.location = '<%=path%>/'+data.url;
                //window.location.href = 'login.html';
            }
        }
    }); 
}
</script>
</head>
<body class="mainbody">
<form method="post"  id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKMjAwMzg5NDk5Ng9kFgICAQ9kFgICAQ8PFgIeBFRleHQFBnN5c3RlbWRkZMTXDzns5QhuY3uuqcuwL4cIBEEfI7omj9FWdEUUacGw" />
</div>

<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="DF5726CA" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAAn5QVrXFeTFcgjE/sROD7n8Y3plgk0YBAefRz3MyBlTcHqUVpCuxdHCdO1vEppZaQt2NvjHOkq5wKoqN6Aim8WGaqjtp7DWFLVtVh+HfplH8u2Wcw0Rv1zag2iaW5ajyOsges75OxJ0l5JIe8rPDn1bQwdmH1m48FGJ7a8D8d+hEjzmltaUM7aEAN+g9cP/m138ES5hCQoLl/I8k6TRe3A9s2OLEWOpsLQ+ZrunXpwWzg==" />
</div>
<!--导航栏-->
<div class="location">
  <a href="manager/toManagerList" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="manager/toManagerList"><span>管理员</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>修改密码</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">修改密码</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
<%--   <dl>
    <dt>用户名</dt>
    <dd><input name="user_name" type="text" value="${manager.user_name }" readonly="readonly" id="user_name" class="input normal" /></dd>
  </dl>
  
  <dl>
    <dt>旧登录密码</dt>
    <dd><input name="oldPassword" type="password"  id="oldPassword" class="input normal" datatype="*6-20" nullmsg="请输入旧密码" errormsg="密码范围在6-20位之间" sucmsg=" " /> <span class="Validform_checktip">*</span></dd>
  </dl>
  <dl>
    <dt>新登录密码</dt>
    <dd><input name="newPassword" type="password" id="newPassword" class="input normal" datatype="*6-20" nullmsg="请输入新密码" errormsg="密码范围在6-20位之间" sucmsg=" " /> <span class="Validform_checktip">*</span></dd>
  </dl>
  <dl>
    <dt>新确认密码</dt>
    <dd><input name="newPassword1" type="password" id="newPassword1" class="input normal" datatype="*" recheck="newPassword" nullmsg="请再输入一次新密码" errormsg="两次输入的密码不一致" sucmsg=" " /> <span class="Validform_checktip">*</span></dd>
  </dl>
   --%>
 
   
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" />
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
  </div>
</div>
<!--/工具栏-->
</form>
</body>
</html>