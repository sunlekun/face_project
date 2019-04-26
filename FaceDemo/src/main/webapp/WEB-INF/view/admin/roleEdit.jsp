<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<title>角色编辑</title>

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
<form method="post" action="role/roleEdit" id="form1">
 <input type="hidden" name="flag" id="flag" value="1" />
 <input type="hidden" name="id" id="di" value="${role.id}" />


<!--导航栏-->
<div class="location">
  <a href="role/toRoleList" class="back"><i class="iconfont icon-up"></i><span>返回列表页</span></a>
  <a href="manager/center"><i class="iconfont icon-home"></i><span>首页</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <a href="role/toRoleList"><span>角色</span></a>
  <i class="arrow iconfont icon-arrow-right"></i>
  <span>角色编辑</span>
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
				
				<c:if test="${role.role_type==1}"> 
				    <option value="1" selected="selected">超级用户</option>
			    	<option value="2" >系统用户</option>
				</c:if>
				<c:if test="${role.role_type==2}"> 
				   <option value="1"> 超级用户</option>
				   <option value="2" selected="selected">系统用户</option>
				</c:if> 
		
		  </select>

       
      </div>
    </dd>
  </dl>
  <dl>
    <dt>角色名称</dt>
    <dd><input name="role_name" type="text" id="role_name"  value='${role.role_name }' class="input normal" datatype="*1-100" sucmsg=" " /> <span class="Validform_checktip" id="msg">*角色中文名称，100字符内</span></dd>
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
                 <c:forEach items="${nav.action_type.split(',') }" var="act" varStatus="actions" >
                     <c:set var="sum" value="${nav.id },${nav.name },${act }" /> 
                     <input id="rptList_cblActionType_${status.index}_${actions.index}_${status.index}" type="checkbox"  ${fn:contains(roleNavs,sum)?"checked='true'":""}    name="actList" value="${nav.id },${nav.name },${act }" /> 
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