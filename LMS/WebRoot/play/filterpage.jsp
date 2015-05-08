<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>播放选择界面</title>
    
	<meta charset="utf-8">
	<link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
	<link rel="stylesheet" href="<%=basePath %>css/templatemo_misc.css">
	<link rel="stylesheet" href="<%=basePath %>css/templatemo_style.css">
	<link rel="stylesheet" href="<%=basePath %>play/images/playframe.css" type="text/css">
	
	<script type="text/javascript">
		function restudy( courseID ){
			window.parent.location.href = "<%= basePath %>servlet/FilterCourseServlet?control=restudy&courseID="+courseID;
		}
		function reback(){
			window.parent.location.href = "<%=basePath %>servlet/ViewCourse"
		}
	</script>
  </head>
  
  <body id="tt_left">
  	<div class="content" >
		<div class="container">
			<div class="row">
				<div class="col-md-12">
  					<h1>恭喜您，此课程您已经完成啦，您可以：</h1>
  				</div>
			</div>
			<div class="clear"></div>
		    <div  class="filterrow">
		    	<div class="col-md-12 mianban-div">
		        	<a  href="javascript:void(0)" onclick="restudy('<%=request.getParameter("courseID") %>')"><div class="restudy"><div class="fa fa-refresh"></div>重新学习</div></a>
		        	<a  href="javascript:void(0)" onclick="reback()"><div class="reback"><div class="fa fa-sign-out"></div>返回平台</div></a>
		        </div>
		    </div>
		</div>
	</div>	
  </body>
</html>