<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="a" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:include page="../common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>发布问题</title>
    
	<meta charset="utf-8">
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/discuss.css">

	<script type="text/javascript">
		function isAskValid(){
			if(askform.askcontent.value == ""){
				alert("请输入提问内容！");
				return false;
			}
			if(askform.selecttitle.value == ""){
				alert("您还没有注册课程，暂时没法提问哦！请您先注册课程然后在来提问！");
				return false;
			}
			return true;
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>发布问题</h1>
				</div>
			</div>
			<div class="col-md-12">
				<a class="retbtn" href="<%=basePath%>servlet/UserDiscussServlet?control=base">返回</a>
			</div>
			<div id="askdiv">
	  			<form name="askform" action="<%=basePath%>servlet/UserDiscussServlet?control=saveQue" method="post" onSubmit="return isAskValid(this)">
	  				<div class="coursetitle">
	  					<span class="lefttitle">课程标题:</span>
	  					<div class="secout">
	  						<select name="selecttitle">
		  						<a:forEach items='<%=session.getAttribute("UserCourseList")%>' var="ucl" varStatus="vs">
		  							<option><a:out value="${ucl}"></a:out>
		  						</a:forEach>
		  					</select>
	  					</div>
	  				</div>
	  				<div class="askcontent">
	  					<span class="leftcontent">内容:</span>
	  					<textarea type="text" name="askcontent"></textarea>
	  				</div>
	  				<div class="operation">
	  					<button type="submit" class="askquebtn">提交</button>
	  				</div>
	  			</form>
	  			<div class="err">
			  		<font color="red">
				  		<%
						 	if (request.getAttribute("result") != null) {
						 %> 
						 <%=request.getAttribute("result")%> <%
						 	}
						 %> 
					 </font>
				</div>
	  		</div>
	  		<!-- 提问模块结束 -->
  		</div>
	</div>
  </body>
</html>
