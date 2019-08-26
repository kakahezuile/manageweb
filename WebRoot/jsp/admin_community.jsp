<%@ page language="java" import="java.util.*,com.xj.utils.PropertyTool" pageEncoding="utf-8"%>

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
<link href="${pageContext.request.contextPath}/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath}/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath}/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.6.js"></script>
<script src="${pageContext.request.contextPath}/js/admin/admin_community.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script type="text/javascript">
var thisAppVersionId = 1;
var thisCommunityId = 1;
var thisCommunityName = "";
var ad=[];
getAppVersions();

function getAppVersions(){
	var path = "<%= path%>/api/v1/communities/appversion";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			var len = data.info.length;
			$("#app_version_select_id").html("");
			for(var i = 0 ; i < len ; i++){
				version = data.info[i].version;
				appVersionId = data.info[i].appVersionId;
				$("#app_version_select_id").append("<option value=\""+appVersionId+"\">"+version+"</option>");
				if(i == 0){
					thisAppVersionId = appVersionId;
					getCommunityList();
				}
			}
		}
	});
}

function clickUlGetCommunity(communityId,communityName,longitude,latitude){
	$("#communityContainer").show();
	$("#wuyeContainer").hide();
	
	thisCommunityId = communityId;
	thisCommunityName = communityName;
	
	var path = "<%= path%>/api/v1/communities/summary/"+communityId;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			document.getElementById("longitude").innerHTML=data.longitude;
			document.getElementById("latitude").innerHTML=data.latitude;
			document.getElementById("longitude_id").value=data.longitude;
			document.getElementById("latitude_id").value=data.latitude;
		}
	});
	
	$("#admin_community_start").html("");
	$("#admin_community_stop").html("");
	getCommunityStartSercice(communityId);
	getStopServiceCategory(communityId);
	if(communityName != ""){
		$("#admin_community_title").html(communityName);
	}
	seletPhoe(communityId);
	leftButton(communityId);
	seletShopName();
}

function leftButton(communityId){
	var admin_left_name=document.getElementsByName("admin_left_name");
	for(var i=0;i < admin_left_name.length;i++){
		admin_left_name[i].className="";
	}
	document.getElementById("admin_left_"+communityId).className="select";
}

function getCommunityList(){
	var path = "<%= path%>/api/v1/communities/webIm/communityService";
	var communityName = "";
	var communityId = 0;
	var longitude="";
	var latitude="";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			$("#admin_community_left_ul").html("");
			$("#admin_community_left_ul").append("<li><a class=\"left-menu-add\" onclick=\"$('#communityContainer').hide();$('#wuyeContainer').show();\" href=\"javascript:void(0)\">添加新物业</a></li>");
			$("#admin_community_left_ul").append("<li><a class=\"community-version\" href=\"${pageContext.request.contextPath}/jsp/admin/admin_community_version.jsp\">物业版本管理</a></li>");
			for(var i = 0; i < data.length; i++){
				communityName = data[i].communityName;
				communityId = data[i].communityId;
				longitude=data[i].longitude;
				latitude=data[i].latitude;
				addCommunityUl(communityName, communityId, i,longitude,latitude);
				if(i == 0){
					clickUlGetCommunity(communityId,communityName,longitude,latitude);
					thisCommunityId = communityId;
				}
			}
		}
	});
}
function addCommunityUl(communityName , communityId , num ,longitude,latitude){
	if(num == 0){
		$("#admin_community_left_ul").append("<li><a class=\"select\" name=\"admin_left_name\" id=\"admin_left_"+communityId+"\" href=\"javascript:void(0);\" onclick=\"clickUlGetCommunity("+communityId+",'"+communityName+"','"+longitude+"','"+latitude+"');\">"+communityName+"</a></li>");
	}else{
		$("#admin_community_left_ul").append("<li><a href=\"javascript:void(0);\" name=\"admin_left_name\" id=\"admin_left_"+communityId+"\" onclick=\"clickUlGetCommunity("+communityId+",'"+communityName+"','"+longitude+"','"+latitude+"');\" >"+communityName+"</a></li>");
	}
}

