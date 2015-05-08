<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>
   <title>播放器</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <meta http-equiv="refresh" content="3; url=<%= request.getAttribute("nextItemToLaunch") %>">
   <link rel="stylesheet" href="<%=basePath %>play/images/playframe.css" type="text/css">
 	<script language="javascript">	
	var ctrl = "<%=session.getAttribute("control") %>";
    if (ctrl == "choice")  //官方课件就是此类型
    { //隐藏控制按钮
       window.parent.frames[0].document.forms[0].next.style.visibility = "hidden";
       window.parent.frames[0].document.forms[0].previous.style.visibility = "hidden";
    }
    else
    {
       // 显示控制按钮
       window.parent.frames[0].document.forms[0].next.style.visibility = "visible";
       window.parent.frames[0].document.forms[0].previous.style.visibility = "visible";
    }
    
    function initLMSFrame()
    {
        window.top.frames[0].document.forms[0].control.value = ctrl;
    }
    </script>
   </head>
   <body id="tt_left" marginwidth="0" marginheight="0" onload="initLMSFrame()">    
   		<div style="position: absolute;left:300px;top:180px;font-size:50;">
            <h2><font>Please wait....</font></h2>
        </div>
   </body>
  </html> 