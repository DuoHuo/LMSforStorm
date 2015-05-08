<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>问答模块</title>
    
	<meta charset="utf-8">

	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/mynote.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/discuss.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery.myPagination6.0.js"></script>
	<script type="text/javascript">
		function switchTab(n){
		    for(var i = 1; i <= 2; i++){
		        document.getElementById("tab_" + i).className = "";
		        document.getElementById("tab_con_" + i).style.display = "none";
		    }
		    document.getElementById("tab_" + n).className = "on";
		    document.getElementById("tab_con_" + n).style.display = "block";
		}
		
		function toaskque(){
			window.location.href="<%=basePath%>servlet/UserDiscussServlet?control=proAskQue"
		}
		
	</script>
  </head>
  
  <body>
  	<div id="menu-container">
	  	<div class="divs">
		  	<div class="content">
		  		<div class="mod_2">
			        <ul id="tab">
			            <li class="on" id="tab_1" onclick="switchTab(1)">课程问答</li>
			            <li id="tab_2" onclick="switchTab(2)">我的问答</li>
			        </ul>
			    </div>
		  		<div class="askdiv">
		    		<button type="button" class="askbtn" onclick="toaskque()">我要提问</button>
	    		</div>
				<div class="mod_1">
					
			        <div class="clear"></div>
				    <div>
				        <ul id="tab_con">
				        	<li id="tab_con_1">
								<d:forEach items="${QuestionList}" var="ql">
									<!-- 通过表单传值，预防get传值中文乱码 -->
									<div class="coursedis">
										<span class="distitle" title="<d:out value="${ql.courseTitle}"/>">标题：《<d:out value="${ql.courseTitle}"/>》</span>
										<span class="recordnum"><d:out value="${ql.recordNum}"/>&nbsp;条提问</span>
										<div class="controlform">
											<d:if test="${ql.recordNum != 0}">
												<form action="<%=basePath%>servlet/UserDiscussServlet" method="post" name="course">
													<input type="hidden" name="coursetitle" value='<d:out value="${ql.courseTitle}"/>' />
													<input type="hidden" name="control" value="showcoursedis"/>
													<button type="submit">点击进入</button>
												</form>
											</d:if>
										</div>
									</div>
								</d:forEach>
				        	</li>
				        	
				        	<li id="tab_con_2">
								<d:forEach items="${UserQuestionList}" var="uql" varStatus="st">
				            		<div class="coursedis">
										<span class="distitle" title="<d:out value="${uql.courseTitle}"/>">标题：《<d:out value="${uql.courseTitle}" />》</span>
										<span><d:out value="${uql.queNum}"/>&nbsp;条提问</span>
										<span><d:out value="${uql.anNum}"/>&nbsp;条回答</span>
										<div class="controlmyform">
											<form action="<%=basePath%>servlet/UserDiscussServlet" method="post" name="course">
												<input type="hidden" name="coursetitle" value='<d:out value="${uql.courseTitle}"/>' />
												<input type="hidden" name="control" value="showcoursedis"/>
												<button type="submit">点击进入</button>
											</form>
										</div>
									</div>
				            	</d:forEach>
				        	</li>
				        </ul>
				        </div>
				    </div>
		  	</div>
	  	</div>
  	</div>
  </body>
</html>