function addCommunityStartService(serviceName , serviceId , communityId , communityRelationId){
	$("#admin_community_start").append("<li><a href=\"javascript:;\" onclick=\"updateServiceCategory("+serviceId+",'none',"+communityId+" , "+communityRelationId+");\">"+serviceName+"</a></li>");
}

function addCommunityStopService(serviceName , serviceId , communityId , communityRelationId){
	$("#admin_community_stop").append("<li><a href=\"javascript:;\" onclick=\"addServiceCategory("+serviceId+","+communityId+" );\">"+serviceName+"</a></li>");
}

function getCommunityStartSercice(communityId){
	var path = "<%= path%>/api/v1/communities/webIm/communityService/isStartService/"+thisCommunityId+"?appVersionId="+thisAppVersionId;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			for(var i = 0 ; i < data.length ; i ++){
				addCommunityStartService(data[i].serviceName, data[i].serviceId,communityId,data[i].communityServiceId);
			}
		}
	});
}

function getStopServiceCategory(communityId){
	var path = "<%= path%>/api/v1/communities/webIm/communityService/isStopService/"+thisCommunityId+"?appVersionId="+thisAppVersionId;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			for(var i = 0 ; i < data.length ; i ++){
				addCommunityStopService(data[i].serviceName, data[i].serviceId,communityId,data[i].communityServiceId);
			}
		}
	});
}

function updateServiceCategory(serviceId , status , communityId , communityServiceId){
	var path = "<%= path%>/api/v1/communities/"+thisCommunityId+"/services/"+communityServiceId;
	var myJson = "{\"relationStatus\":\""+status+"\"}";
	$.ajax({
		url: path,
		type: "PUT",
		data : myJson ,
		dataType:'json',
		success:function(data){
			clickUlGetCommunity(communityId,"");
		}
	});
}

function addServiceCategory(serviceId, communityId){
	var path = "<%= path%>/api/v1/communities/"+thisCommunityId+"/services";
	
	var myJson = "[{\"serviceId\":\""+serviceId+"\",\"appVersionId\":\""+thisAppVersionId+"\"}]";
	$.ajax({
		url: path,
		type: "POST",
		data : myJson ,
		dataType:'json',
		success:function(data){
			clickUlGetCommunity(communityId,"");
		}
	});
}

function appVersionSelectClick(){
	appVersionSelect = $("#app_version_select_id");
	thisAppVersionId = appVersionSelect.val();
	clickUlGetCommunity(thisCommunityId, thisCommunityName);
}

function upCommunity(){
	var path = "<%= path%>/api/v1/communities/webIm/communityService/upCommunity";
	var myJson = "[{\"serviceId\":\""+serviceId+"\",\"appVersionId\":\""+thisAppVersionId+"\"}]";
	$.ajax({
		url: path,
		type: "POST",
		data : myJson ,
		dataType:'json',
		success:function(data){
			clickUlGetCommunity(communityId,"");
		}
	});
}

