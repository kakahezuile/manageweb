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
<title>活跃用户数_小间运营系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/calendar.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/respond.min.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/jquery-1.6.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/calendar.js?version=<%=versionNum %>"></script>
<script src="${pageContext.request.contextPath }/js/user/statistics-user-active.js?version=<%=versionNum %>"></script>
<style type="text/css">
.upload-master-face-bg{
	background:url("../../../images/public/bg-blank-60.png") repeat;
	position:fixed;
	top:0;
	width:100%;
	height:100%;
	z-index:21;
	left:0;
}
.loadingbox{
	height: 32px;
	width:32px;
	left: 50%;
	margin-left: -16px;
	margin-top: -16px;
	position: absolute;
	top: 50%;
	z-index: 22;
}
.btn{padding:0 25px;vertical-align:middle;text-align:center;line-height:24px;height:24px;color:#fff;display:inline-block;font-size:12px;background-color:#7dd0f8;}
</style>
</head>
<body>
<input type="hidden" id="master_repir_sort_fei" />
<input type="hidden" id="master_repir_startTime" />
<input type="hidden" id="is_list_detail" />
<input type="hidden" id="shop_id_detail" />
<input type="hidden" id="master_repir_endTime" />
<input id="turnover_zong_get" type="hidden" />
<input type="hidden" id="date_type_get" />
<div class="loadingbox" id="add-price-box" style="display: none;"><img alt="" src="<%= basePath %>/images/chat/loading.gif"></div>
<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
<section>
	<div class="content clearfix">
		<jsp:include flush="true" page="../../public/statistics-left.jsp" />
		<div class="right-body">
			<div class="shop-dosage-box">
				<div class="shop-dosage">
					<ul>
						<li><a href="${pageContext.request.contextPath }/jsp/operation/user/statistics-user-register.jsp">注册用户数</a></li>
						<li><a href="${pageContext.request.contextPath }/jsp/operation/user/statistics-user-active.jsp">活跃用户数</a></li>
						<li><a href="${pageContext.request.contextPath }/jsp/operation/user/statistics-user-runoff.jsp" class="select">流失用户数</a></li>
						<li><a href="${pageContext.request.contextPath }/jsp/operation/user/statistics-user-click.jsp">用户点击数</a></li>
					</ul>
				</div>
				<div style="text-align:right;padding:8px 8px 20px 0;">
					<select id="year"></select>
					&nbsp;
					<select id="month"></select>
					&nbsp;
					<a href="javascript:void(0);" onclick="getMonthSetupInfo();" class="btn">查询</a>
				</div>
				<div class="statistics-public clearfix">
					<table id="data-talbe"></table>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
<script src="${pageContext.request.contextPath}/js/user/statistics-user-runoff.js?version=<%=versionNum %>"></script>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>