<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<html>
<html>
<head>
	<script type="text/javascript">
	</script>
</head>
<body>


<%
   // 重定向到新地址
    
    //接收客户端发送来的请求数据，参数名称为msg
  	 String BizToken= request.getParameter("BizToken");//用request得到
	if(BizToken==null){
		response.sendRedirect("http://111.229.177.211:8080/FaceDemo/user/login.jsp");
	}else{
		//response.sendRedirect("/faceDetectAuth/notify.do?BizToken="+BizToken);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/faceDetectAuth/notify.do?BizToken="+BizToken);
		 rd.forward(request, response);
		}
	
%>

</body>
</html>