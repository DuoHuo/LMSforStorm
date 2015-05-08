<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看学员课程信息</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin.css">
	<script type="text/javascript" src="<%=basePath%>js/login.js"></script>
  </head>
  
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>学员&nbsp;&nbsp;<%=session.getAttribute("viewspeuser")%>&nbsp;&nbsp;的详细学习信息</h1>
				</div>
			</div>
			
			<input class="inputbtn" type="button" name="goback" value="返回" onclick='adduserback("<%=basePath%>")'>
			
			<div class="viewspecuser">
				<table id="mytable">
					<tr>
						<th class="thstyle">课程标题</th>
						<th class="thstyle">观看时间</th>
						<th class="thstyle">完成情况</th>
						<th class="thstyle">我的笔记</th>
					</tr>
					<s:forEach items="${MyCourseScoInfoList}" var="ls">
						<tr>
							<td class="tdstyle">${ls.courseTitle}</td>
							<td class="tdstyle">${ls.totalTime}</td>
							<td class="tdstyle">${ls.lessonStatus}</td>
							<td class="tdstyle"><font color=#00b060>${ls.noteNum}</font>&nbsp;条课程笔记</td>
						</tr>
					</s:forEach>
				</table>
			</div>
		</div>
	</div>
  </body>
</html>
