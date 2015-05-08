<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户问答</title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/discuss.css">

  </head>
  
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>课程《<%=session.getAttribute("precourtitle")%>》的相关问答</h1>
				</div>
				<div class="col-md-12">
					<a class="retbtn" href="<%=basePath%>servlet/UserDiscussServlet?control=base">返回</a>
				</div>
			</div>
			
			<div class="precoucontainer">
				<p:forEach items="${QuestionDetailList}" var="pqdl" varStatus="pst">
					<div class="precourselist">
						<div class="asender">
							<div class="asenderimg">
								<img alt="" src="<%=basePath%>images/user/<p:out value="${pqdl.imgPath}"/>.png">
							</div>
							<span class="pqdlsn"><p:out value="${pqdl.sendName}"/></span>
							<span class="pqdlt"><p:out value="${pqdl.time}"/></span>
						</div>
						<div class="rightcont">
							<div class="quecont">
								<div class="queimg"></div>
								<div class="quecontdetail" title="<p:out value="${pqdl.content}"/>">
									<p:out value="${pqdl.content}"/>
								</div>
							</div>
							<p:choose>
								<p:when test="${pqdl.flag  == 0}">
									<div class="latestan">
										<div class="latestimg"></div>
										<span class="latestac" title="<p:out value="${pqdl.latestAContent}"/>">
											最新回答：<p:out value="${pqdl.latestAContent}"/>
										</span>
										<span class="latestasn">来自：<p:out value="${pqdl.latestASendName}"/>,
											&nbsp;&nbsp;&nbsp;&nbsp;<p:out value="${pqdl.latestASendTime}"/>
										</span>
									</div>
								</p:when>
								<p:otherwise>  
									<div class="latestan">
										<div class="latestimg"></div>
										<span class="latestac">
											最新回答：<p:out value="${pqdl.latestAContent}"/>
										</span>
									</div>
   								</p:otherwise>
							</p:choose>
						</div>
						<div class="precouform">
							<form action="<%=basePath%>servlet/UserDiscussServlet" method="post">
								<input name="control" type="hidden" value="specialcour" />
								<!-- 将课程标题传入后台 -->
								<input name="spequeid" type="hidden" value="<p:out value="${pqdl.quesionId}"/>" />
								<button type="submit">点击进入</button>
							</form>
						</div>
					</div>
				</p:forEach>
			</div>
  		</div>
  	</div>
  </body>
</html>
