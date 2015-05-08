<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username =  (String)session.getAttribute("username");
String flag = (String)request.getAttribute("flag");
%>

<!DOCTYPE html>
  <body>
	<link rel="stylesheet" href="<%=basePath %>css/animate.css">
	<link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
	<link rel="stylesheet" href="<%=basePath %>css/templatemo_misc.css">
	<link rel="stylesheet" href="<%=basePath %>css/templatemo_style.css">
	<link rel="stylesheet" href="<%=basePath %>css/jquery.rating.css">
	<link rel="stylesheet" href="<%=basePath %>css/style.css">
    <!-- JavaScripts -->
    <script src="<%=basePath %>js/event.js"></script>
    
	<script src="<%=basePath %>js/jquery-1.10.2.min.js"></script>
	<script src="<%=basePath %>js/jquery.flexslider.js"></script>
	<script src="<%=basePath %>js/custom.js"></script>
    <script src="<%=basePath %>js/jquery-git2.js"></script><!-- previous next script -->
    <script src="<%=basePath %>js/jquery.rating.js"></script>
    <!-- title start -->
    <script language="JavaScript">
    	if( <%=username %> == null || <%=username %> == ""){
    		alert("您还没有登录，请先登录平台。");
    		window.location.href = "<%=basePath %>";
    	}
    	
    	if( "<%=flag %>" == "yes" ){
    		alert("您最近没有学习任何课程！");
   		}
    </script>
  	<div class="container">
    	<div class="row">
        	<div class="col-md-4 col-sm-4">
            	<div class="templatemo_title"><a href="<%=basePath %>index.jsp">TeamTank</a></div>
                <div class="templatemo_subtitle">LMS for SCORM 1.2</div>
            </div>
            <div class="col-md-2 col-sm-2"">
	            <div class="mysteve fa fa-user" id="mysteve" onmouseout="this.getElementsByTagName('div')[1].style.display='none';" onmouseover="this.getElementsByTagName('div')[1].style.display=''">
	            	<%=session.getAttribute("username") %>
	            <div class="fa fa-chevron-down" style="float:right;margin-right:4px;"></div>
	            	<div class="add" style="display: none;">
		            	<div class="adddiv1"><a href="<%=basePath %>servlet/ContinueServlet" class="fa fa-sign-in">&nbsp继续学习</a></div>
		            	<div class="adddiv2"><a href="<%=basePath %>ScormIntro/ScormIntro.jsp" class="fa fa-paper-plane">&nbsp使用教程</a></div>
		            	<div class="adddiv1"><a href="<%=basePath %>changePwd.jsp" class="fa fa-key">&nbsp修改密码</a></div>
		            	<div class="adddiv2"><a href="<%=basePath %>servlet/LogoutServlet" class="fa fa-power-off">&nbsp退出平台</a></div>
	            	</div>
	            </div>
            </div>
           	<div class="col-md-6 col-sm-6">
            	<form id="search_form" action="<%=basePath %>servlet/SearchServlet">
            		<div class="templatemo_search">
                    	<input name="search" type="text" placeholder="输入想要学习的课程 .... " id="search">
                    </div>
                </form>
            </div>
        </div>
    </div>
  <!-- title end -->
  
  <!-- navigation start -->
  <div class="site-header">
		<div class="main-navigation">
			<div class="container">
				<div class="row">
                     <div class="col-md-12 navigation">
						<div class="row main_menu">
                    		<div class="col-md-2"><a id="prev"></a></div>
							<div class="col-md-2"><a  href="<%=basePath %>index.jsp" title="点击进入平台主页"><div class="fa fa-home"></div></a></div>
							<div class="col-md-2"><a  href="<%=basePath %>servlet/ViewCourse" title="点击查看平台功能与特色介绍"><div class="fa fa-book"></div></a></div>
							<div class="col-md-2"><a  href="<%=basePath %>servlet/RegCourse" title="点击进入上传与共享课程"><div class="fa fa-share"></div></a></div>
							<div class="col-md-2"><a  href="<%=basePath %>center.jsp" title="点击进入功能导航"><div class="fa fa-bars"></div></a></div>
                            <div class="col-md-2"><a id="next"></a></div>
						</div> 
                    </div>
				</div> 
			</div> 
		</div> 
	</div> 
	<!-- navigation end -->
  </body>
</html>
