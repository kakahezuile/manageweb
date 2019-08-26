<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>缴费页</title>
    
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
   	<table>
   		<tr>
   			<td width="150" height="80"><a href="javascript:void(0);">新的缴费通知</a></td>
   			<td width="150"><a href="javascript:void(0);">处理中的缴费</a></td>
   			<td width="150"><a href="javascript:void(0);">完成的缴费</a></td>
   			<td width="150"><a href="javascript:void(0);">通知类编辑</a></td>
   		</tr>
   		
   		<tr>
   			<td width="150" height="80"><a href="javascript:void(0);">电费</a></td>
   			<td width="150"><a href="javascript:void(0);">水费</a></td>
   			<td width="150"><a href="javascript:void(0);">燃气费</a></td>
   			<td width="150"><a href="javascript:void(0);">宽带费</a></td>
   		</tr>
   	</table>
   	
   	
  </body>
</html>
