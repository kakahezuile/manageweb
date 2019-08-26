<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>送水</title>
    
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
  		
  		function water_supply_submit(){
  				
  			var path = "<%= path%>/api/v1/communities/${communityId}/shops/life";
  			var water_supply_address = $("#water_supply_address").val();
  			var water_supply_company_name = $("#water_supply_company_name").val();
  			var water_supply_phone = $("#water_supply_phone").val();
  			if(water_supply_address == "" || water_supply_company_name == "" || water_supply_phone == ""){
  				alert("请完善信息");
  				return false;
  			}
  			var water_supply_data = {
  				"shopName" : water_supply_company_name ,
  				"address" : water_supply_address , 
  				"phone" : water_supply_phone ,
  				"sort" : "4"
  			};
  			
  			$.ajax({

			url: path,

			type: "POST",
			
			data:JSON.stringify(water_supply_data),

			dataType:"json",

			success:function(data){
				if(data.status == "yes"){
					document.getElementById("water_supply_address").value = "";
					document.getElementById("water_supply_company_name").value = "";
					document.getElementById("water_supply_phone").value = "";
					document.getElementById("water_supply_name").value = "";
					
  					
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
  				送水编辑项目
  			</td>
  		</tr>
  	</table>
  	<table width="100%" height="20%" style="margin:25px 0 25px 0;">
  		<tr>
  			<td>
  				名称
  			</td>
  			<td>
  				<input type="text" value="" name="" id="water_supply_name" />
  			</td>
  			
  			<td>
  				<input type="button" onclick="water_supply_submit();" value=" 保 存 "  />
  			</td>
  			
  			<td>
  				<input type="reset" name="water_supply_reset" value="重置" />
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
  				<input type="text" value="" name="" id="water_supply_phone" />
  			</td>
  			
  			<td>
  				公司名称
  			</td>
  			<td>
  				<input type="text" value="" name="" id="water_supply_company_name" />
  			</td>
  		</tr>
  		
  		<tr>
  			<td>
  				地址
  			</td>
  			<td colspan="3">
  				<input type="text" value="" name="" id="water_supply_address" />
  			</td>
  		</tr>
  	</table>
  	
  	
  </body>
</html>
