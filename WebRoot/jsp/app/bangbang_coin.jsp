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
<title>帮帮币使用说明</title>
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
<body style="background:#fff;">
	<section>
		<div class="content" style="background:#fff;">
			<div class="shop_help_title"><b>&nbsp;</b>帮帮币使用说明</div>
			<div class="shop_help-item">
				<P><b>帮帮币的用途</b></P>
				<p>
					<b>1</b>、帮帮币可用于在帮帮消费时抵扣部分现金；
				</p>
				<p>
					<b>2</b> 、帮帮币和现金的抵扣比例为100:1，即100帮帮币可抵扣1元人民币；
				</p>
				<p><b>如何获取帮帮币</b></p>
				<p>
					<b>1</b>、帮帮币由帮帮自动发放，可通过活动、购物等途径获得帮帮币；
				</p>
				<p>
					<b>2</b> 、每笔消费都可得到一定数量的帮帮币，消费越多得到的帮帮币也越多；
				</p>
				<p>
					<b>3 </b>、购买快店商品后，点击一键分享至生活圈也可获取一定数量的帮帮币；
				</p>
				<p>
					<b>4</b> 、帮帮币没有过期时间，可不断累加；
				</p>
				<P><b>相关声明</b></P>
				<p>
					<b>1</b> 、帮帮币仅可在帮帮使用，如用户账号封停，则帮帮将取消该用户账号内帮帮币相关使用权益；
				</p>
				<p>
					<b>2</b> 、帮帮币规则由帮帮制定并依据国家相关法律法规及规章制度予以解释和修改，规则以帮帮软件公布为准；
				</p>
			</div>
		</div>
	</section>
</body>
</html>