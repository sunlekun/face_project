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



 <!--jquery库-->
    <script src="js/admin/bootstrap/nav/jquery.min.js"></script>
    <!--bootstrap库-->
    <link href="css/admin/bootstrap/nav/bootstrap.min.css" rel="stylesheet" />
    <script src="js/admin/bootstrap/nav/bootstrap/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
      <script src="js/bootstrap/html5shiv.min.js"></script>
      <script src="js/bootstrap/respond.min.js"></script>
    <![endif]-->
    <!--font-awesome字体库-->
    <link href="css/admin/bootstrap/nav/font-awesome.min.css" rel="stylesheet" />
    <!--页面加载进度条-->
    <link href="css/admin/bootstrap/nav/pace/dataurl.css" rel="stylesheet" />
    <script src="js/admin/bootstrap/nav/pace/pace.min.js"></script>
    <!--jquery.hammer手势插件-->
    <script src="js/admin/bootstrap/nav/jquery.hammer/hammer.min.js"></script>
    <script src="js/admin/bootstrap/nav/jquery.hammer/jquery.hammer.js"></script>
    <!--平滑滚动到顶部库-->
    <script src="js/jquery.scrolltopcontrol/scrolltopcontrol.js" type="text/javascript"></script>
    <!--主要写的jquery拓展方法-->
    <script src="js/admin/bootstrap/nav/jquery.extend.js" type="text/javascript"></script>
    <!--主要写的css代码-->
    <link href="css/admin/bootstrap/nav/default.css" rel="stylesheet" type="text/css" />
    <!--主要写的js代码-->
    <script src="js/admin/bootstrap/nav/default.js" type="text/javascript"></script>
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
  
  
 <%--  <div class="main-left">
    <a href="center.aspx" target="mainframe"><h1 class="logo"></h1></a>
    <div id="sidebar-nav" class="sidebar-nav">
 


<c:forEach items="${allNav }" var="nav1" varStatus="status" >
<div class="list-group selected" style="display: block;">
		<h1 title="${nav1.title }"><i class="iconfont icon-setting-full"></i></h1>
		<div class="list-wrap">
		<h2>${nav1.title }<i></i></h2>
		<ul style="display: block;">
		<li>
			<c:forEach items="${nav1.children }" var="nav2">
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
			</c:forEach>

		</li>
		</ul>
	</div>
</div>

