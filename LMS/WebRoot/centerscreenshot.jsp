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
    
    <title>封面制作</title>
    
  </head>
  
  <body>
    <div id="menu-container">
		<div class="divs">
			<!-- share start -->
			<div class="content portfolio" >
				<div class="container">
					<div class="row">
			        	<div class="col-md-12">
			            	<h1>封面制作</h1>
			            </div>
			        </div>
				  	<div class="row templatemo_portfolio">
				       	<c:forEach items="${courseList}" var="ls" varStatus="st">
				        	<div class="col-md-3 col-sm-12">
				            	<div class="gallery-item">
				            		<div onmouseout="this.getElementsByTagName('div')[0].style.display='none';" onmouseover="this.getElementsByTagName('div')[0].style.display=''">
										<img src="<%=basePath %>images/course/<c:out value="${ls.image}"/>.png" alt="gallery 4">
										<div style="display: none;" class="change_btn_dl_div">
											<a class="btn_dl" href="<%=basePath %>play/coursePlay.jsp?courseID=<c:out value="${ls.courseID}"/> "><div class="fa fa-camera"></div></a>
										</div>
									</div>
									<div>
				                       	<div class="templatemo_teamtitle" title="<c:out value="${ls.courseTitle}"/>"><c:out value="${ls.courseTitle}"/></div>
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
