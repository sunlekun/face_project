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
    <title>人脸识别</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-touch-fullscreen" content="YES">
	
	<script type="text/javascript" src="js/user/jquery.min.js"></script>
	<script type="text/javascript" src="js/user/sweetalert.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/user/sweetalert.css" />
	<link rel="stylesheet" type="text/css" href="css/user/index.css" />
    <link rel="stylesheet" type="text/css" href="css/user/main.css" />
    
    <script>
if (/Android (\d+\.\d+)/.test(navigator.userAgent)) {
	var version = parseFloat(RegExp.$1);
	if (version > 2.3) {
		var phoneScale = parseInt(window.screen.width) / 720;
		document.write('<meta name="viewport" content="width=720, minimum-scale = ' + phoneScale + ', maximum-scale = ' + phoneScale + ', target-densitydpi=device-dpi">');
	} else {
		document.write('<meta name="viewport" content="width=720, target-densitydpi=device-dpi">');
	}
} else {
	document.write('<meta name="viewport" content="width=720, user-scalable=no, target-densitydpi=device-dpi">');
}
</script>
<script type="text/javascript">
  
    //$(document).ready();这个是要在页面加载时执行的函数,document表示本页面  
 
    /* $(document).ready(function (){  
        alert("弹出一个对话框");  
    });  
     */
</script> 
 
  </head>
  <body>
<div class="top" >
	<div class="top_l"><a href="javascript:history.go(-1);"><img src="http://face.yzrszp.com:8080/sh_identity/images/home_f.png" alt=""/></a></div>
	<div class="top_c" style="-webkit-flex:1;text-align: center;color: #fff;font-size: 1.8em;line-height: 80px;">实名认证</div> 
</div>
<br><br><br>
<section class="indexTop">
   <img src="<%=path %>/images/${img}" width="200px" height="200px">      
    <p  style="font-size:4.0rem; ">${message }</p>  
    </br>
    <p style="font-size:3.5rem;color: gray;text-align:center;">服务提供：长葛市人社局</p>
</section> 

 
</body>
</html>
