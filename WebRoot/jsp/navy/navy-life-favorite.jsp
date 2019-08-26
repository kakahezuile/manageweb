<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	Properties properties = PropertyTool.getPropertites("/configure.properties");
	String versionNum = properties.getProperty("version");
	String activitiesId=request.getParameter("activitiesId");
%>
<%
String community_id=request.getParameter("community_id");
String communityName=request.getParameter("communityName");
String str = new String(communityName.getBytes("ISO-8859-1"),"UTF-8");
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
<title>转发生活圈_小间运营系统</title>
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum%>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/navy.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/calendar.css?version=<%=versionNum%>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/operation.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath}/js/teamwork/teamwork-survey.js?version=<%=versionNum%>"></script>
<script type="text/javascript">
function upImg(el){
	var me = $(el),
		li = me.closest("li");
	
	li.before("<li><p><img></p><p><a href='javascript:void(0)' onclick='delImg(this)'>删除</a></p></li>");
	li.after("<li class='navy-upload'><a><input name='" + el.name + "' type='file' onchange='upImg(this)'></a></li>");
	li.hide();
	
	preview(el, li.prev().find("img"));
}
function delImg(el) {
	var li = $(el).closest("li");
	li.next().remove();
	li.remove();
}

function addLifeCircle() {
	var communities = document.getElementsByName("checkTheme"),
		communityIds = [],
		emobIds = [];
	
	$("[name=checkTheme]:checked").each(function() {
		communityIds.push(this.value);
		emobIds.push($("#user_select_" + this.value).val());
	});
	
	if (communityIds.length == 0) {
		alert("请选择要发布的小区!");
		return;
	}
	
	$("input[name=file_]").each(function() {
		var me = $(this);
		if (!me.val()) {
			me.attr("disabled", "disabled");
		}
	});
	
	$("#communityIds").val(communityIds.join(","));
	$("#emobIds").val(emobIds.join(","));
	$("#lifeCircleForm").get(0).submit();
	alert("转发成功");
// 	location.reload();
}

function communitiesUser(){
	$.ajax({
		url : "<%=path %>/api/v1/communities/summary/communitiesUser",
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data=data.info;
			
			var liList="";
			
			//根据需求更改置顶小区
			liList+="<div>";
				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\"3\">";
				liList+="<span>狮子城</span>";
				liList+="<select id=\"user_select_3\">";
				var listUser=data[2].listUser;
				for ( var j = 0; j <listUser.length; j++) {
					liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
				}
				liList+="</select>";
				liList+="</div>";
				
			liList+="<div>";
				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\"2\">";
				liList+="<span>首邑溪谷</span>";
				liList+="<select id=\"user_select_2\">";
				var listUser=data[1].listUser;
				for ( var j = 0; j <listUser.length; j++) {
					liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
				}
				liList+="</select>";
				liList+="</div>";
				
			liList+="<div>";
				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\"53\">";
				liList+="<span>加华印象</span>";
				liList+="<select id=\"user_select_53\">";
				for(var i=0;i<data.length;i++){
					if(data[i].communities.communityId!=53){
						continue;
					}
					var listUser=data[i].listUser;
				
					for ( var j = 0; j <listUser.length; j++) {
						liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
					}
				}
				liList+="</select>";
				liList+="</div>";
				
			liList+="<div>";
				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\"5\">";
				liList+="<span>汇邦幸福城</span>";
				liList+="<select id=\"user_select_5\">";
				var listUser=data[4].listUser;
				for ( var j = 0; j <listUser.length; j++) {
					liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
				}
				liList+="</select>";
				liList+="</div>";
				
			liList+="<div>";
				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\"6\">";
				liList+="<span>高成天鹅湖</span>";
				liList+="<select id=\"user_select_6\">";
				var listUser=data[5].listUser;
				for ( var j = 0; j <listUser.length; j++) {
					liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
				}
				liList+="</select>";
				liList+="</div>";
				
			for ( var i = 0; i < data.length; i++) {
				var skipId = data[i].communities.communityId;
				if(skipId==2 || skipId==3 || skipId==5 || skipId==6 || skipId==53){
					continue;
				}
				liList+="<div>";
				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\""+data[i].communities.communityId+"\">";
				liList+="<span>"+data[i].communities.communityName+"</span>";
				liList+="<select id=\"user_select_"+data[i].communities.communityId+"\">";
				var listUser=data[i].listUser;
				for ( var j = 0; j <listUser.length; j++) {
					liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
				}
				liList+="</select>";
				liList+="</div>";
			}
			
			//小区遍历原方法
// 			for ( var i = 0; i < data.length; i++) {
// 				liList+="<div>";
// 				liList+="<input type=\"checkbox\" name=\"checkTheme\" value=\""+data[i].communities.communityId+"\">";
// 				liList+="<span>"+data[i].communities.communityName+"</span>";
// 				liList+="<select id=\"user_select_"+data[i].communities.communityId+"\">";
// 				var listUser=data[i].listUser;
// 				for ( var j = 0; j <listUser.length; j++) {
// 					liList+="<option value=\""+listUser[j].emobId+"\">"+listUser[j].nickname+"</option>";
// 				}
// 				liList+="</select>";
// 				liList+="</div>";
// 			}
			
			$("#navy_community_id").html(liList);
			filterExist(<%=activitiesId%>);
		}
	});
}
function getActivitiesDetail(activitiesId){
	var life_circle_id = activitiesId;
	$.ajax({
		url: "<%=path %>/api/v1/communities/1/lifeCircle/getLifeCireDelit?life_circle_id="+life_circle_id,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			
			var list = data.list;
			var	activitesDatail = "";
			for (var i = 0; i < list.length; i++) {
				var url = list[i].photoUrl;
				activitesDatail += "<li><p><img src='" + url + "'></p><p><a href='javascript:;' onclick=\"delImg(this)\">删除</a></p></li><input type='hidden' name='existImages' value='" + url + "'>";
			}
			activitesDatail += "<li class='navy-upload'><a><input type='file' onchange='upImg(this)' name='file_'/></a></li>";
			
			$("#lifeContent").val(data.lifeContent);
			$("#statistics_shi_id").html(activitesDatail);
		}
	});
}

