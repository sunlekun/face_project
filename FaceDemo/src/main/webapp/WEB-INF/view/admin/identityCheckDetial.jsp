<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<title>认证详情</title>

<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />

<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/scripts/jquery/Validform_v5.3.2_min.js"></script> 
<script type="text/javascript" src="js/admin/scripts/webuploader/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/uploader.js"></script>
<script type="text/javascript" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>
 
<script type="text/javascript" charset="utf-8" src="js/admin/picbox.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/dist/plyr.js"></script>
<link rel="stylesheet" type="text/css" href="css/admin/skin/picbox.css" />
<link rel="stylesheet" type="text/css" href="js/admin/dist/plyr.css" rel="stylesheet" />

  <script type="text/javascript">
    $(function () {
        //初始化上传控件
        $(".upload-img").InitUploader({ sendurl: "identityCheck/upload", swf: "js/admin/scripts/webuploader/uploader.swf" });
        $(".upload-album").InitUploader({
        	btntext: "图片上传", 
        	multiple: true, 
        	water: true, 
        	thumbnail: true, 
        	filesize: "1024000", 
        	sendurl: "identityCheck/upload",
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
<form method="post" action="randomCheck/randomCheckConfirm?status=${status}&video_status=${video_status}" id="form1">
<input type="hidden" name="flag" id="flag" value="1" />
<div class="aspNetHidden">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="" />
<input type="hidden" name="__LASTFOCUS" id="__LASTFOCUS" value="" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKLTYwMDkyNzEwNA9kFgICAQ9kFgYCAQ8QZBAVBxLor7fpgInmi6nop5LoibIuLi4P6LaF57qn566h55CG57uED+ezu+e7n+euoeeQhuWRmAzmiYDplb/mnYPpmZAS5Z+O5Lmh5bGF5L+d5Lit5b+DDOacuuWFs+S/nemZqQbmtYvor5UVBwABMQEyATMBNAE1ATYUKwMHZ2dnZ2dnZ2RkAgMPEGRkFgECAWQCBQ8WAh4HVmlzaWJsZWcWAgIBDxBkEBUbFeivt+mAieaLqeS5oemVh+WKni4uLgnmnLHpmIHkuaEJ6KSa5rKz6ZWHCeW8oOW+l+S5oQnpopblt53lip4J5bCP5ZCV5LmhCeWkj+mDveWKngnml6DmooHplYcJ5paH5q6K6ZWHCemhuuW6l+mVhwnnpZ7lkI7plYcJ5bGx6LSn5LmhCea1heS6leS5oQnno6jooZfkuaEJ5qKB5YyX6ZWHCemSp+WPsOWKngnpuKDlsbHplYcJ54Gr6b6Z6ZWHCeiKseefs+S5oQnpuL/nlYXplYcJ6Z+p5Z+O5YqeCemDrei/numVhwnlj6Tln47plYcJ5pa55bGx6ZWHCeaWueWyl+S5oQnojIPlnaHplYcJ6IuM5bqE5LmhFRsACeacsemYgeS5oQnopJrmsrPplYcJ5byg5b6X5LmhCemiluW3neWKngnlsI/lkJXkuaEJ5aSP6YO95YqeCeaXoOaigemVhwnmlofmrorplYcJ6aG65bqX6ZWHCeelnuWQjumVhwnlsbHotKfkuaEJ5rWF5LqV5LmhCeejqOihl+S5oQnmooHljJfplYcJ6ZKn5Y+w5YqeCem4oOWxsemVhwnngavpvpnplYcJ6Iqx55+z5LmhCem4v+eVhemVhwnpn6nln47lip4J6YOt6L+e6ZWHCeWPpOWfjumVhwnmlrnlsbHplYcJ5pa55bKX5LmhCeiMg+WdoemVhwnoi4zluoTkuaEUKwMbZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFCGNiSXNMb2Nr+cFedHN2adGau+oFqeNcySvkYy9U9TZLj3H1m+/boa8=" />
</div>

<div class="aspNetHidden">

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="32BA39F4" />
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAC5vyWlmQUu0Cd9FtVriJx2cJNc9N2sZRgHQr0c8oBmMvyWH/otKA12bY8M1IRpJFzJqLJF8libJlanDSMTb1QBbmeRr6sdoPfvU07H8WyTbFjME6pIn9WnRtxjmGK/dhhdHmpWCqiQHaqV6CesTm5TAUulrLCak5Mj7YLs4ipiyPRv17zoz/ce8CPYu9S1wFKrdXlr6cNp+xslLYi6qPAsxVTQNw6opmbFnWJIkOMENKtKfyRP3/LiVMTAKhP6tyKFJZ1CA8bio1bJF1jQrnTJWKyHkYGoNcPuUzEwnMvANO4pM2nK2tzkmLdcQfbfGGpANh3RJxEW3fTWy8IZuQlzOJ4Emua6MZ1GnVNCmHedF4PrZZGUDb3N6Z6Zixk9qr3ZlxFQlJwokHpX5h3o5nYSjw2VeZVkF8aidMAGvrRFFaRG2an6HkefDIPm7ZQQ1VxBYuW849pjo47duQ7gwnTre2kScC7G0slt0MOUGHziMnHF201SIPVMesNY/sh87E/Qr71LjrnEzL0aAxjRPH4O/Uv0cHECMKqswvkwl2lroe0Qrs+dAV2PcX19aAXg2flIt9dcsTxGIJC5Wlg69kvbYOC3NprCjrHA2Que5c8sOvBkDnmk7Nppid4T8yLo8JSrNvJyehZfdBe6pC78Zxlsue6tr7b+Jv+8WtDOL2GExI/Wpo66ce0uh9XXGyTafIUd1z/mwd7Q1gE8vVwRfh/BWJdtcfWu8XEd7YQFygcYSeDibdcEMRwMGYs8BoHQs7fMpx+8poDnFLfPrx3+IHYDtmvC5IRHJAl8BkDkwvjAC0Y0ujQmoDGeIZ7lMpFKi03z6HaaD64bAEKCq47uAqKpwY3plgk0YBAefRz3MyBlTcHY2+Mc6SrnAqio3oCKbxYZqqO2nsNYUtW1WH4d+mUfy7ZZzDRG/XNqDaJpblqPI6yB6zvk7EnSXkkh7ys8OfVs85pbWlDO2hADfoPXD/5tdwDwEJbfn2nrgkmxsNFF0UeMtWd3l0hZ+5/zmgtYLdVg=" />
</div>
<!--导航栏-->
<div class="location">
  <a href="identityCheck/toIdentityCheckList?type=${type}&video_status=${video_status}&user_township=${user_township}&year=${year}" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="identityCheck/identityCheckList?type=${type}"><span>身份认证审核列表</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>认证详情</span>
</div>

<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">基本信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">

<dl>
    <dt>审核人员：</dt>
    <dd>
      <input name="auditors_txt" type="text" value="${videoIdent.auditors_txt }" readonly maxlength="100" id="auditors_txt" class="input normal" datatype="*1-100" sucmsg=" " minlength="2" />
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  
 <dl>
    <dt>备注说明：</dt>
	    <dd><textarea name="txt_remarks" rows="2" cols="20" id="txtRemarks" readonly class="input" placeholder="审核未通过时填写" value="${videoIdent.txt_remarks }" >
		</textarea></dd>
  </dl>
  
   <c:if test="${videoIdent.txt_img!='NULL'}">  
  <dl>
    <dt>备注图片：</dt>
    <dd>
  
      <div class="upload-box upload-album"></div>
      <input name="hidFocusPhoto" type="hidden" id="hidFocusPhoto" class="focus-photo">
      <div class="photo-list">
         <ul id="img_ul">
     
          
            <c:forEach items="${videoIdent.txt_img.split(';')}" var="path" >
           
            <li>
              <input type="hidden" name="hid_photo_name" value="4211|${path}|${path}" />
              <div class="img-box" onclick="setFocusImg(this);">
                <img src="${path}" bigsrc="${path}" />
              </div>
              
              <a href="javascript:;" onclick="javascript:openWin('${path}','','700','600');">预览</a>
            <!--   <a href="javascript:;" onclick="delImg(this);">删除</a> -->
            </li>
           
           </c:forEach>
         
         </ul>
      </div>
     
       </dd>
  </dl>
  
     </c:if>
     
      <c:if test="${videoIdent.txt_img=='NULL'}">
	  <dl>
	    <dt>备注图片：</dt>
	    <dd><input name="txt"  value="无"  class="input normal"></dd>
	  </dl>
  
     </c:if>
     
  <dl>
    <dt>审核状态：</dt>
    <dd>
      <div class="rule-multi-radio">
        <span id="rblIsStatus">
	        <input id="rblIsStatus_0" readonly type="radio" name="rblIsStatus" value="2" ${videoIdent.video_status==2?"checked='checked'":'' }  /><label for="rblIsStatus_0">视频认证审核通过</label>
	        <input id="rblIsStatus_1" readonly type="radio" name="rblIsStatus" value="3" ${videoIdent.video_status==3?"checked='checked'":'' } /><label for="rblIsStatus_1">视频认证审核未通过</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>用户姓名：</dt>
    <dd>
      <input name="txtTitle" type="text" readonly value="${videoIdent.user_name }" maxlength="100" id="txtTitle" class="input normal"  datatype="*1-100" sucmsg=" " minlength="2" />
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>身份证号码：</dt>
    <dd>
      <input name="txtIdcard" type="text" readonly value="${videoIdent.user_idcard }" maxlength="18" id="txtIdcard" class="input normal" datatype="*1-100" sucmsg=" " minlength="2" />
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>手机号码：</dt>
    <dd>
      <input name="txtMobile" type="text" readonly value="${videoIdent.mobile }" id="txtMobile" class="input normal" />
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
<!--   <dl> -->
<!--     <dt>验证口令：</dt> -->
<!--     <dd> -->
<%--       <input name="txtCode" type="text" readonly value="${videoIdent.video_code}" id="txtCode" class="input small" datatype="n" sucmsg=" " /> --%>
<!--       <span class="Validform_checktip">*4位数字。</span> -->
<!--     </dd> -->
<!--   </dl> -->
  <dl>
    <dt>视频截图：</dt>
    <dd>
      <a href="/video_identity${videoIdent.img_url}" rel="lightbox"><img width="220" src="/video_identity${videoIdent.img_url}" onerror="this.src='css/admin/skin/default/loadimg.gif'" /></a>
    </dd>
  </dl>
  <dl>
    <dt>原始图片：</dt>
    <dd>
      <a href="/img_identity${videoIdent.user_img}?w=220&h=165&mode=max" rel="lightbox"><img src="/img_identity${videoIdent.user_img}?w=220&h=165&mode=max" onerror="this.src='css/admin/skin/default/loadimg.gif'" /></a>
    </dd>
  </dl>
  <dl>
    <dt>对比视频：</dt>
    <dd style="width:600px;">
	<video width="450" height="550" poster="css/admin/skin/default/vf.jpg" controls >
	  <source src="/video_identity${videoIdent.video_url}" type="video/mp4">
	  <!-- <source src="path/to/video.webm" type="video/webm">-->
	  <!-- Captions are optional -->
	</video>
	<script>plyr.setup();</script>
    </dd>
  </dl>
</div>
<!--/内容-->



<!--工具栏-->
<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap">
<!--     <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" /> -->
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);" />
  </div>
</div>
<!--/工具栏-->

</form>
</body>
</html>

</html>