function addCommunities(){
	var longitude_id=document.getElementById("longitude_id").value;
	var latitude_id=document.getElementById("latitude_id").value;
	$.ajax({
		url: "<%=path%>/api/v1/communities/summary/"+thisCommunityId,
		type: "PUT",
		data:JSON.stringify({
			"longitude" : longitude_id,
			"latitude" : latitude_id
		}),
		dataType:'json',
		success:function(data){
			alert(data.message);
			
			document.getElementById("longitude").innerHTML=longitude_id;
			document.getElementById("latitude").innerHTML=latitude_id;
			document.getElementById("longitude_id").value=longitude_id;
			document.getElementById("latitude_id").value=latitude_id;
		}
	});
}
function getUpDa(){
	var path = "<%= path%>/api/v1/communities/webIm/communityService/isStartService/"+thisCommunityId+"?appVersionId="+thisAppVersionId;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			$("#admin_community_start_id").html('');
			for(var i = 0 ; i < data.length ; i ++){
				ad[i]=data[i].communityServiceId;
				$("#admin_community_start_id").append("<li><input type=\"text\" id=\"upCommu_"+data[i].communityServiceId+"\" value=\""+data[i].serviceLevel+"\"/>"+data[i].serviceName+"</li>");
			}
		}
	});
}
function upCommun(communityServiceId){
	var path = "<%= path%>/api/v1/communities/"+thisCommunityId+"/services/"+communityServiceId;
	var i=document.getElementById("upCommu_"+communityServiceId).value;
	$.ajax({
		url: path,
		type: "PUT",
		data:JSON.stringify({"serviceLevel" : i}),
		dataType:'json'
	});
}
function listUp(){
	for (var i=0;i<ad.length;i++) {
		upCommun(ad[i]);
	}
	alert("成功");
}
function seletPhoe(){
	var path = "<%= path%>/api/v1/communities/summary/getPublicizePhotos/"+thisCommunityId;
	$.ajax({
		url: path,
		type: "POST",
		dataType:'json',
		success:function(data){
			data=data.info;
			if(data!=null&&data.imgUrl!=null){
				document.getElementById("express_logo").src = data.imgUrl;
			}else{
				document.getElementById("express_logo").src = "${pageContext.request.contextPath}/images/community/1/ranking_bg.png";
			}
		}
	});
}

function addExpress(){
	var path = "<%= path%>/api/v1/communities/summary/addPublicizePhotos/"+thisCommunityId;
	$.ajaxFileUpload({
		url: path,
		secureuri: false,
		fileElementId: 'shops_file',
		type: 'POST',
		dataType : 'json',
		success: function (result) {
			alert("成功");
		}
	});
}

/**
* 从 file 域获取 本地图片 url
*/
function getFileUrl(sourceId) {
	var url;
	if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
		url = document.getElementById(sourceId).value;
	} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
		url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
	} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
		url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
	}
	return url;
}

/**
* 将本地图片 显示到浏览器上
*/
function preImg(sourceId, targetId) {
	var url = getFileUrl(sourceId);
	var imgPre = document.getElementById(targetId);
	imgPre.src = url;
}

(function ($) {
	jQuery.expr[':'].Contains = function(a,i,m){
		return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase())>=0;
	};
	function filterList(header, list) {
		var form = $("<form>").attr({"class":"filterform","action":"#"}),
			input = $("<input>").attr({"class":"filterinput","type":"text","id":"shop_id_name","style":"width:300px"}),
			aTe = $("<a>保存</a>").attr({"class":"green-button","onclick":"upCommShops();","href":"javascript:void(0);"});
		
		form.append(input).append(aTe).appendTo(header);
		
		$(input).change(function () {
			var str = $(this).val();
			var filter = str;
			if(filter) {
				$matches = $(list).find('a:Contains(' + filter + ')').parent();
				$('li', list).not($matches).slideUp();
				$matches.slideDown();
			} else {
				$(list).find("li").slideDown();
			}
			return false;
		}).keyup(function () {
			document.getElementById("contactlistUL").style.display="";
			$(this).change();
		});
	}
	$(function() {
		filterList($("#form_id"), $("#contactlistUL"));
	});
}(jQuery));

function shopList(){
	var path="<%= path%>/api/v1/communities/1/shops/getShopList";
	
	$.ajax({
		url : path,
		type : "GET",
		dataType : 'json',
		success : function(data) {
			data = data.info;
			
			var repair_overman = $("#contactlistUL");
			repair_overman.empty();
			var liList="";
			for ( var i = 0; i < data.length; i++) {
				liList+="<li  style=\"display: none;\"><a href=\"#/"+data[i].shopName+"\" onclick=\"addForm('"+data[i].shopName+"','"+data[i].shopId+"');\">"+data[i].shopName+"</a></li>";
			}
			repair_overman.append(liList);
		}
	});
}

