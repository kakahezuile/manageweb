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
<title>维修报价表_小间物业管理系统</title>
<link href="${pageContext.request.contextPath }/favicon.ico?version=<%=versionNum %>" type="image/x-icon" rel="shortcut icon">
<link href="${pageContext.request.contextPath }/css/public.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/css/computer_device.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<link href="${pageContext.request.contextPath }/css/ie8orlow.css?version=<%=versionNum %>" rel="stylesheet" type="text/css">
<![endif]-->
<script type="text/javascript">

	$(function(){
	//	$("#repair_price_ul > li a").click(function(){
	//		alert();
	//	});
	});
	
	var repairPricePageNum = 1;
	
	var repairPricePageSize = 10;
	
	var repairPriceCatId = 0;
	
		var repairPriceThisPageNum = 1;
	
	var repairPriceThisPageSize = 10;
	
	var repairPriceIsgoing = 0;
	
	var repairPriceIsreview = 0;
	
	var repairPriceEnded = 0;
	
	var repairPriceSum = 0;
	
	var repairPricePageCount;
	
	var repairPricePageFirst = 0;
	
	var repairPricePageLast = 0;
	
	var repairPricePageNext = 0;
	
	var repairPricePrev = 0;
	
	
	function repairUlAclick(myThis,value){
		repairPriceCatId = value;
		$("#repair_price_ul > li a").attr("class","");
		$(myThis).attr("class","select");
		repairPricegetShopItemList(value);
		
	}

	function repairPriceGetItemCategory(type){
	
		var path = "<%=path%>/api/v1/communities/{communityId}/users/"+myUserName+"/itemCategories?q="+type;
	
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				data = data.info;
				$("#repair_price_ul").html("");
				var len = data.length;
				for(var i = 0 ; i < len ; i++){
					
					if(i == 0){
						repairPriceCatId = data[i].catId;
						$("#repair_price_ul").append("<li><a class=\"select\" onclick=\"repairUlAclick(this,"+data[i].catId+");\" href=\"javascript:;\">"+data[i].catName+"</a></li>");
						repairPricegetShopItemList(data[i].catId);
						
					}else{
						$("#repair_price_ul").append("<li><a href=\"javascript:;\" onclick=\"repairUlAclick(this,"+data[i].catId+");\" >"+data[i].catName+"</a></li>");
					}
					
				}
			},
			error : function(er) {
				alert(er);
			}
		});
	}
	
	function repairPriceAddClick(){
		$("#add-price-box").attr("style","display:block");
		$("#upload-master-face-bg").attr("style","display:block");
		
	}
	
	function repairPriceCloseClick(){
		$("#add-price-box").attr("style","display:none");
		$("#upload-master-face-bg").attr("style","display:none");
	}
	
	function repairPricegetShopItemList(catId){
	
		repairPriceCatId = catId;
	
		var path = "<%= path%>/api/v1/communities/${communityId}/shops/quotation/"+catId+"?pageNum="+repairPricePageNum+"&pageSize="+repairPricePageSize+"&status=all";
		
		$("#repair_price_table").html("");
		$("#repair_price_table").append("<tr><th>&nbsp;</th><th>名称</th><th>价格</th><th class=\"repair-price-status\">是否启用</th></tr>");
		$.ajax({
			url : path,
			type : "GET",
			dataType : 'json',
			success : function(data) {
				
				repairPriceSum = data.info.rowCount; // 总条数
				
				repairPricePageFirst = data.info.first; // 首页
				
				repairPricePageLast = data.info.last; // 尾页
				
				repairPricePageNext = data.info.next; // 下一页
				
				repairPricePageNum = data.info.num; // 当前页
				
				repairPricePrev = data.info.prev; // 上一页
				
				repairPricePageCount = data.info.pageCount;
				
				$("#repairPricePageNum").html(repairPricePageNum);
				
				$("#repairPricePageSize").html(repairPricePageCount);
				
				$("#repairPriceSum").html(repairPriceSum);
				
				data = data.info.pageData;
				
				var len = data.length;
				for(var i = 0 ; i < len ; i++){
					var serviceName = data[i].serviceName;
					var price = data[i].currentPrice;
					var status = data[i].status;
					if(status == "available"){
						$("#repair_price_table").append("<tr><td><input type=\"checkbox\" name=\"catName\" value=\""+catId+","+serviceName+"\"/></td><td>"+serviceName+"</td><td>"+price+"</td><td class=\"repair-price-status\"><a class=\"stop\" onclick=\"repairPriceUpdate("+catId+",'"+serviceName+"','unavailable');\" href=\"javascript:;\">点击停用</a></td></tr>");
					}else{
						$("#repair_price_table").append("<tr><td><input type=\"checkbox\" name=\"catName\" value=\""+catId+","+serviceName+"\"/></td><td>"+serviceName+"</td><td>"+price+"</td><td class=\"repair-price-status\"><a class=\"start\"  onclick=\"repairPriceUpdate("+catId+",'"+serviceName+"','available');\" href=\"javascript:;\">点击启用</a></td></tr>");
					}
					
				}
			},
			error : function(er) {
				alert(er);
			}
		});
	}
	
	function repairPriceSave(){
	  
		var path = "<%= path%>/api/v1/communities/${communityId}/shops/quotation";
		
		var myJson = {
			"catId" : 0 ,
			"serviceName" : "" ,
			"currentPrice" : "" , 
			"status" : "available"
		};
		myJson.catId = repairPriceCatId;
		myJson.serviceName = $("#repair_price_name").val();
		myJson.currentPrice = $("#repair_price_price").val();
		$.ajax({
			url : path,
			type : "POST",
			data : JSON.stringify(myJson) ,
			dataType : 'json',
			success : function(data) {
				if(data.status == "yes"){
				alert("成功");
					//data = data.info.pageData;
					repairPriceCloseClick();
					repairPricegetShopItemList(repairPriceCatId);
					document.getElementById("add-price-box").style.display="none";
	              document.getElementById("upload-master-face-bg").style.display="none";
				}else if(data.status == "no"){
				document.getElementById("add-price-box").style.display="none";
	                document.getElementById("upload-master-face-bg").style.display="none";
				}else if(data.status == "isEmpty"){
					 alert("当前内容已存在");
					document.getElementById("add-price-box").style.display="none";
	                  document.getElementById("upload-master-face-bg").style.display="none";
					
				}
				
			},
			error : function(er) {
				alert(er);
			}
		});
	}
	
	function repairPriceUpdate(catId , serviceName , status){
	
		var path = "<%= path%>/api/v1/communities/${communityId}/shops/quotation/"+catId;
		
		var myJson = {
			"catId" : catId ,
			"serviceName" : serviceName ,
			"status" : status
		};
		
		$.ajax({
			url : path,
			type : "PUT",
			data : JSON.stringify(myJson) ,
			dataType : 'json',
			success : function(data) {
				
				if(data.status == "yes"){
					repairPricegetShopItemList(catId);
				}
			},
			error : function(er) {
				alert(er);
			}
		});
	}
	
	function delQuotation(){
	var catName=document.getElementsByName("catName");
	for ( var i = 0; i < catName.length; i++) {
	 if(catName[i].checked == true) {
	  var cn=	catName[i].value;
		  var caN=cn.split(",");
		    repairPriceUpdate(caN[0] , caN[1] , "delete");
      }
	  
	}
		
	
	}
	function getRepairPriceList(num){
		if(num == -1){ // 上一页
			if(repairPricePageNum != 1){
				repairPricePageNum = repairPricePrev;
				repairPricegetShopItemList(repairPriceCatId);
			}
			
		}else if(num == -2){ // 下一页
			if(repairPricePageNum < repairPricePageSize){
				repairPricePageNum = repairPricePageNext;
				repairPricegetShopItemList(repairPriceCatId);
			}
			
		}else if(num == -3){ // 首页
			if(repairPricePageNum != repairPricePageFirst){
				repairPricePageNum = repairPricePageFirst;
				repairPricegetShopItemList(repairPriceCatId);
			}
		}else if(num == -4){ // 尾页
			if(repairPricePageNum != repairPricePageLast){
				repairPricePageNum = repairPricePageLast;
				repairPricegetShopItemList(repairPriceCatId);
			}
		}
	}
