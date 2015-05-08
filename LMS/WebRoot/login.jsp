<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns:wb="http://open.weibo.com/wb">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登陆界面</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/login.css">
	<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
  </head>
  	
  <body>
  	<div id="header">
  		<div class="title">TeamTank</div>
  		<div class="subtitle">LMS for SCORM 1.2</div>
  	</div>
  	
  	<!-- 表单主体 -->
  	<div class="formcontainer">
  		<div id="login">
	  		<form name="loginform" action="servlet/Check" method="post" onSubmit="return isLoginValid(this);">
		  		<div class="logincontainer" >
		  			<div class="header">
		  				<span>Login</span>
		  			</div>
		  			<div class="username">
		  				<span class="inputHead">用户名:</span><input type="text" name="username" id="input_style" onfocus="cls()" value="输入用户名" />
		  			</div>
		  			<div class="pwd">
		  				<span class="inputHead">密码 :</span><input type="password" name="password" id="input_style" />
		  			</div>
		  			<div class="radio">
		  				<input id="user" type="radio" name="kind" value="user" checked="checked" /><label for="user">普通用户</label>&nbsp&nbsp
		  				<input id="admin" type="radio" name="kind" value="manager" /><label for="admin">管理员</label>
		  			</div>
		  			<div class="btn">
		  				<button type="submit" class="btn1">登录</button>
		  				<button type="button" class="btn2" onclick="window.location.href='<%=basePath %>reg.jsp'">注册</button>
		  			</div>
		  			
		  		</div>
  			</form>
  			
	  		<div class="err">
		  		<%
				 	if (request.getAttribute("err") != null) {
				 %> 
				 <%=request.getAttribute("err")%> <%
				 	}
				 %> 
			</div>
			
	  	</div>
	  	<!-- login end -->
  	</div>
  	<!-- 表单主体end -->
  	<div id="footer">
  		<span><p class="footer-text">Copyright &copy; 2014 TeamTank Group 刘斌&卢文泉</p></span>
  	</div>
  </body>
</html>
