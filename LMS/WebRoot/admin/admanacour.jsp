<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="ad" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>管理员查看课程</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin.css">
  </head>
  
  <body>
	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>管理员查看课程</h1>
				</div>
			</div>
			
			<div id="manacourse">
				<table id="myadtable">
					<tr>
						<th class="thadstyle">课程ID</th>
						<th class="thadstyle">课程标题</th>
						<th class="thadstyle">注册总人数</th>
						<th class="thadstyle">观看总时间</th>
						<th class="thadstyle">平均得分</th>
					</tr>
					<ad:forEach items="${adminmanagecourse}" var="adminmanagecourse">
						<tr>
							<td class="tdadstyle">${adminmanagecourse.courseID}</td>
							<td class="tdadstyle">${adminmanagecourse.courseTitle}</td>
							<td class="tdadstyle">${adminmanagecourse.regNum}</td>
							<td class="tdadstyle">${adminmanagecourse.totalTime}</td>
							<td class="tdadstyle">${adminmanagecourse.evaluate}</td>
						</tr>
					</ad:forEach>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
  </body>
</html>
