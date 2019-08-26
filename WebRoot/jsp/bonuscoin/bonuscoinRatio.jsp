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
    
    <title>帮帮币兑换比例</title>
    
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
		function getPushStatistics(communityId,sort){
			var path = "<%= path%>/api/v2/communities/"+communityId+"/bonuscoin/"+sort+"/web";
			$.ajax({

				url: path,

				type: "GET",

				dataType:'json',

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						$("#ratio_message").html("");
						data = data.info;
						$("#ratio_message").append("当前消费"+data.consume+"元可积"+data.exchange+"帮币");
					}else{
						$("#ratio_message").html("");
						$("#ratio_message").append("当前模块没有录入帮帮币消费比例");
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
		}
		
		function bonuscoinRatioSelect(){
			var communityId = $("#bonuscoin_select").val();
			var sort = $("#bonuscoin_shop").val();
			getPushStatistics(communityId, sort);
		}
		
		function bonuscoinSave(){
			var communityId = $("#bonuscoin_select").val();
			var sort = $("#bonuscoin_shop").val();
			var consume = $("#bonuscoin_consume").val();
			var exchange = $("#bonuscoin_exchange").val();
			
			var path = "<%= path%>/api/v2/communities/"+communityId+"/bonuscoin/web";
			var json = {
				"communityId":communityId,
				"sort":sort,
				"consume":consume,
				"exchange":exchange
			};
			$.ajax({

				url: path,

				type: "POST",

				dataType:'json',
				
				data:JSON.stringify(json),

				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						$("#ratio_message").html("");
						$("#ratio_message").append("当前消费"+consume+"元可积"+exchange+"帮币");
					}else{
						$("#ratio_message").html("");
						$("#ratio_message").append("当前模块没有录入帮帮币消费比例");
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
			
		}
	</script>	
	<style type="text/css">
	.bonuscoin-top{
		width: 100%;
		height: 50px;
		float: left;
	}
	
	.bonuscoin-center{
		width: 100%;
		height: 200px;
	}
	</style>	
  </head>
  
  <body>
  
  <div class="bonuscoin-top">
  	<select name="bonuscoin_select" id="bonuscoin_select">
  		<option value="1">天华</option>
  		<option value="3">狮子城</option>
  	</select>
  	
  	<select name="bonuscoin_shop" id="bonuscoin_shop">
  		<option value="2">快店</option>
  		<option value="4">送水</option>
  		<option value="5">维修</option>
  	</select>
  	
  	<input type="button" value=" SELECT " onclick="bonuscoinRatioSelect();" />
  	
  	<font id="ratio_message"></font>
  </div>
  	
  	<div class="bonuscoin-center">
  		消费<input type="text" value="100" id="bonuscoin_consume"  />元积累帮币数：<input type="text" value="" id="bonuscoin_exchange" />
  		<input type="button" value="保存" onclick="bonuscoinSave();" />
  	</div>
  
  	
  </body>
</html>
