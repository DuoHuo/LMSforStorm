<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>播放器</title>
<script src="<%=basePath %>play/dwr/engine.js" /></script>
<script src="dwr/util.js" /></script>
<script src="dwr/interface/APIAdapter.js"></script>

<script language="JavaScript">
var courseID = "<%=request.getParameter( "courseID" )%>";

  var API = null;
  var result = null;
  window.API = new Object();
  API.name = "APIAdapter";

  function init(){
  	  APIAdapter.init();
  }
  var callBack=function(data) {
	  window.result = data;
  };
  API.LMSInitialize=function(param){
	dwr.engine.setAsync(false);
	APIAdapter.LMSInitialize(param,callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSFinish=function(param){
	dwr.engine.setAsync(false);
    APIAdapter.LMSFinish(param, callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSGetValue=function(element){
	dwr.engine.setAsync(false);
    APIAdapter.LMSGetValue(element, callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSSetValue=function(element,value){
	dwr.engine.setAsync(false);
    APIAdapter.LMSSetValue(element,value, callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSCommit=function(param){
	dwr.engine.setAsync(false);
    APIAdapter.LMSCommit(param, callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSGetLastError=function(){
	dwr.engine.setAsync(false);
    APIAdapter.LMSGetLastError(callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSGetErrorString=function(errorCode){
	dwr.engine.setAsync(false);
	APIAdapter.LMSGetErrorString(errorCode, callBack);
	dwr.engine.setAsync(true);
	return result;
  };
  API.LMSGetDiagnostic=function(errorCode){
	dwr.engine.setAsync(false);
    APIAdapter.LMSGetDiagnostic(errorCode, callBack);
	dwr.engine.setAsync(true);
	return result;
  };
</script>
  </head>
  	<!-- 使用框架 -->
	<frameset rows="70,*" frameborder="no" border="0" framespacing="0" onload="init()">
		<frame id="topframe" name="topframe" src="<%=basePath%>play/playtop.jsp" scrolling="no" noresize="noresize">
		<frameset cols="280,*" frameborder="no" border="0" framespacing="0" >
			<frame id="leftframe" name="leftframe" src="<%=basePath%>play/playleft.jsp" scrolling="no" noresize="noresize">
			<frame id="rightframe" name="rightframe" src="<%=basePath%>play/defaut.jsp" scrolling="yes" noresize="noresize">
		</frameset>
	</frameset>
  <noframes>
    <body>您的浏览器不支持框架！</body>
  </noframes> 
</html>

