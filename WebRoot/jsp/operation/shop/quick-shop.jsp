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
<title>周边商铺_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/operation.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/bootstrap-3.3.5-dist/js/jquery-1.11.1.min.js?version=<%=versionNum%>"></script>
<script src="${pageContext.request.contextPath }/js/admin/admin_shop_sort.js"></script>
<script src="${pageContext.request.contextPath }/js/public.js"></script>
<script type="text/javascript">
var communityId = window.parent.document.getElementById("community_id_index").value;
$(document).ready(function(){
	fastGetItemCategoryList(2);
});
var fastShopList;
var fastShopCatId;
function fastGetItemCategoryList(sort){
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories/web?q="+sort;
	fastShopMain = $("#fastShopMain");
	$.ajax({
		url: path,
		type: "GET",
		dataType:"json",
		success:function(data){
			var dataList = data.info;
			fastShopList = dataList;
			fastShopMain.html("");
			fastShopMain.append("<tr><th>快店分类</th><th>分类版本</th><th>排序</th><th>操作</th></tr>");
			for(var i = 0 ; i < dataList.length ; i++){
				var style = i%2==0?"odd":"even";
				catName = dataList[i].catName;
				catId = dataList[i].catId;
				catDesc = dataList[i].categoryVersion;
				sortOrder = dataList[i].sortOrder;
				if(i%2==0){
					className="even";
				}else{
					className="odd";
				}
				fastShopMain.append("<tr class='"+style+"'><td>"+catName+"</td><td>"+catDesc+"</td><td><label>"+sortOrder+"</label></td><td width=\"20%\"><a href=\"javascript:void(0);\" onclick=\"fastUpdate("+i+");\" >编辑</a><a style=\"margin-left:15px;\" href=\"javascript:void(0);\" onclick=\"fastDelete("+catId+");\" >删除</a></td></tr>");
			}
		}
	});
}
function fastAddItemCategory(type){
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories";
	catName = $("#fastShopType").val();
	keywords = "";
	catDesc = $("#fastShopDescription").val();
	sort = $("#sort_order").val();
	if(catName != null && catName != ""){
		if(catName.indexOf("特卖") > 0){
			keywords = "yes";
		}else{
			keywords = "no";
		}	
	}else{
		keywords = "no";
	}
	
	if(catName == "" || catDesc == ""){
		alert("需要完善内容!");
		return;
	}
	
	var myJson = {
		"catName" : catName ,
		"keywords" : keywords ,
		"categoryVersion" : catDesc ,
		"shopType" : type,
		"sortOrder":sort
	};
	
	$("#show_text").text("操作中,请等待！");
	$("#showing").show();
	
	$.ajax({
		url: path,
		type: "POST",
		dataType:"json",
		data:JSON.stringify(myJson),
		success:function(data){
			if(data.status == "yes"){
				fastGetItemCategoryList(type);
			}else if(data.status == "no"){
				alert("新增分类出现了错误!");
			}
		}
	});
}

function fastUpdate(index){
	myObejct = fastShopList[index];	
	document.getElementById("fastShopTypeUpdate").value = myObejct.catName;
	document.getElementById("categoryVersionUpdate").value = myObejct.categoryVersion;
	document.getElementById("sort_order_update").value = myObejct.sortOrder;
	fastShopCatId = myObejct.catId;
	$("#maintenceTable").attr("style","display:block");
}

function fastDelete(index){
	var path = "<%=path%>/api/v1/communities/"+communityId+"/users/admin/itemCategories/"+catId;
	alert(path);
	$.ajax({
		url: path,
		type: "DELETE",
		dataType:"json",
		success:function(data){
			if(data.status == "yes"){
				alert("删除分类成功!");
				fastGetItemCategoryList(2);
			}else{
				alert("删除分类失败!");
			}
		}
	});
}

