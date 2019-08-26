<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	path = "http://localhost:8080"+path;
	String propetiesPath = this.getClass().getClassLoader().getResource("/").getPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员密码修改</title>
<script src="${pageContext.request.contextPath }/js/respond.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/jquery-1.11.1.js"></script>
<script type='text/javascript' src='${pageContext.request.contextPath }/easymob-webim1.0/strophe-custom-1.0.0.js'></script>
<script type='text/javascript' src='${pageContext.request.contextPath }/easymob-webim1.0/json2.js'></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/easemob.im-1.0.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easymob-webim1.0/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/base64.js"></script>
<script type="text/javascript">
$(function(){
	$("#update").on('click', function() {
		var repassword = $("#repassword").val();
		var username = $("#username").val();
		var password = $("#password").val();
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
		updatePut(repassword, username, password);
  });
});
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
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function (data) { // Play with response returned in JSON format 
					if(data.status == "yes"){
						alert("修改成功了");
					}
				 },
		error: function (msg) {
					alert(msg);
				}
});
}
</script>
</head>
<body>

<table align="center" style="width: 300px;height: 200px;">
	<tr>
		<td>用户名:</td>
		<td><input type="text" name="username" id="username" /></td>
	</tr>
	
	<tr>
		<td>原密码:</td>
		<td><input type="password" name="password" id="password" /></td>
	</tr>
	
	<tr>
		<td>新密码:</td>
		<td><input type="password" name="repassword" id="repassword" /></td>
	</tr>
	
	<tr>
	
		<td><input type="button" value="修改" name="update" id="update" /></td>
	</tr>
	
</table>

</body>
</html>