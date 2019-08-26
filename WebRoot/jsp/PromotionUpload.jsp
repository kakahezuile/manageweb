<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>促销图片添加</title>
    
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
		promotionUploadGetServices();
		function promotionUploadGetServices(){
 			
 			var path = "<%= path%>/api/v1/communities/1/services?appVersionId=1";
 		
 			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",
 
				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						$("#promotion_service_select").html("");
						data = data.info;
						var len = data.length;
						for(var i = 0 ; i < len ; i++){
							serviceId = data[i].serviceId;
							serviceName = data[i].serviceName;
							$("#promotion_service_select").append("<option value=\""+serviceId+"\">"+serviceName+"</option>");
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
 		
 		function promotionSubmit(){
 			var value = $("#promotion_service_select").val();
 			if(value == "-1"){
 				alert("请选择模块");
 				return;
 			}
 			var fileValue = $("#promotionUploadFile").val();
 			if(fileValue == "" || fileValue == null || fileValue == undefined){
 				alert("请选择图片文件");
 				return;
 			}
 			document.getElementById("promotionForm").submit();
 		}
	</script>		
  </head>
  
  <body>
  <form action="<%=path %>/api/v1/communities/1/promotions/upload" name="promotionForm" id="promotionForm" method="post" enctype="multipart/form-data">
  	<select id="promotion_service_select" name="promotionServiceSelect">
  		<option value="-1">请选择</option>
  	</select>
  	
  	<input type="file" name="promotionUploadFile" id="promotionUploadFile" accept="image/*" />
  	
  	<input type="button" onclick="promotionSubmit();" value=" 提 交 " name="" id="">
  </form>
   	
  </body>
</html>
