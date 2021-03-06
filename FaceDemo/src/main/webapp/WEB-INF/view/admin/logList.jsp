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
<title>日志列表</title>
  

<link href="js/admin/scripts/artdialog/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="css/admin/pagination.css" rel="stylesheet" type="text/css" />
<link href="css/admin/skin/icon/iconfont.css" rel="stylesheet" type="text/css" />
<link href="css/admin/skin/default/style.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/js/admin/scripts/datepicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/layindex.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>

<!--css样式-->
<link rel="stylesheet" type="text/css"  href="css/admin/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css"  href="css/admin/bootstrap/bootstrap-table.css" />
<!--js-->
<%-- <script src="<%=path %>/js/bootstrap/jquery-1.12.0.min.js" type="text/javascript"></script> --%>
<script type="text/javascript" charset="utf-8"  src="js/admin/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" charset="utf-8"  src="js/admin/bootstrap/bootstrap-table.js"></script>
<script type="text/javascript" charset="utf-8"  src="js/admin/bootstrap/bootstrap-table-zh-CN.js"></script>
  

<script type="text/javascript"> 
	  $(document).ready(function () { 
    $('#table').bootstrapTable({
                        
                        url : "<%=path%>/log/logList?startTime=${startTime}&endTime=${endTime}",
						dataType : "json", 
						contentType : "application/x-www-form-urlencoded;charset=utf-8", // 如果是post必须定义
						method : 'get',
						striped: true,                      //是否显示行间隔色 
                        pagination: true,                   //是否显示分页（*）
						dataField : "data",
                        queryParams: queryParams,//传递参数（*）
                        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                        pageNumber:1,                       //初始化加载第一页，默认第一页
                        pageSize: 10,                       //每页的记录行数（*）
                        pageList: [10,25,50,100,500,1000],        //可供选择的每页的行数（*）
                        search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                        formatter:timeFormat,      
                        responseHandler:responseHandler    //请求数据成功后，渲染表格前的方法
                     
                 }); 
                     
                 /* 
                      $('#table').on('dbl-click-row.bs.table',function(row, $element) {
                       window.location.href = "user/userDetial?id="+$element.id;                  
                     
                   }); */
              
         }  );    
      
      
 //得到查询的参数
  function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            pageSize : params.limit, //每一页的数据行数，默认是上面设置的10(pageSize)
            pageNumber : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
            key: $("#key").val()
        };
        
        return temp;
    } 

 function responseHandler(result){ 
 
// alert(result.rows[1].add_time);
    //如果没有错误则返回数据，渲染表格
    return {
        total : result.total, //总页数,前面的key必须为"total"
        data : result.rows //行数据，前面的key要与之前设置的dataField的值一致.
    };
}
   <%--   $.ajax({
            type: 'post',
            url: "<%=path%>/log/logList?startTime=${startTime}&endTime=${endTime}",
            async: true,
            type: 'post',
            dataType: 'text',
            success: function (data, status) {
                
                var strs = $.parseJSON($.trim(data));
                var tabledata=strs.jsons;
               
                    $('#table').bootstrapTable({
                        data: tabledata
                    });
                    $('#table').bootstrapTable('load', tabledata);
                    
		}
       }) --%>

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")   ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { //author: meizz
var o = {
"M+": this.getMonth() + 1, //月份
"d+": this.getDate(), //日
"h+": this.getHours(), //小时
"m+": this.getMinutes(), //分
"s+": this.getSeconds(), //秒
"q+": Math.floor((this.getMonth() + 3) / 3), //季度
"S": this.getMilliseconds() //毫秒
};
if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
for (var k in o)
if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
return fmt;
}

    function timeFormat(val) {
    if (val != null) {
            var date = new Date(val);
            return   date.Format("yyyy-MM-dd hh:mm:ss");
           // return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+' '+date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
        }
	}
	
	 
     
 function Search(){ 
	 if($("#startTime").val() !=""||$("#endTime").val() !="") 
	  {
     document.getElementById("form1").action=""; 
     document.getElementById("form1").submit();
	  }
 }
 
	  
</script>


 
</head>
<body class="mainbody">
<form method="post"  action="log/toLogList"  id="form1"> 
<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i class="iconfont icon-up"></i><span>返回上一页</span></a>
  <a href="manager/center" class="home"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>日志列表</span>
</div>
<!--/导航栏-->
 

<!--工具栏-->
  
 <div id="toolbar" class="toolbar-wrap">
  <div class="toolbar">
    <div class="box-wrap">
      <a class="menu-btn"><i class="iconfont icon-more"></i></a>
      <div class="l-list">
       
        <div class="menu-list">
          <input name="startTime" type="text" id="startTime" value="${startTime}" class="input rule-date-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   />
          -
          <input name="endTime" type="text" id="endTime" value="${endTime}"  class="input rule-date-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
        </div>
        <ul class="icon-list">
           <li><a class="add" onclick="Search()"><i class="iconfont icon-search"></i><span></span></a></li>
           
        </ul>
        
      </div> 
    </div>
  </div>
</div>
 
<!--/工具栏--> 
 
<!--列表-->
<div class="table-container">
  <table width="100%"    id="table"  
           data-striped="true" data-side-pagination="client"  data-toolbar="#toolbar"  data-search="true"
           data-show-export="true" data-page-list="[10,25,50,100,500,1000,ALL]"  
           data-detail-view="false" data-detail-formatter="detailFormatter"
           data-minimum-count-columns="2" data-pagination="true"
           data-response-handler="responseHandler" data-row-style="rowStyle1"
           data-filter-control="true" 
           data-classes="ltable" class="ltable"   >
        <thead>
        <tr>
           
            <th data-field="state" data-checkbox="true"></th>
            <th data-sortable="false" data-field="id"  data-visible="false"  data-align="center"
                data-filter-control="input">选择
            </th>
            <th data-sortable="true" data-field="user_name" data-align="center"
                data-filter-control="input">用户名
            </th>
            
            <th data-sortable="true" data-field="action_type" data-align="center"
                data-filter-control="input">操作类型
            </th>
            
            <th data-sortable="true" data-field="remark" data-align="center"
                data-filter-control="input">备注
            </th>
            
            <th data-sortable="true" data-field="user_ip" data-align="center"
                data-filter-control="input">用户IP
            </th>
            
            
            <th data-sortable="true"  data-field="add_time" data-align="center"
                data-filter-control="input" data-formatter="timeFormat">操作时间
            </th>
              
        </tr>
        </thead>
    
</table>
  
  
</div>


<!--/列表-->
 
</form>
</body>
</html>