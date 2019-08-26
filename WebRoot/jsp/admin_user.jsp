<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>添加物业_小间物业管理系统</title>
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
var adminUserList;
var adminAdduserThisCommunityId;
var adminUserThisRole;

getAminList();

var thisAdminId = 0;
var adminUserThisEmobId ;
var adminUserThisAdminId ;
var adminUser = 0
function getAminList(){
	var path = "<%= path%>/api/v1/communities/1/admin";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			adminUserList = data.info;
			addAdminUserMenuUl(data);
		}
	});
}

function addAdminUserMenuUl(data){
	$("#admin_user_menu_ul").find("li").remove();
	$("#admin_user_menu_ul").append("<li><a class=\"left-menu-add\" href=\"${pageContext.request.contextPath }/jsp/regist.jsp\">添加管理员</a></li>");
	
	for(var i = 0 ; i < data.info.length ; i++){
		$("#admin_user_menu_ul").append("<li><a href=\"javascript:void(0);\" name=\"user_id_name\" id=\"user_id_"+data.info[i].adminId+"\" onclick=\"getAdminUser('"+data.info[i].username+"',"+data.info[i].adminId+","+i+");\"  >"+data.info[i].username+"</a></li>");
		if(i == 0){
			$("#admin_user_name").html(data.info[i].username);
			getAllModel(data.info[i].adminId);
			addCommuminyList(data.info[i].adminId);
			thisAdminId = data.info[i].adminId;
		}
	}
}
function leftUser(adminId){
	var user_id_name=document.getElementsByName("user_id_name");
	for ( var i = 0; i < user_id_name.length; i++) {
		user_id_name[i].className="";
	}
	document.getElementById("user_id_"+adminId).className="select";
}
function getAdminUser(adminName , adminId ,index){
	$("#admin_user_name").html(adminName);
	adminUserThisRole = adminUserList[index].role;
	adminUserThisEmobId = adminUserList[index].emobId;
	adminUserThisAdminId = adminUserList[index].adminId;
	
	if(adminUserThisRole=="partner"){
		leftUser(adminId);
		partner(adminUserList[index].emobId);
		return ;
	}
	getAllModel(adminId);
	addCommuminyList(adminId);
	thisAdminId = adminId;
	leftUser(adminId);
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
				if(i == 0){
					adminAdduserThisCommunityId = data[i].communityId;
				}
				$("#admin_user_communityList_ul").append("<li><a onclick=\"clickAdminCommunity(this);\" name=\""+data[i].communityId+"\" id=\"adminUserLiId"+data[i].communityId+"\" href=\"javascript:void(0); \" >"+data[i].communityName+"</a></li>");
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
				document.getElementById(tempId).className = "select";
			}
		},
		error:function(er){
			alert(er);
		}
	});
}

function getAllModel(adminId){
	var path = "<%= path%>/api/v1/rule/getAllModel?adminId="+adminId;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			if(data.status == "yes"){
				data = data.info;
				addAdminUserModelUl(data, adminId);
				isTrueModel(adminId);
			}
		},
		error:function(er){
			alert(er);
		}
	});
}

