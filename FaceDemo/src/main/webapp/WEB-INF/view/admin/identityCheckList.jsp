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
<title>身份认证审核列表</title>
  

<link href="js/admin/scripts/artdialog/ui-dialog.css" rel="stylesheet" type="text/css" />
<link href="css/admin/skin/icon/iconfont.css" rel="stylesheet" type="text/css" />
<link href="css/admin/skin/default/style.css" rel="stylesheet" type="text/css" />
<link href="css/admin/pagination.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/layindex.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>

<!-- <link rel="stylesheet" type="text/css"  href="js/admin/source/jquery.fancybox.css" />
<script type="text/javascript" charset="utf-8"  src="js/admin/source/jquery.fancybox.js"></script> -->

<!--css样式-->
<link rel="stylesheet" type="text/css"  href="css/admin/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css"  href="css/admin/bootstrap/bootstrap-table.css" />
<!--js-->
<%-- <script src="<%=path %>/js/bootstrap/jquery-1.12.0.min.js" type="text/javascript"></script> --%>
<script type="text/javascript" charset="utf-8"  src="js/admin/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" charset="utf-8"  src="js/admin/bootstrap/bootstrap-table.js"></script>
<script type="text/javascript" charset="utf-8"  src="js/admin/bootstrap/bootstrap-table-zh-CN.js"></script>
 
 
 
