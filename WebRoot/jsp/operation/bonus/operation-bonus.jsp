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
		<title>用户统计_小间运营系统</title>
		<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">

		<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>
		<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
	<script src="${pageContext.request.contextPath }/js/calendar.js"></script>
<link
			href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>"
			type="image/x-icon" rel="shortcut icon">
		<link
			href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
		<link
			href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>"
			rel="stylesheet" type="text/css">
				<script src="${pageContext.request.contextPath }/js/operation.js?version=<%=versionNum %>"></script>
<script type="text/javascript">
	var communityId = window.parent.document
			.getElementById("community_id_index").value;
	var myUserName = window.parent.document
			.getElementById("myUserName_index").value;
	var adminId = window.parent.document
			.getElementById("admin_id_index").value;
	var username = window.parent.document
			.getElementById("username_id_index").value;
	var password = window.parent.document
			.getElementById("password_id_index").value;
    function getFormatDate(date) {
		var myDate = new Date(date);
      
		return  myDate.getFullYear()+"年"+ myDate.getMonth() + 1 + "月" + myDate.getDate() + "日";
	}
</script>
	</head>
	<body>
			<section>
				<div style="display: block;" id="create_bangbang_td">
					<jsp:include page="./create_bangbang.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>
				<div style="display: none;" id="send_bangbang_td">
					<jsp:include page="./send_bangbang.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>
				<div style="display: none;" id="record_bangbang_td">
					<jsp:include page="./record_bangbang.jsp">
						<jsp:param name="communityId"
							value="{parameterValue | ${communityId }}" />
						<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
						<jsp:param name="username" value="{parameterValue | ${username }}" />
						<jsp:param name="password" value="{parameterValue | ${password }}" />
					</jsp:include>
				</div>

			</section>
	</body>
	<jsp:include flush="true" page="../../public/footer.jsp"></jsp:include>
</html>