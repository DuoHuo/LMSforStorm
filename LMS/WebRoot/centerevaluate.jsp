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
    <title>评价课程</title>
  </head>
  
  <body>
  <div id="menu-container">
		<div class="divs">
			<!-- share start -->
			<div class="content portfolio" >
				<div class="container">
					<div class="row">
			        	<div class="col-md-12">
			            	<h1>评价课程</h1>
			            </div>
			        </div>
				  	<div class="row templatemo_portfolio">
				       	<c:forEach items="${comList}" var="ls" varStatus="st">
				        	<div class="col-md-3 col-sm-12">
				            	<div class="gallery-item">
				            		<div onmouseout="this.getElementsByTagName('div')[0].style.display='none';" onmouseover="this.getElementsByTagName('div')[0].style.display=''">
										<img src="<%=basePath %>images/course/<c:out value="${ls.image}"/>.png" alt="gallery 4">
										<div style="display: none;" class="change_btn_div">
											
										</div>
									</div>
									<div>
				                       	<div class="templatemo_teamtitle" title="<c:out value="${ls.courseTitle}"/>"><c:out value="${ls.courseTitle}"/></div>
										<div class="templatemo_teampost">我的评价</div>&nbsp;
										<div class="stardiv1">
											<input name="<c:out value="${ls.courseID}"/>" type="radio" class="star" value="1" <c:out value="${ls.evaluate[0]}" /> onChange="subScore('<c:out value="${ls.courseID}"/>')">
											<input name="<c:out value="${ls.courseID}"/>" type="radio" class="star" value="2" <c:out value="${ls.evaluate[1]}" /> onChange="subScore('<c:out value="${ls.courseID}"/>')">
											<input name="<c:out value="${ls.courseID}"/>" type="radio" class="star" value="3" <c:out value="${ls.evaluate[2]}" /> onChange="subScore('<c:out value="${ls.courseID}"/>')">
											<input name="<c:out value="${ls.courseID}"/>" type="radio" class="star" value="4" <c:out value="${ls.evaluate[3]}" /> onChange="subScore('<c:out value="${ls.courseID}"/>')">
											<input name="<c:out value="${ls.courseID}"/>" type="radio" class="star" value="5" <c:out value="${ls.evaluate[4]}" /> onChange="subScore('<c:out value="${ls.courseID}"/>')">
										</div>
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