<script type="text/javascript"> 
var formParams="type="+encodeURI('${type}')+"&video_status="+encodeURI('${video_status}')+"&user_township="+encodeURI('${user_township}')+
           "&year="+encodeURI('${year}')+"&dataType="+encodeURI('${dataType}');
 $(document).ready(function () { 
    $('#table').bootstrapTable({
                        
                        url : "identityCheck/identityCheckList?"+formParams,  
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
                      /*  var paras="type=${type}&video_status="+encodeURI('${video_status}')+"&user_township="+encodeURI('${user_township}')+"&year="+encodeURI('${year}')+"&dataType=" +encodeURI('${dataType}');
                       window.location.href ="identityCheck/identityCheckDetial?id="+$element.id+'&'+paras;     */    
                      
                       window.location.href ="identityCheck/identityCheckDetial?"+formParams+"&id="+$element.id;        
                     
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
// (new Date()).Format("yyyy-M-d h:m:s.S")?? ==> 2006-7-2 8:9:4.18
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
	
    
	function strFormat(val) { 
         if (val == 3) 
         return "匹配失败";
        else  if (val == 2)  
            return "审核通过";
        else  if (val == 4)  
        	return "黑名单";
        else	
           return "待审核";
         
	}
    function dataFormat(val) { 
        return val;
	}

 
function infoFormatter( value, row, index){ 
 
   var date = new Date(row['vadd_time']);
   var d=   date.Format("yyyy-MM-dd hh:mm:ss");
   var te;
  if(row['data_type']=="城乡居民养老保险")
    te="乡镇办："+row['user_township']+'-'+row['user_village'];
  else 
    te="所属单位："+row['user_township'];
   
   var s=  
   '<div>'+
	   '<div  style="float: left;">'+
		   '<shiro:hasPermission name="identityCheck:Audit">'+
		      '<a class="user-avatar" href="identityCheck/identityCheckConfirm?'+formParams+'&id='+row['id']+'">'+
		          '<img width="64" height="64" src="/video_identity'+row['img_url']+'" />'+
		       '</a>' +
		  '</shiro:hasPermission>'+
		  '<shiro:lacksPermission name="identityCheck:Audit">'+
		      '<a class="user-avatar" href="#" onclick="return false">'+
		          '<img width="64" height="64" src="/video_identity'+row['img_url']+'" />'+
		       '</a>' +
		  '</shiro:lacksPermission>'+
	   '</div>'+
       '<div class="user-box" style="float: left;">'+ 
          '<h4><b style="font-size:16px;" title="姓名：'+row['user_name']+'">'+row['user_name']+'</b> <y title="年份：'+row['year']+'">(采集年份：'+row['year']+')</y></h4>'+
          '<i>'+te+'</i>'+
          '<span>'+
           ' 身份证号：'+row['user_idcard']+
         ' </span>'+
        ' </div>'+
     ' </div>'; 
  
   
 return s;
}
	function actionFormatter(value, row, index) { 
<%--   return "<a class='update'  href = '<%=path%>/identityCheck/toRandomCheckEdit?id="+value+"'>修改</a><br>" ; --%>
<%--      return "<shiro:hasPermission name='randomCheck:Confirm'><a class='update'   href = '<%=path%>/identityCheck/toRandomCheckConfirm?status=${status}&video_status=${video_status}&id="+row['id']+"&video_id="+row['video_id']+"'>抽查审核</a></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;<shiro:hasPermission name='randomCheck:View'><a class='detial'  href = '<%=path%>/identityCheck/randomCheckDetial?status=${status}&video_status=${video_status}&id="+row['id']+"&video_id="+row['video_id']+"'>详情</a></shiro:hasPermission>";  --%>
   return "<a class='detial'  href = 'identityCheck/identityCheckDetial?"+formParams+"&id="+row['id']+"'>详情</a>";
    
    } 
    
    function goURL(url ,paras){   
     document.getElementById("form1").action=url+"?s=1"+"&"+paras; 
     document.getElementById("form1").submit();  
     }
 
   /*  function Search(){ 
     document.getElementById("form1").action="identityCheck/toIdentityCheckList?type=${type}"; 
     document.getElementById("form1").submit();  
     } */
    
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
        from.action="<%=path%>/identityCheck/identityCheckDelete"; 
        from.submit();
       }  
     
    }
}
	  
</script>
</head>
<body class="mainbody">
<form method="post"  action="<%=path%>/identityCheck/toIdentityCheckList"  id="form1">
  <input type="hidden" name="ids" id="ids" value="" />
  <input type="hidden" name="flag" id="ids" value="${type}" />

<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i class="iconfont icon-up"></i><span>返回上一页</span></a>
  <a href="manager/center" class="home"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>身份认证审核列表</span>
</div>
<!--/导航栏-->
 



<!--工具栏-->
 
 <div id="toolbar" class="btn-group"> 
   <div class="toolbar"  >
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
       
        <shiro:hasPermission name="identityCheck:Delete">
            <li><a onclick="deleteDiaryList();" id="btnDelete" class="del" href="javascript:void(0)"><i class="iconfont icon-delete"></i><span>删除</span></a></li> 
        </shiro:hasPermission>
          <li><a id="btnDownExcel" onclick="goURL('identityCheck/downExcel','flag=1')"><i class="iconfont icon-exl"></i><span>认证信息导出</span></a></li>
          <li><a id="btnDownExcel" onclick="goURL('identityCheck/downExcel','flag=2')"><i class="iconfont icon-exl"></i><span>未证名单导出</span></a></li>
        </ul>
         <shiro:hasPermission name="identityCheck:Show">
          <div class="menu-list">
          
             <div class="rule-single-select">
	            <select name="year" onchange="goURL('identityCheck/toIdentityCheckList','')" id="year">
		           <option value="">请选择认证年限</option>
		           <c:forEach var="item" items="${years}">
	                 <option  ${item==year?"selected='selected'":'' }   value="${item}">${item}</option>
	              </c:forEach> 
		          
		        </select>
	        </div>
        
        
        	  <div class="rule-single-select">
         		   <select name="video_status" onchange="goURL('identityCheck/toIdentityCheckList','')" id="video_status">
			           <option  ${video_status==null?"selected='selected'":'' } value="">审核状态</option>
			           <option  ${video_status==1?"selected='selected'":'' }  value="1">待验证</option>
		               <option  ${video_status==2?"selected='selected'":'' }  value="2">审核通过</option>
			           <option  ${video_status==3?"selected='selected'":'' }  value="3">匹配失败</option>
		               <option  ${video_status==4?"selected='selected'":'' }  value="4">黑名单</option>
		    	    </select>
        		
      		  </div>
      		  
      		   
	         <div class="rule-single-select">
	            <select name="user_township" onchange="goURL('identityCheck/toIdentityCheckList','')"  id="user_township">
	               <option   value="">所有乡镇办</option>
		           <c:forEach items="${xzbs }" var="xzb"> 
		              <option value="${xzb.title }"  ${user_township==xzb.title?"selected='selected'":'' }>${xzb.title }</option>
		           </c:forEach>
		        
	            </select>
	        </div>
	        
	        
	         <div class="rule-single-select">
	            <select name="dataType" onchange="goURL('identityCheck/toIdentityCheckList','')"  id="dataType">
	                  <option   value="">所有数据类别</option> 
		              <option value="机关事业养老保险"  ${dataType=='机关事业养老保险'?"selected='selected'":'' }>机关事业养老保险</option>
		              <option value="企业职工养老保险"  ${dataType=='企业职工养老保险'?"selected='selected'":'' }>企业职工养老保险</option>
		              <option value="城乡居民养老保险"  ${dataType=='城乡居民养老保险'?"selected='selected'":'' }>城乡居民养老保险</option>
		        </select>
	        </div>
        </div>
        
        
      </shiro:hasPermission>
     
      <ul class="icon-list">
       
           <li style="float:right;"> <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="identityCheck/toIdentityCheckList?type=Img"><i class="iconfont icon-list-img"></i></a></li>
           <li> <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="identityCheck/toIdentityCheckList?type=Word"><i class="iconfont icon-list-txt"></i></a></li>
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
           <!--  <th data-sortable="true" data-field="title" data-align="center"   
                data-filter-control="input" data-formatter="imgFormatter">图片
            </th> -->
            <th data-sortable="true" data-field="title" data-align="left" 
                data-filter-control="input" data-formatter="infoFormatter">用户信息
            </th>
            <th data-sortable="true"  data-field="data_type" data-align="center"
                data-filter-control="input" data-formatter="dataFormat">类型
            </th>
           <th data-sortable="true" data-field="video_status" data-align="center"
                data-filter-control="input"  data-formatter="strFormat">视频认证状态
            </th>           
            <th data-sortable="true"  data-field="add_time" data-align="center"
                data-filter-control="input" data-formatter="timeFormat">创建时间
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