function addAdminUserModelUl(data, adminId){
	$("#admin_user_model_ul").find("li").remove();
	$("#admin_user_model_ul2").find("li").remove();
	for(var i = 0; i < data.length ; i++){
		role = data[i].role;
		if(role == "owner"){
			if(data[i].ruleId==1||data[i].ruleId==4){//1是物业客服，4是投诉处理
				$("#admin_user_model_ul").append("<li><a id=\"adminUserModelAid"+data[i].ruleId+"\" name=\""+data[i].ruleId+"\" onclick=\"checkKefu("+data[i].ruleId+", '" + data[i].modelName + "', " + adminId + ", this);\" href=\"javascript:;\">"+data[i].modelName+"</a></li>");
			}else{
				$("#admin_user_model_ul").append("<li><a id=\"adminUserModelAid"+data[i].ruleId+"\" name=\""+data[i].ruleId+"\" onclick=\"clickAdminUserModel(this);\" href=\"javascript:;\">"+data[i].modelName+"</a></li>");
			}
		}else if(role == "operations"){
			if(data[i].ruleId==14||data[i].ruleId==13){//13是店家客服，14是投诉评价
				$("#admin_user_model_ul2").append("<li><a id=\"adminUserModelAid"+data[i].ruleId+"\" name=\""+data[i].ruleId+"\" onclick=\"checkKefu("+data[i].ruleId+", '" + data[i].modelName + "', " + adminId + ", this);\" href=\"javascript:;\">"+data[i].modelName+"</a></li>");
			}else{
				$("#admin_user_model_ul2").append("<li><a id=\"adminUserModelAid"+data[i].ruleId+"\" name=\""+data[i].ruleId+"\" onclick=\"clickAdminUserModel(this);\" href=\"javascript:;\">"+data[i].modelName+"</a></li>");
			}
		}
	}
}
function checkKefu(moduleId, moduleName, adminId, el) {
	el = $(el);
	if (el.hasClass("select")) {
		el.removeClass("select");
		adminUser = 0;
		return;
	}
	
	var kefu = getKefu();
	if (kefu.id != 0 && kefu.id != moduleId) {
		alert("该用户已经是" + kefu.name + "了，不能再将其设置为:" + moduleName + "!");
		return;
	}
	
	var communityId = $("#admin_user_communityList_ul li a[class=select]").attr("name");
	var adminStatus;
	if (moduleId == 1) {
		adminStatus = "3";
	} else if(moduleId == 4) {
		adminStatus = "2";
	} else if(moduleId == 13){
		adminStatus = "4";
	} else if(moduleId == 14) {
		adminStatus = "1";
	}
	
	$.ajax({
		url: "/api/v1/communities/" + communityId + "/admin/" + adminId + "/isUniqueKf?adminStatus=" + adminStatus,
		type: "GET",
		dataType: "json",
		async: false,
		success:function(data) {
			if (data.info === true) {
				el.addClass("select");
				adminUser = moduleId;
			} else if (data.info === false) {
				alert("该小区的" + moduleName + "已经存在了，不能再为该小区设置了!");
				el.removeClass("select");
				adminUser = 0;
			}
		}
	});
}

function getKefu() {
	var kefu = {id: 0};
	$("#admin_user_model_ul").find("a.select").each(function() {
		var ruleId = parseInt($(this).attr("name"), 10);
		if (ruleId == 1) {
			kefu.id = ruleId;
			kefu.name = "物业客服";
		} else if (ruleId == 4) {
			kefu.id = ruleId;
			kefu.name = "物业投诉";
		}
	});
	if (kefu != 0) {
		return kefu;
	}
	$("#admin_user_model_ul2").find("a.select").each(function() {
		var ruleId = parseInt($(this).attr("name"), 10);
		if (ruleId == 13) {
			kefu.id = ruleId;
			kefu.name = "帮帮客服";
		} else if (ruleId == 14) {
			kefu.id = ruleId;
			kefu.name = "帮帮投诉";
		}
	});
	return kefu;
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
					if(document.getElementById(tempId) != null){
						document.getElementById(tempId).className = "select";
					}
				}
			}
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

function clickAdminCommunity(me){
	$("#admin_user_communityList_ul li").find("a").attr("class","noselect");
	me.className = "select";
	adminAdduserThisCommunityId = me.name;
}

function adminUserAddCommunity(adminId , communityId){
	var path = "<%= path%>/api/v1/rule/addCommunity/"+adminId+"/"+communityId;
	$.post(path,function(data){
		if(data.status == "yes"){
			addCommuminyList(adminId);
		}
	},"json");
}

