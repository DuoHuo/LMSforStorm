<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册页面</title>
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
	  	
	  	<div id="reg">
	  		<form name="regform" action="servlet/Register" method="post" onSubmit="return isRegValid(this);">
		  		<div class="regcontainer" >
		  			<div class="header">
		  				<span>Register</span>
		  			</div>
		  			<div class="username">
		  				<span class="inputHead">用户名:</span><input type="text" name="username" id="input_style" onfocus="cls()" onblur="res()" value="输入用户名" />
		  			</div>
		  			<div class="pwd1">
		  				<span class="inputHead">密码 :</span><input type="password" name="password" id="input_style" />
		  			</div>
		  			<div class="pwd1">
		  				<span class="inputHead">确认密码 :</span><input type="password" name="pwdConfirm" id="input_style" />
		  			</div>
		  			<div class="userface">
		  				<span class="inputHead">选择头像 :</span>
		  				<select class="useropt" name="secuserimg" onchange='showHead("<%=basePath%>", this.options[this.selectedIndex].value)'>
		  					<option>user1</option>
		  					<option>user2</option>
		  					<option>user3</option>
		  					<option>user4</option>
		  					<option>user5</option>
		  					<option>user6</option>
		  					<option>user7</option>
		  					<option>user8</option>
		  					<option>user9</option>
		  					<option>user10</option>
		  					<option>user11</option>
		  					<option>user12</option>
		  					<option>user13</option>
		  				</select>
		  				<div id="preuserface">
			  				<img id="presecimg" class="userimg" src="<%=basePath%>images/user/user1.png">
			  			</div>
		  			</div>
		  			<div class="btn3">
		  				<button type="submit" class="btn1">注册</button>
		  				<button type="button" class="btn2" onclick="location.href='<%=basePath %>login.jsp'">返回</button>
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
	  	<!-- reg end -->
  	
  	</div>
  	<!-- 表单主体end -->
  	
  	<div id="footer">
  		<span align="center"><p class="footer-text">Copyright &copy; 2014 TeamTank Group 刘斌&卢文泉</p></span>
  	</div>
  </body>
</html>
