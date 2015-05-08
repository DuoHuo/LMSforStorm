<%@ page language="java" import="java.util.*,com.tank.model.Discuss" pageEncoding="UTF-8"%>
<%@ taglib prefix="sp" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Discuss specoudet =  (Discuss) request.getAttribute("SpeQueDet");
%>
<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>问题答疑</title>
    
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/discuss.css">
	<script type="text/javascript" src="<%=basePath%>js/discuss.js"></script>
  </head>
  
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>关于《<%=specoudet.getCourseTitle()%>》的详细问答</h1>
				</div>
			</div>
			<div class="col-md-12">
				<a class="retbtn" href="<%=basePath%>servlet/UserDiscussServlet?control=base">返回</a>
			</div>
			<div id="specdet">
				<div class="specleft">
					<img alt="" src="<%=basePath%>images/user/<%=specoudet.getImgPath()%>.png">
					<span class="pqdlsn"><%=specoudet.getSendName()%></span>
					<span class="pqdlt"><%=specoudet.getTime()%></span>
				</div>
				<div class="specright" title="<%=specoudet.getContent()%>">
					<%=specoudet.getContent()%>
				</div>
			</div>
			
			<sp:forEach items="${SpeAnDet}" var="sad" varStatus="vst">
				<div class="specanlist">
					<div class="cspecanleft">
						<div class="asenderimg">
							<img alt="" src="<%=basePath%>images/user/<sp:out value="${sad.imgPath}"/>.png">
						</div>
						<span class="pqdlsn"><sp:out value="${sad.sendName}"/></span>
						<span class="pqdlt"><sp:out value="${sad.time}"/></span>
					</div>
					<div class="cspecanright" title="${sad.content}">
						<sp:out value="${sad.content}"/>
					</div>
				</div>
			</sp:forEach>
			<div id="specaddan">
				<img alt="" src="<%=basePath%>images/user/answer.jpg">
				<div class="addanform">
					<form action="<%=basePath%>servlet/UserDiscussServlet" method="post" name="anform" id="anformid">
						<input type="hidden" value="<%=specoudet.getQuesionId()%>" name="questionid" />
						<input type="hidden" value="<%=specoudet.getCourseTitle()%>" name="coutitle" />
						<input type="hidden" name="control" value="addAn" />
						<textarea type="text" name="addancont" class="addancont"></textarea>
						<button type="button" class="addanbtn" onclick="isAnValid(this)">回答</button>
					</form>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>
 