function adminUserOnSubmit(){
	if(adminUserThisRole=="partner"){
		upPartner();
	}
	
	var communityId = $("#admin_user_communityList_ul li a[class=select]").attr("name");
	if (adminUser == 1 || adminUser == 4 || adminUser == 14 || adminUser == 13) {
		setAdminAsKefu(communityId);
	}
	
	adminUserAddCommunity(thisAdminId, communityId);
	var myList = adminUserSelectedModel();
	adminUserAddModel(myList , thisAdminId);
}

function setAdminAsKefu(communityId) {
	var nickname,
		adminStatus;
	if (adminUser == 1) {
		nickname = "物业客服";
		adminStatus = "3";
	} else if(adminUser == 4) {
		nickname = "物业投诉";
		adminStatus = "2";
	} else if(adminUser == 13) {
		nickname = "帮帮客服";
		adminStatus = "4";
	} else if(adminUser == 14) {
		nickname = "帮帮投诉";
		adminStatus = "1";
	}
	
	$.ajax({
		url: "/api/v1/communities/" + communityId + "/admin/setAdminAsKefu",
		type: "POST",
		dataType: "json",
		data:JSON.stringify({
			"adminId": thisAdminId,
			"avatar": "http://wuye.ixiaojian.com/images/chat/serviceHeader.png",
			"adminStatus": adminStatus,
			"nickname": nickname,
			"startTime": "08:00:00",
			"endTime": "21:30:00"
		}),
		success:function(data) {
			if ("yes" != data.status) {
				alert("将管理员设置为" + nickname + "失败!");
				$("a[name='" + adminUser + "']").removeClass("select");
			}
		},
		error:function(e){
			alert("操作失败:" + e);
		}
	});
}

function adminUserAddModel(json,adminId){
	var path = "<%= path%>/api/v1/rule/"+adminAdduserThisCommunityId+"/addModel";
	$.post(path,JSON.stringify(json),function(data){
		if(data.status == "yes"){
			getAllModel(adminId);
		}
	},"json");
}

function adminUserSelectedModel(){
	var myList = new Array();
	var myUl;
	if(adminUserThisRole == "other"){
		myUl = document.getElementById("admin_user_model_ul");
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
		myUl = document.getElementById("admin_user_model_ul2");
		myLi = myUl.getElementsByTagName("li");
		for(var i = 0 ; i < myLi.length ; i++){
			var myJson = {
				"adminId" : 0 ,
				"ruleId" : 0 , 
				"status" : "block"
			};
			var myClassName = myLi[i].getElementsByTagName("a")[0].className;
			var myName = myLi[i].getElementsByTagName("a")[0].name;
			myJson.adminId = thisAdminId;
			myJson.ruleId = parseInt(myName);
			console.info(myClassName == "select");
			if(myClassName+"" == "select"){
				myJson.status = "block";
			}else{
				myJson.status = "none";
			}
			myList.push(myJson);
		}
	}else{
		if(adminUserThisRole == "owner"){
			myUl = document.getElementById("admin_user_model_ul");
		}else if(adminUserThisRole == "operations"){
			myUl = document.getElementById("admin_user_model_ul2");
		}
		
		var myLi = myUl.getElementsByTagName("li");
		for(var i = 0 ; i < myLi.length ; i++){
			var myJson = {
				"adminId" : 0 ,
				"ruleId" : 0 , 
				"status" : "block"
			};
			var myClassName = myLi[i].getElementsByTagName("a")[0].className;
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
	}
	
	return myList;
}

function partner(emobId){
	$("#admin_user_communityList_ul").find("li").remove();
	$("#admin_user_model_ul").find("li").remove();
	$("#admin_user_model_ul2").find("li").remove();
	var path = "<%= path%>/api/v1/rule/getCommunityList";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			for(var i = 0 ; i < data.length ; i++){
				$("#admin_user_communityList_ul").append("<li><a onclick=\"clickAdminPartner(this);\" name=\"partner\" id=\"adminUserLiId_"+data[i].communityId+"\" href=\"javascript:void(0); \" >"+data[i].communityName+"</a></li>");
			}
			getCommunityPartner(emobId);
		},
		error:function(er){
		
		}
	});
}

