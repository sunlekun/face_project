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
<title>新增角色</title>

<link rel="stylesheet" type="text/css" href="js/admin/scripts/artdialog/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/icon/iconfont.css" />
<link rel="stylesheet" type="text/css" href="css/admin/skin/default/style.css" />

<script type="text/javascript" src="js/admin/scripts/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/admin/scripts/jquery/Validform_v5.3.2_min.js"></script>  
<script type="text/javascript" src="js/admin/scripts/artdialog/dialog-plus-min.js"></script>
<script type="text/javascript" src="js/admin/scripts/webuploader/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="js/admin/common.js"></script>
 
<style type="text/css">
.tree-list .col-1{ padding-left:1%; width:39%; }
.tree-list .col-2{ width:50%; }
.tree-list .col-3{ width:10%; text-align:center; }
</style>
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
        //初始化分类的结构
        initCategoryHtml('.tree-list', 1);
        //初始化分类的事件
        $('.tree-list').initCategoryTree(true);
        //是否启用权限
        if ($("#role_type").find("option:selected").attr("value") == 1) {
            $(".tree-list").find("input[type='checkbox']").prop("disabled", true);
        }
        $("#role_type").change(function () {
            if ($(this).find("option:selected").attr("value") == 1) {
                $(".tree-list").find("input[type='checkbox']").prop("checked", false);
                $(".tree-list").find("input[type='checkbox']").prop("disabled", true);
            } else {
                $(".tree-list").find("input[type='checkbox']").prop("disabled", false);
            }
        });
        //权限全选
        $("input[name='checkAll']").click(function () {
            if ($(this).prop("checked") == true) {
                $(this).parent().siblings(".col").find("input[type='checkbox']").prop("checked", true);
            } else {
                $(this).parent().siblings(".col").find("input[type='checkbox']").prop("checked", false);
            }
        });
    });
    
    
    $(function () {
       $(".mainbody").Validform({
		  	beforeSubmit : function(curform) {  
			 if($("#flag").val()=='0') 
               {
                  alert("角色名已被占用,请更换!");  
                  $("#msg").html("角色名已被占用,请更换!");                            
                  $("#msg").attr("class","Validform_checktip Validform_wrong");
                  $("#flag").val("0");
                  return false;
               }  
              else
				return true;
				//这里明确return false的话表单将不会提交;
			}   
			
		});
	}); 
	
	 $(document).ready(function(){    
   $("#role_name").blur(function(){  

    var role_name = $("#role_name").val(); 
 
     if(role_name!='')//验证是否为空
    { 
        $.ajax(
            {
                url:"<%=path%>/role/isExistRoleName",
                data:{role_name:role_name},
                type: "POST",
                dataType:"json",
                success: function(data)
                    {                          
                        if(data.status=='true')
                        {
                            $("#msg").html("角色名可使用");
                            $("#msg").attr("class","Validform_checktip Validform_right");
                            $("#flag").val("1");
                        }
                        else
                        {
                            $("#msg").html("角色名已被占用,请更换!");                            
                            $("#msg").attr("class","Validform_checktip Validform_wrong");
                            $("#flag").val("0");
                        }
                    }
            });
    }
  });
 
    });
</script>

</head>
<body class="mainbody">
<form method="post" action="role/roleAdd" id="form1">
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
  <a href="role/toRoleList" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="role/toRoleList"><span>角色</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>新增角色</span>
</div>

<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a class="selected" href="javascript:;">编辑角色信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>角色类型</dt>
    <dd>
      <div class="rule-single-select">
        <select name="role_type" id="role_type" datatype="*" errormsg="请选择角色类型！" sucmsg=" ">
	<option value="">请选择类型...</option>
	<option value="1">超级用户</option>
	<option value="2">系统用户</option>

</select>

       
      </div>
    </dd>
  </dl>
  <dl>
    <dt>角色名称</dt>
    <dd><input name="role_name" type="text" id="role_name" class="input normal" datatype="*1-100" sucmsg=" " /> <span class="Validform_checktip" id="msg">*角色中文名称，100字符内</span></dd> 
  </dl>   
  <dl>
    <dt>管理权限</dt>
    <dd>

      <div class="tree-list">
        <div class="thead">
          <div class="col col-1">导航名称</div>
          <div class="col col-2">权限分配</div>
          <div class="col col-3">全选</div>
        </div>
        <ul>
        
       <c:forEach items="${allNav }" var="nav" varStatus="status">
       <li class="layer-${nav.level }">
            <div class="tbody">
              <div class="col index col-1">
                <input type="hidden" name="rptList$ctl0${status.index}$hidName" id="rptList_hidName_${status.index}" value="${nav.name }" />
                <input type="hidden" name="rptList$ctl0${status.index}$hidActionType" id="rptList_hidActionType_${status.index}" value="${nav.action_type }" /> 
                ${nav.title }
              </div>
              <div class="col col-2">
                <span id="rptList_cblActionType_${status.index}" class="cbllist"> 
               <%--   <c:forEach items="${nav.action_type.split(',') }" var="act" varStatus="actions" >
                     <input id="rptList_cblActionType_${status.index}_${actions.index}_${status.index}" type="checkbox" name="rptList$ctl0${status.index}$cblActionType$${actions.index}" value="${act }" />
                     <label for="rptList_cblActionType_${status.index}_${actions.index}_${status.index}">
                        <c:if test="${act=='Show' }" > 显示</c:if>             
		                <c:if test="${act=='View' }" >查看</c:if>
						<c:if test="${act=='Add' }" > 添加</c:if>
						<c:if test="${act=='Edit' }" > 修改</c:if>
						<c:if test="${act=='Audit' }" > 审核</c:if>
						<c:if test="${act=='Delete' }"> 删除</c:if>
						<c:if test="${act=='Confirm' }"> 确认</c:if>
				     </label>
                </c:forEach> --%>
                
                 <c:forEach items="${nav.action_type.split(',') }" var="act" varStatus="actions" >
                     <input id="rptList_cblActionType_${status.index}_${actions.index}_${status.index}" type="checkbox" name="actList" value="${nav.id },${nav.name },${act }" /> 
                     <label for="rptList_cblActionType_${status.index}_${actions.index}_${status.index}">
                        <c:if test="${act=='Show' }" > 显示</c:if>             
		                <c:if test="${act=='View' }" >查看</c:if>
						<c:if test="${act=='Add' }" > 添加</c:if>
						<c:if test="${act=='Edit' }" > 修改</c:if>
						<c:if test="${act=='Audit' }" > 审核</c:if>
						<c:if test="${act=='Delete' }"> 删除</c:if>
						<c:if test="${act=='Confirm' }"> 确认</c:if>
				     </label>
                </c:forEach>
                </span>
              </div>
              <div class="col col-3">
                <input name="checkAll" type="checkbox" />
              </div>
            </div>
       </li>
        	
        </c:forEach>  
         
        </ul>
      </div>

    </dd>
  </dl>
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