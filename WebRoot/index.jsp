<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	
	String propetiesPath = this.getClass().getClassLoader().getResource("/").getPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->
 
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>登录_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js?version=<%=versionNum %>"></script>
<script type='text/javascript' src='${pageContext.request.contextPath }/easymob-webim1.0/strophe-custom-1.0.0.js?version=<%=versionNum %>'></script>
<script type='text/javascript' src='${pageContext.request.contextPath }/easymob-webim1.0/json2.js?version=<%=versionNum %>'></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/easemob.im-1.0.3.js?version=<%=versionNum %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/bootstrap.js?version=<%=versionNum %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/md5.js?version=<%=versionNum %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/base64.js?version=<%=versionNum %>"></script>


<script type="text/javascript">
$(function(){
	$("#lg_login").on("click",function(){
		MyLogin();
	});
});
function MyLogin(){
	 var musername = $("#lg_username").val();
	 var mpassword = $("#lg_password").val();
	 var xpassword = hex_md5(mpassword);

	 var path = "<%= path%>/api/v1/communities/1/admin/"+musername+"?mpassword="+mpassword+"&test=@bangTvTbang@";
	 if(musername != "" && mpassword != ""){
		var myJson = "{\"username\": \""+musername+"\",\"password\":\""+xpassword+"\"}";
		
	 	$.post(path,myJson,function(data){
	 		
	 		if(data.status == "yes"){
	 		
	 			if(data.info.role == "super"){
	 				window.location.href="jsp/admin_community.jsp";
	 				return false;
	 			}

	 			if(data.info.role == "other"){
	 			   document.getElementById("login_index").style.display="none";
	 			   document.getElementById("login_other").style.display="";
	 			}
	 		
                
                
	 			$("#UserName").attr("value",data.info.username);
	 			$("#hiddenUserName").attr("value",hex_md5(musername));
	 		 	$("#hiddenPassWord").attr("value",base64encode(xpassword));
	 		 	$("#hiddenCommunityId").attr("value",data.info.communityId);
	 		 	$("#hiddenAdminId").attr("value",data.info.adminId);
	 		 	$("#hiddenEmobId").attr("value",data.info.emobId);
	 		 //	alert(data.info.adminStatus);
	 		 	$("#admin_status").attr("value",data.info.adminStatus);
	 		 	//return false;
	           if(data.info.role=="owner"){
	         	 document.getElementById("myForm").submit();
	           }
	           
	 		 if(data.info.role=="operations"){
	 		   document.getElementById("myForm").action="<%= path%>/api/v1/communities/page/index/statHome";
	 		   document.getElementById("myForm").submit();
	 		 }
	 		 if(data.info.role=="partner"){
	 		   document.getElementById("myForm").action="<%= path%>/api/v1/communities/page/index/partnerHome";
	 		   document.getElementById("myForm").submit();
	 		 }
	 		 	
	 			//window.location.href="jsp/home.jsp?username="+hex_md5(musername) + "&password="+base64encode(xpassword); 
	 			
	 		}else{
	 			alert("用户名或密码错误");
	 		}
	 	},"json");
	 }else{
	 	alert("用户名或密码不能为空");
	 }
}
function strToJson(str){
	return JSON.parse(str);
} 
function loginOther(type){
  if(type=="owner"){
     document.getElementById("myForm").submit();
  }else if(type=="operations"){
     document.getElementById("myForm").action="<%= path%>/api/v1/communities/page/index/statHome";
     document.getElementById("myForm").submit();
  }
  
}
</script>
</head>
<body style="overflow:hidden;">

<div id="login_index">




	<form method="post" action="<%= path%>/api/v1/communities/page/index/goHome2" id="myForm" name="">
	<div class="login-bg"></div>
	<div class="login-blank"></div>
	<div class="login-box">
		<div class="login-logo"><img alt="" src="${pageContext.request.contextPath }/images/public/logo.png"/></div>
		<div class="login-form">
			<p class="login-user"><input type="text" id="lg_username" placeholder="用户名"/></p>
			<p class="login-password"><input onkeydown="if(event.keyCode==13){MyLogin();}" type = "password" id="lg_password" placeholder="密码"/></p>
		</div>
		<div class="login-option clearfix">
			<!--<label class="error-top">您输入的用户名或密码有误</label>--> 
			<a href="javascript:;">忘记密码？</a>	
			<input type="checkbox" checked="checked"/>
			<label href="javascript:;">记住密码？</label>
		</div>
		<div>
			<a id="lg_login" class="login-button" href="javascript:void(0);">登录</a>
		</div>
		<!--<a id="lg_regist" href="${pageContext.request.contextPath }/jsp/updateUser.jsp">修改密码</a>-->
		
		<input type="hidden" value="" name="hiddenPassWord" id="hiddenPassWord"/>
		<input type="hidden" value="" name="hiddenUserName" id="hiddenUserName" />
		<input type="hidden" value="" name="hiddenCommunityId" id="hiddenCommunityId" />
		<input type="hidden" value="" name="hiddenAdminId" id="hiddenAdminId" />
		<input type="hidden" value="" name="UserName" id="UserName" />
		<input type="hidden" value="" name="emobId" id="hiddenEmobId" />
		<input type="hidden" value="" name="adminStatus" id="admin_status" />
	</div>

	</form>
</div>
<div id="login_other" style="display: none;">

<input type="button" value="运营" onclick="loginOther('operations');"/>
<input type="button" value="物业"  onclick="loginOther('owner');"/>
</div>
</body>
</html>
