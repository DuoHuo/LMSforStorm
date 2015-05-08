<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.* , com.tank.model.Users ;" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Users user = (Users) session.getAttribute("findeduserinfo");
String userID = request.getParameter("userID");
%>
<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>更新学员信息</title>
    
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin.css">
	<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
  </head>
  
  <body>
	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>更新学员&nbsp;&nbsp;<%=user.getUserName()%>&nbsp;&nbsp;的信息</h1>
				</div>
			</div>
			
			<input class="inputbtn" type="button" name="goback" value="返回" onclick='adduserback("<%=basePath%>")'>

			<div class="uptuserform">
				<form method="post" id="uptuseform" action="<%=basePath%>servlet/UpdateUserInfoServlet?userID=<%=userID%>" name="updateuser">
					<div class="uptname">
						<div class="uptleft">用户名</div>
						<input class="right_input" name="userName" value="<%=user.getUserName()%>">
					</div>
					<div class="uptpwd">
						<div class="uptleft">密码</div>
						<input class="right_input" name="password" type="password" value="<%=user.getPassword()%>">
					</div>
					
					<div class="opebtn">
						<input class="uptinputbtn" type="button" value="提交" onclick="isUptValid()">
					</div>
				</form>
				<div align="center">
					<%
						if (request.getAttribute("err") != null) {
					%>
					<%=request.getAttribute("err")%>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>
