<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>身份验证系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-touch-fullscreen" content="YES">
	
	<script type="text/javascript" src="js/user/jquery.min.js"></script>
	<script type="text/javascript" src="js/user/sweetalert.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/user/sweetalert.css" />
	<link rel="stylesheet" type="text/css" href="css/user/index.css" />

<script type="text/javascript">
$(function () {

	if(localStorage.user_idcard!=undefined){
	/* 	$("#username").val(localStorage.username); */
		$("#user_idcard").val(localStorage.user_idcard);
	/* 	$("#mobile").val(localStorage.mobile); */
	}
});

function renzheng(){
	/* var username = $.trim($("#username").val()); */
	var user_idcard = $.trim($("#user_idcard").val());
/* 	var mobile = $.trim($("#mobile").val()); 
	if(username==""||user_idcard==""||mobile==""){
		swal("内容填写不完整。");
		return;
	}*/
	if(user_idcard==""){
		swal("内容填写不完整。");
		return;
	}
/* 	localStorage.username=username; */
	localStorage.user_idcard=user_idcard;
	/* localStorage.mobile=mobile; */
// 	var data_ = {status:status,msg:msg};
	$.ajax({
		type : "POST",
		url : "faceDetectAuth/reqFaceDetectAuth.do",
		data : {user_idcard : user_idcard
			/* username : username,
			user_idcard : user_idcard,
			mobile : mobile */
		},
		datatype:"json",
		success : function(resp) {
			var s = resp["status"];
			if("error"==s){
				alert(resp["msg"]);
			}else if("sucess"==s){
				window.location.href=resp["msg"]; 
			}
		},
		
	});
}
window.alert = function(name){
    var iframe = document.createElement("IFRAME");
    iframe.style.display="none";
    iframe.setAttribute("src", 'data:text/plain,');
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
}

</script>
  </head>
  <body>
<div class="top">
	<div class="top_l"><a href="javascript:history.go(-1);"><img src="http://face.yzrszp.com:8080/sh_identity/images/home_f.png" alt=""/></a></div>
	<div class="top_c">实名认证</div>
<!--	<div class="top_r">退出不拍了</div>-->
</div>
<div class="bannerpic">
	<div class="bannerpic_pic"><img width="100%" alt=""/></div>
	
</div>
<div class="sminput">
	<!-- <div class="sminput_xm">
     	<div class="sminput_xm01">真实姓名：</div>
	    <div class="sminput_xm02"><input  name="username" id="username" type="text"></div>
	</div> -->
	<div class="sminput_sfz">
        <div class="sminput_sfz01">身份证号：</div>
	    <div class="sminput_sfz02"><input name="user_idcard" id="user_idcard" type="text"></div>
	</div>
	<!-- <div class="sminput_sfz">
        <div class="sminput_sfz01">手机号码：</div>
	    <div class="sminput_sfz02"><input name="mobile" id="mobile" type="text"></div>
	</div> -->
	<div class="tiaok">
	     <img src="http://face.yzrszp.com:8080/sh_identity/images/ico1.png" alt=""/>同意
  	     <a href = "JavaScript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">《我已认真阅读并同意全部协议》</a>
	     <div id="light" class="white_content">
	         <p>ceshi</p>
          <a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">我知道了</a></div> 
          <div id="fade" class="black_overlay"></div> 
     </div>
     <div class="tijiaobt">
     	<input type="button" name="" value="立即认证" onclick="renzheng()">
     </div>
</div>
</body>
</html>
