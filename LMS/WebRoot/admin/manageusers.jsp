<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="u" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户管理</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin.css">
  </head>
  
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>学员管理</h1>
				</div>
			</div>
			
			<div class="manauser">
				<div id="manauserope">
					<span>
						<a href="<%=basePath %>admin/addnewuser.jsp">添加用户</a>
					</span>
					<span class="tips">(1:管理员，0:普通用户)</span>
				</div>
				
				<table id="mytable">
					<tr>
						<th class="thstyle">用户ID</th>
						<th class="thstyle">用户名</th>
						<!-- <th class="thstyle">密码</th> -->
						<th class="thstyle">用户权限</th>
						<th class="thstyle"colspan="3"><center>操作</center></th>
					</tr>
					<u:forEach items="${showUserInfoList}" var="userInfo">
						<tr>
							<td class="tdstyle">${userInfo.userID}</td>
							<td class="tdstyle">${userInfo.userName}</td>
							<%-- <td class="tdstyle">${userInfo.password}</td> --%>
							<td class="tdstyle">${userInfo.isAdmin}</td>
							<td class="tdstyle"><a href="<%=basePath %>servlet/ViewUserCourseInfoServlet
									?userName=<u:out value='${userInfo.userName}'/>">学习信息</a></td>
							<%-- <td class="tdstyle"><a href="<%=basePath %>servlet/DelUserServlet
									?userID=<u:out value='${userInfo.userID}'/>">删除</a></td> --%>
							<td class="tdstyle"><a href="javascript:0;">删除</a></td>
							<td class="tdstyle"><a href="<%=basePath %>servlet/FindUpdatedUserInfoServlet
									?userID=<u:out value='${userInfo.userID}'/>">更新</a></td>
						</tr>
					</u:forEach>
				</table>
			</div>
  		</div>
  	</div>
  	<jsp:include page="../footer.jsp"></jsp:include>
  </body>
</html>