function filterExist(activitiesId){
	$.ajax({
		url: "<%=path %>/api/v1/communities/1/lifeCircle/getExisting?life_circle_id="+activitiesId,
		type: "GET",
		dataType:'json',
		success:function(data){
			var list = data.info;
			for(var i=0;i<list.length;i++){
				list[i] = list[i].communityId;
			}
			var communities = document.getElementsByName("checkTheme");
			for(var i=0;i<communities.length;i++){
				communities[i].disabled = $.inArray(parseInt(communities[i].value,10),list)!=-1;
			}
		}
	});
}

$(function() {
	communitiesUser();
	getActivitiesDetail("<%=activitiesId%>");
	
	var message = "${message}";
	if (message) {
		alert(message);
	}
});
</script>
</head>
<body>
	<input type="hidden" id="emobId" value="${sessionScope.emobId}">
	<input type="hidden" id="community_id" value="${sessionScope.community_id}">
	<div class="loadingbox" id="add-price-box" style="display: none;">
		<img alt="" src="<%=basePath%>/images/chat/loading.gif">
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display:none;">&nbsp;</div>
	<jsp:include flush="true" page="../public/navy-head.jsp" />
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/navy-left.jsp" >
				<jsp:param name="select" value="{parameterValue | 3}" />
				<jsp:param name="communityId" value="{parameterValue | 5}" />
			</jsp:include>
			<div class="right-body" style="border: none;">
				<div class="teamwork-title">所有生活圈话题</div>
				<div class="clearfix">
					<div class="navy-life-tag">请选择要发布的小区</div>
					<div class="navy-life-content">
						<div class="vavy-life-search">
							<input type="text" placeholder="请输入小区名字">
						</div>
						<div class="navy-community-list" id="navy_community_id"></div>
					</div>
				</div>
				<form id="lifeCircleForm" method="post" enctype="multipart/form-data" action="<%=path %>/api/v1/communities/1/lifeCircle/addLifeCircle">
					<input type="hidden" name="communityIds" id="communityIds">
					<input type="hidden" name="communityName" value=<%=str %>>
					<input type="hidden" name="emobIds" id="emobIds">
					<div class="clearfix">
						<div class="navy-life-tag">上传图片</div>
						<div class="navy-life-content">
							<div class="navy-upload-img">
								<ul id="statistics_shi_id">
									<li class="navy-upload">
										<a><input type="file" onchange="upImg(this);" name="file_"/></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="clearfix">
						<div class="navy-life-tag">创建群聊</div>
						<div class="navy-life-content">
							<input type="radio" id="yes" name="group" value="1">
							<label for="yes">创建 </label>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="no" name="group" value="2" checked="checked">
							<label for="yes">不创建 </label>
						</div>
					</div>
					<div class="clearfix">
						<div class="navy-life-tag">生活圈话题内容</div>
						<div class="navy-life-content">
							<textarea class="navy-life-word" name="lifeContent" id="lifeContent"></textarea>
						</div>
					</div>
					<div class="navy-life-send">
						<a href="javascript:;" onclick="addLifeCircle();">发布</a>
					</div>
				</form>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>