<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改管理员nickname</title>
    
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
		
		var adminEmobUpdateAdmins;
		
		function adminEmobUpdateGetAdmins(){
			var path = "<%= path%>/api/v1/communities/1/admin";
			$.ajax({

				url: path,

				type: "GET",

				dataType:"json",
 
				success:function(data){
					console.info(data);
					if(data.status == "yes"){
						$("#adminEmobUpdateSelect").html("<option value=\"-1\">请选择</option>");
						data = data.info;
						if(data != null && data != undefined){
							adminEmobUpdateAdmins = data;
							var len = data.length;
							for(var i = 0 ; i < len ; i++){
								$("#adminEmobUpdateSelect").append("<option value=\""+i+"\">"+data[i].username+"</option>");
							}
						}
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
		}
		
		function adminEmobUpdateSubmit(){
			var value = $("#adminEmobUpdateSelect").val();
			if(value == "-1"){
				alert("请选择管理员");
				return;
			}
			var obj = adminEmobUpdateAdmins[value];
			var path = "<%= path%>/api/v1/communities/1/admin/updateEmobId/"+obj.emobId;
			var nickname = $("#adminEmobUpdateNickname").val();
			if(nickname == ""){
				alert("请填写nickName");
				return;
			}
			var jsonObject = {
				nickname:nickname ,
				adminId:obj.adminId
			};
			$.ajax({

				url: path,

				type: "PUT",

				dataType:"json",
				
				data:JSON.stringify(jsonObject),
 
				success:function(data){
					if(data.status == "yes"){
						$("#adminEmobUpdateNicknameValue").attr("value",nickname);
						obj.nickname = nickname;
						document.getElementById("adminEmobUpdateNickname").value = ""; 
						alert(data.message);
					}
				},

				error:function(er){
			
					alert(er);
			
				}

			});
		}
		
		function adminEmobUpdateSelectOnchange(){
			var value = $("#adminEmobUpdateSelect").val();
			var obj = adminEmobUpdateAdmins[value];
			$("#adminEmobUpdateNicknameValue").attr("value",obj.nickname);
		}
	</script>		
  </head>
  
  <body>
  	<input type="button" value=" S E L E C T " onclick="adminEmobUpdateGetAdmins();" />
    <select id="adminEmobUpdateSelect" onchange="adminEmobUpdateSelectOnchange();">
    	<option value="-1">请选择</option>
    </select>
    
         原NickName: <input type="text" value="" id="adminEmobUpdateNicknameValue" readonly="readonly" />
    
    新NickName : <input type="text" value="" id="adminEmobUpdateNickname" />
    
    
    
    <input type="button" value=" 修 改 " id="adminEmobUpdateSubmit" onclick="adminEmobUpdateSubmit();" />
  </body>
</html>
