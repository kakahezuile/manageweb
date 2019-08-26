<%@page import="com.xj.bean.ShopType"%>
<%@page import="com.xj.bean.Services"%>
<%@page import="com.xj.utils.PropertyTool"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<meta name="keywords" content="小间物业,小间物业管理系统" />
<meta name="Description" content="小间物业,小间物业管理系统" />
<title>签约店家_小间物业管理系统</title>
<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script src="${pageContext.request.contextPath }/js/signed.js"></script>
<script type="text/javascript">
var signedThisPageNum = 1;
var signedThisPageSize = 10;
var signedIsgoing = 0;
var signedIsreview = 0;
var signedEnded = 0;
var signedSum = 0;
var signedPageSize = 0;
var signedPageNum = 0;
var signedPageFirst = 0;
var signedPageLast = 0;
var signedPageNext = 0;
var signedPrev = 0;
var serviceMap = {};

function getShopsList(signed_text){
	var sort = "";
	var phone = "";
	var status = "";
	var authCode = "";
	var pageList = "";
	var shopName = "";
	var qianyue_sum = 0;
	var shopId = 0;
	var path = "";
	if(signed_text == ""){
		path = "<%= path%>/api/v1/communities/"+communityId+"/shops/findShopsById?pageNum="+signedThisPageNum+"&pageSize="+signedThisPageSize;
		//path = "<%= path%>/api/v1/communities/"+communityId+"/shops/keyword/?pageNum="+signedThisPageNum+"&pageSize="+signedThisPageSize;
	}else{
		path = "<%= path%>/api/v1/communities/"+communityId+"/shops/keyword/"+signed_text+"?pageNum="+signedThisPageNum+"&pageSize="+signedThisPageSize;
	}
	
	var normalSum = 0;
	var blockSum = 0;
	var suspendSum = 0;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			pageList = data.pageData;
			signedSum = data.rowCount; // 总条数
			signedPageFirst = data.first; // 首页
			signedPageLast = data.last; // 尾页
			signedPageNext = data.next; // 下一页
			signedPageNum = data.num; // 当前页
			signedPrev = data.prev; // 上一页
			signedPageSize = data.pageCount; // 总页数
			
			$("#signedPageNum").html(signedPageNum);
			$("#signedPageSize").html(signedPageSize);
			$("#signedSum").html(signedSum);
			for(var i = 0 ; i < pageList.length ; i++){
				if(pageList[i].status == "normal"){
					normalSum++;
				}else if(pageList[i].status == "block"){
					blockSum++;
				}else{
					suspendSum++;
				}
			}
			signedSumLoaing(normalSum, suspendSum, blockSum);
			
			$("#singed-list-ul").find("li").remove();
			for(var i = 0 ; i < pageList.length ; i++){
				qianyue_sum = i;
				sort = pageList[i].sort;
				status = pageList[i].status;
				authCode = pageList[i].authCode;
				shopName = pageList[i].shopName;
				phone = pageList[i].phone;
				shopId = pageList[i].shopId;
				addLiForSingedListUl(sort, phone, status, authCode, shopName, qianyue_sum , shopId);
			}
			document.getElementById("findNoneWordShops").value="getShopsList";
		}
	});
}
function findNoneWordShops(){
	var sort = "";
	var phone = "";
	var status = "";
	var authCode = "";
	var pageList = "";
	var shopName = "";
	var qianyue_sum = 0;
	var shopId = 0;
	var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/findNoneWordShops?pageNum="+signedThisPageNum+"&pageSize="+signedThisPageSize;
	var normalSum = 0;
	var blockSum = 0;
	var suspendSum = 0;
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			pageList = data.pageData;
			signedSum = data.rowCount; // 总条数
			signedPageFirst = data.first; // 首页
			signedPageLast = data.last; // 尾页
			signedPageNext = data.next; // 下一页
			signedPageNum = data.num; // 当前页
			signedPrev = data.prev; // 上一页
			signedPageSize = data.pageCount; // 总页数
			$("#signedPageNum").html(signedPageNum);
			$("#signedPageSize").html(signedPageSize);
			$("#signedSum").html(signedSum);
			for(var i = 0 ; i < pageList.length ; i++){
				if(pageList[i].status == "normal"){
					normalSum++;
				}else if(pageList[i].status == "block"){
					blockSum++;
				}else{
					suspendSum++;
				}
			}
			signedSumLoaing(normalSum, suspendSum, blockSum);
			
			$("#singed-list-ul").find("li").remove();
			for(var i = 0 ; i < pageList.length ; i++){
				qianyue_sum = i;
				sort = pageList[i].sort;
				status = pageList[i].status;
				authCode = pageList[i].authCode;
				shopName = pageList[i].shopName;
				phone = pageList[i].phone;
				shopId = pageList[i].shopId;
				addLiForSingedListUl(sort, phone, status, authCode, shopName, qianyue_sum , shopId);
			}
			document.getElementById("findNoneWordShops").value="findNoneWordShops";
		}
	});
}
function addLiForSingedListUl(sort,phone,status,authCode,shopName , qianyue_sum , shopId){
	sort = parseInt(sort);
	var sortName = serviceMap[sort];
	var singedUl = $("#singed-list-ul");
	if(status == "suspend"){
		if(qianyue_sum % 2 == 0){
			singedUl.append("<li class=\"odd disable\"><span>"+sortName+"</span><span class=\"signed-shop-name\"><b>验证码 </b>"+authCode+"</span><span>未开通服务</span><a href=\"javascript:;\" class=\"red-button signed-opt\">屏蔽</a></li>");
		}else{
			singedUl.append("<li class=\"even disable\"><span>"+sortName+"</span><span class=\"signed-shop-name\"><b>验证码 </b>"+authCode+"</span><span>未开通服务</span><a href=\"javascript:;\" class=\"red-button signed-opt\">屏蔽</a></li>");
		}
	}else if(status == "normal"){
		if(qianyue_sum % 2 == 0){
			singedUl.append("<li class=\"odd\"><span>"+sortName+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+shopName+"</b> "+phone+"</a><span id=\"signed_spanid_"+shopId+"\">已开通服务</span><a id=\"signed_aid_"+shopId+"\" href=\"javascript:;\" onclick=\"updateShopsType("+shopId+",'block')\" class=\"red-button signed-opt\">屏蔽</a>");
		}else{
			singedUl.append("<li class=\"even\"><span>"+sortName+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+shopName+"</b> "+phone+"</a><span id=\"signed_spanid_"+shopId+"\">已开通服务</span><a id=\"signed_aid_"+shopId+"\" href=\"javascript:;\" onclick=\"updateShopsType("+shopId+",\'block\')\" class=\"red-button signed-opt\">屏蔽</a>");
		}
	}else{
		if(qianyue_sum % 2 == 0){
			singedUl.append("<li class=\"odd\"><span>"+sortName+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+shopName+"</b> "+phone+"</a><span id=\"signed_spanid_"+shopId+"\">已屏蔽</span><a id=\"signed_aid_"+shopId+"\" href=\"javascript:;\" onclick=\"updateShopsType("+shopId+",\'normal\')\" class=\"green-button signed-opt\">开启</a>");
		}else{
			singedUl.append("<li class=\"even\"><span>"+sortName+"</span><a href=\"javascript:;\" class=\"signed-shop-name\"><b>"+shopName+"</b> "+phone+"</a><span id=\"signed_spanid_"+shopId+"\">已屏蔽</span><a id=\"signed_aid_"+shopId+"\" href=\"javascript:;\" onclick=\"updateShopsType("+shopId+",\'normal\')\" class=\"green-button signed-opt\">开启</a>");
		}
	}
}

