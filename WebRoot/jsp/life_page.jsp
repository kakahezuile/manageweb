<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生活黄页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	
	<script type="text/javascript">
		$(function(){
			$("#life_page_water_supply_a").click(function(){
				lifePageSetNone();
				$("#this_water_supply_div_id").attr("style","display:block");
			});
			
			$("#life_page_emergency_number_a").click(function(){
				lifePageSetNone();
				$("#this_emergency_number_div_id").attr("style","display:block");
			});
		});
		function lifePageSetNone(){
			$("#this_water_supply_div_id").attr("style","display:none");
			$("#this_emergency_number_div_id").attr("style","display:none");
		}
	</script>
  </head>
  
  <body>
  	
  	<div style="width: 100%;float: left;height: 20px;" id="this_life_page_div_id">
  		<a href="javascript:void(0);" id="life_page_water_supply_a">送水</a>
  		<a href="javascript:void(0);" id="life_page_emergency_number_a">紧急电话</a>
  	</div>
  	
  	<div id="this_water_supply_div_id" style="width: 100%;float: left;">
  		<jsp:include flush="true" page="../jsp/water_supply.jsp"></jsp:include>
  	</div>
  	
  	<div id="this_emergency_number_div_id" style="width: 100%;float: left;display: none;">
  		<jsp:include flush="true" page="../jsp/emergency_number.jsp"></jsp:include>
  	</div>
  	
  </body> 
</html>
