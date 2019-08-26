<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();

	String propetiesPath = this.getClass().getClassLoader().getResource("/").getPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	$("#regist").on('click', function() {
	var userName = $("#username").val();
	var passWord = $("#password").val();
	var repassWord = $("#repassword").val();
	var phone = $("#phone").val();
	if(userName == "" || userName == null){
		alert("用户名不能为空");
		return false;
	}
	if(passWord == "" || repassWord == "" || passWord == null || repassWord == null){
		alert("密码不能为空");
		return false; 
	}
	if(passWord != repassWord){
		alert("确认密码不一致");
		return false;
	}
	if(phone == ""){
		alert("手机号为空");
		return false;
	}
	
	passWord = hex_md5(passWord);
	/*var options = {
		username : hex_md5(userName),
		password : passWord,
		appKey : 'xiaojiantech#bangbang',
		success : function(result) {
				//注册成功;
				alert("成功了");
				console.info(result);
			//	var str = JSON.stringify(result);
			//	var jsonObject = strToJson(str);
			//	var id = jsonObject.duration;
				registPost(hex_md5(userName), userName, passWord);
		},
		//{ action: "post", application: "c78f55a0-76e7-11e4-9c9b-ff6d1f1000af", path: "/users", uri: "https://a1.easemob.com/kuaiju/xiaojianproperty/users", entities: Array[1], timestamp: 1420712853937, duration: 74, organization: "kuaiju", applicationName: "xiaojianproperty" }
		error : function(e) {
				//注册失败;
				alert("失败了");
		}
	};
	Easemob.im.Helper.registerUser(options);*/
	registPost(userName, passWord,phone);
  });
});
function registPost(username,password,phone){


  var dd=hex_md5("&bang#bang@"+password);
    var idd=document.getElementById("competenceId").value;
	var path = "<%= path%>/api/v1/communities/"+idd+"/admin/user";
	var competence = $("#competenceId").find("option:selected").val();
	
	var myJson = "{ \"username\": \""+phone+"\",\"nickname\": \""+username+"\", \"password\": \""+password+"\" ,  \"role\":\"owner\" }";
	$.post(path,myJson,function(data){
		if(data.status == "yes"){
			alert("注册成功了");
		}
	},"json");
}
function strToJson(str){
	return JSON.parse(str);
} 

function xiaoqu(){
	var path = "<%= path%>/api/v1/communities/webIm/communityService";
	$.ajax({
			url: path,
			type: "GET",
			dataType:'json',
			success:function(data){
				data = data.info;
				var repair_overman = $("#competenceId");
			      repair_overman.empty();
				for(var i = 0 ; i < data.length ; i++){
				    repair_overman.append("<option value=\""+data[i].communityId+"\">"+data[i].communityName+"</option>");
				}
				
					
			},

			error:function(er){
			
			
			}

		});
	


}
xiaoqu();
</script>
</head>
<body>

<table align="center" style="width: 300px;height: 200px;" >
	<tr>
		<td>手机:</td>
		<td><input type="text" name="phone" id="phone" /></td>
	</tr>
	<tr>
		<td>昵称:</td>
		<td><input type="text" name="username" id="username" /></td>
	</tr>
	
	<tr>
		<td>所在小区</td>
		<td><select id="competenceId" name="competenceId">
		<option value="owner">物业端</option>
		<option value="operations">运营端</option>
		<option value="super">管理端</option>
		<option value="partner">运营合作伙伴</option>
		<option value="other">通用</option>
		</select></td>
	</tr>
	
	<tr>
		<td>密码:</td>
		<td><input type="password" name="password" id="password" /></td>
	</tr>
	
	<tr>
		<td>确认密码:</td>
		<td><input type="password" name="repassword" id="repassword" /></td>
	</tr>
	
	<tr>
		<td><input type="button" value="注册" name="regist" id="regist" /></td>
	</tr>
	
</table>

</body>
</html>