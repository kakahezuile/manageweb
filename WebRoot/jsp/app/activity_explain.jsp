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
<title>活动说明</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/app.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
</head>
<body>
	<section>
		<div class="content">
			<div class="shop_help-item">
				<div class="shop_help_title"><b>&nbsp;</b>活动/话题</div>
				<p>
					活动/话题由小区业主自主发起，自愿参与；
				</p>
				<p>
					只要你注册成为帮帮的业主（以下简称帮主），既可以发起活动/话题啦； 
				</p>
				<p>
					在业主活动版块点击屏幕下方的"发起活动" ”，即可编写活动/话题详情；
				</p>
				<div class="shop_help_title"><b>&nbsp;</b>发起活动</div>
				<p>
					不想宅在家里，世界那么大想去看看，去组织个活动吧；
				</p>
				<p>
					发起活动一定要填写活动的<b>名称、</b><b>时间、</b><b>地点</b>，这样才能让小伙伴们找到你哦；
				</p>
				<p>
					 同时可上传最多6张照片（每张小于2M），填写活动详情（140字以内）也会有利于您活动的推广，让更多的人加入到你的活动中来；
				</p>
				<div class="shop_help_title"><b>&nbsp;</b>发起话题</div>
				<p>
					你有想法，你发现身边好玩的事，你心中有个疑惑;
				</p>
				<p>
					去发起一个话题吧，与邻居们一起聊天，说不定能找到志同道合的朋友哦；
				</p>
				<p>
					起个话题名称，格式为：#话题内容#，字数20字以内。发起话题就不需要写时间、地点了；
				</p>
				<div class="shop_help_title"><b>&nbsp;</b>隐私</div>
				<p>
					点击群聊右上角图标可以查看群内成员、屏蔽群信息、退出群聊等功能；
				</p>
				<p>
					为了让我们的帮帮平台健康持续的发展，帮主可对涉嫌发布“黄、赌、毒、暴”等违法信息的用户进行举报；
				</p>
				<p>感谢您对帮帮平台的支持！</p>
			</div>
			
		</div>
	</section>
</body>
</html>