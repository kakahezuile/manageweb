<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>帮帮券</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	


  </head>
  
  <body>
  	<div style="display: block;" id="create_bangbang_td">
   		<jsp:include page="./create_bangbang.jsp">
				<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
				<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
					<jsp:param name="username" value="{parameterValue | ${username }}" />
					<jsp:param name="password" value="{parameterValue | ${password }}" />
			</jsp:include>
   	</div>
   	<div  style="display: none;" id="send_bangbang_td">
   		<jsp:include page="./send_bangbang.jsp">
   				<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
   				<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
								<jsp:param name="username" value="{parameterValue | ${username }}" />
								<jsp:param name="password" value="{parameterValue | ${password }}" />
   			</jsp:include>
   	</div>
   	<div  style="display: none;" id="record_bangbang_td">
   		<jsp:include page="./record_bangbang.jsp">
   				<jsp:param name="communityId" value="{parameterValue | ${communityId }}" />
   				<jsp:param name="adminId" value="{parameterValue | ${adminId }}" />
								<jsp:param name="username" value="{parameterValue | ${username }}" />
								<jsp:param name="password" value="{parameterValue | ${password }}" />
   			</jsp:include>
   	</div>
   
  </body>
</html>

