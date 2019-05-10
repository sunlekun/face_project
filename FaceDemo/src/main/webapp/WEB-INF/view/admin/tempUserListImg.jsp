<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>居民信息采集列表</title>
  
<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />
<link rel="stylesheet" type="text/css" href="css/admin/pagination.css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="js/admin/scripts/jquery/jquery.lazyload.min.js"></script>

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/layindex.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>
 
 <script type="text/javascript">
        $(function () {
            //图片延迟加载
            $(".pic img").lazyload({ effect: "fadeIn" });
            //点击图片链接
            $(".pic img").click(function () {
                var linkUrl = $(this).parent().parent().find(".foot a").attr("href");
                if (linkUrl != "") {
                    location.href = linkUrl; //跳转到修改页面
                }
            });
        });
    </script>
 
<script type="text/javascript"> 
 function Search(){

	 var pageSize= $("#pageSize").val();
     document.getElementById("form1").action="tempUser/toTempUserList?type=${type}&pageSize="+pageSize; 
     document.getElementById("form1").submit();
 }
    function toPage(pageNumber){
  
     document.getElementById("form1").action="tempUser/toTempUserList?type=${type}&pageNumber="+pageNumber; 
     document.getElementById("form1").submit();
 }
  function setPageSize(){
    var pageSize= $("#pageSize").val();
     document.getElementById("form1").action="tempUser/toTempUserList?type=${type}&pageSize="+pageSize; 
     document.getElementById("form1").submit();
 }
		 //批量删除  
   function deleteDiaryList() {  
    //获取所有被选中的记录  
    // var rows1 = $.map($("#table").bootstrapTable('getSelections');
    var rows = $("#table").bootstrapTable('getSelections');  
    if (rows.length== 0) {  
        alert("对不起，请先选择要删除的记录!");  
        return;  
    }  
     var con=confirm("本操作会删除角色及相关权限，是否继续？"); //在页面上弹出对话框
            
     if(con==true)
     {
        var ids = '';  
          for (var i = 0; i < rows.length; i++)   
              ids += rows[i]['id'] + ",";  
         
         ids = ids.substring(0, ids.length - 1);  
      document.getElementById("ids").value=ids;
      var from=  document.getElementById("form1");
      if(from!=null){
        from.action="tempUser/tempUserDelete"; 
        from.submit();
       }  
     
    }
}
	  
</script>
 
</head>
<body class="mainbody">
<form method="post"  action="tempUser/toTempUserList?type=${type}"  id="form1">
  <input type="hidden" name="ids" id="ids" value="" />


<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i class="iconfont icon-up"></i><span>返回上一页</span></a>
  <a href="manager/center" class="home"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>居民信息采集列表</span>
</div>
<!--/导航栏-->


<!--工具栏-->
<div id="floatHead" class="toolbar-wrap">
  <div class="toolbar">
    <div class="box-wrap">
      <a class="menu-btn"><i class="iconfont icon-more"></i></a>
      <div class="l-list">
        <ul class="icon-list">
          <shiro:hasPermission name="tempUser:Add">
               <li><a class="add" href="tempUser/toTempUserAdd?type=${type}"><i></i><span>新增</span></a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="tempUser:Delete">
          	 <li><a onclick="deleteDiaryList();" id="btnDelete" class="del" href="javascript:void(0)"><i></i><span>删除</span></a></li>  
          </shiro:hasPermission>
          <li><a href="javascript:;" onclick="checkAll(this);"><i class="iconfont icon-check"></i><span>全选</span></a></li>
          
        </ul>
      </div>
      <div class="r-list">
        <input name="key" type="text" id="key" class="keyword" value="${key}" />
        <a id="lbtnSearch" class="btn-search" onclick="Search()"><i class="iconfont icon-search"></i></a>
        <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="tempUser/toTempUserList?type=Img"><i class="iconfont icon-list-img"></i></a>
        <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="tempUser/toTempUserList?type=Word"><i class="iconfont icon-list-txt"></i></a>
      </div>
    </div>
  </div>
</div>
<!--/工具栏-->


<!--列表-->
<div class="table-container">
  <!--文字列表-->
  
  <!--/文字列表-->

  <!--图片列表-->
  
  <div class="imglist">
    <ul>
    <c:forEach items="${page.list }" var="tempUser" >
  
      <li>
        <div class="details">
          <div class="check">
            <span class="checkall"><input id="chkId" type="checkbox" name="chkId" /></span>
            <input type="hidden" name="hidId" id="hidId" value="${ tempUser.id}" />
          </div>
          <div class="pic"><img src="../skin/default/loadimg.gif" data-original="${fn:split(tempUser.original_path, ';')}[0]?w=228&h=165&mode=crop" /></div><i class="absbg"></i>
          <h1><span>
          
          
            <shiro:hasPermission name="tempUser:Edit">
             	  <a href="tempUser/toTempUserEdit?type=${type }&id=${ tempUser.id}" >${ tempUser.user_name}</a>
            </shiro:hasPermission>
            <shiro:lacksPermission name="tempUser:Edit">
           		  ${ tempUser.user_name}
            </shiro:lacksPermission>
            
            
          
          
          </span></h1>
          <div class="remark">
            ${ tempUser.user_idcard}
          </div>
          <div class="tools">
	          <c:if test="${ tempUser.status==1}">
	                                         待审核
	          </c:if>
	          <c:if test="${ tempUser.status==2}">
	               <font color="#45b97c">审核通过</font>
	          </c:if>
	           <c:if test="${ tempUser.status==3}">
	                <font color="red">审核未通过</font>
	          </c:if>
             
          </div>
          <div class="foot">
            <p class="time">${ tempUser.add_time}</p>
            <shiro:hasPermission name="tempUser:Edit">
             	  <a href="tempUser/toTempUserEdit?type=${type }&id=${ tempUser.id}" title="编辑"><i class="iconfont icon-pencil"></i></a>
            </shiro:hasPermission>
            <shiro:lacksPermission name="tempUser:Edit">
           		 <i class="iconfont icon-pencil">
            </shiro:lacksPermission>
            
            <shiro:hasPermission name="tempUser:Add">
          	 	 <a href="tempUser/toTempUserAdd?type=${type }" title="导入"><i class="iconfont icon-copy"></i></a> 
            </shiro:hasPermission>
            <shiro:lacksPermission name="tempUser:Add">
               <i class="iconfont icon-copy">
            </shiro:lacksPermission>
           
          </div>
        </div>
      </li>
  
    </c:forEach>
      
    </ul>
  </div>
  
  <!--/图片列表-->
