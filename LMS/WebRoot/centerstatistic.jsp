<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>统计分析</title>

<link rel="stylesheet" type="text/css" href="<%=basePath %>css/default.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/climacons.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/component.css" />

<script type="text/javascript" src="<%=basePath %>js/modernizr.custom.js"></script>
<script language="Javascript" src="<%=basePath%>js/FusionCharts.js"></script>

</head>
<body>

<div class="container">	

	<header class="clearfix">
		<h1>统计分析<span>----私人定制您的学习计划</span></h1>	
		<nav class="codrops-demos">
			<a href="<%=basePath %>center.jsp">Return Home</a>
		</nav>
	</header>
	
	
	<div class="main">

		<ul id="rb-grid" class="rb-grid clearfix">
			<li class="icon-clima-1">
				<h3>热门课程TOP10</h3><span class="rb-temp">HOT</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">Chart for HOT TOP10</span><span class="icon-clima-1"></span></div>
						<div id="chart1"></div>
					</div>
					<script language="JavaScript">  
					     var areaObject=${jsonArr1};
					     var strXML="<graph caption='热门课程 TOP 10 ' xAxisName='课程ID' yAxisName='num' baseFont='楷体' baseFontSize='15' bgColor='2D87B4' rotateNames='0'>";  
					     var length=areaObject.length;  
					     for(var i=1;i<=length;i++){  
					        strXML= strXML+"<set name='"+areaObject[i-1].name+"'  value='"+areaObject[i-1].value+"' hoverText='"+areaObject[i-1].title+"' color='"+areaObject[i-1].color+"'/>";  
					     }  
					        strXML= strXML+"</graph>";   
					                 var chart1 = new FusionCharts("<%=basePath%>charts/FCF_Column3D.swf", "chart1Id", "800", "480");  
					                 chart1.setDataXML(strXML); 
					                 chart1.render("chart1");  
					 </script>
				</div>
			</li>
			<li class="icon-clima-2">
				<h3>星级评价TOP10</h3><span class="rb-temp">STAR</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">Chart for Star Assess</span><span class="icon-clima-2"></span></div>
						<div id="chart2"></div>
					</div>
					<script language="JavaScript">  
					     var areaObject=${jsonArr2};
					     var strXML="<graph caption='星级评价TOP10 ' xAxisName='课程ID' yAxisName='NUM of STAR' baseFont='楷体' baseFontSize='15' bgColor='2DB4B4' useRoundEdges='1' showBorder='0' exportEnabled='1' exportShowMenuItem='0'>";  
					     var length=areaObject.length;  
					     for(var i=1;i<=length;i++){  
					        strXML = strXML+"<set name='"+areaObject[i-1].name+"' value='"+areaObject[i-1].value+"' hoverText='"+areaObject[i-1].title+"' color='"+areaObject[i-1].color+"'/>";  
					     }  
					        strXML= strXML+"</graph>";
					                 var chart1 = new FusionCharts("<%=basePath%>charts/FCF_Column2D.swf", "chart1Id", "800", "480");  
					                 chart1.setDataXML(strXML); 
					                 chart1.render("chart2");
					 </script>
				</div>
			</li>
			<li class="icon-clima-3">
				<h3>分享达人TOP10</h3><span class="rb-temp">SHARE</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">CHART FOR BEST SHARER </span><span class="icon-clima-3"></span></div>
						<div id="chart3"></div>
					</div>
					<script language="JavaScript">  
					     var areaObject=${jsonArr3};
					     var strXML="<graph caption='分享达人TOP10 ' xAxisName='用户名' yAxisName='NUM OF SHARE' baseFont='楷体' baseFontSize='15' bgColor='936293' useRoundEdges='1' showBorder='0' exportEnabled='1' exportShowMenuItem='0' >";  
					     var length=areaObject.length;  
					     for(var i=1;i<=length;i++){  
					        strXML = strXML+"<set name='"+areaObject[i-1].name+"' value='"+areaObject[i-1].value+"' color='"+areaObject[i-1].color+"'/>";  
					     }  
					        strXML= strXML+"</graph>";
					                 var chart1 = new FusionCharts("<%=basePath%>charts/FCF_Column2D.swf", "chart1Id", "800", "480");  
					                 chart1.setDataXML(strXML); 
					                 chart1.render("chart3");  
					 </script>
				</div>
			</li>
			<li class="icon-clima-4">
				<h3>学习标兵TOP10</h3><span class="rb-temp">STUDY</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">CHART FOR BSET STU</span><span class="icon-clima-4"></span></div>
						<div id="chart4"></div>
					</div>
					<script language="JavaScript">  
					     var areaObject=${jsonArr4};
					     var strXML="<graph caption='学习标兵TOP10 ' xAxisName='用户名' yAxisName='NUM OF COURSE' baseFont='楷体' baseFontSize='15' bgColor='B83D3D' useRoundEdges='1' showBorder='0' exportEnabled='1' exportShowMenuItem='0'>";  
					     var length=areaObject.length;  
					     for(var i=1;i<=length;i++){  
					        strXML = strXML+"<set name='"+areaObject[i-1].name+"' value='"+areaObject[i-1].value+"' color='"+areaObject[i-1].color+"'/>";  
					     }  
					        strXML= strXML+"</graph>";
					                 var chart1 = new FusionCharts("<%=basePath%>charts/FCF_Column2D.swf", "chart1Id", "800", "480");  
					                 chart1.setDataXML(strXML); 
					                 chart1.render("chart4");  
					 </script>
				</div>
			</li>
			<li class="icon-clima-5">
				<h3>待开发块</h3><span class="rb-temp">NONE</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">待开发NONE</span><span class="icon-clima-5"></span></div>
						<div id="chart5"></div>
					</div>
				</div>
			</li>
			<li class="icon-clima-6">
				<h3>待开发块</h3><span class="rb-temp">NONE</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">待开发NONE</span><span class="icon-clima-6"></span></div>
						<div id="chart6"></div>
					</div>
				</div>
			</li>
			<li class="icon-clima-7">
				<h3>待开发块</h3><span class="rb-temp">NONE</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">待开发NONE</span><span class="icon-clima-7"></span></div>
						<div id="chart7"></div>
					</div>
				</div>
			</li>
			<li class="icon-clima-8">
				<h3>待开发块</h3><span class="rb-temp">NONE</span>
				<div class="rb-overlay">
					<span class="rb-close">close</span>
					<div class="rb-week">
						<div><span class="rb-item">待开发NONE</span><span class="icon-clima-8"></span></div>
						<div id="chart8"></div>
					</div>
				</div>
			</li>
		</ul>
		
	</div>
</div><!-- /container -->

<script type="text/javascript" src="<%=basePath %>js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.fittext.js"></script>
<script type="text/javascript" src="<%=basePath %>js/boxgrid.js"></script>
<script type="text/javascript">
$(function(){
	Boxgrid.init();
});
</script>

</body>
</html>
