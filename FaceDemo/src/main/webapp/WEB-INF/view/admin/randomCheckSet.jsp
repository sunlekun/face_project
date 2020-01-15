<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
<title>随机抽查分配</title>
  

<link href="js/admin/scripts/artdialog/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="css/admin/skin/icon/iconfont.css" rel="stylesheet" type="text/css" />
<link href="css/admin/skin/default/style.css" rel="stylesheet" type="text/css" />
<link href="css/admin/pagination.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>

 
 
<script type="text/javascript">
    function randomCheckSet() {
     
     var val=$('input:radio[name="rblRandom"]:checked').val();
     if(val==null||val=="") 
     {
         alert("请选择分配个数！");
         return;
     }
     else{ 
            $.ajax({
                type: "POST",
                url: "randomCheck/randomCheckSet", 
                dataType: "json",
                success: function (data) {
                    if (data.status == 'false') {
                        alert(data.msg);
                        return;
                    }
                    else { 
                   
                        window.location.href ="<%=path%>/"+data.url;                     
                    }
                } 
            });
       } 
    }

</script>
</head>
<body class="mainbody">
<form method="post"  action="randomCheck/randomCheckSet"  id="form1">
  
<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i class="iconfont icon-up"></i><span>返回上一页</span></a>
  <a href="manager/center" class="home"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>随机抽查分配</span>
</div>
<!--/导航栏-->

<!--工具栏-->

 <div id="toolbar" class="btn-group"> 
   <div class="toolbar"  >
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
          
        </ul>
         
          <div class="menu-list">
          <div class="rule-single-select">
            <select name="year" onchange="Search()" id="year">
	           <option value="">请选择抽查年限</option>
	           <c:forEach var="item" items="${years}">
                 <option  ${item==current?"selected='selected'":'' }   value="${item}">${item}</option>
              </c:forEach> 
	          
        </select>
        </div>
        </div>
       
        
   
      </div>
       
    </div>
  </div>
 </div>
<!--/工具栏-->


 <!--内容-->
<div id="floatHead" class="content-tab-wrap">
    <div class="content-tab">
        <div class="content-tab-ul-wrap">
            <ul>
                <li><a class="selected" href="javascript:;">随机抽查分配</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content">
  <dl>
    <dt>分配条数</dt>
    <dd>
      <div class="rule-multi-radio">
        <span id="rblRandom"><input id="rblRandom_0" type="radio" name="rblRandom" value="1" /><label for="rblRandom_0">随机分配300条认证信息</label></span>
      </div>
    </dd>
  </dl>
</div>
    <!--/内容-->

    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-wrap">
            <input type="button" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" onclick="randomCheckSet()" />
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript: history.back(-1);" />
            
        </div>
    </div>
    <!--/工具栏-->

 
 
</form>
</body>
</html>