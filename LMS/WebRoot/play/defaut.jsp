<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>启动界面</title>
	<link rel="stylesheet" href="<%=basePath %>play/images/playframe.css" type="text/css">
	<script language="JavaScript">
	function opencourse()
	{
		var courseID = window.parent.courseID;
		window.parent.frames[0].initFlag = true;
		window.parent.frames[1].location.href="<%=basePath %>servlet/MenuServlet?courseID=" + courseID;
		window.location.href="<%=basePath%>servlet/ProViewCourse?courseID=" + courseID;
	}
	</script>
  </head>
  
  <body id="tt_left" marginwidth="0" marginheight="0">
  <div style="position: absolute;left:300px;top:200px;font-size:50;height:900px;">
    <a href="javascript:opencourse()"><font>点击启动课程</font></a> <br>
  </div>
  </body>
</html>
