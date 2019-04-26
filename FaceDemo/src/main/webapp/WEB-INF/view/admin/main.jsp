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

<link rel="stylesheet" type="text/css" href="css/admin/nav.css"> 
<script type="text/javascript" src="js/admin/jquery.min.js"></script>
<script type="text/javascript" src="js/admin/nav.js"></script>
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
<form method="post" action="logout" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUJLTU3NTIwMjQ3ZGRPjE4CX7F5+Nsof87UohqNzgOFalrP+9K1v5iT+bO8Zg==" />
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
</script>


<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="1B9537F6" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAAJs59KIdbzbsfIHT/BsCBaz5cAl+B8NpP7aYmtMOUT8hcnyr+h1lpSI4imZBvDBiRymLPbITG/fKcw5nIHmZT7O" />
</div>
  <!--全局菜单-->
  <a class="btn-paograms" href="javascript:;" onclick="togglePopMenu();">
    <i class="iconfont icon-list-fill"></i>
  </a>
  <div id="pop-menu" class="pop-menu">
    <div class="pop-box">
      <h1 class="title"><i class="iconfont icon-setting"></i>导航菜单</h1>
      <i class="close iconfont icon-remove" onclick="togglePopMenu();"></i>
      <div class="list-box"></div>
    </div>
  </div>
  <!--/全局菜单-->

  <div class="main-top">
    <a class="icon-menu"><i class="iconfont icon-nav"></i></a>
   <!--  <div id="main-nav" class="main-nav">
    </div> -->
    
       <div id="main-nav" class="main-nav"> 
      
         <c:forEach items="${allNav }" var="nav1" varStatus="status" >
             <a class="selected" id="a_${status.index}" >${nav1.title}</a>
           
		  </c:forEach>
        
       
    </div>
    <div class="nav-right">
      <div class="info">
         <i></i>
        <span>
          
        </span>
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
              <a href="manager/toSetPwd" onclick="linkMenuTree(false, '');" target="mainframe">修改密码</a>
            </li>
            <li>
              <a id="lbtnExit" href="javascript:__doPostBack(&#39;lbtnExit&#39;,&#39;&#39;)">注销登录</a>
            </li>
            <%--  <li>
            <a href="<%=path %>/manager/logout">退出系统</a>
            </li> --%>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- <div class="main-left">
    <a href="center.jsp" target="mainframe"><h1 class="logo"></h1></a>
    <div id="sidebar-nav" class="sidebar-nav"></div>
    
  </div> -->
  
  
  <div class="main-left">
    <a href="center.aspx" target="mainframe"><h1 class="logo"></h1></a>
    <div id="sidebar-nav" class="sidebar-nav">
 


<c:forEach items="${allNav }" var="nav1" varStatus="status" >
<div class="list-group selected" style="display: block;">
		<h1 title="${nav1.title }"><i class="iconfont icon-setting-full"></i></h1>
		<div class="list-wrap">
		<h2>${nav1.title }<i></i></h2>
		<ul style="display: block;">
		<c:forEach items="${nav1.children }" var="nav2">
		<li>
			
				<a navid="${nav2.name }" target="mainframe">
				    <i class="icon iconfont icon-folder"></i><span>${nav2.title }</span><b class="expandable iconfont icon-open"></b>
				</a>
			<ul style="display: block;">

		    	<c:forEach items="${nav2.children }" var="nav3" >
				<li>
					<a navid="manager_list" href="${nav3.link_url }" target="mainframe" class="selected">
				    	<i class="icon"></i><i class="icon iconfont icon-file"></i><span>${nav3.title }</span>
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
  
  
<%--     <div class="main-left">
      
     
   <div class="nav">
	<div class="nav-top">
	     <a  class="logo" href="javascript:;" target="mainframe"></a>
		<div id="mini" style="border-bottom:1px solid rgba(255,255,255,.1)">
		    <img src="<%=path%>/images/mini.png"  style="width: 40px;height:40px;"> 
		</div>
	</div>
	 <a href="center.aspx" target="mainframe"><h1 class="logo"></h1></a>
    <div id="sidebar-nav" class="sidebar-nav">
 
	<ul> 
		 
          <c:forEach items="${allNav}" var="nav1"  varStatus="status"> 
          
           <li class="nav-item">
			<a href="javascript:;" id="b_$${status.index} "><i class="my-icon nav-icon icon_${status.index}"></i><span> ${nav1.title }</span><i class="my-icon nav-more"></i></a>
			<ul>
				<c:forEach items="${nav1.children }" var="nav2"> 
			      <li><a href="${nav2.link_url}"  target="mainframe"><span>${nav2.title}</span></a></li>
		        </c:forEach>
			</ul>
		  </li>
          
		  </c:forEach>
         
	
	</ul>
</div>
  </div> --%>
  
  <div class="main-container">
    <iframe id="mainframe" name="mainframe" frameborder="0"  allowfullscreen="true" src="center.jsp"></iframe>
  </div>
</form>
</body>
</html>
