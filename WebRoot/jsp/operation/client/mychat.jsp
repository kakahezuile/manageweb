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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easymob-webim1.0/css/webim.css?version=<%=versionNum %>" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easymob-webim1.0/css/bootstrap.css?version=<%=versionNum %>" />
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js?version=<%=versionNum %>"></script>
<script type='text/javascript' src='${pageContext.request.contextPath }/easymob-webim1.0/strophe-custom-1.0.0.js?version=<%=versionNum %>'></script>
<script type='text/javascript' src='${pageContext.request.contextPath }/easymob-webim1.0/json2.js?version=<%=versionNum %>'></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/easemob.im-1.0.3.js?version=<%=versionNum %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/bootstrap.js?version=<%=versionNum %>"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easymob-webim1.0/css/webim.css?version=<%=versionNum %>" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easymob-webim1.0/css/bootstrap.css?version=<%=versionNum %>" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
</head>
<body>
	<div class="hx-content" id="content" >
		<div class="hx-leftcontact" id="leftcontact">
			<div class="chat-search">
				<input class="search" type="text" />
			</div>
			<div id="headerimg" class="leftheader">
				<span> <img src="${pageContext.request.contextPath }/easymob-webim1.0/img/head/header2.jpg" alt="logo"
					class="img-circle" width="60px" height="60px"
					style="margin-top: -40px;margin-left: 20px" /></span> <span
					id="login_user" class="login_user_title"> <a
					class="leftheader-font" href="#"></a>
				</span>
			</div>
			<div id="leftmiddle">
			</div>
			<div id="contractlist11"
				style="height: 492px; overflow-y: auto; overflow-x: auto;">
				
				<div class="accordion" id="accordionDiv">
					<div class="accordion-group">
						<div class="accordion-heading">
							<a id="accordion1" class="accordion-toggle"
								data-toggle="collapse" data-parent="#accordionDiv"
								href="#collapseOne">所有业主 </a>
						</div>
						<div id="collapseOne" class="accordion-body collapse in">
							<div class="accordion-inner" id="contractlist">
								<ul id="contactlistUL" class="chat03_content_ul">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="rightTop" style="height:0;"></div>
		<!-- 聊天页面 -->
		<div class="chatRight">
			<div id="chat01">
				<div class="chat01_title">
					<ul class="talkTo">
						<li id="talkTo"><a href="#"></a><span id="mychat_user_title"></span></li>
						<li id="recycle" style="float: right;"><img
							src="${pageContext.request.contextPath }/easymob-webim1.0/img/recycle.png" onclick="clearCurrentChat();"
							style="margin-right: 15px; cursor: hand; width: 18px;" title="清屏" /></li>
					</ul>
				</div>
				<div id="null-nouser" class="chat01_content"></div>
			</div>
			<div class="chat02">
				<div class="chat02_title">
					<label id="chat02_title_t"></label>
					<div id="wl_faces_box" class="wl_faces_box">
						<div class="wl_faces_content">
							<div class="title">
								<ul>
									<li class="title_name">常用表情</li>
									<li class="wl_faces_close"><span
										onclick='turnoffFaces_box()'>&nbsp;</span></li>
								</ul>
							</div>
							<div id="wl_faces_main" class="wl_faces_main">
								<ul id="emotionUL">
								</ul>
							</div>
						</div>
						<div class="wlf_icon"></div>
					</div>
				</div>
				<div id="input_content" class="chat02_content">
					<textarea id="talkInputId" style="resize: none;"></textarea>
				</div>
				<div class="chat02_bar">
					<ul>
						<li style="right: 15px;"><img src="${pageContext.request.contextPath }/easymob-webim1.0/img/send_btn.jpg" onclick="homeSendText();" /></li>
					</ul>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
	<div class="chat-bottom-info">
		&nbsp;
	</div>
</body>
</html>