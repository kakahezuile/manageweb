<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>服务模块推送消息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript"
			src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
 	<script type="text/javascript">
 		function serviceMessageGetServices(){
 			
 			var path = "<%= path%>/api/v1/communities/1/services?appVersionId=1";
 		
 			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",
 
				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						$("#service_message_select").html("");
						data = data.info;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							serviceId = data[i].serviceId;
							serviceName = data[i].serviceName;
							$("#service_message_select").append("<option value=\""+serviceId+"\">"+serviceName+"</option>");
						}
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
 		}
 		
 		function serviceMessageSendMessage(){
 			var path = "";
 		
 			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",
 
				success:function(data){
					console.info(data);
					if(data.status == "yes"){
					
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
 		}
 		
 		function promotionSendChange(){
 			var sort = $("#service_message_select").val();
 			var path = "<%= path%>/api/v1/communities/1/promotions?q="+sort;
 			
 			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",
 
				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						data = data.info;
						if(data == undefined){
							$("#promotionSendImgId").attr("src","");
						}else{
							imgUrl = data.imgUrl;
							$("#promotionSendImgId").attr("src",imgUrl);
						}
						console.info(data);
						
						
					}else{
						alert(data.message);
					}	
				},

				error:function(er){
			
					alert(er);
			
				}

			});
 		}
 	</script>
  </head>
  
  <body>
  	<select id="service_message_select" onchange="promotionSendChange();">
  	
  	</select>
  	
    <input type="button" value="SELECT" onclick="serviceMessageGetServices();" />
    
    <input type="button" value=" 发 送 " onchange="serviceMessageSendMessage();" />
    
    <img alt="" src="" id="promotionSendImgId" />
  </body>
</html>