</c:forEach>  


 
</div>
    
  </div> --%>
  <!-- <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle show pull-left" data-target="sidebar">
                    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Bootstrap</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="top1.html">top1.html</a></li>
                    <li><a href="top2.html">top2.html</a></li>
                    <li><a href="top3.html">top3.html</a></li>
                    <li><a href="https://github.com/shihao316558512/bootstrap" target="_blank"><i class="fa fa-download fa-fw"></i>&nbsp;原码下载</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-user fa-fw"></i>&nbsp;小王&nbsp;<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="top-right1.html">top-right1.html</a></li>
                            <li class="divider"></li>
                            <li><a href="top-right2.html">top-right2.html</a></li>
                            <li class="divider"></li>
                            <li><a href="top-right3.html"><i class="fa fa-sign-out fa-fw"></i>&nbsp;top-right3.html</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav> -->
    <div class="main-left">
  <div class="container-fluid all" style="margin-left: 220px;">
        <div class="sidebar" style="left: 0px;">
            <div class="sidebar-scroll">
                <ul class="nav">
                    <li class="active"><a href="index.html">主页面</a></li>
                    <li><a href="form.html">Form库</a></li>
                    <li><a href="message.html">Message库</a></li>
                    <li><a href="ui.html">UI库</a></li>
                    <li><a href="animate.html">Animate库</a></li>
                    <li><a href="carousel.html">Carousel库</a></li>
                    <li><a href="chart.html">Chart库</a></li>
                    <li class="has-sub">
                        <a href="javascript:void(0);"><span>导航选中演示</span><i class="fa fa-caret-right fa-fw pull-right"></i></a>
                        <ul class="sub-menu">
                            <li><a href="left1.html"><i class="fa fa-circle-o fa-fw"></i>&nbsp;left1</a></li>
                            <li><a href="left2.html"><i class="fa fa-circle-o fa-fw"></i>&nbsp;left2</a></li>
                            <li><a href="left3.html"><i class="fa fa-circle-o fa-fw"></i>&nbsp;left3及子页面</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="maincontent row">
            <!--我是主要内容 start-->
            <ul class="breadcrumb">
                <li class="active">首页</li>
            </ul>
            <div class="col-sm-12">
                <div class="jumbotron">
                    <h1>这是一套开源的基于bootstrap的简易框架</h1>
                    <p>所用插件和库几乎都属于开源（个别特殊的会做说明），且功能正在不间断更新中</p>
                </div>
                <ul class="list-group">
                    <li class="list-group-item active">
                        核心库（现在页面呈现效果必须依赖的库）
                    </li>
                    <li class="list-group-item">
                        jquery-1.12.4.js（<a href="https://jquery.com/" target="_blank">官网</a>）
                    </li>
                    <li class="list-group-item">
                        bootstrap3.3.6（<a href="https://getbootstrap.com/" target="_blank">官网</a>）
                    </li>
                    <li class="list-group-item">
                        font-awesome4.6.3（<a href="http://fontawesome.io/" target="_blank">官网</a>）
                    </li>
                    <li class="list-group-item">
                        <p>让页面返回顶部时平滑滚动（<a href="sample/scrolltop.html" target="_blank">样例</a>）</p>
                        <p>使用说明：页面需要引用scrolltopcontrol.js</p>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">2016.4.28</span>
                        <p>让bootstrap的carousel插件支持手势（<a href="sample/carousel.html" target="_blank">样例</a>）（<a href="https://github.com/hammerjs/jquery.hammer.js" target="_blank">jquery.hammer官网</a>）（<a href="http://hammerjs.github.io/" target="_blank">hammer.js官网</a>）</p>
                        <p>
                            使用说明：页面需要引用hammer.min.js、jquery.hammer.js
                        </p>
                        <p>注意：已将carousel的手势绑定事件封装到了default.js中，如果页面已经引用了default.js，就不用再给carousel写手势绑定事件了</p>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">2016.7.1</span>
                        <p>页面加载进度条（<a href="sample/pace.html" target="_blank">样例</a>）（<a href="http://github.hubspot.com/pace/docs/welcome/" target="_blank">官网</a>）</p>
                        <p>
                            使用说明：1.页面需要引用pace.min.js、dataurl.css <br>
                            2.官网提供多种样式供选择，只需要下载相应的css文件替换掉原来的css文件就行<br>
                            3.dataurl.css里面可以调整进度条位置
                        </p>
                    </li>
                </ul>
                <ul class="list-group">
                    <li class="list-group-item active">
                        更新日志
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Form库&nbsp;2016.4.19</span>
                        <a href="form.html?#form-DateRangePicker">加入bootstrap-DateRangePicker时间范围选择插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Message库&nbsp;2016.4.20</span>
                        <a href="message.html?#message-toastr">加入toastr通知插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Message库&nbsp;2016.4.20</span>
                        <a href="message.html?#message-sweetalert">加入bootstrap-sweetalert通知插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">UI库&nbsp;2016.4.20</span>
                        <a href="ui.html?#ui-metisMenu">加入metisMenu菜单样式特效库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">UI库&nbsp;2016.4.20</span>
                        <a href="ui.html?#ui-vide">加入vide.js把视频作为背景特效库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Form库&nbsp;2016.4.21</span>
                        <a href="form.html?#form-colorpicker">加入bootstrap-colorpicker颜色选择插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Animate库&nbsp;2016.4.21</span>
                        <a href="animate.html?#animate-wow">加入WOW特效库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Animate库&nbsp;2016.4.21</span>
                        <a href="animate.html?#animate-hover">加入Hover.css特效库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Animate库&nbsp;2016.4.21</span>
                        <a href="animate.html?#animate-animo">加入animo特效库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">UI库&nbsp;2016.4.22</span>
                        <a href="ui.html?#ui-switch">加入bootstrap-switch开关插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Form库&nbsp;2016.4.22</span>
                        <a href="form.html?#form-datepicker">加入bootstrap-datepicker日期选择插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Form库&nbsp;2016.4.28</span>
                        <a href="form.html?#form-datetimepicker">加入bootstrap-datetimepicker日期时间选择插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Form库&nbsp;2016.4.28</span>
                        <a href="form.html?#form-formhelper">加入bootstrap-formhelper插件集合库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">UI库&nbsp;2016.4.29</span>
                        <a href="ui.html?#ui-masonry">加入masonry流式布局插件&nbsp;<label class="label label-danger">强烈推荐</label></a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">UI库&nbsp;2016.5.4</span>
                        <a href="ui.html?#ui-tabdrop">加入bootstrap-tab超出自动折叠插件</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Carousel库&nbsp;2016.9.10</span>
                        <a href="carousel.html?#carousel-fotorama">加入jquery-fotorama图片滚动库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Carousel库&nbsp;2016.9.10</span>
                        <a href="carousel.html?#carousel-owlcarousel2">加入jquery-owlcarousel2图片滚动库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Carousel库&nbsp;2016.9.10</span>
                        <a href="carousel.html?#carousel-owlcarousel">加入jquery-owlcarousel图片滚动库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Carousel库&nbsp;2016.9.10</span>
                        <a href="carousel.html?#carousel-fullpage">加入jquery-fullpage图片滚动库</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">UI库&nbsp;2016.9.10</span>
                        <a href="ui.html?#ui-mmenu">加入jquery-mmenu Demo</a>
                    </li>
                    <li class="list-group-item">
                        <span class="badge">Chart库&nbsp;2016.9.10</span>
                        <a href="chart.html?chart-flot">加入jquery-flot库</a>
                    </li>
                </ul>
                <ul class="list-group">
                    <li class="list-group-item active">
                        代码示例
                    </li>
                    <li class="list-group-item">
                        <span class="badge">2016.6.24</span>
                        <a href="sample/iframe/parent.html" target="_blank">嵌套响应式iframe</a>
                    </li>
                </ul>
            </div>
            <!--我是主要内容 end-->
        </div>
    </div>
  
    </div>
    
  
  <div class="main-container">
    <iframe id="mainframe" name="mainframe" frameborder="0"  allowfullscreen="true" src="center.jsp"></iframe>
  </div>
</form>
</body>
</html>
