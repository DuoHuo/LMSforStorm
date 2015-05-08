<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>TeamTank | LMS for SCORM1.2 ----一个走在前沿的学习平台，快来体验学习的乐趣吧！</title>
  </head>
  
  <body>
	<div id="menu-container">
		<div class="divs">
			<!-- service start -->
			<div class="content service">
				<div class="container">
			        <div class="row">
			        	<div class="col-md-12">
			        		<h1>功能与特色</h1>
			            </div>
			        </div>
			        <div class="row">
			        	<div class="col-md-3 col-sm-12 templatemo_servicegap">
			            	<div class="templatemo_icon">
			                	<span class="fa fa-comments"></span>
			                </div>
			            	<div class="templatemo_greentitle">知识问答</div>
			                <div class="clear"></div>
			                <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp问答互动功能有助于同学们形成一个学习讨论小组，将疑问说出来，会有热心的同学和专业的老师回答你的疑问，你也可以回答小伙伴的疑问，互相帮助，实现共赢。</p>
			            </div>
			            <div class="col-md-3 col-sm-12 templatemo_servicegap">
			           		 <div class="templatemo_icon">
			                	<span class="fa fa-exchange"></span>
			                </div>
			            	<div class="templatemo_greentitle">课件共享</div>
			                <div class="clear"></div>
			                <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp分享出自己的课件，不吝啬知识的传递，让大家在同一个平台上学习到更多的知识，也可以下载自己喜欢的课件到其他平台上播放，实现真正的跨平台学习，让知识随处可得。</p>
			            </div>
			            <div class="col-md-3 col-sm-12 templatemo_servicegap">
			            	<div class="templatemo_icon">
			                	<span class="fa fa-pencil-square-o"></span>
			                </div>
			            	<div class="templatemo_greentitle">课堂笔记</div>
			                <div class="clear"></div>
			                <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"温故而知新"是课堂笔记的重要作用，通过复习笔记能够快速的回忆所学的知识点并加深理解，也可以分享给小伙伴帮助其他同学理解相关课程的知识点，大家一起来学习。</p>
			            </div>
			            <div class="col-md-3 col-sm-12 templatemo_servicegap">
			            	<div class="templatemo_icon">
			                	<span class="fa fa-bar-chart-o"></span>
			                </div>
			            	<div class="templatemo_greentitle">图表统计</div>
			                <div class="clear"></div>
			                <p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp大量的数据总是能表现出潜在的意义，数据统计图表给你带来各种各样的数据分析，你可以根据统计结果发掘出自己的学习计划，让学习更有效率，让数据来说话，获取更多的平台体验。</p>
			            </div>
			        </div>
			    </div>
			    <div class="clear"></div>
			    <div class="container">
			    	<div class="row">
			        	<div class="col-md-12">
			            	<h1>开启学习旅程</h1>
			            </div>
			        </div>
			        <div class="clear"></div>
			        <div class="row">
			        	<c:forEach items="${viewList}" var="ls" varStatus="st">
				        	<div class="col-md-4 col-sm-12 templatemo_servicegap">
			            		<img src="<%=basePath %>images/course/<c:out value="${ls.image}"/>.jpg" alt="Tracy - Designer">
				            	<div class="templatemo_email">
				                	<a href="<%=basePath %>play/coursePlay.jsp?courseID=<c:out value="${ls.courseID}"/>" ><div class="fa fa-play"></div></a>
				                </div>
				                <div class="clear"></div>
				                <div class="templatemo_teamtext">
				                    <div class="templatemo_teamname">
				                        <div class="templatemo_teamtitle" title="<c:out value="${ls.courseTitle}"/>"><c:out value="${ls.courseTitle}"/></div>
				                        <div class="templatemo_teampost">状态:&nbsp ${ls.lessonStatus}</div>
				                    </div>
				                   <div class="templatemo_teamsocial">
				                    
				                    </div>   
		                            <div class="bdsharebuttonbox">
		                          		 <div style="float:left;">&nbsp&nbsp&nbsp分享至:&nbsp</div>
		                            	<a href="#" class="bds_more" data-cmd="more"></a>
		                            	<a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
		                            	<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
		                            	<a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
		                            	<a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
		                            </div>
	   								<script>
	   									window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"16"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
	   								</script>	
				               </div>
				            </div>
			            </c:forEach>
			        </div>
    			</div>
    		</div>
    	</div>
	</div>
	<!-- service end -->
 <jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>
