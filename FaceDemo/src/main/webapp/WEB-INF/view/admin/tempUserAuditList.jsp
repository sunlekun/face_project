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
<title>居民信息采集列表</title>
  
<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />
<link rel="stylesheet" type="text/css" href="css/admin/pagination.css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
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
	 
     $.ajax({
            type: 'post',
            url: "tempUserAudit/tempUserAuditList?status=${status}&dataType=${dataType}&type=${type}",
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
                       window.location.href = "tempUserAudit/tempUserAuditDetial?status=${status}&dataType=${dataType}&type=${type}&id="+$element.id;                  
                     
                   });
                   
		}
       })

 
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
 
 var s="<shiro:hasPermission name='tempUserAudit:Edit'><a class='detial'  href = 'tempUserAudit/toTempUserAuditEdit?status=${status}&dataType=${dataType}&type=${type}&id="+row['id']+"'>"+row['user_name']+"</a></shiro:hasPermission>"+
 
		"<shiro:lacksPermission name='tempUserAudit:Edit'>"+row['user_name']+"</shiro:lacksPermission>";
   
 return s;
}

function strFormat(val) { 
         if (val == 3) 
         return "审核未通过";
        else  if (val == 2)  
            return "审核通过";
        else 
           return "待审核";
         
	}
    function timeFormat(val) {
    if (val != null) {
            var date = new Date(val);
            return   date.Format("yyyy-MM-dd hh:mm:ss");
           // return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+' '+date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
        }
	}
	
	
	function actionFormatter(value, row, index) {  
	
      return "<shiro:hasPermission name='tempUserAudit:Edit'><a class='update'  href = 'tempUserAudit/toTempUserAuditEdit?status=${status}&dataType=${dataType}&type=${type}&id="+row['id']+"'>修改</a></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp; "+
      "<shiro:hasPermission name='tempUserAudit:Show'><a class='detial'  href = 'tempUserAudit/toTempUserAuditImportIdCardImg?status=${status}&dataType=${dataType}&type=${type}&id="+row['id']+"'>导入</a></shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp; "+
      "<shiro:hasPermission name='tempUserAudit:View'><a class='detial'  href = 'tempUserAudit/tempUserAuditDetial?status=${status}&dataType=${dataType}&type=${type}&id="+row['id']+"'>详情</a></shiro:hasPermission>";
  
    } 
    //表格  - 操作 - 事件
    window.actionEvents = {
     'click .update': function(e, value, row, index) {   
          //修改操作
          window.location.href = "<tempUserAudit/toTempUserAuditEdit?status=${status}&dataType=${dataType}&type=${type}&id="+row['id'];
      } 
     } 
     
      function Search(){
     document.getElementById("form1").action="tempUserAudit/toTempUserAuditList?type=${type}"; 
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
        from.action="tempUserAudit/tempUserAuditDelete?status=${status}&dataType=${dataType}&type=${type}"; 
        from.submit();
       }  
     
    }
}

 function upLoadFiles(){
  
       var files = document.getElementById("file_Import").files;
    
         if(files.length==0)
         {
               alert("请选择要导入的文件!(支持'png', 'img'格式)");
               return;
          }
             
      var con=confirm("请确保上传文件为图片格式且文件的命名为身份证号，确定上传吗？"); //在页面上弹出对话框 
     if(con==true)
     {       /*   $('#files').click(); */
               var formData = new FormData($('#form1')[0]);//序列化表单，
                 
                $.ajax({
                type: 'post',
                url: "tempUserAudit/tempUserAuditImportIdCardImgs?status=${status}&dataType=${dataType}&type=${type}",
                data: formData ,
                processData: false,
                contentType: false,
                async: true,
                dataType: "json",
             
                success: function (data, status) { 
              
               window.location.href = "tempUserAudit/uploadImgsResultExcel?status=${status}&dataType=${dataType}&type=${type}&fileName="+data.fileName;
                  
                   
		}
       })  
       
    
    } 
     
 }
	  
