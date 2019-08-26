<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String webTitle = properties.getProperty("webTitle"); 
%>
<%

String userName=request.getParameter("userName");

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
<title>修改密码_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->

<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script src="${pageContext.request.contextPath }/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath }/js/admin/admin_user.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/base64.js"></script>
<script type="text/javascript">
function registPost(xPassWord,username,password){
	var path = "<%= path%>/api/v1/communities/1/admin";
	password = hex_md5(password);
	var myJson = "{ \"username\": \""+username+"\", \"password\": \""+password+"\" , \"xPassWord\": \""+xPassWord+"\" }";
	$.post(path,myJson,function(data){
		if(data.status == "yes"){
			alert("修改成功了");
		}
	},"json");
}
function strToJson(str){
	return JSON.parse(str);
} 

function updatePut(xPassWord,username,password){
	password = hex_md5(password);
	xPassWord = hex_md5(xPassWord);
	var myJson = "{ \"username\": \""+username+"\", \"password\": \""+password+"\" , \"xPassWord\": \""+xPassWord+"\" }";
	var path = "<%= path%>/api/v1/communities/1/admin";
	$.ajax({
		type: "PUT",
		url: path,
		data: myJson,
		//contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function (data) { // Play with response returned in JSON format 
					if(data.status == "yes"){
						alert("修改成功了");
					}else{
					   alert("密码错误");
					}
				 },
		error: function (msg) {
					alert("网络连接失败");
		}
});
}

function up(){
       var repassword = $("#repassword").val();
		var username = $("#username").val();
		var password = $("#password").val();
		var password2 = $("#repassword_2").val();
		if(repassword == "" || repassword == null){
			alert("新密码不能为空");
			return false;
		}
		if(username == "" || username == null){
			alert("用户名不能为空");
			return false;
		}
		if(password == "" || password == null){
			alert("原密码不能为空");
			return false;
		}
		
		if(password2!=repassword){
		alert("密码不一致");
			return false;
		}
		updatePut(repassword, username, password);

}
</script>
</head>
<body>
	<section>
		<div class="admin_content clearfix center-personal-info-content">
			<div style="float:left;width:210px;float:left;">&nbsp;</div>
			<div class="admin-right-body">
				<div class="main clearfix">
					<div class="user-titile">修改密码</div>
					<input type="hidden" value="<%=userName%>" name="username" id="username" />
					<div class="adduser-main">
						<div class="adduse-item">
							<span>原密码</span>
							<input type="password" placeholder="原始密码" name="password" id="password"  />
						</div>
						<div class="adduse-item">
							<span>新密码</span>
							<input type="password" placeholder="6位以上密码"  name="repassword" id="repassword"/>
						</div>
						<div class="adduse-item">
							<span>重复密码</span>
							<input type="password" placeholder="重复密码"  id="repassword_2"/>
						</div>
					</div>
					<div class="adduse-add-btn" style="margin-bottom:60px;">
						<a class="admin-green-button" href="javascript:;" onclick="up();" id="update">确定</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>