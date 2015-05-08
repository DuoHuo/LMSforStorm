<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="common.jsp"></jsp:include>
<head>
	<title>LMS for SCORM 1.2</title>
    <meta name="description" content="">
	<meta name="keywords" content="" />
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1">
</head>
<body>
  
  <div id="menu-container">

	<div class="divs">
		<!-- 开始 -->
		<div class="content homepage" >
			<div class="container">
		    	<div class="row">
		        	<div class="col-md-12">
		            	<div class="main-slider">
							<div class="flexslider">
								<ul class="slides">
									<li>
										<div class="slider-caption">
											<h2>梦想从这里开始，旅程在这里起航</h2>
											<p>DIFFRENT FROM OTHER LEARNING PLATFORM , IT CAN BRING US JOY , IT DOESN'T MAKE US FEEL BORING.</p>
										</div>
										<img src="<%=basePath %>images/slide1.jpg" alt="Slide 1">
									</li>
			
									<li>
										<div class="slider-caption">
											<h2>可访问性  适应性  可承受性 持久性 重用性</h2>
											<p>ACCESSIBILITY ADAPTABILITY AFFORDABILITY DURABILITY REUSABILITY</p>
										</div>
										<img src="<%=basePath %>images/slide2.jpg" alt="Slide 2">
									</li>
			
			                        <li>
										<div class="slider-caption">
											<h2></h2>
											<p></p>
										</div>
										<img src="<%=basePath %>images/slide3.jpg" alt="Slide 3">
									</li>
			                        
			                        <li>
										<div class="slider-caption">
											<h2></h2>
											<p></p>
										</div>
										<img src="<%=basePath %>images/slide4.jpg" alt="Slide 4">
									</li>
								</ul>
							</div>
						</div>
		            </div>
		        </div>
		    </div>
	    </div>
		<!--HOME 结束-->
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>