function updateShopsType(id,status){
	if(status == "block"){
		$("#signed_spanid_"+id+"").html("已屏蔽");
		$("#signed_aid_"+id+"").html("开通");
		$("#signed_aid_"+id+"").removeClass();
		$("#signed_aid_"+id+"").addClass("green-button signed-opt");
		var sum = $("#signed_block_sum").html();
		$("#signed_block_sum").html(parseInt(sum)+1);
		var sum2 = $("#signed_normal_sum").html();
		$("#signed_normal_sum").html(parseInt(sum2)-1);
		
		$("#signed_aid_"+id+"").attr("onclick", "updateShopsType("+id+",'normal')");
	}else if(status == "normal"){
		$("#signed_spanid_"+id+"").html("已开通服务");
		$("#signed_aid_"+id+"").html("屏蔽");
		$("#signed_aid_"+id+"").removeClass();
		$("#signed_aid_"+id+"").addClass("red-button signed-opt");
		var sum = $("#signed_normal_sum").html();
		$("#signed_normal_sum").html(parseInt(sum)+1);
		var sum2 = $("#signed_block_sum").html();
		$("#signed_block_sum").html(parseInt(sum2)-1);
		
		$("#signed_aid_"+id+"").attr("onclick", "updateShopsType("+id+",'block')");
	}

	var path = "<%= path%>/api/v1/communities/"+communityId+"/shops/"+id;
	var myJson = "{ \"status\": \""+status+"\"}";
	$.ajax({
		url: path,
		type: "PUT",
		data: myJson,
		dataType:'json'
	});
}

