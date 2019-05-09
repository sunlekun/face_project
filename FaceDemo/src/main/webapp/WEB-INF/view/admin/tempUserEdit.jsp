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
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
        
    });
     $(function () {
       $(".mainbody").Validform({
      
		 beforeSubmit : function(curform) {
		 
		var user_idcard = $("#user_idcard").val(); 
		if(user_idcard!=''&&user_idcard!='${tempUser.user_idcard}')//验证是否为空
		    {  
		     $.ajax(
		            {
		                url:"tempUser/isExistUserIdcard",
		                data:{user_idcard:user_idcard},
		                async: false,
		                type: "POST",
		                dataType:"json",
		                success: function(data)
		                    {   
		                  
		                        if(data.status=='true')
		                        {
		                            $("#msg").html("用户身份信息可使用");
		                            $("#msg").attr("class","Validform_checktip Validform_right");
		                            $("#flag").val("1"); 
		                        }
		                        else
		                        {   
		                            $("#msg").html("用户身份信息已采集,请勿重复采集!");                            
		                            $("#msg").attr("class","Validform_checktip Validform_wrong");
		                            $("#flag").val("0"); 
		                        }
		                    }
		            });
		      
		    }  
				
          if($("#flag").val()=='0') 
           {
                  alert("用户身份信息已采集,请勿重复采集!");   
                  $("#msg").html("用户身份信息已采集,请勿重复采集!");                            
                  $("#msg").attr("class","Validform_checktip Validform_wrong");
                  $("#flag").val("0");
                   
                            
                  return false;
               }  
               if($("#img_ul li").length!=3) 
              {
                  alert("用户身份信息采集,必须上传三张照片!\n1、请上传被采集人正面照片要求白色背景。2、上传被采集人身份证照片。3、上传采集人和被采集人合照。");   
                  return false;
               } 
				return true;
				//这里明确return false的话表单将不会提交;
			}   
			
		});
	}); 
	 
     $(function () {
        //初始化上传控件
        $(".upload-img").InitUploader({ sendurl: "tempUser/upload", swf: "js/admin/scripts/webuploader/uploader.swf" });
        $(".upload-album").InitUploader({
        	btntext: "批量上传", 
        	multiple: true, 
        	water: true, 
        	thumbnail: true, 
        	filesize: "1024000", 
        	sendurl: "tempUser/upload",
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

function changes(obj){
    var opt = obj.options[obj.selectedIndex].text;  
       
    if('城乡居民养老保险'==opt)   
     {   
    	 $("#div_xzb").show();  
         $("#user_township").attr("datatype","*");
         $("#div_village").show();  
         $("#user_village").attr("datatype","*");
     }
    else 
     { 
    	 $("#div_xzb").hide();
         $("#user_township").removeAttr("datatype");  
         $("#div_village").hide();  
         $("#user_village").removeAttr("datatype");  
      }
     
   }  

</script>
</head>
<body class="mainbody">
<form method="post" action="tempUser/tempUserEdit" id="form1" enctype="multipart/form-data">
<input type="hidden" name="id" id="id" value="${tempUser.id }" />
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
  <a href="tempUser/toTtempUserList" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="tempUser/toTtempUserList"><span>居民信息采集列表</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>居民信息编辑</span>
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
      <select name="data_type" id="data_type" onchange="changes(this)" datatype="*" errormsg="请选择所属类型！" sucmsg=" "> 
     	<option value="">请选择所属类型...</option>
	    <option value="机关事业养老保险"  ${tempUser.data_type=='机关事业养老保险'?"selected='selected'":'' }>机关事业养老保险</option> 
	    <option value="城乡居民养老保险"  ${tempUser.data_type=='城乡居民养老保险'?"selected='selected'":'' }>城乡居民养老保险</option>
	    <option value="企业职工养老保险"  ${tempUser.data_type=='企业职工养老保险'?"selected='selected'":'' }>企业职工养老保险</option> 
      </select>
      </div>
      <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  

<dl id="div_xzb" style="display:${tempUser.data_type=='城乡居民养老保险'?'block':'none'}" >
    <dt>所属乡镇办</dt>
    <dd>
      <div class="rule-single-select">
        <select name="user_township" id="user_township" datatype="*"  errormsg="请选择所属乡镇办" sucmsg=" ">
	    <option selected="selected" value="">请选择乡镇办...</option>
	    <c:forEach items="${xzbs }" var="xzb"> 
		   <option value="${xzb.title }" ${xzb.title==tempUser.user_township?"selected='selected'":'' }>${xzb.title }</option>
	    </c:forEach>

      </select>
      </div>
    </dd>
  </dl>
  
  
  
  <dl id="div_village" style="display:${tempUser.data_type=='城乡居民养老保险'?'block':'none'}">
    <dt>村(社区)名称</dt>
    <dd><input name="user_village" type="text"  id="user_village"  value="${ tempUser.user_village}"  class="input normal" datatype="*" nullmsg="请输入村(社区)名称" errormsg="请输入村(社区)名称" /> <span class="Validform_checktip">*</span></dd>
  </dl>
  
  <dl>
    <dt>用户姓名</dt>
    <dd><input name="user_name" type="text"  id="user_name" value="${ tempUser.user_name}"  class="input normal" datatype='zh2-4'  nullmsg='请输入真实姓名' errormsg='姓名为中文' sucmsg=' '/> <span class="Validform_checktip">*请输入真实姓名</span></dd>
 </dl>
 
 <dl>
    <dt>身份证号</dt>
    <dd><input name="user_idcard" type="text"  id="user_idcard" value="${ tempUser.user_idcard}"  maxlength='18' class="input normal" datatype='idcard'  nullmsg='请输入身份证' errormsg='身份证格式错误' ajaxurl='' sucmsg=' '/> <span class="Validform_checktip"  id="msg">*请认真核对,输入后不可修改</span></dd>
 </dl>
  
   
  <dl>
    <dt>手机号码</dt>
    <dd><input name="mobile" type="text" maxlength="11" id="mobile" value="${ tempUser.mobile}"  class="input normal" datatype="/((^1\d{10})(,1\d{10})*$)+/" nullmsg="请填写手机号码" errormsg="手机号必须是以1开头的11位数字" /> <span class="Validform_checktip">*请认真核对,输入后不可修改</span></dd>
  </dl>
  
  
  <!-- 
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
 <dl><b><font color="red"> *友情提醒：1、请上传被采集人正面照片要求白色背景。2、上传被采集人身份证照片。3、上传采集人和被采集人合照。</font></b></dl> -->

 <dl>
    <dt>图片相册</dt>
    <dd>
  
      <div class="upload-box upload-album"></div>
      <input name="hidFocusPhoto" type="hidden" id="hidFocusPhoto" class="focus-photo">
      <div class="photo-list">
         <ul id="img_ul">
           
            <c:forEach items="${tempUser.original_path.split(';')}" var="path" >
            <li>
              <input type="hidden" name="hid_photo_name" value="4211|${path}|${path}" />
              <div class="img-box" onclick="setFocusImg(this);">
                <img src="${path}" bigsrc="${path}" />
              </div>
              
              <a href="javascript:;" onclick="javascript:openWin('${path}','','700','600');">预览</a>
              <a href="javascript:;" onclick="delImg(this);">删除</a>
            </li>
           </c:forEach>
            
         </ul>
      </div>
     
       </dd>
  </dl>
 <dl><b><font color="red"> *友情提醒：1、请上传被采集人正面照片要求白色背景。2、上传被采集人身份证照片。3、上传采集人和被采集人合照。</font></b></dl>
     
  
    </c:if>
   <c:if test="${empty tempUser}">
    未查询到相关信息！
    </c:if>
 
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" />
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
  </div>
</div>
<!--/工具栏-->

</form>
</body>
</html>

</html>