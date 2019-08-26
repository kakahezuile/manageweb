<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>PushStatistics</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript"
			src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js?version=<%=versionNum %>"></script>
<script type="text/javascript">
	function getPushStatistics(CMD_CODE,communityId){
		var path = "<%= path%>/api/v1/communities/"+communityId+"/pushstatistics/"+CMD_CODE;
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function pushStatisticsClick(){
		var CMD_CODE = $("#CMD_CODE").val();
		alert(CMD_CODE);
		var communityId = $("#communityId").val();
		getPushStatistics(CMD_CODE, communityId);
	}
</script>	
<style type="text/css">
	.push_div_top{
		width: 100%;
		height: 20%;
	}
	
	.push_div_center{
		width: 100%;
		height: 80%;
	}
</style>		
  </head>
  
  <body>
  <div id="push_div_top">
  	  小区：<select name="communityId" id="communityId">
  	  	<option value="1">天华</option>
  	  	<option value="3">狮子城</option>
  	  </select>	
  	 CMD_CODE：<select name="CMD_CODE" id="CMD_CODE">
  	  	<option value="100">普通通知</option>
  	  	<option value="101">电费通知</option>
  	  	<option value="102">燃气费通知</option>
  	  	<option value="103">水费通知</option>
  	  	<option value="104">快递通知</option>
  	  	<option value="105">宽带通知</option>
  	  	<option value="106">帮帮券通知</option>
  	  </select>
  	  
  	  <input value=" 查 询 " id="pushStatisticsClick" type="button" onclick="pushStatisticsClick();" />
  </div>
  
  <div id="push_div_center">
  	<table id="pushStatisticsTable" >
  		
  	</table>
  </div>
  </body>
</html>
