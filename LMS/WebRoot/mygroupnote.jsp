<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我的笔记</title>
    
	<meta charset="utf-8">
	<script type="text/javascript" src="<%=basePath%>xheditor-1.1.14/jquery-1.4.4.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/mynote.css">
	<script type="text/javascript" src="<%=basePath%>xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>xheditor-1.1.14/xheditor_plugins/ubb.min.js"></script>
	
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>我的笔记</h1>
				</div>
			</div>
  		</div>
  		
  		<!-- 显示笔记 -->
  		<div class="mod">
		  	<ul>
			  	<c:forEach items="${noteGroupList}" var="ls" varStatus="st" >
			  		<li class="course-notes">
			  			<form action="<%=basePath%>servlet/LoadNote" method="post" name="notecontent">
			  				<input type="hidden" name="courseTitle" value="${ls.courseTitle}">
				  			<div class="groupnotelist">
				  				<div class="noteimg"></div>
								<div class="info">
									<div class="grcoursetitle" title="<c:out value="${ls.courseTitle}"/>"><c:out value="${ls.courseTitle}"/></div>	
									<div class="coursenum"><c:out value="${ls.myNoteNum}"/>条我的笔记</div>
									<div class="coursenum"><c:out value="${ls.otherNoteNum}"/>条共享笔记</div>	
									<button type="submit" class="seedetail">查看详细信息</button>
								</div>
				  			</div>
			  			</form>
			  		</li>
			  	</c:forEach>
		  	</ul>
		  </div>
  	</div>
  </body>
</html>

