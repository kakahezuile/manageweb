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
<title>维修类型设置_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
</head>
<body>
	<jsp:include flush="true" page="../public/operation-head.jsp"/>
	<section>
		<div class="content clearfix">
			<div class="left-body">
				<ul>
					<li>
						<a onclick="" name="" href="javascript:void(0);">创建店家</a>
					</li>
					<li>
						<a onclick="" name="" href="javascript:void(0);">屏蔽店家</a>
					</li>
					<li>
						<a onclick="" name="" href="javascript:void(0);">周边店家</a>
					</li>
					<li>
						<a onclick="" name="" href="javascript:void(0);">维修类型设置</a>
					</li>
					<li>
						<a onclick="" name="" href="javascript:void(0);">快店类型设置</a>
					</li>
				</ul>
			</div>
			<div class="right-body">
				<label>维修类型:</label><input type="text" placeholder="如：强弱电维修">
				<label>维修类型:</label><input type="text" placeholder="如：强弱电维修">
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>