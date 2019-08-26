<%@ page language="java" import="java.util.*,com.xj.utils.PropertyTool" pageEncoding="utf-8"%>

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
<title>用户统计_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/jquery-1.11.1.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/statistics-user.js"></script>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<script type="text/javascript">
var listShopType=window.parent.document.getElementById("listShopType_id_index").value;
var communityId = window.parent.document.getElementById("community_id_index").value;
var myUserName = window.parent.document.getElementById("myUserName_index").value;
var adminId = window.parent.document.getElementById("admin_id_index").value;
var username = window.parent.document.getElementById("username_id_index").value;
var password = window.parent.document.getElementById("password_id_index").value;
</script>
</head>

<body>
<section>
	<div class="content-normal clearfix">
		<div class="main clearfix" id="tousu_lie_div_id">
			<jsp:include flush="true" page="./signed.jsp">
				<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
				<jsp:param name="listShopType" value="{parameterValue | ${listShopType }}" />
				<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
				<jsp:param name="username" value="{parameterValue | ${username }}" />
				<jsp:param name="password" value="{parameterValue | ${password }}" />
			</jsp:include>
		</div>
	</div>
</section>
</body>
<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>