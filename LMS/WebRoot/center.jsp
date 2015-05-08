<%@ page language="java" contentType="text/html;charset=UTF-8" %>
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
    <title>功能面板</title>
  </head>
  
  <body>
	<div id="menu-container">
		<div class="divs">
    		<!-- 管理 start -->
			<div class="content" >
  				<div class="container">
  					<div class="row">
    					<div class="col-md-12">
        					<h1>功能面板</h1>
        				</div>
    				</div>
      				<div class="clear"></div>
					    <div  class="row">
					    	<div class="col-md-12 mianban-div">
					        	<a  href="<%=basePath%>servlet/LoadGroupNoteServlet"><div class="col-md-2 mianban mianban1"><div class="fa fa-pencil">我的笔记</div></div></a>
					        	<a  href="<%=basePath %>servlet/ShowMyCourseInfoServlet"><div class="col-md-2 mianban mianban2"><div class="fa fa-list-ol">课程信息</div></div></a>
					        	<a  href="<%=basePath%>servlet/LoadAllCourse?screenshot=a"><div class="col-md-2 mianban mianban3"><div class="fa fa-download">课件下载</div></div></a>
					        	<a  href="<%=basePath%>servlet/UserDiscussServlet?control=base"><div class="col-md-2 mianban mianban4"><div class="fa fa-users">问答互动</div></div></a>
					        	<a  href="<%=basePath%>servlet/ShowStatisticsInfoServlet"><div class="col-md-2 mianban mianban5"><div class="fa fa-bar-chart-o">统计分析</div></div></a>
					        	<a  href="<%=basePath %>servlet/EvaluateCourseServlet"><div class="col-md-2 mianban mianban6"><div class="fa fa-star-half-o">评价课程</div></div></a>
					        	<c:if test="${admin == true}">
					        		<a  href="<%=basePath%>servlet/ShowUserInfoServlet"><div class="col-md-2 mianban mianban7"><div class="fa fa-child ">学员管理</div></div></a>
					        		<a  href="<%=basePath%>servlet/ShowCourseInfoServlet"><div class="col-md-2 mianban mianban8"><div class="fa fa-database">课件管理</div></div></a>
					        		<a  href="<%=basePath%>servlet/LoadAllCourse?screenshot=b"><div class="col-md-2 mianban mianban9"><div class="fa fa-camera">封面制作</div></div></a>
					        	</c:if>
					        </div>
					    </div>
    					<div class="clear"></div>
    					<div class="row">
    						<div class="col-md-8 col-sm-12">

             					<div class="clear"></div>
       						</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	<!-- 管理 end -->
	<jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>
