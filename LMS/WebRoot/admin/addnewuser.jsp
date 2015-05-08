<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<title>添加新用户</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin.css">
	<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
  </head>
  
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>添加新用户</h1>
				</div>
			</div>
			
			<input class="inputbtn" type="button" name="goback" value="返回" onclick='adduserback("<%=basePath%>")'>
			
			<form method="post" id="addform" action="<%=basePath%>servlet/AddNewUserServlet" name="newuser">
				<div id="adduseform">
					<div class="addusername">
						<div class="left_head">用户名</div>
						<input class="right_input" type="text" name="userName">
					</div>
					<div class="addnewpwd">
						<div class="left_head">密码</div>
						<input class="right_input" type="text" name="password">
					</div>
					<div class="addnewpwdconfirm">
						<div class="left_head">确认密码</div>
						<input class="right_input" type="text" name="pwdConfirm">
					</div>
					<div class="isadmin">
						<div class="left_head">管理员权限</div>
						<select name="isAdmin">
		                    <option>No</option> <option>Yes</option>
		                </select>  
					</div>
					
					<div class="opebtn">
						<input class="inputbtn" type="button" value="提交" onclick="isAddValid()">
						<input class="inputbtn" type="reset" name="reset" value="重置">
					</div>
					
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
			</form>
		</div>
	</div>
  </body>
</html>
