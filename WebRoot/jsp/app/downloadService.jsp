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
<title>帮帮物业客服APP下载</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">


<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<link href="${pageContext.request.contextPath }/css/app.css?version=<%=versionNum %>1" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/jsp/app/downloadService.js?version=<%=versionNum %>"></script>
</head>
<body>
<!--<div class="donwload-bg"><img src="${pageContext.request.contextPath }/images/app/download.jpg"/></div>-->
	<section>
		<div class="download-content">
			<div class="download-title">
				<img src="${pageContext.request.contextPath }/images/app/logo-client.png"/>
			</div>
			<div>
				<p class="tip-title">点击按钮下载安装物业客服APP</p>
			</div>
			<div class="download">
				<p class="other">
					<a class="android" href="http://7d9lcl.com2.z0.glb.qiniucdn.com/bangbang_kefu.apk">
						<span>安卓版下载</span> 
					</a>
				</p>
				<p class="other">
					<a class="ios-future" href="#">
						<span>敬请期待</span>
					</a>
				</p>
			</div>
		</div>
	</section>
	<div class="download-img">
		<img src="${pageContext.request.contextPath }/images/app/download-bg.jpg">
	</div>
	<div class="blank-bg" id="blank_bg" style="display:none;">
		<div id="ios-word" class="tip-word" style="display:none;">
			<p class="tip-img"><img src="${pageContext.request.contextPath }/images/app/line-tip.png"/></p>
			<p class="tip-content">微信内无法下载，请点击右上角&nbsp;&nbsp;<img src="${pageContext.request.contextPath }/images/app/more-ios.png">&nbsp;&nbsp;选择&nbsp;&nbsp;<img class="safari" src="${pageContext.request.contextPath }/images/app/browser-ios.png">&nbsp;&nbsp;下载安装。</p>
		</div>
		<div id="android-word" class="tip-word" style="display:none;">
			<p class="tip-img"><img src="${pageContext.request.contextPath }/images/app/line-tip.png"/></p>
			<p class="tip-content">微信内无法下载，请点击右上角&nbsp;&nbsp;<img src="${pageContext.request.contextPath }/images/app/more.png">&nbsp;&nbsp;选择&nbsp;&nbsp;<img src="${pageContext.request.contextPath }/images/app/browser.png">&nbsp;&nbsp;下载安装。</p>
		</div>
	</div>
</body>
</html>