<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>周边商铺_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/shop/nearby-pay.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/shop/nearby.js?version=<%=versionNum %>"></script>
</head>
<body>
<input id="facilities_class" type="hidden" value="" >
<input id="facilities_type" type="hidden" value="" >
<section>
	<div class="content clearfix center-personal-info-content">
		<jsp:include flush="true" page="./shop-manage-left.jsp">
			<jsp:param name="module" value="nearby"/>
		</jsp:include>
		<div class="nearby_right-body">
			<div class="shop-dosage">
				<ul>
					<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/nearby.jsp">抢购活动</a></li>
					<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/nearby-cover-scope.jsp">商家活动发布范围</a></li>
					<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/nearby-pay.jsp"  class="select">店家充值</a></li>
				</ul>
			</div>
			<div class="main clearfix">
				<div class="nearby-pay-box">
					<p><input type="text" placeholder="输入店家注册手机号"></p>
					<p><input type="text" placeholder="输入充值金额"></p>
				</div>
				<div class="pay-commit"><a href="javascript:;">充值</a></div>
			</div>
		</div>
	</div>
</section>
</body>
<script type="text/javascript">
selectNearbyCrazySalesShop(1,10) ;
</script>
</html>