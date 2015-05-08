<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="uppercenter.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>课程信息</title>
    
  </head>
  
  <body>
    <div id="menu-container">
		<div class="divs">
			<!-- share start -->
			<div class="content portfolio" >
				<div class="container">
					<div class="row">
			        	<div class="col-md-12">
			            	<h1>课程信息</h1>
			            </div>
			        </div>
				  	<div class="row templatemo_portfolio">
				       	<c:forEach items="${MyCourseScoInfoList}" var="ls" varStatus="st">
				       	<div class="col-md-16">
				        	<div class="col-md-3 col-sm-12">
				            	<div class="gallery-item">
									<img src="<%=basePath %>images/course/<c:out value="${ls.image}"/>.png" alt="gallery 4">
			                        <div class="templatemo_teamtitle" title="<c:out value="${ls.courseTitle}"/>"><c:out value="${ls.courseTitle}"/></div>
									<div class="fa fa-history infodiv">&nbsp观看时间:&nbsp&nbsp<font>${ls.totalTime}</font></div>
									<div class="fa fa-arrow-circle-right infodiv">&nbsp完成进度:&nbsp&nbsp<font>${ls.lessonStatus}</font></div>
									<div class="fa fa-file-text-o infodiv">&nbsp测试结果:&nbsp&nbsp<font>${ls.score}</font></div>
									<div class="fa fa-book infodiv">&nbsp笔记数目:&nbsp&nbsp<font>${ls.noteNum}</font>篇</div>
								</div>
				            </div>
			            		
				         </div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
    <jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>
