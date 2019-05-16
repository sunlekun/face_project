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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>认证抽查列表</title>
  

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
	 
     $.ajax({
            type: 'post',
            url: "<%=path%>/identityCheck/identityCheckList?status=${status}&video_status=${video_status}",
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
                      window.location.href = "<%=path%>/identityCheck/randomCheckDetial?status=${status}&video_status=${video_status}&id="+$element.id+"&video_id="+$element.video_id;                  
                     
                   });
                   
		}
       })
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")   ==> 2006-7-2 8:9:4.18
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
         return "抽查审核未通过";
        else  if (val == 2)  
            return "抽查审核通过";
        else 
           return "等待人工抽查审核";
         
	}
 
function infoFormatter( value, row, index){ 
 
   var date = new Date(row['vadd_time']);
   var d=   date.Format("yyyy-MM-dd hh:mm:ss");
   var s=  
   '<div>'+
	   '<div  style="float: left;">'+
		   '<shiro:hasPermission name="randomCheck:Confirm">'+
		      '<a class="user-avatar" href="randomCheck/toRandomCheckConfirm?status=${status}&video_status=${video_status}&id='+row['id']+'&video_id='+row['video_id']+'">'+
		          '<img width="64" height="64" src="/video_identity'+row['user_img']+'" />'+
		       '</a>' +
		  '</shiro:hasPermission>'+
		  '<shiro:lacksPermission name="randomCheck:Confirm">'+
		      '<a class="user-avatar" href="#" onclick="return false">'+
		          '<img width="64" height="64" src="/video_identity'+row['user_img']+'" />'+
		       '</a>' +
		  '</shiro:lacksPermission>'+
	   '</div>'+
       '<div class="user-box" style="float: left;">'+ 
          '<h4><b style="font-size:16px;" title="姓名：'+row['user_name']+'">'+row['user_name']+'</b> <y title="年份：'+row['year']+'">(采集年份：'+row['year']+')</y></h4>'+
          '<i>所属乡镇：'+row['user_township']+'-'+row['user_village']+'</i>'+
          '<span>'+
           ' 身份证号：'+row['user_idcard']+
         ' </span>'+
        ' </div>'+
     ' </div>'; 
  
   
 return s;
}
	function actionFormatter(value, row, index) { 
 <%--  return "<a class='update'  href = '<%=path%>/identityCheck/toRandomCheckEdit?id="+value+"'>修改</a><br>" ; --%>
     return "<shiro:hasPermission name='randomCheck:Confirm'><a class='update'   href = '<%=path%>/identityCheck/toRandomCheckConfirm?status=${status}&video_status=${video_status}&id="+row['id']+"&video_id="+row['video_id']+"'>抽查审核</a></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;<shiro:hasPermission name='randomCheck:View'><a class='detial'  href = '<%=path%>/identityCheck/randomCheckDetial?status=${status}&video_status=${video_status}&id="+row['id']+"&video_id="+row['video_id']+"'>详情</a></shiro:hasPermission>"; 
 
    } 
    
     
      function Search(){
      
     document.getElementById("form1").action="<%=path%>/identityCheck/toIdentityCheckList"; 
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
        from.action="<%=path%>/identityCheck/randomCheckDelete"; 
        from.submit();
       }  
     
    }
}
	  
</script>
</head>
<body class="mainbody">
<form method="post"  action="<%=path%>/identityCheck/toIdentityCheckList"  id="form1">
  <input type="hidden" name="ids" id="ids" value="" />
   

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
          
        </ul>
         <shiro:hasPermission name="identityCheck:Show">
          <div class="menu-list">
          <div class="rule-single-select">
            <select name="video_status" onchange="Search()" id="video_status">
	           <option  ${video_status==null?"selected='selected'":'' } value="">审核状态</option>
	           <option  ${video_status==1?"selected='selected'":'' }  value="1">待验证</option>
               <option  ${video_status==2?"selected='selected'":'' }  value="2">审核通过</option>
	           <option  ${video_status==3?"selected='selected'":'' }  value="3">匹配失败</option>
               <option  ${video_status==4?"selected='selected'":'' }  value="4">黑名单</option>
        </select>
        </div>
        </div>
        
      
        
        </div>
        </div>
        
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
           <!--  <th data-sortable="true" data-field="title" data-align="center"   
                data-filter-control="input" data-formatter="imgFormatter">图片
            </th> -->
            <th data-sortable="true" data-field="title" data-align="left" 
                data-filter-control="input" data-formatter="infoFormatter">用户信息
            </th>
            
           <th data-sortable="true" data-field="status" data-align="center"
                data-filter-control="input"  data-formatter="strFormat">人工抽查审核状态
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
  <!-- <table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tr>
      <th width="6%">选择</th>
      <th align="left" colspan="2">用户信息</th>
      <th align="left" width="16%">人工复查状态</th>
      <th width="10%">操作</th>
    </tr>
  
    <tr>
      <td align="center">
        <span class="checkall" style="vertical-align:middle;"><input id="rptList_chkId_0" type="checkbox" name="rptList$ctl01$chkId" /></span>
        <input type="hidden" name="rptList$ctl01$hidId" id="rptList_hidId_0" value="103155" />
      </td>
       
      <td>
       <div>
       <div style="float: left;">
        <a class="user-avatar" href="check_show.aspx?action=Show&id=103155">
          <img width="64" height="64" src="http://face.yzrszp.com:8080/sh_identity/upload/2018/6/1528849367447.mp4.jpg" />
        </a>
       
       </div>
        <div class="user-box"   <div style="float: left;">
       
          <h4><b style="font-size:16px;" title="姓名：杨风仙">杨风仙</b> <y title="年份：2018">(采集年份：2018)</y></h4>
          <i>采集时间：2018/6/13 8:22</i>
          <span>
            身份证号：411081193910271600
          </span>
        </div>
        </div>
      </td>      
      <td>等待人工抽查审核</td>
      <td align="center">
        <a href="check_show.aspx?action=Show&id=103155">预览</a>
      </td>
    </tr>
</table> 
   -->
</div>


<!--/列表-->
 
</form>
</body>
</html>