function getCommunityPartner(emobId){
	var path = "<%= path%>/api/v1/communities/summary/getListCommunityEmobId/"+emobId;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			for(var i = 0 ; i < data.length ; i++){
				document.getElementById("adminUserLiId_"+data[i].communityId).className="select";
			}
		},
		error:function(er){
		}
	});
}

function upPartner(){
	var path = "<%= path%>/api/v1/communities/summary/delPartnerPermission";
	var myJson={"emobId":adminUserThisEmobId};
	$.ajax({
		url: path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType:'json',
		success:function(data){
			var partner=document.getElementsByName("partner");
			for ( var i = 0; i < partner.length; i++) {
				if(partner[i].className=="select"){
					addPartner(partner[i].id);
				}
			}
			alert("成功");
		},
		error:function(er){
			alert("失败");
		}
	});
}

function addPartner(comm){
	var value = comm.replace(/[^0-9]/ig,"");
	var path = "<%= path%>/api/v1/communities/summary/addPartnerPermission";
	var myJson={"emobId":adminUserThisEmobId,"adminId":adminUserThisAdminId,"communityId":value};
	$.ajax({
		url: path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType:'json',
		success:function(data){
			alert("成功!");
		},
		error:function(er){
			alert("失败!");
		}
	});
}

function clickAdminPartner(me) {
	if (me.className == "select") {
		me.className = "";
	} else {
		me.className= "select";
	}
}
</script>
</head>
<body>
	<jsp:include flush="true" page="public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix">
			<jsp:include flush="true" page="public/admin_user_menu.jsp"></jsp:include>
			<div class="admin-right-body">
				<div class="main clearfix">
					<div class="user-titile">管理员 <span id="admin_user_name">李静</span>  权限分配</div>
				</div>
				<div class="community-box">
					<div class="community-item-title">受权物业管理</div>
					<div class="community-user-owner clearfix">
						<ul id="admin_user_communityList_ul">
							<li><a class="select" href="javascript:;" >狮子城</a></li>
							<li><a href="javascript:;" >清水湾</a></li>
							<li><a href="javascript:;" >小间公寓</a></li>
							<li><a href="javascript:;" >海棠湾</a></li>
						</ul>
					</div>
					<div class="community-item-title">物业受权使用功能</div>
					<div class="community-user-opt clearfix">
						<ul id="admin_user_model_ul">
							<li><a class="select" href="javascript:;">业主咨询</a></li>
							<li><a class="select" href="javascript:;">店家咨询</a></li>
							<li><a class="select" href="javascript:;">公告通知</a></li>
							<li><a href="javascript:;">物业缴费</a></li>
							<li><a href="javascript:;">投诉/处理</a></li>
							<li><a href="javascript:;">周边商铺</a></li>
							<li><a href="javascript:;">签约店家</a></li>
						</ul>
					</div>
					<div class="community-item-title">运营受权使用功能</div>
					<div class="community-user-opt clearfix">
						<ul id="admin_user_model_ul2">
							<li><a class="select" href="javascript:;">业主咨询</a></li>
							<li><a class="select" href="javascript:;">店家咨询</a></li>
							<li><a class="select" href="javascript:;">公告通知</a></li>
							<li><a href="javascript:;">物业缴费</a></li>
							<li><a href="javascript:;">投诉/处理</a></li>
							<li><a href="javascript:;">周边商铺</a></li>
							<li><a href="javascript:;">签约店家</a></li>
						</ul>
					</div>
				</div>
				<div class="community-user-save">
					<a class="admin-green-button" onclick="adminUserOnSubmit();" href="javascript:;">保存</a>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>