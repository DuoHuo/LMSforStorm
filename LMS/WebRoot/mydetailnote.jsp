<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String recourseTitle = request.getParameter("recourseTitle");
%>

<jsp:include page="common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我的笔记</title>
    
	<meta charset="utf-8">
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/mynote.css">
	<script type="text/javascript" src="<%=basePath%>js/note.js"></script>
	<script type="text/javascript" src="<%=basePath%>xheditor-1.1.14/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>xheditor-1.1.14/xheditor-1.1.14-zh-cn.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>xheditor-1.1.14/xheditor_plugins/ubb.min.js"></script>
  </head>
  <body>
  	<div class="content">
  		<div class="container">
  			<div class="row">
				<div class="col-md-12">
					<h1>课程《<%=session.getAttribute("recourseTitle")%>》的相关笔记</h1>
				</div>
			</div>
			<div class="btn_right">
		    	<input type="button" class="btnStyle" value="新建笔记" onclick="newnote()">
		    </div>
		    <div class="btn_right">
		    	<input type="button" class="btnStyle" value="返回" onclick='goback("<%=basePath%>")'>
		    </div>
  		</div>
  		
  		<div class="mod">
  			<div id="box">
		        <ul id="tab">
		            <li class="on" id="tab_1" onclick="switchTab(1)">我的笔记</li>
		            <li id="tab_2" onclick="switchTab(2)">共享笔记</li>
		        </ul>
		        <ul id="tab_con">
		            <li id="tab_con_1">
					  	<c:forEach items="${noteDetailList}" var="dn" varStatus="st" >
								<div class="detailnotelist">
									<div class="detailnoteimg"></div>
									<div class="notecontent" title="<c:out value="${dn.content}"/>">
										<c:out value="${dn.content}" escapeXml="false"/>
									</div>
									<div class="blockedit">
					  					<a href="javascript:editnote('<c:out value="${dn.content}"/>','<c:out value="${dn.noteID}"/>');"><span class="gsee">查看/编辑</span></a>
					  					<a href="<%=basePath %>servlet/DeleteNote?noteID='<c:out value="${dn.noteID}"/>'"><span class="gs">删除</span></a>
					  					<span class="gs"><c:out value="${dn.noteStatus}"/></span>
					  				</div>	
								</div>
					  	</c:forEach>
					</li>
		            <li id="tab_con_2">
						<c:forEach items="${noteOtherList}" var="on" varStatus="st" >
								<div class="detailnotelist">
									<div class="detailnoteimg"></div>
									<div class="otherinfo" title="<c:out value="${on.content}"/>">
										<c:out value="${on.content}" escapeXml="false"/>
									</div>
									<div class="edit">
										作者：<c:out value="${on.author}"/>
					  				</div>	
									<div class="edit">
					  					<a href="javascript:seeothernote('<c:out value="${on.content}"/>');">查看</a>
					  				</div>	
								</div>
					  	</c:forEach>
					</li>
		        </ul>
		        <div class="clear"></div>
		    </div>
		    
  		</div>
  	</div>
  	
  	<!-- 笔记本编辑显示区 -->
  	<div id="note_div">
      <form name="notebook" method="post" action="<%= basePath %>servlet/SaveNote">
      	<input type="hidden" id="noteID" name="noteID" value=""/>
      	<input type="hidden" id="title" name="title" value="<%=session.getAttribute("recourseTitle")%>"/>
      	<div class="noteheader">
      		<span class="notename">-|笔记小窗|-</span> 
        	<span class="coursetitle"><%=session.getAttribute("recourseTitle")%></span>
      	</div>
        <div class="contentarea">
        	<textarea id="texta" class="xheditor {tools:'Bold,Italic,Underline,Strikethrough,|,Fontface,FontSize,FontColor,BackColor,|,Align,Source,Fullscreen,About'}" 
           			name="notebody" cols="35" rows="25">
           	</textarea>
        </div>
        <div class="doarea">
        	<div class="option">
        		<select id="note_status" name="noteStatus" required="required">
	        		<option value="0">私有笔记</option><option value="1">公开笔记</option>
	        	</select>
        	</div>
        	<button id="savenote" name="submit" type="submit">保存笔记</button>
            <button name="close" type="button" onclick="closediv('note_div')" />关闭</button>
        </div>
      </form>
  	</div>
  	
  	<!-- other笔记本显示区 -->
  	<div id="othernote_div">
  		<!-- <textarea id="othertexta" rows="" cols="" class="xheditor {tools:'none'}"></textarea> -->
  		<div id="othertexta"></div>
  		<button name="close" type="button" class="btn btn-info" onclick="closediv('othernote_div')" />关闭</button>
  	</div>
  </body>
</html>

