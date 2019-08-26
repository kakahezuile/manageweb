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
<style type="text/css">
.error-content{
	width:900px;
	height:500px;
	background:#f9fbf1;
	border:1px solid #e5ecd1;
	-ms-filter: "progid:DXImageTransform.Microsoft.Shadow(Strength=3, Direction=180, Color=#d2d2d2)";/*IE 8*/
	-moz-box-shadow: 0px 3px 3px #d2d2d2;/*FF 3.5+*/
	-webkit-box-shadow: 0px 3px 3px #d2d2d2;/*Saf3-4, Chrome, iOS 4.0.2-4.2, Android 2.3+*/
	filter: progid:DXImageTransform.Microsoft.Shadow(Strength=3, Direction=180, Color=#d2d2d2); /*IE 5.5-7*/
	box-shadow:0 3px 3px #d2d2d2;
	position:absolute;
	left:50%;
	top:50%;
	margin-top:-280px;
	margin-left:-450px;
}
.error-content p {
	text-align:center;
}
.error-content p img{
	margin-top:40px;
}
.error-content p span{
	color: #000;
    font-size: 18px;
    margin-bottom: 10px;
    display:block;
    margin:30px 0 10px;
}
.error-content p a{
	font-size: 15px;
    height: 46px;
    line-height: 46px;
    margin-top: 35px;
    width: 162px;
}
</style>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间官网,小间租房,在线租房,在线合租" />
<meta name="Description" content="小间网为你提供中国最现代化的公寓，最便宜的价格，最优质的服务，最完备的公用设施。" />
<title>小间官网_页面不存在_在线租房_在线合租</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

</head>
<body>
	<section>
		<div class="content clearfix error-content">
			<p><img alt="" src="${pageContext.request.contextPath }/jsp/tvstatistics/error-404.png"/></p>
			<p><span>很遗憾，您的IP不在我们的允许范围内，不能访问此页面！</span></p>
		</div>
	</section>
</body>
</html>