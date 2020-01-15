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
<title>居民信息列表</title>
  
<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />
<link rel="stylesheet" type="text/css" href="css/admin/pagination.css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/js/admin/scripts/datepicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/Validform_v5.3.2_min.js"></script>
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
	 
   /*   $.ajax({
            type: 'post',
            url: "user/userList?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}",
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
                     
                    $('#table').on('dbl-click-row.bs.table',function(row, $element) {
                       window.location.href = "user/userDetial?id="+$element.id;                  
                     
                   });
                   
		}
       })
 */
  $(document).ready(function () { 
    $('#table').bootstrapTable({
                        
                        url : "user/userList?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}", 
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
                     
                 
                      $('#table').on('dbl-click-row.bs.table',function(row, $element) {
                       window.location.href = "user/userDetial?id="+$element.id;                  
                     
                   });
              
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


function infoFormatter( value, row, index){ 
 
  var date = new Date(row['update_time']);
  var d;
  if(row['update_time']==null) 
     d="暂无登录";
  else
     d= date.Format("yyyy-MM-dd hh:mm:ss");
  
  var te;
  if(row['data_type']=="城乡居民养老保险")
    te="乡镇办："+row['user_township']+'-'+row['user_village'];
  else 
    te="所属单位："+row['user_township'];
 
   
   var s=  
   '<div>'+
	   '<div  style="float: left;">'+
		   '<shiro:hasPermission name="user:Edit">'+
		      '<a class="user-avatar" href="user/toUserEdit?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}&id='+row['id']+'">'+
		          '<img width="64" height="64" src="/img_identity'+row['img_url']+'" />'+
		       '</a>' +
		  '</shiro:hasPermission>'+
		  '<shiro:lacksPermission name="user:Edit">'+
		      '<a class="user-avatar" href="#" onclick="return false">'+
		          '<img width="64" height="64" src="/img_identity'+row['img_url']+'" />'+
		       '</a>' +
		  '</shiro:lacksPermission>'+
	   '</div>'+
       '<div class="user-box" style="float: left;">'+ 
          '<h4><b style="font-size:16px;" title="姓名：'+row['user_name']+'">'+row['user_name']+'</b></h4>'+
          '<i>上次登录时间：'+d+'</i>'+
          '<span>'+ te+ ' </span>'+
        ' </div>'+
     ' </div>'; 
  
   
 return s;
}

 
	
    function timeFormat(val) {
    if (val != null) {
            var date = new Date(val);
            return   date.Format("yyyy-MM-dd hh:mm:ss");
           // return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+' '+date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
        }
	}
	
	
	function actionFormatter(value, row, index) {  
	 
     return "<shiro:hasPermission name='user:Edit'><a class='update'  href = 'user/toUserEdit?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}&id="+row['id']+"'>修改</a></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp; <shiro:hasPermission name='user:View'><a class='detial'  href = 'user/userDetial?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}&id="+row['id']+"'>详情</a></shiro:hasPermission>" ;
    } 
    //表格  - 操作 - 事件
    window.actionEvents = {
     'click .update': function(e, value, row, index) {   
          //修改操作
          window.location.href = "user/toUserEdit?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}&id="+row['id'];
      } 
     } 
     
function Search(){
     document.getElementById("form1").action="user/toUserList?type=${type}"; 
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
     var con=confirm("删除记录后不可恢复，您确定吗？"); //在页面上弹出对话框
            
     if(con==true)
     {
        var ids = '';  
          for (var i = 0; i < rows.length; i++)   
              ids += rows[i]['id'] + ",";  
         
         ids = ids.substring(0, ids.length - 1);  
      document.getElementById("ids").value=ids;
      var from=  document.getElementById("form1");
      if(from!=null){
        from.action="user/userDelete"; 
        from.submit();
       }  
     
    }
}
	  
</script>
 
</head>
<body class="mainbody">
<form method="post"  action="user/toUserList?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}"  id="form1">
  <input type="hidden" name="ids" id="ids" value="" />


<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i class="iconfont icon-up"></i><span>返回上一页</span></a>
  <a href="manager/center" class="home"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>居民信息列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
  <div id="toolbar" class="btn-group"> 
   <div class="toolbar"  >
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
          <shiro:hasPermission name="user:Add">
               <li><a class="add" href="user/toUserAdd?isHasVideo=${isHasVideo}&user_township=${user_township}&startTime=${startTime}&endTime=${endTime}&dataType=${dataType}"><i class="iconfont icon-close"></i><span>新增</span></a></li> 
          </shiro:hasPermission>
          <shiro:hasPermission name="user:Delete"> 
          	 <li><a onclick="deleteDiaryList();" id="btnDelete" href="javascript:void(0)"><i class="iconfont icon-delete"></i><span>删除</span></a></li>
          	 
          </shiro:hasPermission>
           
           
        </ul>
        
        
         <shiro:hasPermission name="user:Show">
         
         
         
         
	         <div class="menu-list">
	         
	        
	         
	          <div class="rule-single-select">
	            <select name="isHasVideo" onchange="Search()" id="isHasVideo">
					<option   value="">是否上传视频</option>
					<option value="1"  ${isHasVideo==1?"selected='selected'":'' }>已上传</option>
					<option value="2"  ${isHasVideo==2?"selected='selected'":'' }>未上传</option>
	
				</select>
             </div>
          
          
          
	         <div class="rule-single-select">
	            <select name="user_township" onchange="Search()"  id="user_township">
	               <option   value="">所有乡镇办</option>
		           <c:forEach items="${xzbs }" var="xzb"> 
		              <option value="${xzb.title }"  ${user_township==xzb.title?"selected='selected'":'' }>${xzb.title }</option>
		           </c:forEach>
		        
	            </select>
	        </div>
	        
	         <div class="rule-single-select">
	            <select name="dataType" onchange="Search()"  id="dataType">
	                  <option   value="">所有数据类别</option> 
		              <option value="机关事业养老保险"  ${dataType=='机关事业养老保险'?"selected='selected'":'' }>机关事业养老保险</option>
		              <option value="企业职工养老保险"  ${dataType=='企业职工养老保险'?"selected='selected'":'' }>企业职工养老保险</option>
		              <option value="城乡居民养老保险"  ${dataType=='城乡居民养老保险'?"selected='selected'":'' }>城乡居民养老保险</option>
		        </select>
	        </div>
	        </div>
	        
		     <div class="menu-list">
	          <input name="startTime" type="text" id="startTime" value="${startTime}" class="input rule-date-input"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   />
	          -
	          <input name="endTime" type="text" id="endTime" value="${endTime}"  class="input rule-date-input"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
	        </div>
	        <ul class="icon-list">
	           <li><a class="add" onclick="Search()"><i class="iconfont icon-search"></i><span></span></a></li>
	           
	        </ul>
	        
        
      </shiro:hasPermission>
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
             
            <th data-sortable="true" data-field="title" data-align="left" 
                data-filter-control="input" data-formatter="infoFormatter">用户信息
            </th>
            
            <th data-sortable="true" data-field="user_idcard" data-align="center"
                data-filter-control="input" >身份证号
            </th>  
            
            <th data-sortable="true" data-field="data_type" data-align="center"
                data-filter-control="input" >类型
            </th>  
                     
            <th data-sortable="true"  data-field="add_time" data-align="center"
                data-filter-control="input" data-formatter="timeFormat">时间
            </th>
             <th data-field="id" data-formatter="actionFormatter" data-align="center"
                        data-events="actionEvents">操作
             </th>
        </tr>
        </thead>
    
</table>
  
  
</div>


<!--/列表-->
 
</form>
</body>
</html>