function signedSumLoaing(normalSum,suspendSum,blockSum){
	$("#signed_suspend_sum").html(suspendSum);
	$("#signed_normal_sum").html(normalSum);
	$("#signed_block_sum").html(blockSum);
}

function signed_search(){
	var signed_search_text = $("#signed_search_text").val();
	if(signed_search_text != "" && signed_search_text != null){
		getShopsList(signed_search_text);
	}else{
		getShopsList("");
	}
}

function getServiceCategoryList(){
	var path = "<%= path%>/api/v1/communities/shoptype";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			for(var i = 0 ; i < data.info.length ; i++){
				serviceMap[parseInt(data.info[i].shopTypeId)] = data.info[i].shopTypeName+"";
			}
		}
	});
}
function getMySignedList(num){
	if(num == -1){ // 上一页
		if(signedThisPageNum != 1){
			signedThisPageNum = signedPrev;
		}else{
			alert("已经是第一页了");
			return;
		}
	}else if(num == -2){ // 下一页
		if(signedThisPageNum < signedPageSize){
			signedThisPageNum = signedPageNext;
		}else{
			alert("已经是最后一页了");
			return;
		}
	}else if(num == -3){ // 首页
		if(signedThisPageNum != signedPageFirst){
			signedThisPageNum = signedPageFirst;
		}else{
			alert("已经是首页了");
			return;
		}
	}else if(num == -4){ // 尾页
		if(signedThisPageNum != signedPageLast){
			signedThisPageNum = signedPageLast;
		}else{
			alert("已经是尾页了");
			return;
		}
	}
	var fnws=document.getElementById("findNoneWordShops").value;
	if(fnws=="findNoneWordShops"){
		findNoneWordShops();
	}else if(fnws=="getShopsList"){
		signed_search();
	}
}
var serviceCategoryData;
function addAndDiminish(type){

<% List<ShopType> listShopType = (List<ShopType>)session.getAttribute("listShopType"); %> 
<% Iterator<ShopType> it = listShopType.iterator(); %>
<% while(it.hasNext()){ %>
		<% ShopType shopType = it.next(); %>
		if(Math.abs(type) == "<%= shopType.getDivType() %>"){
			var obj = $("#<%= shopType.getInputId()%>");
			if(obj.val() == "" || obj.val() == "0"){
				if(parseInt(type) > 0){
					obj.attr("value", "1");
				}else{
					obj.attr("value", "0");
				}
			}else{
				if(parseInt(type) > 0){
					obj.attr("value", parseInt(obj.val()) + 1);
				}else{
					obj.attr("value", parseInt(obj.val()) - 1);
				}
			}
		}
<% }%>
}

function getServiceCategory(){
	var path = "<%= path%>/api/v1/communities/shoptype";
	$.ajax({
		url: path,
		type: "GET",
		dataType:'json',
		success:function(data){
			data = data.info;
			addServiceCategory(data);
		}
	});
}
var serviceList = [];
function addServiceCategory(data){
	serviceCategoryData = data;
	var serviceCategoryUl = $("#service_category_ul");
	serviceCategoryUl.find("li").remove();
	for(var i = 0 ; i < data.length ; i++){
		serviceCategoryUl.append("<li><span>"+data[i].shopTypeName+"</span> "+ 
			" <a href=\"javascript:;\" onclick=\"addAndDiminish(-"+data[i].divType+");\" ><img alt=\"-\" src=\"${pageContext.request.contextPath }/images/signed/creat-sub.png\"/></a> " +
				"	<input type=\"text\" id=\""+data[i].inputId+"\" placeholder=\"0\" value=\"\"/> " +
					" <a href=\"javascript:;\"  onclick=\"addAndDiminish("+data[i].divType+");\" ><img alt=\"-\" src=\"${pageContext.request.contextPath }/images/signed/creat-add.png\"/></a> "+
				"</li>");
		serviceList[i] = data[i].inputId;
	}
}

