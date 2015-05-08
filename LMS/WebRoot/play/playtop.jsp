<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>topbar</title>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>play/images/playframe.css">
	
<script language=javascript>
var courseID = window.parent.courseID;
var iniFlag = false;
//返回主页
function retIndex() 
{
   if (initFlag == true)
   {
      var  mesg = "请先退出课程再执行此操作！";
      alert(mesg);
   }
   else
   {
      window.parent.location.href="<%=basePath %>servlet/ViewCourse";
   }
}

//下一页
function  nextSCO()
{
   var scoWinType = typeof(window.parent.frames[2].scoWindow);
   var theURL = "<%=basePath %>servlet/ProViewCourse?button=next";
  
   window.parent.frames[2].document.location.href = theURL;
   window.parent.frames[1].location.href="<%=basePath %>servlet/MenuServlet?courseID=" + courseID;
}


//前一页
function  previousSCO()
{
   var scoWinType = typeof(window.parent.frames[2].scoWindow);
   var theURL = "<%=basePath %>servlet/ProViewCourse?button=prev";
   
   window.parent.frames[2].document.location.href = theURL;
   window.parent.frames[1].location.href="<%=basePath %>servlet/MenuServlet?courseID=" + courseID;
}
//退出课程前提示
function doConfirm()
{
    if( confirm("您确认要退出课程吗？") )
    {
        var value = window.parent.API.LMSFinish("");
        if (value == "true")
	    {
	   		window.parent.frames[2].location.href = "<%=basePath %>play/defaut.jsp";
	   		window.parent.frames[1].location.href="<%=basePath %>servlet/MenuServlet?courseID=" + courseID;
	   		initFlag = false;
	    }
	    else
	    {
	    	if( confirm("数据未能保存，退出课程失败,您确定要强制退出吗？信息可能会丢失。") )
	    	{
	    		window.parent.frames[2].location.href = "<%=basePath %>play/defaut.jsp";
	    		window.parent.frames[1].location.href="<%=basePath %>servlet/MenuServlet?courseID=" + courseID;
	    		initFlag = false;
	    	}
	    	else
	    	{
	    		return false;
	    	}
	    }
    }
    else
    {
    }
}

//显示DIV层
function opennote() {
	var note_div = window.parent.frames[1].document.getElementById("note_div");
	note_div.style.display = "block";
	window.parent.frames[1].document.getElementById("texta").value = window.parent.frames[1].document.getElementById("temptexta").value;
}
//截图
function screenshot(){
	window.parent.location.href = "<%=basePath %>servlet/ScreenShotServlet";
}

</script>
  </head>
  <body>
  <form name="buttonform"> 
      <input type="hidden" name="tempdata" id="tempdata" value="" /> 
      <input type="hidden" name="control" value="" /> 
  	<div id="topbar">
  		<div class="topbar_logo"></div>
		<ul>
			<li><input type="button" value="上一章" id="previous" name="previous" style="visibility: hidden" language="javascript"
                    onclick="return previousSCO();"></li>
		    <li><input type="button" value="下一章" id="next" name="next" style="visibility: hidden" language="javascript"
                    onclick="return nextSCO();"></li>
            <li><input type="button" value="添加笔记" id="notebook" name="notebook" language="javascript"
             		onclick="opennote()	"></li>
		    <li><input type="button" value="退出课程" id="quit" language="javascript"
             		onclick="doConfirm()"></li>
            <li><input type="button" value="返回平台" id="retindex" language="javascript"
                    onclick="return retIndex();"></li>
		</ul>
		<c:if test="${admin == true }">
			<div class="screen_div" title="点击制作封面"><input type="button" class="screen_btn" onclick="screenshot()"></div>
		</c:if>
	</div>
	</form>
  </body>
</html>
