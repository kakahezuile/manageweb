<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>紧急号码</title>
    
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
  			
  		});
  		
  		function emergency_number_submit(){
  				
  			var path = "<%= path%>/api/v1/communities/${communityId}/shops/number";
  			var emergency_number_address = $("#emergency_number_address").val();
  			var emergency_number_name = $("#emergency_number_name").val();
  			var emergency_number_phone = $("#emergency_number_phone").val();
  			if(emergency_number_address == "" || emergency_number_name == "" || emergency_number_phone == ""){
  				alert("请完善信息");
  				return false;
  			}
  			var emergency_number_data = {
  				"shopName" : emergency_number_name ,
  				"address" : emergency_number_address , 
  				"phone" : emergency_number_phone ,
  				"sort" : "11"
  			};
  			
  			$.ajax({

			url: path,

			type: "POST",
			
			data:JSON.stringify(emergency_number_data),

			dataType:"json",

			success:function(data){
				if(data.status == "yes"){
					document.getElementById("emergency_number_address").value = "";
					
					document.getElementById("emergency_number_phone").value = "";
					document.getElementById("emergency_number_name").value = "";
					
  					
					alert("添加信息成功");
					
				}else{
					alert("添加信息出错");
				}
			},

			error:function(er){
			
				alert("添加信息出错");
			
			}

		});
  		}
  	</script>
  </head>
  
  <body>
  	<table width="100%" height="10%" style="margin:25px 0 25px 0;">
  		<tr>
  			<td>
  				紧急号码编辑项目
  			</td>
  		</tr>
  	</table>
  	<table width="100%" height="20%" style="margin:25px 0 25px 0;">
  		<tr>
  			<td>
  				名称
  			</td>
  			<td>
  				<input type="text" value="" name="" id="emergency_number_name" />
  			</td>
  			
  			<td>
  				<input type="button" onclick="emergency_number_submit();" value=" 保 存 "  />
  			</td>
  			
  			<td>
  				<input type="reset" name="emergency_number_reset" value="重置" />
  			</td>
  			
  		</tr>
  	</table>
  	
  	<table width="100%" height="69%">
  		<tr>
  			<td colspan="4">
  				其他信息
  			</td>
  		</tr>
  		
  		<tr>
  			<td height="30"> </td>
  		</tr>
  		
  		<tr>
  			<td>
  				联系电话
  			</td>
  			<td>
  				<input type="text" value="" name="" id="emergency_number_phone" />
  			</td>
  			
  			
  		</tr>
  		
  		<tr>
  			<td>
  				地址
  			</td>
  			<td colspan="3">
  				<input type="text" value="" name="" id="emergency_number_address" />
  			</td>
  		</tr>
  	</table>
  	
  	
  </body>
</html>
