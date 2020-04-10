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
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>居民信息采集</title>

<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />

<script type="text/javascript" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/admin/scripts/jquery/Validform_v5.3.2.js"></script>  
<script type="text/javascript" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" src="js/admin/scripts/webuploader/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>

  

<script type="text/javascript">
   /*  $(function () {
        //初始化表单验证
        $("#form1").initValidform();
        
    });
     $(function () {
       $(".mainbody").Validform({
      
		 beforeSubmit : function(curform) {
		  
		 
          if($("#img_ul li").length!=1) 
              {
                  alert("只能上传一张被采集人正面白色背景照片!");   
                  return false;
               }  
			
				return true;
				//这里明确return false的话表单将不会提交;
			}   
			
		});
	});  */
	 
     $(function () {
        //初始化上传控件
        $(".upload-img").InitUploader({ sendurl: "user/upload", swf: "js/admin/scripts/webuploader/uploader.swf" });
        $(".upload-album").InitUploader({
        	btntext: "批量上传", 
        	multiple: true, 
        	water: true, 
        	thumbnail: true, 
        	filesize: "1024000", 
        	sendurl: "user/upload",
        	swf: "js/admin/scripts/webuploader/uploader.swf",
        	filetypes: "jpg,jpge,png,gif" 
        	});
        //创建上传附件
        $(".attach-btn").click(function () {
            showAttachDialog();
        });
    });
    //初始化附件窗口
    function showAttachDialog(obj) {
        var objNum = arguments.length;
        var attachDialog = top.dialog({
            id: 'attachDialogId',
            title: "上传附件",
            url: 'dialog/dialog_article_attach.aspx',
            width: 500,
            height: 180,
            onclose: function () {
                var liHtml = this.returnValue; //获取返回值
                if (liHtml.length > 0) {
                    $("#showAttachList").children("ul").append(liHtml);
                }
            }
        }).showModal();
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            attachDialog.data = obj;
        }
    }
    //删除附件节点
    function delAttachNode(obj) {
        $(obj).parent().remove();
    }
    function openWin(url, name, iWidth, iHeight) {
        //获得窗口的垂直位置 
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
        //获得窗口的水平位置 
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
        window.open(url, name, 'height=' + iHeight + ',innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
    }

  function upLoadFiles(){
  
  
        if($("#img_ul li").length!=1) 
         {
                  alert("只能上传一张被采集人正面白色背景照片!");   
                  return false;
         }  
      
             
       
               var formData = new FormData($('#form1')[0]);//序列化表单，
                 
                $.ajax({
                type: 'post',
                url: "tempUserAudit/tempUserAuditImportIdCardImg",
                data: formData ,
                processData: false,
                contentType: false,
                async: true,
                dataType: "json",
             
                success: function (data, status) { 
               alert(data.msg);
               window.location.href = data.url+"?status=${status}&dataType=${dataType}&type=${type}";
                  
                   
		     }
       })  
       
   
     
     
 }
	 

</script>
</head>
<body class="mainbody">
<form method="post" action="tempUserAudit/tempUserAuditImportIdCardImg" id="form1" enctype="multipart/form-data">
  <input type="hidden" name="user_township" id="user_township" value="${tempUser.user_township}" />
  <input type="hidden" name="data_type" id="data_type" value="${tempUser.data_type}" />   
 
<!--导航栏-->
<div class="location">
  <a href="tempUserAudit/toTempUserAuditList?status=${status}&dataType=${dataType}&type=${type}" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="tempUserAudit/toTempUserAuditList?type=${type}"><span>居民信息审核列表</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>居民信息采集</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">基本资料</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" >
  
    
    <c:if test="${!empty tempUser}">
  <dl>
    <dt>所属类型</dt>
    <dd>
      <div class="rule-single-select">
        <select name="data_type" id="data_type" datatype="*" errormsg="请选择所属类型！" sucmsg=" " disabled="false">
			<option value="">请选择所属类型...</option>
			<option value="机关事业养老保险" ${tempUser.data_type=='机关事业养老保险'?"selected='selected'":'' }>机关事业养老保险</option>
			<option value="城乡居民养老保险" ${tempUser.data_type=='城乡居民养老保险'?"selected='selected'":'' }>城乡居民养老保险</option>
			<option value="企业职工养老保险" ${tempUser.data_type=='企业职工养老保险'?"selected='selected'":'' }>企业职工养老保险</option>

		</select>
      </div>
      <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  
  <c:if test="${tempUser.data_type=='城乡居民养老保险'}"> 
  <dl>
    <dt>所属乡镇办</dt>
    <dd>
      <div class="rule-single-select">
        <select name="user_township" id="user_township" datatype="*" ignore="ignore" errormsg="请选择乡镇办" sucmsg=" " disabled="false">
			<option value="">请选择乡镇办...</option>
			<c:forEach items="${xzbs }" var="xzb"> 
		    <option value="${xzb.title }" ${xzb.title==tempUser.user_township?"selected='selected'":'' }>${xzb.title }</option>
	        </c:forEach>
		</select>
      </div>
    </dd>
  </dl>
  
  <dl>
    <dt>村(社区)名称</dt>
    <dd><input name="user_village" type="text" value="${ tempUser.user_village}" readonly id="user_village" class="input normal"  /></dd>
  </dl>
  </c:if>
  
  <c:if test="${tempUser.data_type!='城乡居民养老保险'}">  
 <dl id="div_company" >
    <dt>所属单位</dt>
    <dd><input name="user_company" type="text"  id="user_company"value="${ tempUser.user_township}" readonly id="txtVillage" class="input normal" /></dd>
  </dl>
  </c:if>
  
  <dl>
    <dt>用户姓名</dt>
    <dd><input name="user_name" type="text"  value="${ tempUser.user_name}" readonly  id="user_name" class="input normal" datatype="*2-100" nullmsg="请输入姓名" errormsg="姓名为中文" sucmsg=" " /> <span class="Validform_checktip">*请输入真实姓名</span></dd>
  </dl> 
  <dl>
    <dt>身份证号码</dt>
    <dd><input name="user_idcard" type="text" value="${ tempUser.user_idcard}" readonly maxlength="18" readonly="readonly" id="user_idcard" class="input normal" datatype="idcard" nullmsg="请输入身份证" errormsg="身份证格式错误" sucmsg=" " /> <span class="Validform_checktip">*请认真核对,输入后不可修改</span></dd>
  </dl>
  <dl>
    <dt>手机号码</dt>
    <dd><input name="mobile" type="text" value="${ tempUser.mobile}" readonly  maxlength="11" id="mobile" class="input normal" datatype="/((^1\d{10})(,1\d{10})*$)+/" nullmsg="请填写手机号码" errormsg="手机号必须是以1开头的11位数字" /> <span class="Validform_checktip">*请认真核对,输入后不可修改</span></dd>
  </dl>
  
 <dl>
    <dt>图片相册</dt>
    <dd>
      <div class="upload-box upload-album"></div>
      <input name="hidFocusPhoto" type="hidden" id="hidFocusPhoto" class="focus-photo">
      <div class="photo-list">
        <ul id="img_ul">
          
        </ul>
      </div>
    </dd>
  </dl>
 <dl><b><font color="red"> *友情提醒：1、请上传一张被采集人正面白色背景的照片。</font></b></dl> 
   
   
  </c:if>
   <c:if test="${empty tempUser}">
    未查询到相关信息！
    </c:if>
  
   

  
  
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
    <input type="button" name="btnSubmit" value="导入图片" id="btnSubmit" class="btn" onclick="upLoadFiles()" />
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
  </div>
</div>
<!--/工具栏-->

</form>
</body>
</html>

</html>