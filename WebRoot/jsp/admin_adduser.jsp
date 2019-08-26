<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
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
<title>添加用户_小间物业管理系统</title>
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
<script type="text/javascript">
adminAdduserThisCommunityId;
getAminList();
var thisAdminId = 0;
function getAminList(){
	console.info("被执行");
	var path = "<%= path%>/api/v1/communities/1/admin";
		$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				console.info(data);
				addAdminUserMenuUl(data);
			},

			error:function(er){
			
				alert(er);
			
			}

		});
}

function addAdminUserMenuUl(data){
	$("#admin_user_menu_ul").find("li").remove();
	$("#admin_user_menu_ul").append("<li><a class=\"left-menu-add\" href=\"${pageContext.request.contextPath }/jsp/admin_adduser.jsp\">添加管理员</a></li>");
	
	for(var i = 0 ; i < data.info.length ; i++){
		$("#admin_user_menu_ul").append("<li><a href=\"javascript:void(0);\" onclick=\"getAdminUser('"+data.info[i].username+"',"+data.info[i].adminId+");\"  >"+data.info[i].username+"</a></li>");
		if(i == 0){
			$("#admin_user_name").html(data.info[i].username);
			getAllModel(data.info[i].adminId);
			addCommuminyList(data.info[i].adminId);
			thisAdminId = data.info[i].adminId;
		}
	}
}

function getAdminUser(adminName , adminId){
	$("#admin_user_name").html(adminName);
	getAllModel(adminId);
	addCommuminyList(adminId);
	thisAdminId = adminId;	
}
function addCommuminyList(adminId){
	$("#admin_user_communityList_ul").find("li").remove();
	var path = "<%= path%>/api/v1/rule/getCommunityList";
	$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				data = data.info;
				for(var i = 0 ; i < data.length ; i++){
					
					$("#admin_user_communityList_ul").append("<li><a onclick=\"clickAdminCommunity(this,"+data[i].communityId+");\" name=\""+data[i].communityId+"\" id=\"adminUserLiId"+data[i].communityId+"\" href=\"javascript:void(0); \" >"+data[i].communityName+"</a></li>");
				}
				getCommunityByAdminId(adminId);			
			},

			error:function(er){
			
				alert(er);
			
			}

		});
}

function getCommunityByAdminId(adminId){
	var path = "<%= path%>/api/v1/rule/getCommunity/"+adminId;
	$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
					data = data.info;
					 var tempId = "adminUserLiId"+data.communityId;
					 console.info(tempId + "------------- community");
					 document.getElementById(tempId).className = "select";
					// $("[name='admin_user_li_a_id_"+data.communityId+"']").attr("class","select");
				
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
}

function getAllModel(adminId){
	var path = "<%= path%>/api/v1/rule/getAllModel";
	$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
					data = data.info;
					addAdminUserModelUl(data);
					isTrueModel(adminId);
				}
				
			},

			error:function(er){
			
				alert(er);
			
			}

		});
}

function addAdminUserModelUl(data){
	$("#admin_user_model_ul").find("li").remove();
	for(var i = 0 ; i < data.length ; i++){
		$("#admin_user_model_ul").append("<li><a id=\"adminUserModelAid"+data[i].ruleId+"\" name=\""+data[i].ruleId+"\" onclick=\"clickAdminUserModel(this);\" href=\"javascript:;\">"+data[i].modelName+"</a></li>");
	}
}

function isTrueModel(adminId){
	var path = "<%= path%>/api/v1/rule/isTrueModel/"+adminId;
	$.ajax({

			url: path,

			type: "GET",

			dataType:'json',

			success:function(data){
				if(data.status == "yes"){
					data = data.info;					
					for(var i = 0 ; i < data.length ; i++){
						 var tempId = "adminUserModelAid"+data[i].ruleId;
						 console.info(tempId + "--ruleId --------------");
						 document.getElementById(tempId).className = "select";
						// $("[name='admin_user_model_a_"+data[i].ruleId+"']").attr("class","select");
					}
				}
			},

			error:function(er){
			
				alert(er);
			
			}

		});
}

function clickAdminUserModel(me){
	if(me.className == "select"){
		me.className = "";
	}else{
		me.className = "select";
	}
}

function clickAdminCommunity(me,communityId){
	$("#admin_user_communityList_ul li").find("a").attr("class","noselect");
	me.className = "select";
	adminAdduserThisCommunityId = communityId;
}

function adminUserAddCommunity(adminId , communityId){
	var path = "<%= path%>/api/v1/rule/addCommunity/"+adminId+"/"+communityId;
	$.post(path,function(data){
		if(data.status == "yes"){
		//	getAllModel(adminId);
			addCommuminyList(adminId);
		}
	},"json");
}

function adminUserOnSubmit(){
	var communityId = $("#admin_user_communityList_ul li a[class=select]").attr("name");
	adminUserAddCommunity(thisAdminId, communityId);
	var myList = adminUserSelectedModel();
	adminUserAddModel(myList , thisAdminId);
}
 
function adminUserAddModel(json,adminId){
	var path = "<%= path%>/api/v1/rule/"+adminAdduserThisCommunityId+"/addModel";
	$.post(path,JSON.stringify(json),function(data){
		if(data.status == "yes"){
			getAllModel(adminId);
		//	addCommuminyList(adminId);
		}
	},"json");
}

function adminUserSelectedModel(){
	
	
	var myList = new Array();
	var myUl = document.getElementById("admin_user_model_ul");
	var myLi = myUl.getElementsByTagName("li");
	for(var i = 0 ; i < myLi.length ; i++){
		var myJson = {
			"adminId" : 0 ,
			"ruleId" : 0 , 
			"status" : "block"
		};
		var myClassName = myLi[i].getElementsByTagName("a")[0].className;
		console.info(myClassName + "-----"+i);
		var myName = myLi[i].getElementsByTagName("a")[0].name;
		myJson.adminId = thisAdminId;
		myJson.ruleId = parseInt(myName);
		console.info(myClassName == "select");
		if(myClassName+"" == "select"){
			myJson.status = "block";
		}else{
			myJson.status = "none";
		}
		myList[i] = myJson;
	}
	console.info(myList);
	return myList;
}
</script>
</head>
<body>
	<jsp:include flush="true" page="public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix center-personal-info-content">
			<jsp:include flush="true" page="public/admin_user_menu.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix">
					<div class="user-titile">添加管理员</div>
					<div class="adduser-main">
						<div class="adduse-item">
							<span>用户名</span>
							<input type="text" placeholder="用户手机号"/>
							<p>您输入的手机号不正确</p>
						</div>
						<div class="adduse-item">
							<span>密码</span>
							<input type="password" placeholder="6位以上密码"/>
						</div>
						<div class="adduse-item">
							<span>重复密码</span>
							<input type="password" placeholder="重复密码"/>
						</div>
						<div class="adduse-item">
							<span>真实姓名</span>
							<input type="text" placeholder="例：李明"/>
						</div>
						<div class="adduse-item">
							<span>联系电话</span>
							<input type="text" placeholder="例：18645474778"/>
						</div>
					</div>
					<div class="adduse-add-btn">
						<a class="admin-green-button" href="javascript:;">添加用户</a>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>