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
<title>帮帮券使用说明</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/app.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/complain.js"></script>
</head>
<body>
	<section>
		<div class="content">
			<div class="shop_help_title"><b>&nbsp;</b>帮帮券使用说明</div>
			<div class="shop_help-item">
				<p>
					1、帮帮券是通过买赠、活动参与等形式发放给用户，是用于减免购买支付的优惠措施；
				</p>
				<p>
					2 、本券不兑现金、不找零、不可转赠、不开发票；
				</p>
				<p>
					3、使用到帮帮券的订单，如遇到退货、退款、订单取消等情况，则返还原帮帮券；
				</p>
				<p>
					4、每张订单只能使用一帮帮券，且不得与其他优惠方式同时使用；
				</p>
				<p>
					5 、请在有效期内使用帮帮券；
				</p>
				<p>
					6 、通用券，适用于帮帮平台上所有的消费项目；
				</p>
				<p>
					7 、专用券，仅适用于相对应的消费项目；
				</p>
			</div>
			
		</div>
	</section>
</body>
</html>