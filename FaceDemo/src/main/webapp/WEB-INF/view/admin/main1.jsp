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

 
<!-- <link rel="stylesheet" type="text/css" href="css/admin/nav.css"> 
<script type="text/javascript" src="js/admin/jquery.min.js"></script>
<script type="text/javascript" src="js/admin/nav.js"></script> -->
<script type="text/javascript">
    //页面加载完成时
    $(function () {
        //检测IE
        if ('undefined' == typeof (document.body.style.maxHeight)) {
            window.location.href = 'ie6update.html';
        }
    });
</script>
</head>
<body class="indexbody">

<form method="post" action="index.aspx" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUJLTU3NTIwMjQ3ZGRPjE4CX7F5+Nsof87UohqNzgOFalrP+9K1v5iT+bO8Zg==">
</div>

<script type="text/javascript">
//<![CDATA[
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
//]]>
//切换按钮显示事件
function clicks(index ){

var ind='div'+index;
 
$(ind).addClass("selected");
$(ind).show();

//var navObj = $("#main-nav");
}

</script>


<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="1B9537F6">
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAAJs59KIdbzbsfIHT/BsCBaz5cAl+B8NpP7aYmtMOUT8hcnyr+h1lpSI4imZBvDBiRymLPbITG/fKcw5nIHmZT7O">
</div>
  <!--全局菜单-->
  <a class="btn-paograms" href="javascript:;" onclick="togglePopMenu();">
    <i class="iconfont icon-list-fill"></i>
  </a>
  <div id="pop-menu" class="pop-menu">
    <div class="pop-box" style="width: 244px;">
      <h1 class="title"><i class="iconfont icon-setting"></i>导航菜单</h1>
      <i class="close iconfont icon-remove" onclick="togglePopMenu();"></i>
      <div class="list-box">
<c:forEach items="${allNav }" var="nav1" varStatus="status" >
<div class="list-group">
	<h1 title="${nav1.title }"><i class="${nav1.icon_url }"></i></h1>
	<div class="list-wrap">
		<h2>${nav1.title }<i></i></h2>
		<ul>
		   <c:forEach items="${nav1.children }" var="nav2">
				<li>
					<a navid="${nav2.name }" target="mainframe" class="nolink">
					<span>${nav2.title }</span>
					</a>
					<ul>
						<c:forEach items="${nav2.children }" var="nav3" >
							<li>
							<a navid="${nav3.name }" target="mainframe" class="link" href="${nav3.link_url }">
							<span>${nav3.title }</span>
							</a>
							</li>
 						</c:forEach>
					</ul>
				</li>
		  </c:forEach>
	</ul>
	</div>
</div>
</c:forEach>


</div>
    </div>
  </div>
  <!--/全局菜单-->

  <div class="main-top">
    <a class="icon-menu"><i class="iconfont icon-nav"></i></a>
    <div id="main-nav" class="main-nav" style="">
      <c:forEach items="${allNav }" var="nav1" varStatus="status1" >
        <a class="${status1.index==0?'selected':''}"  onclick="clicks('${status1.index}');"   >${nav1.title }</a>
    </c:forEach>
    </div>
    
    <div class="nav-right">
      <div class="info">
        
      </div>
      <div class="option">
        <i class="iconfont icon-arrow-down"></i>
        <div class="drop-wrap">
          <ul class="item">
            <li>
              <a href="../" target="_blank">预览网站</a>
            </li>
            <li>
              <a href="center.aspx" target="mainframe">管理中心</a>
            </li>
            <li>
              <a href="manager/manager_pwd.aspx" onclick="linkMenuTree(false, '');" target="mainframe">修改密码</a>
            </li>
            <li>
              <a id="lbtnExit" href="javascript:__doPostBack('lbtnExit','')">注销登录</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  
  
  
  <div class="main-left">
    <a href="center.aspx" target="mainframe"><h1 class="logo"></h1></a>
    <div id="sidebar-nav" class="sidebar-nav">
    
    <c:forEach items="${allNav }" var="nav1" varStatus="status1" >
    
    <div class="list-group ${status1.index==0?'selected':''}" style="display: ${status1.index==0?'block':'none'};" id="div_${status1.index}">
	<h1 title="${nav1.title }"><i class="${nav1.icon_url }"></i></h1>
	<div class="list-wrap">
		<h2>${nav1.title }<i></i></h2>
		<ul style="display: block;">
			<li>
				
				<ul style="display: block;">
				<c:forEach items="${nav1.children }" var="nav2">
				<li>
					<a navid="${nav2.name }" target="mainframe" >
						<i class="icon"></i><i class="icon iconfont icon-folder"></i><span>${nav2.title }</span>
						<b class="expandable iconfont  ${status1.index==0?'icon-open':'icon-close'}"></b>
					</a>
					<ul style="display: block;">
						<c:forEach items="${nav2.children }" var="nav3" varStatus="status2">
							<li>
							<a navid="${nav3.name }" href="${nav3.link_url }" target="mainframe">
							<i class="icon"></i><i class="icon"></i><i class="icon iconfont icon-file"></i><span>${nav3.title }</span>
							</a>
							</li>
					     </c:forEach>
					 
					</ul>
				</li>
				</c:forEach>
				
			   <!--  <li>
					<a navid="spot_check" target="mainframe">
						<i class="icon"></i><i class="icon iconfont icon-folder"></i><span>认证抽查</span>
						<b class="expandable iconfont icon-close"></b>
					</a>
					<ul style="display: none;">
						<li>
						<a navid="check_list" href="users/check_list.aspx" target="mainframe">
						<i class="icon"></i><i class="icon"></i><i class="icon iconfont icon-file"></i><span>抽查记录</span>
						</a>
						</li>
					 
					</ul>
			   </li> -->
			</ul>
		</li>
		</ul>
	</div>
  </div>
  </c:forEach>
  
 <!--  <div class="list-group selected" style="display: block;">
	<h1 title="控制面板"><i class="iconfont icon-setting-full"></i></h1>
	<div class="list-wrap">
		<h2>控制面板<i></i></h2>
		<ul style="display: block;">
			<li>
				<a navid="sys_manager" target="mainframe">
					<i class="icon iconfont icon-folder"></i><span>系统用户</span>
					<b class="expandable iconfont icon-open"></b>
				</a>
				<ul style="display: block;">
					<li>
					<a navid="manager_list" href="manager/manager_list.aspx" target="mainframe" class="">
					<i class="icon"></i><i class="icon iconfont icon-file"></i><span>管理员管理</span>
					</a>
					</li>
 
 
 
				</ul>
			</li>
		</ul>
	</div>
  </div> -->
  
</div>
    
  </div>
  <div class="main-container">
    <iframe id="mainframe" name="mainframe" frameborder="0" allowfullscreen="true" src="center.aspx"></iframe>
  </div>
</form>

</body>
</html>
