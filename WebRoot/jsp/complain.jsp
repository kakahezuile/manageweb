<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>投诉/处理_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/complain.js"></script>
</head>
<body>
	<jsp:include flush="true" page="public/head.jsp"/>
	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="public/left-menu.jsp"></jsp:include>
			<div class="right-body">
				<div><jsp:include flush="true" page="public/nav.jsp"></jsp:include></div>
				<div class="main clearfix">
					<div class="notice-public clearfix">
						<div class="notice-edit">
							<textarea rows="" cols=""></textarea>
						</div>
						<div class="notice-calendar"></div>
					</div>
					<div class="notice-title">已发布公告</div>
					<div class="notice-list">
						<ul>
							<li>
								<p class="notice-date">2014-12-13</p>
								<p class="notice-summary"><a href="javascript:;">业主你们好！现本小区已进入装修阶段，进出苑区人员日益增多，管理处为加强小区人员进出管理，确保苑区的安全和谐，须对苑区住户办理业主卡 ...</a></p>
							</li>
							<li>
								<p class="notice-date">2014-12-11</p>
								<p class="notice-summary"><a href="javascript:;">为加强养犬管理，保障业主的健康及人身安全，维护小区环境及公共秩序，根据《xx市养犬管理规定》并结合xx小区的实际情况，现对小区养犬人提 ...</a></p>
							</li>
							<li>
								<p class="notice-date">2014-12-10</p>
								<p class="notice-summary"><a href="javascript:;">昨天本小区6座、8座各发生一起入室盗窃案件，犯罪分子是利用白天上班家中无人的时间使用技术手段开锁入室盗窃。据初步侦查，犯罪分子有可能 ...</a></p>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>