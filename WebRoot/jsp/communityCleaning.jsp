<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'communityService.jsp' starting page</title>
    
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
	
	function cleaningGetShopIdByAuthCode(fCode){
		
		var result = "no";
		
		var path = "<%= path%>/api/v1/communities/${communityId}/shops/authCode/"+fCode;
	
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					result = "yes";
					communityCleaningAddUser(data.info.shopId);
				}else{
					result = "no";
					alert("当前f码不合法");
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
		
		if(result == "no"){
			return false;
		}
		
	}
	
	function communityCleaningAddUser(shopId){
		var path = "<%= path%>/api/v1/communities/${communityId}/users";
		
		var myJson = {
			"username" : "" , 
			"password" : "" ,
			"role" : "shop" ,
			"phone" : ""
		};
		
		myJson.username = $("#community_cleaning_phone").val();
		myJson.password = $("#community_cleaning_pass").val();
		myJson.phone = $("#community_cleaning_phone").val();
		
		if(myJson.username == ""){
			alert("手机号不能为空");
			return false;
		}
		if(myJson.password == ""){
			alert("密码不能为空");
			return false;
		}
		
		$.ajax({

			url: path,

			type: "POST",
			
			data: JSON.stringify(myJson) ,

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					var emobId = data.emobId;
					var resultId = data.resultId;
					$("#community_cleaning_main_userId").attr("value",resultId);
					$("#community_cleaning_main_shopId").attr("value",shopId);
					community_cleaning_updateShop(emobId, shopId);
				}
			},

			error:function(er){
				
				alert(er);
			
			}

		});
	}
	
	function community_cleaning_submit(){
		var community_cleaning_main_shopId = $("#community_cleaning_main_shopId").val();
		var community_cleaning_main_userId = $("#community_cleaning_main_userId").val();
		if(community_cleaning_main_shopId != "" || community_cleaning_main_userId != ""){
			alert("账号注册完毕 请完善信息");
			return false;
		}
		var fCode = $("#community_cleaning_fcode").val();
		if(fCode == ""){
			alert("F码不能为空");
			return false;
		}
		cleaningGetShopIdByAuthCode(fCode);	
	}
	
	function community_cleaning_updateShop(emobId , shopId){
		
		var path = "<%= path%>/api/v1/communities/${communityId}/shops/"+shopId;
		
		var myJson = {
			"emobId" : emobId 
			
		};
		
		$.ajax({

			url: path,

			type: "PUT",
			
			data: JSON.stringify(myJson) ,

			dataType:'json',

			success:function(data){
				console.info(data);
				if(data.status == "yes"){
					alert("创建成功");
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
	}
	
	function community_cleaning_form_sub(){
		var community_cleaning_main_shopId = $("#community_cleaning_main_shopId").val();
		var community_cleaning_main_userId = $("#community_cleaning_main_userId").val();
		if(community_cleaning_main_shopId == "" || community_cleaning_main_userId == ""){
			alert("请先注册该账号");
			return false;
		}
	//	var str=document.getElementsByName("community_cleaning_main_service");
	//	var objarray=str.length;
	//	var chestr="";
	//	var strArray = new Array();
	//	for (i=0;i<objarray;i++){
	//		if(str[i].checked == true){
	//			strArray.push(str[i].value);
	//			chestr+=str[i].value+",";
	//		}
	//	}
	//	console.info(strArray);
	//	$("#community_cleaning_main_service_array").attr("value",chestr);
		document.getElementById("community_cleaning_username").value = myUserName;
		document.getElementById("community_cleaning_form").submit();
		$("input[type=reset]").trigger("click");
	}
	
	
</script>
  </head>
  
  <body>
  <form action="<%=path %>/api/v1/communities/page/index/${communityId}/cleaning/uploadFile" id="community_cleaning_form"  enctype="multipart/form-data" method="post">
  	<table width="100%" height="20%" align="center">
  	
  		<tr>
  			<td>
  				<a href="javascript:void(0);" onclick="weiXiuClick();">维修</a>
  			</td>
  			
  			<td>
  				<a href="javascript:void(0);" onclick="baoJieClick();" >保洁</a>
  			</td>
  			
  			<td>
  				<a href="javascript:void(0);" onclick="kuaiDiClick();" >快递</a>
  			</td>
  		</tr>
  		
  		<tr>
  			<td colspan="7">创建授权账号</td>
  		</tr>
  		<tr>
  			<td>F码</td>
  			<td><input type="text" value="" id="community_cleaning_fcode" /></td>
  			<td>手机号</td>
  			<td><input type="text" value="" id="community_cleaning_phone" /></td>
  			<td>密码</td>
  			<td><input type="password" value="" id="community_cleaning_pass" /></td>
  			
  			<td><input type="button"" value="确认" onclick="community_cleaning_submit();"  /></td>
  		</tr>
  	</table>
  	
  	<table id="community_cleaning_main" width="100%" height="50%" style="display: block;">
  		<tr>
  			<td colspan="4">
  				<input type="file" name="cleaningUploadFile" id="cleaningUploadFile" />
  			</td>
  		</tr>
  		
  		<tr>
  			<td>
  				真实姓名
  			</td>
  			
  			<td>
  				<input type="text" value="" id="community_cleaning_main_nickname" name="community_cleaning_main_nickname" />
  			</td>
  			
  			<td>
  				名称
  			</td>
  			
  			<td>
  				<input type="text" value="" id="community_cleaning_main_shopname" name="community_cleaning_main_shopname" />
  			</td>
  		</tr>
  		
  		<tr>
  			<td>
  				电话
  			</td>
  			
  			<td>
  				<input type="text" value="" id="community_cleaning_main_phone" name="community_cleaning_main_phone" />
  			</td>
  			
  			<td>
  				住址
  			</td>
  			
  			<td>
  				<input type="text" value="" id="community_cleaning_main_adress" name="community_cleaning_main_adress" />
  			</td>
  		</tr>
  		
  		<tr>
  			<td>
  				身份证
  			</td>
  			
  			<td>
  				<input type="text" value="" id="community_cleaning_main_idcard" name="community_cleaning_main_idcard" />
  			</td>
  			
  			
  		</tr>
  		
  		<tr>
  		
  			<td>
  				简介
  			</td>
  			<td>
  				<textarea rows="10" cols="40" id="community_cleaning_main_detail" name="community_cleaning_main_detail"></textarea>
  			</td>
  		</tr>
  		
  		<tr>
  			<td>
  				<input type="button" onclick="community_cleaning_form_sub();" value="保存" id="community_cleaning_main_submit" />
  				<input type="hidden" value="" name="community_cleaning_main_shopId" id="community_cleaning_main_shopId" /> 
  				<input type="hidden" value="" name="community_cleaning_main_userId" id="community_cleaning_main_userId" /> 
  				<input type="hidden" value="" name="community_cleaning_main_service_array" id="community_cleaning_main_service_array" /> 
  				<input type="reset" name="cleaning_reset" style="display: none;" />
  				<input type="hidden" name="community_cleaning_admin_id" value="${adminId }" />
				<input type="hidden" name="community_cleaning_username" value="${username }" />
				<input type="hidden" name="community_cleaning_password" value="${password }" />
  				
  			</td>
  		</tr>
  	</table>
  	</form>
  </body>
</html>