function createShops(){
	var path = "<%= path%>/api/v1/communities/"+communityId+"/shops";
	var jsonArray = [];
	<% Iterator<ShopType> it2 = listShopType.iterator(); %>
	<% while(it2.hasNext()){  %>
		<% ShopType shopType = it2.next(); %>
		var obj = $("#<%= shopType.getInputId()%>");
	if (obj.val() != "" && obj.val() != "0") {
		var myNum = parseInt(obj.val());
		for ( var i = 0; i < myNum; i++) {
			var myJson = {
				"sort" : 0,
				"status" : "suspend"
			};
			myJson.sort =<%= shopType.getShopTypeId() %>;
			jsonArray.push(myJson);
		}
	}
	<% }%>
	jsonArray = JSON.stringify(jsonArray);
	$.post(path, jsonArray, function(data) {
		if (data.status = "yes") {
			getServiceCategoryList();
			getShopsList("");
			getServiceCategory();
		}
	}, "json");
	closeWindow();
}
</script>
</head>

<body>
	<input id="findNoneWordShops" type="hidden"/>
	<div class="content clearfix">
		<jsp:include flush="true" page="./shop-manage-left.jsp">
			<jsp:param name="module" value="signed"/>
		</jsp:include>
		<div class="express-right-body">
			<div class="signed-total">
				<span><b id="signed_normal_sum" class="servicing">loading...</b>正在服务</span>
				<span><b id="signed_suspend_sum" class="no-servicing">loading...</b>未开户服务</span>
				<span><b id="signed_block_sum" class="shielding">loading...</b>被屏蔽</span>
			</div>
			<div class="signed-search">
				<input type="text" class="signed-search-input" value="" id="signed_search_text" placeholder="搜索店家    名称/电话" />
				<a href="javascript:;" onclick="signed_search()" class="blue-button">搜索</a>
				<a href="javascript:creatShop();" class="blue-button">创建新店家 +</a>
				<a href="javascript:;" onclick="findNoneWordShops();" class="blue-button">查看所有未开通的验证码</a>
			</div>
			<div class="signed-list">
				<ul id="singed-list-ul"></ul>
			</div>
			<div class="divide-page" style="float:left;width:100%;height:50px;clear:both;text-align:center;">
				<a href="javascript:void(0);" onclick="getMySignedList(-3);">首页</a>
				<a href="javascript:void(0);" onclick="getMySignedList(-1);">上一页</a>
				当前第
				<font id="signedPageNum"></font> 页 共
				<font id="signedPageSize"></font> 页 共
				<font id="signedSum"></font> 条
				<a href="javascript:void(0);" onclick="getMySignedList(-2);">下一页</a>
				<a href="javascript:void(0);" onclick="getMySignedList(-4);">尾页</a>
			</div>
			<div class="blank-background" style="display: none;"></div>
			<div class="signed-creat-box" style="display: none;top:40%;">
				<div class="signed-creat-title">创建新的店家<a href="javascript:closeWindow();" title="关闭">&nbsp;</a></div>
				<div class="signed-content">
					<ul id="service_category_ul">
						<li>
							<span>外卖</span>
							<a href="javascript:;" onclick="addAndDiminish(-1);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(1);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>便利店</span>
							<a href="javascript:;" onclick="addAndDiminish(-2);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(2);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>洗衣</span>
							<a href="javascript:;" onclick="addAndDiminish(-3);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(3);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>送水</span>
							<a href="javascript:;" onclick="addAndDiminish(-4);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(4);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>维修</span>
							<a href="javascript:;" onclick="addAndDiminish(-5);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(5);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>家政</span>
							<a href="javascript:;" onclick="addAndDiminish(-6);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(6);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>便利店</span>
							<a href="javascript:;" onclick="addAndDiminish(-7);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" id="signed_bianlidian" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(7);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>洗衣</span>
							<a href="javascript:;" onclick="addAndDiminish(-8);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" id="signed_xiyi" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(8);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>送水</span>
							<a href="javascript:;" onclick="addAndDiminish(-9);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" id="signed_songshui" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(9);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>维修</span>
							<a href="javascript:;" onclick="addAndDiminish(-10);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" id="signed_weixiu" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(10);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
						<li>
							<span>家政</span>
							<a href="javascript:;" onclick="addAndDiminish(-11);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-sub.png" /></a>
							<input type="text" id="signed_jiazheng" placeholder="0" value="" />
							<a href="javascript:;" onclick="addAndDiminish(11);"><img alt="-" src="${pageContext.request.contextPath }/images/signed/creat-add.png" /></a>
						</li>
					</ul>
				</div>
				<a class="signed-creat-button" href="javascript:void(0);" onclick="createShops();">创建</a>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
getServiceCategoryList();
getShopsList("");
getServiceCategory();
</script>
</html>