</script>
 
</head>
<body class="mainbody">
<form method="post"  action="tempUserAudit/totempUserAuditList?type=${type}"  id="form1" enctype="multipart/form-data">
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
  <div id="toolbar" class="btn-group"> 
   <div class="toolbar"  >
    <div class="box-wrap">
      <a class="menu-btn"></a>
      <div class="l-list">
        <ul class="icon-list">
          <shiro:hasPermission name="tempUserAudit:Add">
               <li><a class="add" href="tempUserAudit/toTempUserAuditAdd?status=${status}&dataType=${dataType}&type=${type}"><i iconfont icon-add></i><span>新增</span></a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="tempUserAudit:Delete">
          	 <li><a onclick="deleteDiaryList();" id="btnDelete" class="del" href="javascript:void(0)"><i class="iconfont icon-delete"></i><span>删除</span></a></li>
          </shiro:hasPermission>
          <shiro:hasPermission name="tempUserAudit:Show">
	          <li><a id="btnDownLoadFiles" href="tempUserAudit/downImgs?status=${status}&dataType=${dataType}&type=${type}"><i class="iconfont icon-folder-empty"></i><span>图片打包下载</span></a></li>
	          <li><a id="btnDownExcel" href="tempUserAudit/downExcel?status=${status}&dataType=${dataType}&type=${type}"><i class="iconfont icon-exl"></i><span>Excel数据下载</span></a></li>
	        <!--   <li><a id="btnUploadImg"  href="javascript:void(0)"   onclick="files.click()"><i class="iconfont icon-file"></i><span>图片导入</span></a></li> -->
	      </shiro:hasPermission>
          
         
        </ul>
        
            <shiro:hasPermission name="tempUserAudit:Show">
	          <div class="menu-list">
	          <div class="rule-single-select">
	            <select name="status" onchange="Search()" id="status">
		           <option  ${status==null?"selected='selected'":'' } value="">审核状态</option>
		           <option  ${status==1?"selected='selected'":'' }  value="1">待审核</option>
	               <option  ${status==2?"selected='selected'":'' }  value="2">审核通过</option>
		           <option  ${status==3?"selected='selected'":'' }  value="3">审核未通过</option>
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
        
      </shiro:hasPermission>
      
       <ul class="icon-list"> 
         <shiro:hasPermission name="tempUserAudit:Show">
           <li style="float:right;"> <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="tempUserAudit/toTempUserAuditList?type=Img"><i class="iconfont icon-list-img"></i></a></li>
           <li> <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="tempUserAudit/toTempUserAuditList?type=Word"><i class="iconfont icon-list-txt"></i></a></li>
         </shiro:hasPermission>
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
                data-filter-control="input"  data-formatter="infoFormatter">姓名
            </th>
              <th data-sortable="true"  data-field="status" data-align="center"
                data-filter-control="input" data-formatter="strFormat">审核状态
            </th>
            
            <th data-sortable="true"  data-field="audit_time" data-align="center"
                data-filter-control="input" data-formatter="timeFormat">审核时间
            </th>
            
            <th data-sortable="true"  data-field="add_time" data-align="center"
                data-filter-control="input" data-formatter="timeFormat">采集时间
            </th>
             <th data-field="id" data-formatter="actionFormatter" data-align="center"
                        data-events="actionEvents">操作
             </th>
        </tr>
        </thead>
    
</table>
  
  
  
</div>

<div  style="padding-top:40px;">
  <div class="btn-wrap" >
  <table>
  <tr><td>
    <input type="file" name="file_Import" id="file_Import" class="input normal upload-path" onchange="change(this) "  multiple="multiple" accept="image/*" />
    </td>
  
    <td> 
    <input type="button" name="btnImport" value="导入图片" id="btnImport" class="btn"  style="margin-left:10px;" onclick="upLoadFiles()" />
    </td>
    </tr>
    </table>
  </div>
</div>
<!--/列表-->
 
</form>
</body>
</html>