</script>
</head>
<body>
	
	<section>
		<div class="content clearfix">
			<jsp:include flush="true" page="../public/service-left.jsp"></jsp:include>
			<div class="repair-price-body">
				<div class="repair-title-line">
					<p class="repair-price-title">维修报价表</p>
				</div>
				<div class="repair-price-type clearfix">
					<ul id="repair_price_ul">
						<li><a class="select" href="javascript:;">强电</a></li>
						<li><a href="javascript:;">弱电</a></li>
						<li><a href="javascript:;">上下水</a></li>
						<li><a href="javascript:;">综合</a></li>
					</ul>
					<div class="repair-price-opt">
						<a href="javascript:;" class="del" onclick="delQuotation();">删除条目</a>
						<a href="javascript:;" class="add" onclick="repairPriceAddClick();">添加条目</a>
					</div>
				</div>
				<div class="repair-price-list">
					<table id="repair_price_table">
						<tr>
							<th>&nbsp;</th>
							<th>名称</th>
							<th>价格</th>
							<th class="repair-price-status">是否启用</th>
						</tr>
						<tr>
							<td><input type="checkbox"/></td>
							<td>换插座</td>
							<td>40</td>
							<td class="repair-price-status"><a class="start" href="javascript:;">点击启用</a></td>
						</tr>
						<tr>
							<td><input type="checkbox"/></td>
							<td>换插座</td>
							<td>40</td>
							<td class="repair-price-status"><a class="stop" href="javascript:;">点击停用</a></td>
						</tr>
					</table>
				</div>
	<div style="float: left; width:  100% ; height: 50px; clear: both; text-align: center;">
	<a href="javascript:void(0);" onclick="getRepairPriceList(-3);">首页</a>
	<a href="javascript:void(0);" onclick="getRepairPriceList(-1);">上一页</a>
	当前第 <font id="repairPricePageNum"></font> 页
	共 <font id="repairPricePageSize"></font> 页
	共 <font id="repairPriceSum"></font> 条
	
	<a href="javascript:void(0);" onclick="getRepairPriceList(-2);">下一页</a>
	<a href="javascript:void(0);" onclick="getRepairPriceList(-4);">尾页</a>
	</div>
			</div>
		</div>
	</section>
	<div class="add-price-box" id="add-price-box" style="display: none;">
		<div class="add-price-box-title">添加维修项目<a href="javascript:;" title="关闭" onclick="repairPriceCloseClick();">&nbsp;</a></div>
		<div class="add-price-item">
			<span>维修项目</span><input type="text" value="" id="repair_price_name" placeholder="例：接电源"/>
		</div>
		<div class="add-price-item">
			<span>项目价格</span><input type="text" value="" id="repair_price_price" placeholder="例：20"/>
		</div>
		<div class="add-price-button">
			<a href="javascript:;" onclick="repairPriceSave();">添加</a>
		</div>
	</div>
	<div class="upload-master-face-bg" id="upload-master-face-bg" style="display: none;">&nbsp;</div>
</body>
<jsp:include flush="true" page="../public/footer.jsp"></jsp:include>
</html>