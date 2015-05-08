<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>播放器导航</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<%=basePath %>play/images/playframe.css" type="text/css">
   <script language=javascript>
   var courseID = window.parent.courseID;
   // 隐藏DIV层
	function closediv() {
		window.document.getElementById("temptexta").value = window.document.getElementById("texta").value;
		document.getElementById("note_div").style.display = 'none';
		void (0);
	}
	//打开选择的sco并更新菜单状态
	function openSCO( scoID ) {
		window.parent.frames[2].location.href="<%=basePath %>servlet/ProViewCourse?scoID=" + scoID;
		window.location.href="<%=basePath %>servlet/MenuServlet?courseID=" + courseID;
	}
	</script> 
  </head>
  
  <body id="tt_left">
	  <div class="leftbar">
	  	<ul>
			<c:forEach items="${scoList}" var="ls">
				<li>
					<img title="当前状态：${ls.lessonStatus}" src="<%=basePath%>play/images/<c:out value="${ls.lessonStatus}" />.gif">
					<a title="${ls.title}" href="javascript:void(0)" target="rightframe" onclick="openSCO( '<c:out value="${ls.scoID}" />' )"><span>章节<c:out value="${ls.title}" /></span></a>
				</li>
			</c:forEach>
		</ul>	
	  </div>
  
     <!-- 写笔记模块  默认隐藏块 -->
	<div id="note_div" class="layout">
	  <div class="right_body">
	      <form name="notebook" method="post" action="<%= basePath %>servlet/SaveCourseNote">
	        <input type="hidden" id="temptexta" name="temptexta" value=""/>
	        <table class="table">
	          <tr>
	            <th>-|笔记小窗|-</th> 
	          </tr>
	          <tr>
	            <td colspan="2"><textarea id="texta" class="line" name="notebody" cols="35" rows="15"></textarea></td>
	          </tr>
	          <tr>
	          	<td colspan="1" align="letf">
	          		<select id="note_status" name="noteStatus" required="required"><option value="0">私有笔记</option><option value="1">公开笔记</option></select>
	          	</td>
	            <td colspan="1" align="center">
	                <input class="btn" name="submit" type="submit" value="保存笔记" />
	                <input name="close" type="button" value="关闭" onclick="closediv()" />
	            </td>
	          </tr>
	        </table>
	      </form>
	   </div>
	</div>
  </body>
</html>