function fastUpdateSubmit(type){
	catName = document.getElementById("fastShopTypeUpdate").value;
	catVersion = document.getElementById("categoryVersionUpdate").value;
	sortOrder = document.getElementById("sort_order_update").value;
	if(catName == "" || catDesc == ""){
		alert("修改内容不能为空");
		return;
	}
	
	var path = "<%=path%>/api/v1/communities/" + communityId + "/users/admin/itemCategories/" + fastShopCatId;
	var myJson = {
		"catName" : catName,
		"categoryVersion" : catVersion,
		"sortOrder" : sortOrder,
	};
	$.ajax({
		url : path,
		type : "PUT",
		dataType : "json",
		data : JSON.stringify(myJson),
		success : function(data) {
			if (data.status == "yes") {
				fastUpdateHide();
				alert("修改成功");
				fastGetItemCategoryList(type);
			}else{
				alert("修改失败");
			}
		}
	});
}
function fastUpdateHide(){
	$("#maintenceTable").attr("style", "display:none");
}
</script>
</head>
<body>
	<input id="facilities_class" type="hidden" value="">
	<input id="facilities_type" type="hidden" value="">
	<section>
		<div class="content clearfix center-personal-info-content">
			<jsp:include flush="true" page="./shop-manage-left.jsp">
				<jsp:param name="module" value="quick_shop"/>
			</jsp:include>
			<div class="nearby_right-body">
				<div class="shop-dosage">
					<ul>
						<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/quick-shop.jsp" class="select">分类配置</a></li>
						<li><a href="${pageContext.request.contextPath }/jsp/operation/shop/nearby-shop-setting.jsp" >起送配置</a></li>
					</ul>
				</div>
				<div class="main clearfix">
					<div class="main clearfix">
						<div class="adduser-main">
							<div class="adduse-item">
								<span>快店分类</span>
								<input type="text" name="fastShopType" id="fastShopType" placeholder="如：零食" />
							</div>
							<div class="adduse-item">
								<span>分类版本</span>
								<input type="text" name="fastShopDescription" id="categoryVersion" placeholder="如：v1 v2" />
							</div>
							<div class="adduse-item">
								<span>分类排序</span>
								<input type="text" name="fastShopDescription" id="sort_order" placeholder="如：3" />
							</div>
						</div>
						<div class="adduse-add-btn">
							<a class="admin-green-button" href="javascript:void(0);" onclick="fastAddItemCategory(2);">添加</a>
						</div>
					</div>
					<div class="sort-list">
						<table id="fastShopMain" class="express-list-table">
							<col class="repair-type" />
							<col class="repair-description" />
							<col class="repair-operation" />
							<tr>
								<th>维修类型</th>
								<th>维修描述</th>
								<th>操作</th>
								<th>&nbsp;</th>
							</tr>
							<tr class="odd">
								<td></td>
								<td></td>
								<td>
									<a href="javascript:;">编辑</a>
									<a href="javascript:;">删除</a>
								</td>
							</tr>
							<tr class="even">
								<td></td>
								<td></td>
								<td>
									<a href="javascript:;">编辑</a>
									<a href="javascript:;">删除</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
	<div class="activity-blank-bg" style="display:none;">&nbsp;</div>
	
	<div id="bg_upnearby" class="activity-blank-bg" style="display: none;">
		&nbsp;
	</div>
	<div id="upnearby" style="z-index: 300; display: none;"
		class="nearby-pop">
		<input type="hidden" id="facility_id" />
		<div class="nearby-pop-title">
			修改店铺信息
			<a href="javascript:;" onclick="opUpnearby();">&nbsp;</a>
		</div>
		<div>
			<span>店名</span>
			<input type="text" id="facilities_name" placeholder="如：大碗居" />
		</div>
		<div>
			<span>位置</span>
			<input type="text" id="facilities_address" placeholder="如：法华南里" />
		</div>
		<div>
			<span>类型</span>
			<select id="facilities_type_nearby">
			</select>
		</div>
		<div class="time">
			<span>营业时间</span>
			<input type="text" id="businessStartTime" placeholder="如：8:00" />
			<label>到</label>
			<input type="text" id="businessEndTime" placeholder="如：20:30" />
		</div>
		<div class="nearby-pop-opt">
			<a href="javascript:;" onclick="opUpnearby();">取消</a>
			<a onclick="upNearbyFacilities();" href="javascript:void(0);">修改</a>
		</div>
	</div>
	<div class="sort-edit" id="maintenceTable" style="display: none;margin: 0 auto;position: fixed;">
		<div class="adduse-item">
			<span>快店类型</span>
			<input class="textLength" type="text"   id="fastShopTypeUpdate" />
		</div>
		<div class="adduse-item">
			<span>分类版本</span>
			<input class="textLength" type="text"  id="categoryVersionUpdate" />
		</div>
		<div class="adduse-item">
			<span>分类排序</span>
			<input type="text"  id="sort_order_update" placeholder="如：3" />
		</div>
		<div class="sort-save">
			<a href="javascript:void(0);" onclick="fastUpdateHide();">取消</a>
			<a href="javascript:void(0);" onclick="fastUpdateSubmit(2);">保存</a>
		</div>
	</div>
</body>
</html>