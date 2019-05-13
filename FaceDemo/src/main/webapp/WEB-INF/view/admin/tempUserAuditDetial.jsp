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
<title>居民信息审核</title>

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
   
     $(function () {
        //初始化上传控件
        $(".upload-img").InitUploader({ sendurl: "tempUserAudit/upload", swf: "js/admin/scripts/webuploader/uploader.swf" });
        $(".upload-album").InitUploader({
        	btntext: "批量上传", 
        	multiple: true, 
        	water: true, 
        	thumbnail: true, 
        	filesize: "1024000", 
        	sendurl: "tempUserAudit/upload",
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


</script>
</head>
<body class="mainbody">
<form method="post" action="tempUserAudit/tempUserAdd?status=${status}&type=${type}" id="form1" enctype="multipart/form-data">
<!--  <input type="hidden" name="is_lock" id="is_lock" value="0" /> -->
 <input type="hidden" name="flag" id="flag" value="1" />
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTYwMDkyNzEwNA9kFgICAQ9kFgYCAQ8QZBAVBxLor7fpgInmi6nop5LoibIuLi4P6LaF57qn566h55CG57uED+ezu+e7n+euoeeQhuWRmAzmiYDplb/mnYPpmZAS5Z+O5Lmh5bGF5L+d5Lit5b+DDOacuuWFs+S/nemZqQbmtYvor5UVBwABMQEyATMBNAE1ATYUKwMHZ2dnZ2dnZ2RkAgMPEGRkFgECAWQCBQ8WAh4HVmlzaWJsZWcWAgIBDxBkEBUbFeivt+mAieaLqeS5oemVh+WKni4uLgnmnLHpmIHkuaEJ6KSa5rKz6ZWHCeW8oOW+l+S5oQnpopblt53lip4J5bCP5ZCV5LmhCeWkj+mDveWKngnml6DmooHplYcJ5paH5q6K6ZWHCemhuuW6l+mVhwnnpZ7lkI7plYcJ5bGx6LSn5LmhCea1heS6leS5oQnno6jooZfkuaEJ5qKB5YyX6ZWHCemSp+WPsOWKngnpuKDlsbHplYcJ54Gr6b6Z6ZWHCeiKseefs+S5oQnpuL/nlYXplYcJ6Z+p5Z+O5YqeCemDrei/numVhwnlj6Tln47plYcJ5pa55bGx6ZWHCeaWueWyl+S5oQnojIPlnaHplYcJ6IuM5bqE5LmhFRsACeacsemYgeS5oQnopJrmsrPplYcJ5byg5b6X5LmhCemiluW3neWKngnlsI/lkJXkuaEJ5aSP6YO95YqeCeaXoOaigemVhwnmlofmrorplYcJ6aG65bqX6ZWHCeelnuWQjumVhwnlsbHotKfkuaEJ5rWF5LqV5LmhCeejqOihl+S5oQnmooHljJfplYcJ6ZKn5Y+w5YqeCem4oOWxsemVhwnngavpvpnplYcJ6Iqx55+z5LmhCem4v+eVhemVhwnpn6nln47lip4J6YOt6L+e6ZWHCeWPpOWfjumVhwnmlrnlsbHplYcJ5pa55bKX5LmhCeiMg+WdoemVhwnoi4zluoTkuaEUKwMbZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFCGNiSXNMb2Nr+cFedHN2adGau+oFqeNcySvkYy9U9TZLj3H1m+/boa8=" />
</div>

<!-- <script type="text/javascript">
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
</script> -->



<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="32BA39F4" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAC5vyWlmQUu0Cd9FtVriJx2cJNc9N2sZRgHQr0c8oBmMvyWH/otKA12bY8M1IRpJFzJqLJF8libJlanDSMTb1QBbmeRr6sdoPfvU07H8WyTbFjME6pIn9WnRtxjmGK/dhhdHmpWCqiQHaqV6CesTm5TAUulrLCak5Mj7YLs4ipiyPRv17zoz/ce8CPYu9S1wFKrdXlr6cNp+xslLYi6qPAsxVTQNw6opmbFnWJIkOMENKtKfyRP3/LiVMTAKhP6tyKFJZ1CA8bio1bJF1jQrnTJWKyHkYGoNcPuUzEwnMvANO4pM2nK2tzkmLdcQfbfGGpANh3RJxEW3fTWy8IZuQlzOJ4Emua6MZ1GnVNCmHedF4PrZZGUDb3N6Z6Zixk9qr3ZlxFQlJwokHpX5h3o5nYSjw2VeZVkF8aidMAGvrRFFaRG2an6HkefDIPm7ZQQ1VxBYuW849pjo47duQ7gwnTre2kScC7G0slt0MOUGHziMnHF201SIPVMesNY/sh87E/Qr71LjrnEzL0aAxjRPH4O/Uv0cHECMKqswvkwl2lroe0Qrs+dAV2PcX19aAXg2flIt9dcsTxGIJC5Wlg69kvbYOC3NprCjrHA2Que5c8sOvBkDnmk7Nppid4T8yLo8JSrNvJyehZfdBe6pC78Zxlsue6tr7b+Jv+8WtDOL2GExI/Wpo66ce0uh9XXGyTafIUd1z/mwd7Q1gE8vVwRfh/BWJdtcfWu8XEd7YQFygcYSeDibdcEMRwMGYs8BoHQs7fMpx+8poDnFLfPrx3+IHYDtmvC5IRHJAl8BkDkwvjAC0Y0ujQmoDGeIZ7lMpFKi03z6HaaD64bAEKCq47uAqKpwY3plgk0YBAefRz3MyBlTcHY2+Mc6SrnAqio3oCKbxYZqqO2nsNYUtW1WH4d+mUfy7ZZzDRG/XNqDaJpblqPI6yB6zvk7EnSXkkh7ys8OfVs85pbWlDO2hADfoPXD/5tdwDwEJbfn2nrgkmxsNFF0UeMtWd3l0hZ+5/zmgtYLdVg=" />
</div>
<!--导航栏-->
<div class="location">
  <a href="tempUserAudit/toTempUserAuditList?status=${status}&type=${type}" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="tempUserAudit/toTempUserAuditList?type=${type}"><span>居民信息审核列表</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>居民信息审核</span>
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

<div class="tab-content">

 <c:if test="${!empty tempUser}">
  <dl>
    <dt>所属类型</dt>
    <dd>
      <div class="rule-single-select">
        <select name="ddlRoleType" id="ddlRoleType" datatype="*" errormsg="请选择所属类型！" sucmsg=" " disabled="false">
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
    <dt>所属组别</dt>
    <dd>
      <div class="rule-single-select">
        <select name="ddlGroupId" id="ddlGroupId" datatype="*" ignore="ignore" errormsg="请选择组别" sucmsg=" " disabled="false">
			<option value="">请选择组别...</option>
			<c:forEach items="${xzbs }" var="xzb"> 
		    <option value="${xzb.title }" ${xzb.title==tempUser.user_township?"selected='selected'":'' }>${xzb.title }</option>
	        </c:forEach>
		</select>
      </div>
    </dd>
  </dl>
  
  <dl>
    <dt>村(社区)名称</dt>
    <dd><input name="txtVillage" type="text" value="${ tempUser.user_village}" readonly id="txtVillage" class="input normal"  /></dd>
  </dl>
  </c:if>
  
 
  
  <dl>
    <dt>用户姓名</dt>
    <dd><input name="txtUserName" type="text"  value="${ tempUser.user_name}" readonly  id="txtUserName" class="input normal" datatype="*2-100" nullmsg="请输入姓名" errormsg="姓名为中文" sucmsg=" " /> <span class="Validform_checktip">*请输入真实姓名</span></dd>
  </dl> 
  <dl>
    <dt>身份证号码</dt>
    <dd><input name="txtIdcard" type="text" value="${ tempUser.user_idcard}" readonly maxlength="18" readonly="readonly" id="txtIdcard" class="input normal" datatype="idcard" nullmsg="请输入身份证" errormsg="身份证格式错误" sucmsg=" " /> <span class="Validform_checktip">*请认真核对,输入后不可修改</span></dd>
  </dl>
  <dl>
    <dt>手机号码</dt>
    <dd><input name="txtMobile" type="text" value="${ tempUser.mobile}" readonly  maxlength="11" id="txtMobile" class="input normal" datatype="/((^1\d{10})(,1\d{10})*$)+/" nullmsg="请填写手机号码" errormsg="手机号必须是以1开头的11位数字" /> <span class="Validform_checktip">*请认真核对,输入后不可修改</span></dd>
  </dl>
  
  <dl>
    <dt>图片相册</dt>
    <dd>
    <c:if test="${!empty tempUser.original_path}">
      <div class="upload-box upload-album"></div>
      <input name="hidFocusPhoto" type="hidden" id="hidFocusPhoto" class="focus-photo">
      <div class="photo-list">
         <ul>
           
            <c:forEach items="${tempUser.original_path.split(';')}" var="path" >
            <li>
              <input type="hidden" name="hid_photo_name" value="4211|${path}|${path}" />
              <div class="img-box" onclick="setFocusImg(this);">
                <img src="${path}" bigsrc="${path}" />
              </div>
              
              <a href="javascript:;" onclick="javascript:openWin('${path}','','700','600');">预览</a>
              <!-- <a href="javascript:;" onclick="delImg(this);">删除</a> -->
            </li>
           </c:forEach>
            
         </ul>
      </div>
     
      </c:if>
      <c:if test="${empty tempUser.original_path}">
                   无图片信息
      </c:if>
    </dd>
  </dl>
 <dl><b><font color="red"> *友情提醒：1、请上传被采集人正面照片要求白色背景。2、上传被采集人身份证照片。3、上传采集人和被采集人合照。</font></b></dl>
  
   
   
  <div id="div_Is_status">
  <dl>
    <dt>审核状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span id="rblIsStatus">
        <input id="rblIsStatus_0" type="radio" name="rblIsStatus" value="2" ${tempUser.status==2?"checked='checked'":'' } disabled="false"/><label for="rblIsStatus_0">正常</label>
        <input id="rblIsStatus_1" type="radio" name="rblIsStatus" value="1" ${tempUser.status==1?"checked='checked'":'' } disabled="false"/><label for="rblIsStatus_1">待审核</label>
        <input id="rblIsStatus_2" type="radio" name="rblIsStatus" value="3" ${tempUser.status==3?"checked='checked'":'' } disabled="false"/><label for="rblIsStatus_2">未通过</label></span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>未通过原因</dt>
    <dd><textarea name="txtReStatus" rows="2" cols="20" id="txtReStatus" class="input" placeholder="审核未通过时填写" value="${tempUser.status_reason }" readonly>
</textarea></dd>
  </dl>
  <!-- <dl>
    <dt></dt>
    <dd><input type="submit" name="btnStatus" value="审核提交" id="btnStatus" class="btn green" /></dd>
  </dl> -->
  </div>
   
   
  </c:if>
   <c:if test="${empty tempUser}">
    未查询到相关信息！
    </c:if>
 
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
  <!--   <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" disabled="disabled" class="aspNetDisabled btn gray" /> -->
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript: history.back(-1);" />
  </div>
</div>
<!--/工具栏-->



</form>
</body>
</html>

</html>