shopList();
function addForm(shopName,shopId){
	document.getElementById("shop_id_name").value=shopName;
	document.getElementById("shops_id_id").value=shopId;
	li_list();
}

function upCommShops(){
	var shopName=   document.getElementById("shop_id_name").value;
	var path="<%= path%>/api/v1/communities/1/shops/upCommShops";
	var shopId=  document.getElementById("shops_id_id").value;
	var myJson = {
		"communityId" : thisCommunityId,
		"shopId" : shopId
	};
	$.ajax({
		url : path,
		type : "POST",
		data:JSON.stringify(myJson),
		dataType : 'json',
		success : function(data) {
			alert("成功");
			document.getElementById("service_shop_name").innerHTML="为小区选择快店（当前服务快店：<span class=\"send-price\">"+shopName+"</span>）";
		}
	});
}

function seletShopName(){
	getPublishPrice();
	var path = "<%= path%>/api/v1/communities/"+thisCommunityId+"/shops/getShopName";
	$.ajax({
		url: path,
		type: "POST",
		dataType:'json',
		success:function(data){
		data=data.info;
			document.getElementById("service_shop_name").innerHTML="为小区选择快店（当前服务快店：<span class=\"send-price\">"+data+"</span>）";
		},
		error:function(er){
		}
	});
}
function getPublishPrice(){
	var path = "<%= path%>/api/v1/communities/summary/getPublishPrice/"+thisCommunityId;
	$.ajax({
		url: path,
		type: "POST",
		dataType:'json'
	});
}
function upPublishPrice(){
	var path = "<%= path%>/api/v1/communities/summary/addPublishPrice";
	var ongoing_price=document.getElementById("ongoing_price").value;
	var myJson = {
		"communityId" : thisCommunityId,
		"price" : ongoing_price
	};
	$.ajax({
		url: path,
		type: "POST",
		data:JSON.stringify(myJson),
		dataType:'json',
		success:function(data){
			alert("成功");
			document.getElementById("getPublishPrice").innerHTML="小区周边商家发布抢购活动起步价（当前起步价：<span class=\"send-price\">"+ongoing_price+"</span>）";
		}
	});
}

function li_list(){
	document.getElementById("contactlistUL").style.display="none";
}
seletShopName();

function addWuye(){
	var communityName=document.getElementById("communityName").value;
	if(communityName==""){
		alert("请填写小区名字");
		return;
	}
	
	$.ajax({
		url: "<%=path%>/api/v1/communities/summary",
		type: "POST",
		data:JSON.stringify({
			"communityName" : communityName,
			"population" : 0 ,
			"communitiesDesc" : "",
			"longitude" : 116.424667,
			"latitude" : 39.885387,
			"shopServices" : "1,2,3",
			"facilityServices" : "1,2,3",
			"facilityServices" : "1,2,3",
			"cityId" : 0
		}),
		dataType:'json',
		success:function(data){
			alert(data.message);
		}
	});
}