</div>
<!--/列表-->

<!--内容底部-->
<div class="line20"></div>
<div class="pagelist">
  <div class="l-btns">
    <span>显示</span><input name="pageSize" type="text" value="${page.pageSize}" onchange="setPageSize()" onkeypress="if (WebForm_TextBoxKeyHandler(event) == false) return false;" id="pageSize" class="pagenum" onkeydown="return checkNumber(event);" /><span>条/页</span>
  </div>
  <div id="PageContent" class="default"><span>共${page.total}记录</span>
  
  <c:choose>
		<c:when test="${page.pageNum==1  }">	
			 <span class="disabled">«上一页</span> 
		<!-- 分是否为第一页的两种情况，不为第一页的话，那么就要设置首页和上一页为有onclick点击事件 -->
		</c:when>
		<c:otherwise> 
			<a onclick="toPage(${page.pageNum-1})">«上一页</a>
		</c:otherwise>						
 </c:choose>
 
 <!-- 分页处理的优化工作 -->
<c:choose>
	<c:when test="${page.pageNum + 4 > page.pages}">  <!-- 现在每个分页为显示5页 ，所以先判断当前的页面+4是否大于总的页面数-->
		<c:choose>
			<c:when test="${page.pages-4 > 0}">   <!-- 判断是否总的页面数也是大于5，因为这样的话，就会出现两种情况 -->
				<c:forEach var="index1" begin="${page.pages-4}" end="${page.pages}" step="1">
				<c:if test="${index1 >= 1}">
					<c:choose>
						<c:when test="${page.pageNum == index1}">
							<span class="current">${page.pageNum}</span>
						</c:when>
						<c:otherwise>
							<a onclick="toPage(${index1});">${index1}</a>												
						</c:otherwise>
					</c:choose>
				</c:if>
				</c:forEach>
		</c:when>

		<c:otherwise>  <!-- 当总的页面数都不足5的时候，那么直接全部显示即可，不需要考虑太多 -->
			<c:forEach  var="pagenumber"  begin="1" end="${page.pages}">
			<!-- 判断页码是否是当前页，是的话，就换个颜色来标记 -->
			<c:choose>
					<c:when test="${page.pageNum == pagenumber}">
						<span class="current">${page.pageNum}</span>
					</c:when>
					<c:otherwise>
						<a onclick="toPage(${pagenumber});">${pagenumber}</a>												
					</c:otherwise>
			</c:choose>				
			</c:forEach>
		</c:otherwise>
</c:choose>

</c:when>

<c:otherwise>  <!-- 当当前页面数+4都还是小于总的页面数的情况 -->
	<c:choose>
	<c:when test="${page.pageNum != 1}">	<!-- 判断当前页面是否就是第一页，因为这样也会有两种情况的处理 -->												
		<c:forEach var="index2" begin="${page.pageNum-1}" end="${page.pageNum+3}"> <!-- 从当前页面减一的页面数开始，这样点击前面一页就会显示其他的页面，从而实现页面跳转 -->
			<c:choose>
				<c:when test="${page.pageNum == index2}">
					<span class="current">${page.pageNum}</span>
				</c:when>
				<c:otherwise>
					<a onclick="toPage(${index2});">${index2}</a>												
				</c:otherwise>	
			</c:choose>										
		</c:forEach>												
	</c:when>
	
	<c:otherwise>	<!-- 当当前页面数就是第一页的时候，就直接显示1-5页即可 -->											
		<c:forEach var="index3" begin="1" end="5">
			<c:choose>
				<c:when test="${page.pageNum == index3}">
					<span class="current">${page.pageNum}</span>
				</c:when>
				<c:otherwise>
					<a onclick="toPage(${index3});">${index3}</a>											
				</c:otherwise>
			</c:choose>
		</c:forEach>													
	</c:otherwise>												
	</c:choose>
</c:otherwise>
</c:choose>


<!-- 处理 当前页是否最后一页，不是的话，就需要添加下一页的点击时间-->
<c:choose>
	<c:when test="${page.pageNum == page.pages }"> 
		<span class="disabled">下一页»</span> 
	</c:when>
	<c:otherwise>
		<a onclick="toPage(${page.pageNum}+1)">下一页»</a>
	</c:otherwise>						
</c:choose>
									
 
  
  </div>
</div>
<!--/内容底部-->

 

</form>
</body>
</html>