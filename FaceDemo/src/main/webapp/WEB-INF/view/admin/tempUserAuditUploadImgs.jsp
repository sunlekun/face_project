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
<title>居民信息采集</title>

<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />

<script type="text/javascript" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/admin/scripts/jquery/Validform_v5.3.2.js"></script>  
<script type="text/javascript" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" src="js/admin/scripts/webuploader/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>

  

 
</head>
<body class="mainbody">
<form method="post" action="tempUserAudit/uploadImgs" id="form1" enctype="multipart/form-data">
 
 <input type="hidden" name="flag" id="flag" value="1" />
 
<!--导航栏-->
<div class="location">
  <a href="tempUser/toTempUserList?status=${status}&type=${type}" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="tempUser/toTempUserList?type=${type}"><span>居民信息采集列表</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>居民信息采集</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">基本资料</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" >
  
 <input type="file" name="file_Import" id="file_Import" class="input normal upload-path" onchange="change(this) "  multiple="multiple" accept="image/*" />
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

</html>