function importRoom() {
	var fileEl = $("#roomFile"),
		formEl = $("#roomFileForm");
	if (!fileEl.val()) {
		alert("请选择要导入的文件");
		return;
	}
	
	formEl.attr("action", "<%=path%>/api/v1/communities/" + thisCommunityId + "/homeOwner/import");
	formEl.get(0).submit();
}
$(function() {
	$("#file_frame").bind("load", function() {
		var result = eval("(" + $(this.contentDocument.body).html() + ")");
		if (result.status === "yes") {
			alert("导入成功");
			$("#roomFile").val("");
		} else {
			alert(result.message);
		}
	});
});
</script>
</head>
<body>
	<input type="hidden" id="shops_id_id"/>
	<jsp:include flush="true" page="public/admin_head.jsp"/>
	<section>
		<div class="admin_content clearfix">
			<jsp:include flush="true" page="public/admin_community_menu.jsp"></jsp:include>
			<div class="admin-right-body" id="wuyeContainer" style="display:none;">
				<div class="main clearfix">
					<div class="community-titile">添加新物业</div>
					<div class="adduser-main">
						<div class="adduse-item">
							<span>物业名称</span>
							<input type="text" id="communityName" placeholder="例：狮子城"/>
						</div>
					</div>
					<div class="adduse-add-btn">
						<a class="admin-green-button" onclick="addWuye();" href="javascript:;">添加物业</a>
					</div>
				</div>
			</div>
			<div class="admin-right-body" id="communityContainer">
				<div class="main clearfix" style="position:relative; ">
					<div id="admin_community_title" class="community-titile">&nbsp;</div>
					<a href="javascript:;" class="green-button open-community">开启小区</a>
				</div>
				<div class="community-position">
					<p class="community-item-title"><span>小区经度：<b id="longitude"></b></span><span>小区纬度：<b id="latitude"></b></span></p>
					<p class="community-position-box"><label>小区经度</label><input type="text" id="longitude_id"/><label>小区纬度</label><input type="text" id="latitude_id"/><a class="green-button" onclick="addCommunities();" href="javascript:void(0);">保存</a></p>
				</div>
				<div class="community-position">
					<p class="community-item-title">人品值排行榜图片</p>
					<p class="community-position-box"><span><img id="express_logo" src="${pageContext.request.contextPath}/images/community/1/ranking_bg.png"></span><input id="shops_file" name="shops_file" onchange="preImg('shops_file','express_logo');" type="file" style="width:68px;"><a class="green-button" onclick="addExpress();" href="javascript:void(0);">保存</a></p>
				</div>
				<div class="community-box">
					<div class="community-item-title">
						已开启服务
						<div class="communituy-verson">
							<span>版本</span>
							<input onclick="listUp();" type="button" value="保存"/>
							<input onclick="getUpDa();" type="button" value="排序"/>
							<select id="app_version_select_id" style="width: 50px;" onchange="appVersionSelectClick();">
								<option value="0">无</option>
							</select>
						</div>
					</div>
					<div class="clearfix">
						<ul id="admin_community_start_id"></ul>
					</div>
					<div class="community-item-open clearfix" title="关闭服务">
						<ul id="admin_community_start"></ul>
					</div>
					<div class="community-item-title">未开启服务</div>
					<div class="community-item-close clearfix" title="开启服务">
						<ul id="admin_community_stop"></ul>
					</div>
				</div>
				<div class="community-position">
					<p class="community-item-title" id="service_shop_name">为小区选择快店（当前服务快店：<span class="send-price"></span>）</p>
					<div class="community-position-box">
						<div id="form_id"></div>
						<div class="shop-config">
							<ul id="contactlistUL"></ul>
						</div>
					</div>
				</div>
				<div class="community-position">
					<p class="community-item-title" id="getPublishPrice">小区周边商家发布抢购活动起步价（当前起步价：<span class="send-price">100</span>）</p>
					<p class="community-position-box"><input style="width:300px;" id="ongoing_price" type="text" placeholder="请输入抢购活动起步价" ><a class="green-button" onclick="upPublishPrice();" href="javascript:void(0);">保存</a></p>
				</div>
				<div class="community-position" style="margin-bottom:120px;">
					<p class="community-item-title" id="getPublishPrice">导入楼号：</p>
					<form id="roomFileForm" enctype="multipart/form-data" target="file_frame" method="POST">
						<p class="community-position-box">
							<input id="roomFile" name="roomFile" type="file" style="width:180px;">
							<a class="green-button" onclick="importRoom();" href="javascript:void(0);">导入</a>
						</p>
					</form>
					<iframe id="file_frame" name="file_frame" style="position:absolute;top:-100px;height:1px;width:100px;"></iframe>
				</div>
			</div>
		</div>
	</section>
</body>
<jsp:include flush="true" page="public/footer.jsp"></jsp:include>
</html>