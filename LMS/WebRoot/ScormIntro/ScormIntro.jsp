<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>播放教程</title>
	<meta charset="utf-8">
	<link rel="stylesheet" href="<%=basePath %>css/style.css">
  </head>
  
  <body>
  	<input class="inputbtn" type="button" name="goback" value="返回" onclick="window.location.href='<%=basePath%>index.jsp'">
  	
  	<div id="introplayer">
  		<embed src="<%=basePath%>ScormIntro/ScormIntro.mp4" autostart="true" loop="true" width="1270" height="720" >
  	</div>		
  </body>
</html>
