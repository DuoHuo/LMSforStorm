<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<jsp:include page="common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>修改密码</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
		<link rel="stylesheet" href="<%=basePath%>css/style.css"></link>
	</head>

	<body>
		<div class="content">
	  		<div class="container">
		  		<div class="row">
					<div class="col-md-12">
						<h1>修改密码</h1>
					</div>
				</div>
				
				<form action="<%=basePath%>servlet/ChangePwdServlet" method="post" name="changePwd" id="chform">
					<div class="chpwdform">
						<div class="oldpwd">
							<div class="left_head">旧密码</div>
							<input class="right_input" type="text" name="oldPwd">
						</div>
						<div class="newpwd">
							<div class="left_head">新密码</div>
							<input class="right_input" type="password" name="newPwd">
						</div>
						<div class="newpwdconfirm">
							<div class="left_head">确认密码</div>
							<input class="right_input" type="password" name="newPwdConfirm">
						</div>
						
						<div class="opebtn">
							<input class="inputbtn" type="button" value="提交" onclick="isChValid()">
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
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>
