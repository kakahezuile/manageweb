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
<title>签约店家_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/signed.js"></script>
</head>
<body>
	<jsp:include flush="true" page="public/head.jsp"/>
	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="public/left-menu.jsp"></jsp:include>
			<div class="right-body">
				<div><jsp:include flush="true" page="public/nav.jsp"></jsp:include></div>
				<div class="main clearfix">
					<div class="shopdetail-name"><span>老家肉饼</span><span class="shopdetail-phone">18512564854</span></div>
					<div class="shopdetail-statistics">
						<span class="shopdetail-title">当前服务</span>
						<span><b>95%</b>好评度</span>
						<span><b>630</b>总成交量（单）</span>
						<span><b>2</b>平均回复时间（分钟）</span>
					</div>
					<div class="shopdetail-content">
						<div class="shopdetail-item">
							<span class="shopdetail-item-name">鱼香肉丝</span>
							<span class="shopdetail-item-price">20</span>
						</div>
						<div class="shopdetail-item">
							<span class="shopdetail-item-name">宫保鸡丁</span>
							<span class="shopdetail-item-price">18</span>
						</div>
						<div class="shopdetail-item">
							<span class="shopdetail-item-name">香辣土豆丝</span>
							<span class="shopdetail-item-price">16</span>
						</div>
						<div class="shopdetail-item">
							<span class="shopdetail-item-name">鱼香茄子</span>
							<span class="shopdetail-item-price">23</span>
						</div>
						<div class="shopdetail-item">
							<span class="shopdetail-item-name">东坡肉</span>
							<span class="shopdetail-item-price">25</span>
						</div>
						<div class="shopdetail-item">
							<span class="shopdetail-item-name">鱼香肉丝</span>
							<span class="shopdetail-item-price">20</span>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>